package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
	public static Connection getConnection() throws SQLException {
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		new File("db").mkdirs();
		
		System.out.println("Database in: " + new File("db/stock.db").getAbsolutePath());
		
		Connection conn = DriverManager.getConnection("jdbc:sqlite:db/stock.db");
		
		return conn;
	}
}