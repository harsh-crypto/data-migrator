import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		ConnectSQL conn = new ConnectSQL();
		conn.select();
		System.out.println("Rows affected: "+conn.insert(2, "Jatin", "IT"));
		conn.select();
	}

}
