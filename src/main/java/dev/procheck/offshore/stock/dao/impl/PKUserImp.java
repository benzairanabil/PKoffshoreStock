package dev.procheck.offshore.stock.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import dev.procheck.offshore.stock.daoo.PKUserDAO;
import dev.procheck.offshore.stock.models.PKUser;

public class PKUserImp extends PKUserDAO<PKUser> {
	final static Logger logger = Logger.getLogger(PKUserImp.class);

	String userLogin;
	String bankCode;
	String branchCode;

	public PKUserImp(Connection conn) {
		super(conn);
	}

	public PKUserImp(Connection conn, String bankCode, String branchCode, String userLogin) {
		super(conn);
		this.connect = conn;
		this.userLogin = userLogin;
		this.bankCode = bankCode;
		this.branchCode = branchCode;
	}

	@Override
	public PKUser find(String login) {
		logger.info("PKCore - PKUserImp - function #find ");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PKUser pkUser = null;
		if (this.connect != null) {
			try {
				String query = "select s_user_login,s_user_password,n_user_is_active,s_local,s_profil,"
						+ "d_last_cnx_date,n_nb_cnx_failed,s_user_first_name,s_user_last_name,s_user_email,s_user_mobile,s_pk_id_session "
						+ "from T_AUTH_USER where s_user_login = ?";
				stmt = connect.prepareStatement(query);

				stmt.setString(1, login);
				rs = stmt.executeQuery();
				if (rs.next()) {
					pkUser = new PKUser();
					pkUser.setnIsActive(rs.getInt("n_user_is_active"));
					pkUser.setnNbCnxFailed(rs.getInt("n_nb_cnx_failed"));
					pkUser.setsLastSessionId(rs.getString("s_pk_id_session"));
					pkUser.setsLocale(rs.getString("s_local"));
					pkUser.setsLogin(rs.getString("s_user_login"));
					pkUser.setsMail(rs.getString("s_user_email"));
					pkUser.setsPassword(rs.getString("s_user_password"));
					pkUser.setsPhone(rs.getString("s_user_mobile"));
					pkUser.setsProfile(rs.getString("s_profil"));
				}
			} catch (Exception e) {
				logger.error("Error ", e);
				e.printStackTrace();
			} finally {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return pkUser;
	}
	@Override
	public PKUser find(String login,String locale,String profile) {
		logger.info("PKCore - PKUserImp - function #find ");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PKUser pkUser = null;
		if (this.connect != null) {
			try {
				String query = "select s_user_login,s_user_password,n_user_is_active,s_local,s_profil,"
						+ "d_last_cnx_date,n_nb_cnx_failed,s_user_first_name,s_user_last_name,s_user_email,s_user_mobile,s_pk_id_session "
						+ "from T_AUTH_USER where s_user_login = ? and s_local=? and s_profil=?";
				stmt = connect.prepareStatement(query);

				stmt.setString(1, login);
				stmt.setString(2, locale);
				stmt.setString(3, profile);
				rs = stmt.executeQuery();
				if (rs.next()) {
					pkUser = new PKUser();
					pkUser.setnIsActive(rs.getInt("n_user_is_active"));
					pkUser.setnNbCnxFailed(rs.getInt("n_nb_cnx_failed"));
					pkUser.setsLastSessionId(rs.getString("s_pk_id_session"));
					pkUser.setsLocale(rs.getString("s_local"));
					pkUser.setsLogin(rs.getString("s_user_login"));
					pkUser.setsMail(rs.getString("s_user_email"));
					pkUser.setsPassword(rs.getString("s_user_password"));
					pkUser.setsPhone(rs.getString("s_user_mobile"));
					pkUser.setsProfile(rs.getString("s_profil"));
				}
			} catch (Exception e) {
				logger.error("Error ", e);
				e.printStackTrace();
			} finally {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return pkUser;
	}
	@Override
	public PKUser findByPKSessionId(String pkSessionId) {
		logger.info("PKCore - PKUserImp - function #findByPKSessionId ");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PKUser pkUser = new PKUser();
		if (this.connect != null) {

			try {
				String query = "select s_user_login,s_user_password,n_user_is_active,s_local,s_profil,"
						+ "d_last_cnx_date,n_nb_cnx_failed,s_user_first_name,s_user_last_name,s_user_email,s_user_mobile,s_pk_id_session "
						+ "from T_AUTH_USER where s_pk_id_session = ? and "
						+ "DATEDIFF( second , d_last_cnx_date , getdate()) < 14400 ";
				stmt = connect.prepareStatement(query);

				stmt.setString(1, pkSessionId);
				rs = stmt.executeQuery();
				if (rs.next()) {
					pkUser = new PKUser();
					pkUser.setnIsActive(rs.getInt("n_user_is_active"));
					pkUser.setnNbCnxFailed(rs.getInt("n_nb_cnx_failed"));
					pkUser.setsLastSessionId(rs.getString("s_pk_id_session"));
					pkUser.setsLocale(rs.getString("s_local"));
					pkUser.setsLogin(rs.getString("s_user_login"));
					pkUser.setsMail(rs.getString("s_user_email"));
					pkUser.setsPassword(rs.getString("s_user_password"));
					pkUser.setsPhone(rs.getString("s_user_mobile"));
					pkUser.setsProfile(rs.getString("s_profil"));
				} else {
					pkUser = null;
				}
			} catch (Exception e) {
				logger.error("Error ", e);
				pkUser = null;
				e.printStackTrace();
			} finally {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return pkUser;
	}

	public boolean CNXFailed(String login) {
		logger.info("PKCore - PKUserImp - function #CNXFailed ");
		PreparedStatement stmt = null;
		boolean isOk = true;
		try {
			String query = "UPDATE  T_AUTH_USER set " + "n_nb_cnx_failed = n_nb_cnx_failed + 1 "
					+ "where s_user_login = ? ";
			stmt = connect.prepareStatement(query);
			stmt.setString(1, login);
			stmt.executeUpdate();

		} catch (Exception e) {
			logger.error("Error ", e);
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

	public boolean CNXSuccess(String login, String pkSessionId) {
		logger.info("PKCore - PKUserImp - function #CNXSuccess ");
		PreparedStatement stmt = null;
		boolean isOk = true;
		try {
			String query = "UPDATE  T_AUTH_USER set "
					+ "n_nb_cnx_failed = 0 , d_last_cnx_date = getdate(), s_pk_id_session = ? "
					+ " where s_user_login = ? ";
			stmt = connect.prepareStatement(query);
			stmt.setString(1, pkSessionId);
			stmt.setString(2, login);
			stmt.executeUpdate();

		} catch (Exception e) {
			logger.error("Error ", e);
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

	public boolean updateLastCnx(String login, String pkSessionId) {
		logger.info("PKCore - PKUserImp - function #updateLastCnx ");
		PreparedStatement stmt = null;
		boolean isOk = true;
		try {
			String query = "UPDATE  T_AUTH_USER set " + "d_last_cnx_date = getdate() "
					+ " where s_user_login = ? and  s_pk_id_session = ?";
			stmt = connect.prepareStatement(query);
			stmt.setString(1, login);
			stmt.setString(2, pkSessionId);
			stmt.executeUpdate();

		} catch (Exception e) {
			logger.error("Error ", e);
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
	public boolean lockAccount(String login) {
		logger.info("PKCore - PKUserImp - function #lockAccount ");
		PreparedStatement stmt = null;
		boolean isOk = true;
		try {
			String query = "UPDATE  T_AUTH_USER set " + "n_user_is_active = 0 " + "where s_user_login = ? ";
			stmt = connect.prepareStatement(query);
			stmt.setString(1, login);
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