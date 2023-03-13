package com.Think.DataMigrator.Entity;

import java.util.HashMap;
import java.util.List;

public class Table {
	
	List<String> columns;
	String table_name;
	HashMap<String,String> primary_keys;
	HashMap<String,String> foreign_keys;
	
	public Table(List<String> columns, String table_name, HashMap<String,String> primary_keys, HashMap<String,String> foreign_keys) {
		super();
		this.columns = columns;
		this.table_name = table_name;
		this.primary_keys = primary_keys;
		this.foreign_keys = foreign_keys;
	}
	
	public String columnList() {												// Returns the string containing the columns names of the table
		StringBuffer list = new StringBuffer();
		for(String column:columns) {
			list.append(column+",");
		}
		list.deleteCharAt(list.length()-1);
		return list.toString();
	}
	
	public List<String> getColumns() {
		return columns;
	}
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	
	public String getTable_name() {
		return table_name;
	}
	
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	
	public HashMap<String,String> getPrimary_keys() {
		return primary_keys;
	}
	
	public void setPrimary_keys(HashMap<String,String> primary_keys) {
		this.primary_keys = primary_keys;
	}
	
	public HashMap<String,String> getForeign_keys() {
		return foreign_keys;
	}
	
	public void setForeign_keys(HashMap<String,String> foreign_keys) {
		this.foreign_keys = foreign_keys;
	}
}
