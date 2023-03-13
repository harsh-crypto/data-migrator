package com.Think.DataMigrator.DatabaseConncetor;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLConnector implements AutoCloseable{
//	
	private static String DB_URL = "jdbc:mysql://localhost:3306/world";
	private static String USER = "root";
	private static String PASS = "Harsh@123";

//	private static String DB_URL = "jdbc:mysql://10.31.1.229:3306/THINK_SETUP";
//	private static String USER = "thinksetup";
//	private static String PASS = "RDXGgn@321$";
	private DatabaseMetaData dm;
	private static Connection conn;
	
	public MySQLConnector(){
		System.out.print(DB_URL);
		try {
			//Class.forName("com.mysql.jdbc.driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("\nConnection made to "+DB_URL);
			dm = (DatabaseMetaData) conn.getMetaData();
            System.out.println("Driver name: " + dm.getDriverName());
            System.out.println("Driver version: " + dm.getDriverVersion());
            System.out.println("Product name: " + dm.getDatabaseProductName());
            System.out.println("Product version: " + dm.getDatabaseProductVersion());
            
		}
		catch(Exception e) {
			System.out.print("Connection not established. ");
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return conn;
	}
	
	public DatabaseMetaData getMetadata() {
		return this.dm;
	}
	
	
	public ResultSet Get_ID(Integer id) {
		try {
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("select id,name,department from employees WHERE id= "+id+";");
	         return rs;
	         // Extract data from result set
//	         while (rs.next()) {
//	            // Retrieve by column name
//	            System.out.print("ID: " + rs.getInt("id"));
//	            System.out.print(", name: " + rs.getString("name"));
//	            System.out.println(", department: " + rs.getString("department"));
//	         }
	      }
		catch(Exception e) {
			System.out.println("Query cannot be loaded.");
			e.printStackTrace();
		}
		return null;
	}
	
	public ResultSet Get_data(String select_query) {
		try {
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(select_query);
	         
	         return rs;
	         // Extract data from result set
//	         while (rs.next()) {
//	            // Retrieve by column name
//	            System.out.print("ID: " + rs.getInt("id"));
//	            System.out.print(", name: " + rs.getString("name"));
//	            System.out.println(", department: " + rs.getString("department"));
//	         }
	      }
		catch(Exception e) {
			System.out.println("Query cannot be loaded.");
			e.printStackTrace();
		}
		return null;
	}
	
	public void select(String tableName) {																	/// getting the table contents
		try {
			Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("select * from "+tableName+" LIMIT 10;");
	        List<String> columns = new ArrayList<>();
	        try(ResultSet column = dm.getColumns(null,null, tableName , null)){
	        	while(column.next()) {
	        	    String columnName = column.getString("COLUMN_NAME");
	        	    columns.add(columnName);
	        	}
	        	System.out.print(columns);
	        	System.out.println("");
	        }catch(Exception e) {
	        		  System.out.print("couldnt fetch metadata: \n");
	        		  e.printStackTrace();  
	        }

	         // Extract data from result set
	         while (rs.next()) {
	        	 for(String i:columns) {
	        		  System.out.print(rs.getObject(i)+" \t    ");
	        		 }
	        	 System.out.println();
	         }
	      }
		catch(Exception e) {
			System.out.println("Query cannot be loaded.");
			e.printStackTrace();
		}
	}
	
	public void select(String where_clause, String table_name) {							// get the table content over the where clause
		try {
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("select id,name,department from "+table_name+" WHERE "+where_clause+";");
	         List<String> columns = new ArrayList<>();
		     try(ResultSet column = dm.getColumns(null,null, table_name , null)){
		        while(column.next()) {
		        	String columnName = column.getString("COLUMN_NAME");
		        	columns.add(columnName);
		        }
		        System.out.print(columns);
		        System.out.println("");
		     }catch(Exception e) {
		    	 System.out.print("couldnt fetch metadata: \n");
		    	 e.printStackTrace();  
		     }

		     // Extract data from result set
		     while (rs.next()) {
		    	 for(String i:columns) {
		    		 System.out.print(rs.getObject(i)+" \t    ");
		    	 }
		    	 System.out.println();
		     }
	      }
		catch(Exception e) {
			System.out.println("Query cannot be loaded.");
			e.printStackTrace();
		}
	}

	public void insert(List<Object> params, String query) {															// params is the list of parameter to be inserted query os the sql query to be commanded
		try {
			int index =1;
	        PreparedStatement rs = conn.prepareStatement(query);
	        if(params!=null && !params.isEmpty()) {
	        	for(int i=0;i<params.size();i++) {
	        		rs.setObject(index++, params.get(i));
	        	}
	        }
	        int count = rs.executeUpdate();
	        System.out.println("Rows affected: "+count);
	      }catch(SQLIntegrityConstraintViolationException e) {
	    	  System.out.println("Table already have an element\n"+e.getMessage());
	      }
		catch(Exception e) {
			System.out.println("Query cannot be loaded.");
			e.printStackTrace();
		}
	}
	
	public void update(String update_feild, String update_value, String table_name ,String where_clause) {			// update_feild feild to be updated. update value the value of the update feild where_clause is the condition , table_name the name of the table affected
		try { 
	        PreparedStatement rs = conn.prepareStatement("UPDATE ? set ?=? WHERE ?;");
	        rs.setObject(1, table_name);
	        rs.setObject(2, update_feild);
	        rs.setObject(3, update_value);
	        rs.setObject(4,where_clause);
	        int count = rs.executeUpdate();
	        System.out.println("Rows affected: "+count);
	      }catch(Exception e) {
			System.out.println("Query cannot be loaded.");
			e.printStackTrace();
		}
	}
	
	public void delete(String where_clause , String table_name) {												// where_clause is the condition , table_name the name of the table affected
		try { 
	        PreparedStatement rs = conn.prepareStatement("DELETE FROM ? WHERE ?");
	        rs.setString(1, table_name);
	        rs.setString(2,where_clause);
	        int count = rs.executeUpdate();
	        System.out.println("Rows affected: "+count);
	      }catch(Exception e) {
			System.out.println("Query cannot be loaded.");
			e.printStackTrace();
		}
	}
	
	public void batch_making(ResultSet data, String insert_query) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(insert_query);
		conn.setAutoCommit(false);
		while(data.next()) {
    		for(int i=1;i<=data.getMetaData().getColumnCount();i++) {
    			stmt.setObject(i, data.getObject(i));
    		}
    		stmt.addBatch();											//Adding all the mysql data to sql server
    		System.out.print("Add batch");
		}
		System.out.print("Executing batch.");
		int[] result_batch = stmt.executeBatch();
		conn.commit();
		System.out.println(result_batch);
		stmt.close();
	}
	
	public List<String> getColumns(String table_Name){
		
		List<String> column =new ArrayList<String>();
		try(ResultSet columns = conn.getMetaData().getColumns(null, null, table_Name, null)){
			while(columns.next()) {
				column.add(columns.getString("COLUMN_NAME"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return column;
		
	}
	
	public List<String> get_tables() throws SQLException{
		
		List<String> table = null;
		ResultSet tables =  dm.getTables(null, null, null, new String[] {"TABLE"});
		while(tables.next()) {
			table.add(tables.getString("TABLE_NAME"));
		}
		if(table == null && table.isEmpty()) {
			System.out.print("Cannot fetch the tables from database");
		}
		return table;
	}

	@Override
	public void close() throws Exception {
		dm = (DatabaseMetaData) conn.getMetaData();
		System.out.println("Closing: "+dm.getDriverName());
		conn.close();
	}
	
}
