import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectSQL {
	
	private static String DB_URL;
	private static String USER;
	private static String PASS;
	
	public ConnectSQL(){
		DB_URL = "jdbc:mysql://localhost:3306/THINK";
		USER = "root";
		PASS = "Harsh@123";
	}
	
	public static Connection establishConnection() {
		try {
			//Class.forName("com.mysql.jdbc.driver");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connection made to "+DB_URL);
			return conn;
		}
		catch(Exception e) {
			System.out.print("Connection not established. ");
			e.printStackTrace();
		}
		return null;
	}
	
	public void select() {
		try {
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("select id,name,department from employees;");

	         // Extract data from result set
	         while (rs.next()) {
	            // Retrieve by column name
	            System.out.print("ID: " + rs.getInt("id"));
	            System.out.print(", name: " + rs.getString("name"));
	            System.out.println(", department: " + rs.getString("department"));
	         }
	      }
		catch(Exception e) {
			System.out.println("Query cannot be loaded.");
			e.printStackTrace();
		}
	}
	public int insert(int id, String name, String department) {
		try {
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); 
	        PreparedStatement rs = conn.prepareStatement("INSERT into employees(id,name,department) values(?,?,?);");
	        rs.setInt(1, id);
	        rs.setString(2, name);
	        rs.setString(3,department);
	        int count = rs.executeUpdate();
	        return count;
	      }
		catch(Exception e) {
			System.out.println("Query cannot be loaded.");
			e.printStackTrace();
		}
		return 0;
	}

}
