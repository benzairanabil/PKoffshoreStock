package dev.procheck.offshore.stock.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

import org.apache.log4j.Logger;

import dev.procheck.offshore.stock.daoo.PKUserHistoDAO;
import dev.procheck.offshore.stock.models.PKUserHisto;


public class PKUserHistoImp extends PKUserHistoDAO<PKUserHisto> {
	final static Logger logger = Logger.getLogger(PKUserImp.class);
	String userLogin;
	String bankCode;
	String branchCode;
	
	public PKUserHistoImp(Connection conn) {
		super(conn);
	}

	@Override
	public boolean add(PKUserHisto histo) {
		logger.info("PKCore - PKUserHistoImp - function #add ");
		PreparedStatement stmt = null;
		boolean isOk = true;
		try {
			String query = "insert into T_AUTH_USER_HISTO ("+ "s_histo_login," + "s_histo_action,"
					+ "d_histo_date," + "s_histo_id_session," + "s_histo_ip_adresse" + ") "
					+ "values(?,?,?,?,?)";
			stmt = connect.prepareStatement(query);
			
			Date date = new Date();
			
			stmt.setString(1, histo.getUserHistoLogin());
			stmt.setString(2, histo.getUserHistoAction());
			stmt.setTimestamp(3, new java.sql.Timestamp(date.getTime()) );
			stmt.setString(4, histo.getUserHistoIdSession());
			stmt.setString(5, histo.getUserIpAdresse());

			stmt.executeUpdate();

		} catch (Exception e) {
			logger.error("Error ",e);
			isOk = false;
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (Exception e2) {
			}
		}

		return isOk;
	}

}
