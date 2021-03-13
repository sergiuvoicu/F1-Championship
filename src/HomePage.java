import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;


public class HomePage extends JFrame{
	
	// Camp pentru memorarea butonului apasat
	private String type;
	
	HomePage(Connection connection, boolean isAdmin){
		
		// Setarea frame-ului
		setSize(800,600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Campionat F1");
		
		// Centrarea frame-ului
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		type = new String();
		
		// Setarea panoului
		JPanel panel = new JPanel();
		panel.setBackground(new Color(165, 202, 241));
		panel.setLayout(null);
		
		// Setarea barei de meniuri
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 783, 20);
		
		// Initializarea meniurilor
		JMenu menuAdmin = new JMenu("Admin");
		JMenu menuUser1 = new JMenu("UserMenu1");
		JMenu menuUser2 = new JMenu("UserMenu2");
		
		// Initializarea butoanelor meniului Admin
		JMenuItem menuItemIn = new JMenuItem("Insert");
		JMenuItem menuItemUp = new JMenuItem("Update");
		JMenuItem menuItemDl = new JMenuItem("Delete");
		
		// Initializarea butoanelor meniului UserMenu1
		JMenuItem menuItemJ1 = new JMenuItem("JOIN1");
		JMenuItem menuItemJ2 = new JMenuItem("JOIN2");
		JMenuItem menuItemJ3 = new JMenuItem("JOIN3");
		JMenuItem menuItemJ4 = new JMenuItem("JOIN4");
		JMenuItem menuItemJ5 = new JMenuItem("JOIN5");
		JMenuItem menuItemJ6 = new JMenuItem("JOIN6");
		
		// Initializarea butoanelor meniului UserMenu2
		JMenuItem menuItemSQ1 = new JMenuItem("Sub-Query1");
		JMenuItem menuItemSQ2 = new JMenuItem("Sub-Query2");
		JMenuItem menuItemSQ3 = new JMenuItem("Sub-Query3");
		JMenuItem menuItemSQ4 = new JMenuItem("Sub-Query4");
		
		// Adaugarea butoanelor la meniul Admin
		menuAdmin.add(menuItemIn);
		menuAdmin.add(menuItemUp);
		menuAdmin.add(menuItemDl);
		
		// Adaugarea butoanelor la meniul UserMenu1
		menuUser1.add(menuItemJ1);
		menuUser1.add(menuItemJ2);
		menuUser1.add(menuItemJ3);
		menuUser1.add(menuItemJ4);
		menuUser1.add(menuItemJ5);
		menuUser1.add(menuItemJ6);
		
		// Adaugarea butoanelor la meniul UserMenu2
		menuUser2.add(menuItemSQ1);
		menuUser2.add(menuItemSQ2);
		menuUser2.add(menuItemSQ3);
		menuUser2.add(menuItemSQ4);
		
		// Adaugarea meniurilor la bara de meniuri
		menuBar.add(menuAdmin);
		menuBar.add(menuUser1);
		menuBar.add(menuUser2);
		
		// Initializarea butoanelor de logout si exit
		JMenu logout = new JMenu("Logout");
		JMenu exit = new JMenu("Exit");
		
		// Adaugarea butoanelor de logout si exit in partea dreapta a barei de meniuri
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(logout);
		menuBar.add(exit);
		
		// Lista drop-down cu tabelele accesibile
		String[] tables = {"...","Echipe", "Piloti", "Masini", "Ingineri", "Curse", "PilotiCurse"};
		JComboBox<String> tableList = new JComboBox<String>(tables);
		tableList.setBounds(170,40,200,20);
		tableList.setVisible(false);
		
		// Lista drop-down pentru prima interogare simpla
		JComboBox <String> attList = new JComboBox<String>();
		attList.setVisible(false);
		
		// Zona text pentru prima interogare complexa
		JTextField attTF = new JTextField("1998-01-01");
		attTF.setVisible(false);
		
		// Afisaje
			JLabel intro = new JLabel();
			intro.setBounds(20,40,150,20);
			intro.setVisible(false);
			
			JLabel content = new JLabel("Cu campurile:   ");
			content.setBounds(20,80,780,20);
			content.setVisible(false);
			
			JLabel whereLabel = new JLabel("Unde: ");
			whereLabel.setVisible(false);
		
		// Zone text pentru introducerea si conditionarea datelor
			JTextField fields= new JTextField("");
			fields.setBounds(20, 110, 400, 20);
			fields.setVisible(false);
			
			JTextField whereField= new JTextField("");
			whereField.setVisible(false);
		
		// Butoane de insertie si select
			JButton confirm = new JButton("Confirm");
			JButton confirmQuery = new JButton("Confirm");
		
		// Tabele pentru executia interogarilor 
		JTable table= new JTable();
		JTable proofTable= new JTable();
		
		// Scrollbar-uri pentru tabele
		JScrollPane scbAr= new JScrollPane();
		JScrollPane scrollableArea = new JScrollPane();
		
		// Restrictionarea accesului la meniul Admin pentru useri
		if(!isAdmin)
			menuAdmin.setEnabled(false);
		
		// Handler ce afiseaza componentele corespunzatoare introducerii datelor in baza de date
		menuItemIn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				type = "INSERT";
				intro.setText("Introduceti date in:");
				intro.setBounds(20,40,150,20);
				intro.setVisible(true);
				tableList.setVisible(true);
				tableList.setSelectedIndex(0);
				content.setVisible(true);
				content.setText("Cu campurile:   ");
				fields.setVisible(true);
				whereField.setVisible(false);
				whereLabel.setVisible(false);
				confirm.setBounds(170,140,100,30);
				confirm.setVisible(true);
				confirmQuery.setVisible(false);
				scrollableArea.setVisible(false);
				scbAr.setVisible(false);
				attList.setVisible(false);
				attTF.setVisible(false);

			}
		});
		
		// Handler ce afiseaza componentele corespunzatoare actualizarii datelor din baza de date
		menuItemUp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				type = "UPDATE";
				intro.setText("Actualizati date din:");
				intro.setBounds(20,40,150,20);
				intro.setVisible(true);
				tableList.setVisible(true);
				tableList.setSelectedIndex(0);
				content.setVisible(true);
				content.setText("Cu campurile:   ");
				fields.setVisible(true);
				whereLabel.setBounds(20, 160, 100, 20);
				whereLabel.setVisible(true);
				whereField.setBounds(20, 190, 400, 20);
				whereField.setVisible(true);
				confirm.setBounds(170,220,100,30);
				confirm.setVisible(true);
				confirmQuery.setVisible(false);
				scrollableArea.setVisible(false);
				scbAr.setVisible(false);
				attList.setVisible(false);
				attTF.setVisible(false);
				
			}
		});
		
		// Handler ce afiseaza componentele corespunzatoare stergerii datelor din baza de date
		menuItemDl.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				type = "DELETE";
				intro.setText("Stergeti date din:");
				intro.setBounds(20,40,150,20);
				intro.setVisible(true);
				tableList.setVisible(true);
				tableList.setSelectedIndex(0);
				content.setVisible(true);
				content.setText("Cu campurile:   ");
				fields.setVisible(false);
				whereLabel.setBounds(20, 120, 400, 20);
				whereLabel.setVisible(true);
				whereField.setBounds(20, 150, 400, 20);
				whereField.setVisible(true);
				confirm.setBounds(170,180,100,30);
				confirm.setVisible(true);
				confirmQuery.setVisible(false);
				scrollableArea.setVisible(false);
				scbAr.setVisible(false);
				attList.setVisible(false);
				attTF.setVisible(false);
			}
		});
		
		// Handler ce afiseaza datele din tabelul in care urmeaza sa fie introduse/actualizate/sterse date
		tableList.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				String tableSt = (String)tableList.getSelectedItem();
				if(tableSt != "..."){
					try{
						
						String sql = new String();
						
						// Afisare speciala pentru tabela de legatura
						int p;
						if(tableSt != "PilotiCurse"){
							p=2;
							sql = "SELECT * FROM " + tableSt + ";";
						}
						else{
							p=1;
							sql = "SELECT P.PilotID, P.Nume, P.Prenume, C.CursaID, C.TaraGazda, C.DataCursa, CP.PuncteObtinute FROM PilotiCurse CP "
										+ " JOIN Piloti P on CP.PilotID = P.PilotID "
										+ " JOIN Curse C on CP.CursaID = C.CursaID;";
						}
						
						Statement statement= connection.createStatement();
						ResultSet result=statement.executeQuery(sql);
						ResultSetMetaData metaData = result.getMetaData();
						
						Vector<String> v= new Vector<String>();

						
						
						// Afisarea datelor in tabel
						for(int i=p;i<= metaData.getColumnCount();i++)
							v.add(metaData.getColumnName(i));
						
						DefaultTableModel model = new DefaultTableModel(v, 0);
																		
						while(result.next())
						{
							Vector<String> v1= new Vector<String>();
							for(int i=p;i<=metaData.getColumnCount();i++)
								v1.add(result.getString(i));
							model.addRow(v1);
						}
						table.setModel(model);
						
						if(tableSt != "PilotiCurse")
							content.setText("Cu campurile:   "+ v.toString());
						else content.setText("Cu campurile:   [PilotID, CursaID, PuncteObtinute]");
						
						// Adaugare scrollbar
						scrollableArea.setViewportView(table);
						scrollableArea.setBounds(100, 300, 600,150);
						scrollableArea.setVisible(true);
					
					}
					catch(SQLException e){
						e.printStackTrace();
					}
				}
			}
		});
		
		// Handleri ce afiseaza componentele corespunzatoare butonului apasat
			menuItemJ1.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					type = "J1";
					Vector<String> attVec = new Vector<String>();
					try{
						String sql = "SELECT * FROM Echipe;";
						Statement statement= connection.createStatement();
						ResultSet result=statement.executeQuery(sql);
						ResultSetMetaData metaData = result.getMetaData();
						while(result.next())
							attVec.add(result.getString(metaData.getColumnCount()));
						for(int i = 0; i<attVec.size();i++)
							if(attVec.lastIndexOf(attVec.elementAt(i))!=attVec.indexOf(attVec.elementAt(i)))
								attVec.removeElementAt(attVec.lastIndexOf(attVec.elementAt(i)));
						
						attList.setModel(new DefaultComboBoxModel(attVec));
						attList.setVisible(true);
						attList.setBounds(300,40,100,20);
					}
					catch(SQLException e){
						e.printStackTrace();
					}
					
					intro.setText("Afisati pilotii ce fac parte din echipe ce provin din");
					intro.setBounds(20,40,790,20);
					intro.setVisible(true);
					tableList.setVisible(false);
					content.setVisible(false);
					fields.setVisible(false);
					whereLabel.setVisible(false);
					whereField.setVisible(false);
					confirm.setVisible(false);
					confirmQuery.setBounds(20,80,100,30);
					confirmQuery.setVisible(true);
					scrollableArea.setVisible(false);
					scbAr.setVisible(false);
					attTF.setVisible(false);
				}
			});
			
			menuItemJ2.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					type = "J2";
					intro.setText("<html>Afisati Inginerii ce au in evidenta masini cu o greutate a motorului mai mica de 150KG si o turatie maxima de cel putin 14000 RPM \n"
							+ "<br/>si a caror universitate urmata este cunoscuta</html>");
					intro.setBounds(20,40,790,30);
					intro.setVisible(true);
					tableList.setVisible(false);
					content.setVisible(false);
					fields.setVisible(false);
					whereLabel.setVisible(false);
					whereField.setVisible(false);
					confirm.setVisible(false);
					confirmQuery.setBounds(20,80,100,30);
					confirmQuery.setVisible(true);
					scrollableArea.setVisible(false);
					scbAr.setVisible(false);
					attList.setVisible(false);
					attTF.setVisible(false);
				}
			});
			
			menuItemJ3.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					type = "J3";
					intro.setText("<html>Afisati numele, prenumele si numarul de titluri ale castigatorilor primelor trei locuri ale cursei din China din data de 2019-10-02,"
							+ "<br/> in ordinea descrescatoare a punctajului obtinut<html>");
					intro.setBounds(20,40,790,30);
					intro.setVisible(true);
					tableList.setVisible(false);
					content.setVisible(false);
					fields.setVisible(false);
					whereLabel.setVisible(false);
					whereField.setVisible(false);
					confirm.setVisible(false);
					confirmQuery.setBounds(20,80,100,30);
					confirmQuery.setVisible(true);
					scrollableArea.setVisible(false);
					scbAr.setVisible(false);
					attList.setVisible(false);
					attTF.setVisible(false);
				}
			});
			
			menuItemJ4.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					type = "J4";
					intro.setText("Afisati echipele care au piloti care au castigat cel putin un turneu, si cate premii detine echipa");
					intro.setBounds(20,40,790,20);
					intro.setVisible(true);
					tableList.setVisible(false);
					content.setVisible(false);
					fields.setVisible(false);
					whereLabel.setVisible(false);
					whereField.setVisible(false);
					confirm.setVisible(false);
					confirmQuery.setBounds(20,80,100,30);
					confirmQuery.setVisible(true);
					scrollableArea.setVisible(false);
					scbAr.setVisible(false);
					attList.setVisible(false);
					attTF.setVisible(false);
				}
			});
			
			menuItemJ5.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					type = "J5";
					intro.setText("Afisati numele si data nasterii pilotilor, dar si media, numarul si suma tururilor parcurse de ei in anul 2019");
					intro.setBounds(20,40,790,20);
					intro.setVisible(true);
					tableList.setVisible(false);
					content.setVisible(false);
					fields.setVisible(false);
					whereLabel.setVisible(false);
					whereField.setVisible(false);
					confirm.setVisible(false);
					confirmQuery.setBounds(20,80,100,30);
					confirmQuery.setVisible(true);
					scrollableArea.setVisible(false);
					scbAr.setVisible(false);
					attList.setVisible(false);
					attTF.setVisible(false);
				}
			});
			
			menuItemJ6.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					type = "J6";
					intro.setText("<html>Afisati locul, data, numarul de tururi, numarul de masini si media turatiei maxime a masinilor, ale curselor pentru"
							+ "<br/> cursele de cel putin 60 de tururi si cu o medie a turatiei maxime de sub 14500 RPM </html>");
					intro.setBounds(20,40,790,30);
					intro.setVisible(true);
					tableList.setVisible(false);
					content.setVisible(false);
					fields.setVisible(false);
					whereLabel.setVisible(false);
					whereField.setVisible(false);
					confirm.setVisible(false);
					confirmQuery.setBounds(20,80,100,30);
					confirmQuery.setVisible(true);
					scrollableArea.setVisible(false);
					scbAr.setVisible(false);
					attList.setVisible(false);
					attTF.setVisible(false);
				}
			});
			menuItemSQ1.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					type = "SQ1";
					intro.setText("Afisati pilotii care au mai multe puncte decat cel putin un pilot nascut dupa data:");
					intro.setBounds(20,40,790,20);
					intro.setVisible(true);
					tableList.setVisible(false);
					content.setVisible(false);
					fields.setVisible(false);
					whereLabel.setVisible(false);
					whereField.setVisible(false);
					confirm.setVisible(false);
					confirmQuery.setBounds(20,80,100,30);
					confirmQuery.setVisible(true);
					scrollableArea.setVisible(false);
					scbAr.setVisible(false);
					attList.setVisible(false);
					attTF.setVisible(true);
					attTF.setBounds(480,40,100,20);
				}
			});
			
			menuItemSQ2.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					type = "SQ2";
					intro.setText("Afisati echipele care au piloti nascuti dupa anul 1985, care au participat la minim 100 de curse");
					intro.setBounds(20,40,790,20);
					intro.setVisible(true);
					tableList.setVisible(false);
					content.setVisible(false);
					fields.setVisible(false);
					whereLabel.setVisible(false);
					whereField.setVisible(false);
					confirm.setVisible(false);
					confirmQuery.setBounds(20,80,100,30);
					confirmQuery.setVisible(true);
					scrollableArea.setVisible(false);
					scbAr.setVisible(false);
					attList.setVisible(false);
					attTF.setVisible(false);
				}
			});
			
			menuItemSQ3.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					type = "SQ3";
					intro.setText("<html>Afisati numele, prenumele, numarul de curse si media punctelor acumulate de pilotii ce au media punctelor mai mica sau egala<br/>"
							+ "decat media fiecarei echipe, sau a celor care au ingineri de cursa cu ingineri in subordine  </html>");
					intro.setBounds(20,40,790,30);
					intro.setVisible(true);
					tableList.setVisible(false);
					content.setVisible(false);
					fields.setVisible(false);
					whereLabel.setVisible(false);
					whereField.setVisible(false);
					confirm.setVisible(false);
					confirmQuery.setBounds(20,80,100,30);
					confirmQuery.setVisible(true);
					scrollableArea.setVisible(false);
					scbAr.setVisible(false);
					attList.setVisible(false);
					attTF.setVisible(false);
				}
			});
			menuItemSQ4.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					type = "SQ4";
					intro.setText("<html>Afisati masinile ce au o capacitate cilindrica cuprinsa intre 1500cm3 si 1600cm3 si sunt conduse de<br/>"
							+ "piloti ce au un numar de curse mai mare decat media numarului de curse a pilotilor ce participa in cursele<br/>"
							+ " ce au cel putin trei participanti</html>");
					intro.setBounds(20,40,790,50);
					intro.setVisible(true);
					tableList.setVisible(false);
					content.setVisible(false);
					fields.setVisible(false);
					whereLabel.setVisible(false);
					whereField.setVisible(false);
					confirm.setVisible(false);
					confirmQuery.setBounds(20,110,100,30);
					confirmQuery.setVisible(true);
					scrollableArea.setVisible(false);
					scbAr.setVisible(false);
					attList.setVisible(false);
					attTF.setVisible(false);
				}
			});
		
		
		
		// Handleri ce efectueaza delogarea, respectiv inchiderea programului pentru butoanele de logout si exit
			logout.addMouseListener(new MouseListener(){
				@Override
				public void mouseClicked(MouseEvent arg0){
					int input = JOptionPane.showConfirmDialog(null,"Sunteti sigur ca doriti sa va delogati?");
					if(input==0){
						dispose();
						Login login = new Login(connection);
					}
				}
				public void mousePressed(MouseEvent arg0){}
				public void mouseReleased(MouseEvent arg0){}
				public void mouseEntered(MouseEvent arg0){}
				public void mouseExited(MouseEvent arg0){}
			});
			
			exit.addMouseListener(new MouseListener(){
				@Override
				public void mouseClicked(MouseEvent arg0){
					int input = JOptionPane.showConfirmDialog(null,"Sunteti sigur ca doriti sa inchideti aplicatia?");
					if(input==0){
						try {
							connection.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						System.exit(0);
					}
				}
				public void mousePressed(MouseEvent arg0){}
				public void mouseReleased(MouseEvent arg0){}
				public void mouseEntered(MouseEvent arg0){}
				public void mouseExited(MouseEvent arg0){}
			});
		
		// Buton de confirmare pentru introducere/actualizare/stergere date
		confirm.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				try{
					// Statement pentru Update
					Statement slcStatement0 = connection.createStatement();
					ResultSet result0 = slcStatement0.executeQuery("SELECT * from "+tableList.getSelectedItem().toString()+";");
					ResultSetMetaData metaData0 = result0.getMetaData();
					
					String sql= new String();
					
					// Interogare Insert
					if(type.equals("INSERT") && fields.getText()!= "")
						sql="INSERT INTO "+ tableList.getSelectedItem().toString() + " VALUES ("+fields.getText()+");";
					
					// Interogare Update
					else if(type.equals("UPDATE") && fields.getText()!= "" && whereField.getText()!="")
					{
						String str = fields.getText();
						int p0;
						Vector<String> parsedStr = new Vector<String>();
						
						// Se parseaza stringul din zona text astfel incat interogarea sa ajunga in formatul NumeColoana='ValoareColoana'
						// utilizatorul fiind nevoit sa introduca doar 'ValoareColoana'
						for(int i = 2 ; i <= metaData0.getColumnCount(); i++)
						{
							p0 =  str.indexOf(',');
							if(p0 != -1)
								parsedStr.add(metaData0.getColumnName(i) + "="+ str.substring(0, p0+1));
							else parsedStr.add(metaData0.getColumnName(i) + "="+ str);
							str = str.substring(p0+1);
						}
						
						String str0 = parsedStr.elementAt(0);
						for(int i = 0; i< parsedStr.size()-1;i++)
							str0= str0+" "+parsedStr.elementAt(i+1);
						
						// Interogarea de Update
						sql="UPDATE "+ tableList.getSelectedItem().toString() + " SET "+str0+ " WHERE "+ whereField.getText()+";";
					}
					
					// Interogare Delete
					else if(type.equals("DELETE") && whereField.getText()!="")
						sql="DELETE FROM "+ tableList.getSelectedItem().toString() +" WHERE "+ whereField.getText()+";";
					
					// Executare interogare Insert/Update/Delete
					Statement statement = connection.createStatement();
					statement.executeUpdate(sql);
					
					// Afisarea datelor dupa Insert/Update/Delete - asemanator tableList
					Statement slcStatement = connection.createStatement();
					String sql0= new String();
					int p;

					if(tableList.getSelectedItem().toString() != "PilotiCurse"){
						sql0="SELECT * from "+tableList.getSelectedItem().toString()+";";
						p=2;
					}
					else{
						sql0="select P.PilotID, P.Nume, P.Prenume, C.CursaID, C.TaraGazda, C.DataCursa, CP.PuncteObtinute from PilotiCurse CP "
								+ " join Piloti P on CP.PilotID = P.PilotID "
								+ " join Curse C on CP.CursaID = C.CursaID;";
						p=1;
					}
					ResultSet result = slcStatement.executeQuery(sql0);
					ResultSetMetaData metaData = result.getMetaData();
					
					Vector<String> v= new Vector<String>();		
					
					for(int i=p;i<= metaData.getColumnCount();i++)
						v.add(metaData.getColumnName(i));
					
					DefaultTableModel model = new DefaultTableModel(v, 0);
																				
					while(result.next())
					{
						Vector<String> v1= new Vector<String>();
						for(int i=p;i<=metaData.getColumnCount();i++)
								v1.add(result.getString(i));
						model.addRow(v1);
					}
					table.setModel(model);
					
					scrollableArea.setViewportView(table);
					scrollableArea.setBounds(100, 300, 600,150);
					scrollableArea.setVisible(true);
					
					fields.setText("");
					whereField.setText("");
				
				}
			
			catch(SQLException e){
				e.printStackTrace();
			}
			}});
		
		// Handler ce afiseaza doua tabele: unul care executa interogarea propriu-zisa, si unul care executa o interogare pentru a demonstra functionalitatea,
		// in functie de butonul ales si cerinta corespunzatoare
		confirmQuery.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				try{
					
					String sql= new String();
					String proofSql= new String();
					
		// INTEROGARI SIMPLE
					
					/* Afisati pilotii ce fac parte din echipe care provin dintr-o tara la alegerea utilizatorului*/
					if(type.equals("J1")){
						
						sql="SELECT P.Nume,P.Prenume,P.TaraProvenienta,P.NrTitluri,P.NrCurse,E.Nume as 'NumeEchipa' FROM Piloti P"
								+ " JOIN Echipe E ON P.EchipaID = E.EchipaID"
								+ " WHERE E.TaraProvenienta = '"+attList.getSelectedItem()+"';";
						
						proofSql="SELECT P.Nume, P.Prenume, P.TaraProvenienta as 'TaraPilot', P.NrCurse, P.NrTitluri, "
									+ " E.Nume as 'NumeEchipa',E.TaraProvenienta FROM Echipe E"
									+ " JOIN Piloti P ON E.EchipaID = P.EchipaID;";
					}
					
					/* Afisati inginerii ce au in evidenta masini cu o greutate a motorului mai mica de 150KG 
					   si o turatie maxima de cel putin 14000 RPM si a caror universitate urmata este cunoscuta*/
					else if(type.equals("J2")){
						sql="SELECT I.Nume,I.Prenume,I.DataDebut,I.NumeUniversitate,M.SerieMasina FROM Ingineri I"
								+ " JOIN Masini M on I.MasinaID = M.MasinaID"
								+ " WHERE M.TuratieMaxima >= 14000 and M.GreutateMotor < 150 and I.NumeUniversitate IS NOT NULL;";
						
						proofSql="SELECT I.Nume,I.Prenume,I.DataDebut,I.NumeUniversitate,"
									+ " M.SerieMasina, M.NumeProducator, M.CapacitateCilindrica, M.GreutateMotor, M.TuratieMaxima from Masini M"
									+ " JOIN Ingineri I ON I.MasinaID = M.MasinaID;";
					}
					
					/* Afisati numele, prenumele si numarul de titluri ale castigatorilor primelor trei locuri
					   ale cursei din China din data de 2019-10-02, in ordinea descrescatoare a punctajului obtinut */
					else if(type.equals("J3")){
						sql="SELECT TOP 3 P.Nume, P.Prenume, P.NrTitluri, CP.PuncteObtinute as 'Punctaj' FROM Piloti P"
								+ " JOIN PilotiCurse CP ON P.PilotID = CP.PilotID "
								+ " JOIN Curse C ON CP.CursaID = C.CursaID"
								+ " WHERE C.TaraGazda = 'China' and DataCursa = '2019-10-02'"
								+ " ORDER BY CP.PuncteObtinute desc;";
						
						proofSql="SELECT P.Nume, P.Prenume, C.TaraGazda as 'Locatie', C.DataCursa as 'Data', CP.PuncteObtinute as 'Punctaj' FROM Piloti P"
								+ " JOIN PilotiCurse CP ON P.PilotID = CP.PilotID "
								+ " JOIN Curse C ON CP.CursaID = C.CursaID"
								+ " ORDER BY C.TaraGazda;";		
					}
					
					/* Afisati echipele care au piloti care au castigat cel putin un turneu si cate premii detine echipa*/
					else if(type.equals("J4")){
						sql="SELECT E.Nume, E.TaraProvenienta, SUM(P.NrTitluri) as 'NrTitluriEchipa' FROM Echipe E "
								+ " JOIN Piloti P on E.EchipaID = P.EchipaID"
								+ " GROUP BY E.Nume, E.TaraProvenienta"
								+ " HAVING SUM(P.NrTitluri)>=1;";
						
						proofSql="SELECT P.Nume, P.Prenume, P.NrTitluri, E.Nume as 'NumeEchipa' FROM Piloti P "
									+ " JOIN Echipe E on P.EchipaID = E.EchipaID;";
									
					}
					
					/* Afisati numele si data nasterii pilotilor, dar si media, numarul si suma tururilor parcurse de ei in anul 2019*/
					else if(type.equals("J5")){
						sql="SELECT P.Nume,P.Prenume,P.DataNasterii, ROUND(AVG(CAST(C.NrTururi as FLOAT)),2) as 'MediaTururilor',"
								+ " COUNT(C.NrTururi) as 'NrCurse', SUM(C.NrTururi) as 'TotalTururi' FROM Piloti P"
								+ " JOIN PilotiCurse CP on P.PilotID = CP.PilotID "
								+ " JOIN Curse C on C.CursaID = CP.CursaID "
								+ " WHERE YEAR(C.DataCursa)='2019'"
								+ " GROUP BY P.Nume, P.Prenume, P.DataNasterii;";
						
						proofSql="SELECT P.Nume,P.Prenume,P.DataNasterii,C.DataCursa, C.NrTururi FROM Piloti P"
									+ " JOIN PilotiCurse CP on P.PilotID = CP.PilotID "
									+ " JOIN Curse C on C.CursaID = CP.CursaID;";
					}
					
					/* Afisati locul, data, numarul de tururi, numarul de masini si media turatiei maxime a masinilor, 
					   ale curselor pentru cursele de cel putin 60 de tururi si cu o medie a turatiei maxime de sub 14500 RPM*/
					else if(type.equals("J6")){
						sql="SELECT C.TaraGazda, C.DataCursa, C.NrTururi,"
								+ " COUNT(M.SerieMasina) as 'NrMasini', ROUND(AVG(CAST(M.TuratieMaxima as FLOAT)),2) as 'MediaTuratiei'  FROM Curse C"
								+ " JOIN PilotiCurse CP on CP.CursaID = C.CursaID "
								+ " JOIN Piloti P on CP.PilotID = P.PilotID "
								+ " JOIN Masini M on M.MasinaID = P.MasinaID "
								+ " WHERE C.NrTururi >= 60 "
								+ " GROUP BY C.TaraGazda, C.DataCursa, C.NrTururi "
								+ " HAVING ROUND(AVG(CAST(M.TuratieMaxima as FLOAT)),2)<'14500.00';";
						
						proofSql="SELECT C.TaraGazda, C.DataCursa, C.NrTururi,"
										+ " COUNT(SerieMasina) as 'NrMasini', ROUND(AVG(CAST(M.TuratieMaxima as FLOAT)),2) as 'MediaTuratiei'  FROM Curse C"
										+ " JOIN PilotiCurse CP on CP.CursaID = C.CursaID "
										+ " JOIN Piloti P on CP.PilotID = P.PilotID "
										+ " JOIN Masini M on M.MasinaID = P.MasinaID "
										+ " GROUP BY C.TaraGazda, C.DataCursa, C.NrTururi;";
					}
					
		// INTEROGARI COMPLEXE	
					
					/* Afisati pilotii care au mai multe puncte decat cel putin un pilot nascut dupa data: (setata de utilizator)*/
					else if(type.equals("SQ1")){
						
							SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
							
							// Verificare format corect
							try {
								format.parse(attTF.getText());
								sql="SELECT P.Nume, P.Prenume, P.DataNasterii, P.NrTitluri, P.NrCurse, SUM(CP.PuncteObtinute) as 'Punctaj'"
										+ " FROM Piloti P JOIN PilotiCurse CP on P.PilotID = CP.PilotID"
										+ " GROUP BY P.Nume, P.Prenume, P.DataNasterii, P.NrTitluri, P.NrCurse"
										+ " HAVING SUM(CP.PuncteObtinute) > ANY(SELECT SUM(CP1.PuncteObtinute) FROM PilotiCurse CP1"
																					+ " JOIN Piloti P1 on CP1.PilotID = P1.PilotID"
																					+ " WHERE P1.DataNasterii>'"+attTF.getText()+"'"
																					+ " GROUP BY P1.Nume);";
								
								proofSql="SELECT P.Nume, P.Prenume, P.DataNasterii, P.NrTitluri, P.NrCurse, SUM(CP.PuncteObtinute) as 'Punctaj'"
											+ " FROM Piloti P JOIN PilotiCurse CP on P.PilotID = CP.PilotID"
											+ " GROUP BY P.Nume, P.Prenume, P.DataNasterii, P.NrTitluri, P.NrCurse"
											+ " ORDER BY P.DataNasterii DESC;";
								
							} catch (java.text.ParseException e) {
								JOptionPane.showMessageDialog(attTF, "Nu ati introdus formatul corect");
								e.printStackTrace();
							}
														
						
						
					}
					
					/* Afisati echipele care au piloti nascuti dupa anul 1985, care au participat la minim 100 de curse*/
					else if(type.equals("SQ2")){
						sql="SELECT E1.Nume as 'NumeEchipa', E1.TaraProvenienta FROM Echipe E1 "
								+ "WHERE E1.Nume IN(SELECT E.Nume FROM Echipe E"
														+ " JOIN Piloti P ON E.EchipaID = P.EchipaID"
														+ " WHERE year(P.DataNasterii)>'1985' and P.NrCurse >= '100');";
						
						proofSql="SELECT P.Nume, P.Prenume, P.NrCurse, P.DataNasterii, E.Nume as 'NumeEchipa', E.TaraProvenienta FROM Echipe E"
									+ " JOIN Piloti P ON E.EchipaID = P.EchipaID"
									+ " ORDER BY P.DataNasterii DESC;";
					}
					
					/* Afisati numele, prenumele, numarul de curse si media punctelor acumulate de pilotii ce au media punctelor
					   mai mica sau egala decat media fiecarei echipe, sau a celor care au ingineri de cursa cu ingineri in subordine*/
					else if(type.equals("SQ3")){
						sql="SELECT P.Nume, P.Prenume, P.NrCurse, AVG(CP.PuncteObtinute) as 'MediaPunctelor', I.Nume, I.Prenume FROM Piloti P"
								+ " JOIN PilotiCurse CP on CP.PilotID = P.PilotID"
								+ " JOIN Ingineri I on P.InginerID = I.InginerID"		
								+ " GROUP BY P.Nume, P.Prenume, P.NrCurse, I.Nume, I.Prenume"
								+ " HAVING AVG(CP.PuncteObtinute) <= ALL (SELECT AVG(CP1.PuncteObtinute) FROM PilotiCurse CP1"
																		+ " JOIN Piloti P1 on CP1.PilotID = P1.PilotID"
																		+ " JOIN Echipe E on P1.EchipaID = E.EchipaID "
																		+ " GROUP BY E.Nume)"
								+ " UNION "
								+ " SELECT P.Nume, P.Prenume, P.NrCurse, AVG(CP.PuncteObtinute) as 'MediaPunctelor', I.Nume, I.Prenume FROM Piloti P"
								+ " JOIN PilotiCurse CP on CP.PilotID = P.PilotID"
								+ " JOIN Ingineri I on P.InginerID = I.InginerID"
								+ " WHERE P.InginerID IN ( SELECT I.InginerID FROM Ingineri I JOIN Ingineri I1 on I.InginerID = I1.InginerCursaID"
																+ " WHERE I1.InginerCursaID IS NOT NULL)"
								+ " GROUP BY P.Nume, P.Prenume, P.NrCurse, I.Nume, I.Prenume"
								+ "	ORDER BY AVG(CP.PuncteObtinute) ASC;";
						
						
						proofSql = " SELECT I.Nume,I.Prenume,(SELECT I1.Nume FROM Ingineri I1 "
																+ " WHERE I.InginerCursaID = I1.InginerID) as 'NumeInginerCursa',"
																+ " E.Nume, (SELECT AVG(CP1.PuncteObtinute) FROM PilotiCurse CP1"
																							+ " JOIN Piloti P1 on CP1.PilotID = P1.PilotID"
																							+ " JOIN Echipe E1 on P1.EchipaID = E1.EchipaID"
																							+ " WHERE E1.Nume = E.Nume "
																							+ " GROUP BY E1.Nume) as 'MedieCurse' FROM Ingineri I "
									+ " LEFT JOIN Piloti P on I.InginerID = P.InginerID"
									+ " LEFT JOIN Echipe E on E.EchipaID = P.EchipaID"
									+ " LEFT JOIN PilotiCurse CP on P.PilotID = CP.PilotID"
									+ " GROUP BY I.Nume,I.Prenume,I.InginerCursaID, E.Nume"
									+ " ORDER BY E.Nume;";
						
					}
					/* Afisati masinile ce au o capacitate cilindrica cuprinsa intre 1500cm3 si 1600 cm3 si sunt conduse de piloti ce au un numar de curse
					   mai mare decat media numarului de curse a pilotilor ce participa in cursele ce au cel putin trei participanti*/
					else if(type.equals("SQ4")){
						sql = "SELECT M.SerieMasina, M.NumeProducator, M.CapacitateCilindrica, M.GreutateMotor, M.TuratieMaxima, P.NrCurse as 'NrCursePilot' FROM Masini M"
								+ " JOIN Piloti P on P.MasinaID = M.MasinaID"
								+ " WHERE M.CapacitateCilindrica BETWEEN '1500' and '1600' and "
								+ " P.NrCurse > ALL( SELECT AVG(P.NrCurse) FROM Piloti P"
														+ " JOIN PilotiCurse CP on CP.PilotID = P.PilotID"
														+ " JOIN Curse C on C.CursaID = CP.CursaID"
														+ " GROUP BY C.TaraGazda, C.DataCursa"
														+ " HAVING COUNT(CP.PuncteObtinute)>=3);";
						
						proofSql = " SELECT C.TaraGazda, AVG(P.NrCurse) as 'MediaCurselor', COUNT(CP.PuncteObtinute) as 'NrParticipanti' FROM Piloti P"
										+ " JOIN PilotiCurse CP on CP.PilotID = P.PilotID"
										+ " JOIN Curse C on C.CursaID = CP.CursaID"
										+ " GROUP BY C.TaraGazda, C.DataCursa"
										+ " HAVING COUNT(CP.PuncteObtinute)>=3;";
						
					}
					
					// Executarea interogarilor
					Statement statement = connection.createStatement();
					ResultSet result = statement.executeQuery(sql);
					ResultSetMetaData metaData = result.getMetaData();
					
					Statement proofStatement = connection.createStatement();
					ResultSet proofResult = proofStatement.executeQuery(proofSql);
					ResultSetMetaData proofMetaData = proofResult.getMetaData();
					
					// Introducerea rezultatelor in tabele
					Vector<String> v= new Vector<String>();		
					Vector<String> pv= new Vector<String>();	
					
					for(int i=1;i<= metaData.getColumnCount();i++)
						v.add(metaData.getColumnName(i));
					
					DefaultTableModel model = new DefaultTableModel(v, 0);
		
					for(int i=1;i<= proofMetaData.getColumnCount();i++)
						pv.add(proofMetaData.getColumnName(i));
					
					DefaultTableModel proofModel = new DefaultTableModel(pv, 0);
																				
					while(result.next())
					{
						Vector<String> v1= new Vector<String>();
						for(int i=1;i<=metaData.getColumnCount();i++)
							v1.add(result.getString(i));
						model.addRow(v1);
						
					}
					while(proofResult.next())
					{
						Vector<String> v1= new Vector<String>();
						for(int i=1;i<=proofMetaData.getColumnCount();i++)
							v1.add(proofResult.getString(i));
						proofModel.addRow(v1);
						
					}
					table.setModel(model);
					proofTable.setModel(proofModel);
					
					// Adaugarea scrollbar-urilor
					scbAr.setViewportView(proofTable);
					scbAr.setBounds(100, 370, 600,150);
					scbAr.setVisible(true);
					
					scrollableArea.setViewportView(table);
					scrollableArea.setBounds(100, 195, 600,150);
					scrollableArea.setVisible(true);
					
					fields.setText("");
					whereField.setText("");
				
				}
			
			catch(SQLException e){
				e.printStackTrace();
			}		
					
			}
		});
		
		// Adaugarea componentelor la panou
		panel.add(intro);
		panel.add(tableList);
		panel.add(content);
		panel.add(scrollableArea);
		panel.add(scbAr);
		panel.add(fields);
		panel.add(whereLabel);
		panel.add(whereField);
		panel.add(attList);
		panel.add(attTF);
		panel.add(confirm);
		panel.add(confirmQuery);
		panel.add(menuBar);
		
		// Adaugarea panoului la frame
		add(panel);
		setVisible(true);
	}
	
}
