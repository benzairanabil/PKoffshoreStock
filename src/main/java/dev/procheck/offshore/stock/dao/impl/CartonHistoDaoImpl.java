package dev.procheck.offshore.stock.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import dev.procheck.offshore.stock.daoo.CartonHistoDao;
import dev.procheck.offshore.stock.models.Carton;
import dev.procheck.offshore.stock.models.CartonHisto;

public class CartonHistoDaoImpl extends CartonHistoDao<Carton> {
	final static Logger logger = Logger.getLogger(CartonHistoDaoImpl.class);

	public CartonHistoDaoImpl(Connection conn) {
		super(conn);
	}

	public boolean addHistoCarton(CartonHisto histo) {
		boolean isOk = true;
		PreparedStatement ps = null;
		try {

			String req = "insert into T_HISTO_CARTON  values (?,?,?,?,?,?)";
			ps = connect.prepareStatement(req);
			ps.setString(1, histo.getCartonNum());
			ps.setString(2, histo.getLocale());
			ps.setString(3, histo.getUserLogin());
			ps.setString(4, histo.getCodeBarreArchivage());
			ps.setTimestamp(5, new Timestamp(histo.getDateHisto().getTime()));
			ps.setInt(6, histo.getStatus());
			ps.executeUpdate();

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

	public List<CartonHisto> getHistoCarton(String numero) {
		List<CartonHisto> histos = new ArrayList<CartonHisto>();
		PreparedStatement ps = null;
		try {
			ResultSet rs = null;
			String req = "select TH.d_date_histo,TH.s_user_login ,TS.libelle from T_HISTO_CARTON TH inner join T_STATUS_CARTON TS on th.n_status=TS.status where TH.s_carton_num=?  order by d_date_histo";
			ps = connect.prepareStatement(req);
			ps.setString(1, numero);
			rs = ps.executeQuery();
			while (rs.next()) {
				CartonHisto histo = new CartonHisto();
				histo.setDateHisto(rs.getTimestamp(1));
				histo.setLocale(rs.getString(3));
				histo.setUserLogin(rs.getString(2));
				histos.add(histo);
			}
		} catch (Exception ex) {
			logger.error("#Exception#", ex);
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}

		return histos;
	}

}
