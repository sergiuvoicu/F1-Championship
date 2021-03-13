import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.*;
public class Login extends JFrame {

	Login(Connection connection){
		
		// Setarea frame-ului
		setSize(800,600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Campionat F1");
		
		// Declararea componentelor
		JPanel panel;
		JTextField userText;
		JPasswordField passwordText;
		JTextField newUserText;
		JPasswordField newPasswordText;
		JButton button;
		JButton newButton;
		JCheckBox isAdminCB;
		
		// Centrarea frame-ului
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		// Setarea panoului ce contine componentele
		panel = new JPanel();
		panel.setBackground(new Color(165, 202, 241));
		panel.setLayout(null);
		
		// Setarea logo-ului
		JLabel IconLogo = new JLabel();
		IconLogo.setBounds(280,90,250,75);
		ImageIcon img= new ImageIcon("C:\\f1.png");
		img = new ImageIcon(img.getImage().getScaledInstance(250, 75, Image.SCALE_SMOOTH));
		IconLogo.setIcon(img);
		
		// Setarea zonelor text pentru login si register, pentru username si parola
		
			// Login
			userText = new JTextField("Username");
			userText.setBounds(210,200,165,35);
			userText.setFont(new Font("Times New Roman", Font.PLAIN,16));
			
			
			passwordText = new JPasswordField("Password");
			passwordText.setBounds(210,245,165,35);
			passwordText.setFont(new Font("Times New Roman", Font.PLAIN,22));
			
			// Register
			newUserText = new JTextField("New Username");
			newUserText.setBounds(415,200,165,35);
			newUserText.setFont(new Font("Times New Roman", Font.PLAIN,16));
			
			newPasswordText = new JPasswordField("Password");
			newPasswordText.setBounds(415,245,165,35);
			newPasswordText.setFont(new Font("Times New Roman", Font.PLAIN,22));
			
			// Setarea checkbox-ului pentru inregistrarea ca admin
			isAdminCB = new JCheckBox("Admin");
			isAdminCB.setBounds(415,290,165,35);
			isAdminCB.setOpaque(false);
		
		// Handleri care umple zonele text cu text predefinit cand nu se scrie in ele si sunt goale
		userText.addFocusListener(new FocusAdapter (){
			@Override
				public void focusGained(FocusEvent arg0){
					if(userText.getText().equals("Username"))
						userText.setText("");
					else userText.selectAll();
					}
			@Override
				public void focusLost(FocusEvent arg0){
					if(userText.getText().equals(""))
						userText.setText("Username");
				}
			});

		passwordText.addFocusListener(new FocusAdapter (){
			@Override
				public void focusGained(FocusEvent arg0){
					if(passwordText.getText().equals("Password"))
						passwordText.setText("");
					else passwordText.selectAll();
					}
			@Override
				public void focusLost(FocusEvent arg0){
					if(passwordText.getText().equals(""))
						passwordText.setText("Password");
				}
			});
		newUserText.addFocusListener(new FocusAdapter (){
			@Override
				public void focusGained(FocusEvent arg0){
					if(newUserText.getText().equals("New Username"))
						newUserText.setText("");
					else newUserText.selectAll();
					}
			@Override
				public void focusLost(FocusEvent arg0){
					if(newUserText.getText().equals(""))
						newUserText.setText("New Username");
				}
			});
		
		newPasswordText.addFocusListener(new FocusAdapter (){
			@Override
				public void focusGained(FocusEvent arg0){
					if(newPasswordText.getText().equals("Password")){
						newPasswordText.setText("");
						}
					else newPasswordText.selectAll();
					}
			@Override
				public void focusLost(FocusEvent arg0){
					if(newPasswordText.getText().equals(""))
						newPasswordText.setText("Password");
				}
			});
		
		// Setarea butonului de login
		button = new JButton("Login");
		button.setOpaque(false);
		button.setBounds(240,300,100,35);
		
		// Handler care cauta in baza de date username-ul si parola, pentru a aproba sau nu logarea
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				try{
					// Obtinerea informatiilor din baza de date
					String sql = "SELECT Username,Password,IsAdmin FROM Useri";
					Statement statement= connection.createStatement();
					ResultSet result=statement.executeQuery(sql);
					ResultSetMetaData metaData = result.getMetaData();
					
					boolean isAdmin=false;
					boolean isUser=false;
					
					while(result.next())
					{
						// Pentru fiecare rezultat, se verifica daca username-ul si parola introduse coincid, si daca o fac,
						// se transmite daca este user sau admin
						for(int i=1;i<=metaData.getColumnCount();i++)
							{
								if( result.getString(i).equals(userText.getText()) && result.getString(i+1).equals(passwordText.getText()) && result.getBoolean(i+2))
									isAdmin=true;
								else if(result.getString(i).equals(userText.getText()) && result.getString(i+1).equals(passwordText.getText()) && !result.getBoolean(i+2))
									isUser=true;
							}
					}
					
					// Daca datele introduse au fost gasite, se initializeaza pagina principala, in functie de rolul userului
					if(isAdmin || isUser)
					{
						dispose();
						HomePage hp= new HomePage(connection, isAdmin);
					}
					
					// Daca datele nu au fost introduse 
					else JOptionPane.showMessageDialog(button, "Ati gresit username-ul sau parola");

				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
		});
		
		
		// Setarea butonului de Register
		newButton = new JButton("Register");
		newButton.setOpaque(false);
		newButton.setBounds(445,335,100,35);
		
		// Handler ce verifica daca username-ul introdus este deja existent in baza de date
		newButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				try{
					// Obtinerea informatiilor din baza de date
					String sql = "SELECT Username,Password,IsAdmin FROM Useri;";
					Statement statement= connection.createStatement();
					ResultSet result=statement.executeQuery(sql);
					ResultSetMetaData metaData = result.getMetaData();
					
					boolean isTaken=false;
					
					while(result.next())
					{
						// Pentru fiecare rezultat se verifica daca username-ul coincide cu cel introdus in zona text
						for(int i=1;i<=metaData.getColumnCount();i++)
							{
								if( result.getString(i).equals(newUserText.getText()))
									isTaken=true;
							}
					}
					
					// Daca numele nu este luat, datele introduse in campurile pentru inregistrare, sunt introduse in tabela Useri 
					if(!isTaken)
					{
						sql = "INSERT INTO Useri(Username, Password, IsAdmin) VALUES ('"+newUserText.getText()+"', '"+newPasswordText.getText()+"', '"+isAdminCB.isSelected()+"');";
						Statement insertStatement = connection.createStatement();
						insertStatement.executeUpdate(sql);
						
						newUserText.setText("New Username");
						newPasswordText.setText("Password");
						
						// Mesaj de confirmare
						if(isAdminCB.isSelected())
							JOptionPane.showMessageDialog(newButton, "Adminul a fost adaugat cu succes");
						else
							JOptionPane.showMessageDialog(newButton, "Userul a fost adaugat cu succes");
						
						isAdminCB.setSelected(false);
	
					}
					// Daca numele este luat
					else JOptionPane.showMessageDialog(newButton, "Username-ul exista deja");
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
		});
		
		// Adaugarea componentelor la panou
		panel.add(IconLogo);
		panel.add(userText);
		panel.add(passwordText);
		panel.add(button);
		panel.add(newUserText);
		panel.add(newPasswordText);
		panel.add(isAdminCB);
		panel.add(newButton);
		
		// Adaugarea panoului la frame
		add(panel);
		setVisible(true);
	}

}
