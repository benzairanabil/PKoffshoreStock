package dev.procheck.offshore.stock.daoo;

import java.sql.Connection;

import dev.procheck.offshore.stock.models.PKUser;

public abstract class PKUserDAO<PKUser> {

	public Connection connect = null;

	public PKUserDAO(Connection conn) {
		this.connect = conn;
	}

	public abstract PKUser find(String login);
	public abstract PKUser find(String login,String locale,String profile) ;
	public abstract PKUser findByPKSessionId(String pkSessionId);

	public abstract boolean CNXFailed(String login);
	

	public abstract boolean CNXSuccess(String login, String pkSessionId);

	public abstract boolean updateLastCnx(String login, String pkSessionId);
	
	public abstract boolean lockAccount(String login);
}
