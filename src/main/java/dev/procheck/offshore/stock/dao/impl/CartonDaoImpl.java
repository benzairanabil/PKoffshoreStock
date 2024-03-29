package dev.procheck.offshore.stock.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import dev.procheck.offshore.stock.daoo.CartonDao;
import dev.procheck.offshore.stock.models.Carton;

public class CartonDaoImpl extends CartonDao<Carton> {
	final static Logger logger = Logger.getLogger(CartonDaoImpl.class);

	public CartonDaoImpl(Connection conn) {
		super(conn);
	}

	public String importCartons(List<Carton> cartons) throws SQLException {
		String message = "";
		if (cartons == null) {
			message = "Carton null";
		}
		PreparedStatement ps = null;
		try {

			for (Carton carton : cartons) {
				if (!checkCarton(carton.getNumero())) {
					String req = "insert into T_DATA_CARTON (s_numero,d_date_caractrisation,s_type,s_barre_code) values (?,?,?,?)";
					ps = connect.prepareStatement(req);
					ps.setString(1, carton.getNumero());
					ps.setDate(2, carton.getDateCaractirisation() == null ? null
							: new Date(carton.getDateCaractirisation().getTime()));
					ps.setString(3, carton.getTypeCarton());
					ps.setString(4, carton.getNumero());
					ps.executeUpdate();
				}
			}
			message = "Les donnes bien import�";
		} catch (Exception ex) {
			logger.error("#Exception#", ex);
			message = ex.getMessage();
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}
		return message;
	}

	public boolean updateStatus(String code, int status, String cbArchivage) {
		boolean isOk = true;
		PreparedStatement ps = null;
		try {

			String req = "update T_DATA_CARTON SET " + "n_status=? , s_cb_archivage=? " + " where s_barre_code=? or s_numero=? ";
			ps = connect.prepareStatement(req);
			ps.setInt(1, status);
			ps.setString(2, cbArchivage);
			ps.setString(3, code);
			ps.setString(4, code);
			int temoin=ps.executeUpdate();
			isOk = temoin == 1 ? true : false;
			logger.info("updateStatus carton temoin =  "+temoin);
			System.out.println("updateStatus carton temoin = "+temoin + "  s_barre_code="+code); 
		} catch (Exception ex) {
			logger.error("#Exception#", ex);
			isOk = false;
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}
		return isOk;
	}

	public int getStatusCarton(String code) throws SQLException {
		int statu = -1;

		PreparedStatement ps = null;
		try {
			ResultSet result = null;
			String reqCheck = "select n_status from T_DATA_CARTON where s_barre_code=?";
			ps = connect.prepareStatement(reqCheck);
			ps.setString(1, code);
			result = ps.executeQuery();
			if (result.next()) {
				statu = result.getInt(1);
			}

		} catch (Exception ex) {
			logger.error("#Exception#", ex);
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}

		return statu;
	}

	public boolean checkCarton(String code) throws SQLException {
		boolean status = false;

		PreparedStatement ps = null;
		try {
			ResultSet result = null;
			String reqCheck = "select * from T_DATA_CARTON where s_barre_code=? or s_numero=?";
			ps = connect.prepareStatement(reqCheck);
			ps.setString(1, code);
			ps.setString(2, code);
			result = ps.executeQuery();
			if (result.next()) {
				status = true;
			}

		} catch (Exception ex) {
			logger.error("#Exception#", ex);
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}
		return status;
	}

	public List<Carton> allCarton() throws SQLException {
		List<Carton> cartons = new ArrayList<Carton>();
		PreparedStatement ps = null;
		try {

			ResultSet rs = null;
			String reqCheck = "select s_numero,s_type,d_date_caractrisation,s_cb_archivage from T_DATA_CARTON ";
			ps = connect.prepareStatement(reqCheck);
			rs = ps.executeQuery();
			while (rs.next()) {
				Carton carton = new Carton();
				carton.setNumero(rs.getString(1));
				carton.setTypeCarton(rs.getString(2));
				carton.setDateCaractirisation(rs.getDate(3));
				carton.setCb_archivage(rs.getString(4));
				cartons.add(carton);
			}
		} catch (Exception ex) {
			logger.error("#Exception#", ex);

		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}

		return cartons;
	}

	public int getStatus(String numero) throws SQLException {
		int status = -1;

		PreparedStatement ps = null;
		try {
			ResultSet rs = null;
			String reqCheck = "select n_status from T_DATA_CARTON where s_numero=?";
			ps = connect.prepareStatement(reqCheck);
			ps.setString(1, numero);
			rs = ps.executeQuery();
			if (rs.next()) {
				status = rs.getInt(1);
			}

		} catch (Exception ex) {
			status = -9999;
			logger.error("#Exception#", ex);
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}

		return status;
	}

	public String countCartons() {
		String query = "select count(*) from T_DATA_CARTON ";
		int count = 0;
		JSONObject obj = new JSONObject();
		PreparedStatement ps = null;
		try {
			ps = connect.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
				obj.put("count", count);
			}
		} catch (Exception e) {
			logger.error("#Exception#", e);
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}
		return obj.toString();
	}

}
