package com.Think.DataMigrator.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.List;

//import com.Think.DataMigrator.DatabaseConncetor.MySQLConnector;
import com.Think.DataMigrator.DatabaseConncetor.DatabaseConnector;
import com.Think.DataMigrator.Entity.Table;

public class DataBase_Migration {
	
	private static DatabaseConnector mysql;
	private static DatabaseConnector sql;
	
	public DataBase_Migration(){
		mysql = new DatabaseConnector("jdbc:mysql://localhost:3306/BikeStore","root","Harsh@123");
		sql = new DatabaseConnector("jdbc:sqlserver://localhost\\NOD112B21B00638:1434;databaseName=BikeStores","superUser","Sql@123");
	}
	
//	@SuppressWarnings("unlikely-arg-type")
	public void migrate_tables() throws SQLException {
		
		List<Table> table = mysql.get_tables("production");
		
		for(Table t: table) {
			System.out.print(t.toString());
		}
		
	}
}
