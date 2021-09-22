package dev.procheck.offshore.stock.daoo;

import java.sql.Connection;

public abstract class PKUserHistoDAO <PKUserHisto>{

	public Connection connect = null;
	
	public PKUserHistoDAO(Connection conn) {
		this.connect = conn;
	}
	
	public abstract boolean add(PKUserHisto histo);
	
	
}
