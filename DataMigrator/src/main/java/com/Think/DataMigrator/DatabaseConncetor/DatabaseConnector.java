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
import java.util.HashMap;
import java.util.List;

import com.Think.DataMigrator.Entity.Table;


public class DatabaseConnector implements AutoCloseable{
	
//	private static String DB_URL = "jdbc:sqlserver://localhost\\NOD112B21B00638:1434;databaseName=THINK";
//	private static String USER = "superUser";
//	private static String PASS = "Sql@123";
	private DatabaseMetaData dm;
	private static Connection conn;
	
	public DatabaseConnector(String DB_URL, String USER, String PASS){
//		System.out.print(DB_URL);
		
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			System.out.println("\nConnection made to "+DB_URL);
			DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
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
	      }
		catch(Exception e) {
			System.out.println("Query cannot be loaded.");
			e.printStackTrace();
		}
		return null;
	}
	
	public void select(String query) {																	/// getting the table contents limiting 10 
		try {
			Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        List<String> columns = new ArrayList<>();
	        for(int i=1;i<=rs.getMetaData().getColumnCount();i++) {
	        	System.out.print(rs.getMetaData().getColumnName(i) + " \t    ");
	        	columns.add(rs.getMetaData().getColumnName(i));
	        }
	        System.out.println();
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
	public void insert(List<Object> params, String query) {															// params is the list of parameter to be inserted query is the SQL query to be commanded
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
	
	public void update(String update_feild, String update_value, String table_name ,String where_clause) {			// update_feild field to be updated. update value the value of the update feild where_clause is the condition , table_name the name of the table affected
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
    		stmt.addBatch();																					//Adding all the mysql data to sql server
    	}
		System.out.print("Adding batch");
		int[] result_batch = stmt.executeBatch();
		conn.commit();
		System.out.println(result_batch);
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
	@SuppressWarnings("null")
	public List<Table> get_tables(String schema) throws SQLException{
		DatabaseMetaData dm = conn.getMetaData();
		List<Table> table = null;
		String table_name = null;
		ResultSet tables = null;
		tables =  dm.getTables(null, null, null, new String[] {"TABLE"});
		if(table == null && table.isEmpty()) {
			System.out.print("Cannot fetch the tables from database or database has no table");
		}
		while(tables.next()) {
			table_name = tables.getString("TABLE_NAME");
			table.add(new Table(this.getColumns(table_name), table_name, this.get_primary_key(table_name),this.get_Foreign_key(table_name)));
		}
		return table;
	}
	
	public HashMap<String, String> get_primary_key(String table){
		HashMap<String, String> hm  = new HashMap<String, String>();
		
		try(ResultSet rs = dm.getPrimaryKeys(null, null, table)){
			while(rs.next())
				{hm.put(rs.getString("COLUMN_NAME"),rs.getString("PKNAME"));}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return hm;
	}
	public HashMap<String, String> get_Foreign_key(String table){
		HashMap<String, String> hm  = new HashMap<String, String>();
		
		try(ResultSet rs = dm.getImportedKeys(null, null, table)){
			while(rs.next()) {hm.put(rs.getString("FKCOLUMN_NAME"),rs.getString("FKTABLE_NAME"));}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return hm;
	}
	
	public boolean DeleteColumn(String table , String column) throws SQLException {
		Statement stmt = conn.createStatement();
		int res = stmt.executeUpdate("ALTER TABLE "+table+" DROP "+column);
		return (res>0)?true:false;
	}

 
	@Override
	public void close() throws Exception {
		dm = (DatabaseMetaData) conn.getMetaData();
		System.out.println("Closing: "+dm.getDriverName());
		conn.close();
	}
}
