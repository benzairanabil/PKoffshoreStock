package dev.procheck.offshore.stock.daoo;

import java.sql.Connection;



public abstract class CartonDao<Carton> {
	public Connection connect = null;

	public CartonDao(Connection conn) {
		this.connect = conn;
	}
}
