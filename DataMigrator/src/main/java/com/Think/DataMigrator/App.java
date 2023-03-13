package com.Think.DataMigrator;

//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;

import com.Think.DataMigrator.Service.DataBase_Migration;

public class App 
{
	private static DataBase_Migration obj = new DataBase_Migration();
	
    public static void main( String[] args ) throws Exception
    { 
    	obj.migrate_tables();
    }
}
