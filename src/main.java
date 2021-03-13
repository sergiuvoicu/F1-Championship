import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;

public class main  {
	
	public static void main(String[] args) {
		// Realizarea conexiunii cu baza de date si trimiterea ei ca argument in pagina de login
		try{
		Connection connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-IEQELGE\\SQLEXPRESS;databaseName=Campionat_F1;integratedSecurity=true");
		Login l = new Login(connection);
		}
		catch(SQLException e){
			e.printStackTrace();
		}	
	}
	

}
