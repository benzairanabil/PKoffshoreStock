package dev.procheck.offshore.stock.controllers;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import dev.procheck.offshore.stock.dao.impl.PKUserHistoImp;
import dev.procheck.offshore.stock.models.PKUserHisto;
import dev.procheck.offshore.stock.utils.PKConnect;
import dev.procheck.offshore.stock.utils.PKSecurity;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(LogoutController.class);
	public LogoutController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//set Action to User History
		Connection cnxToDb = null;
		cnxToDb = PKConnect.getCnxToDb();
		HttpSession session = request.getSession();
		String userLogin = null;
		try {
			
			String ipAddress = null;
			ipAddress = request.getHeader("X-FORWARDED-FOR"); // proxy
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
			
		
			
			if(session.getAttribute("login")!=null)
			{
				 userLogin = request.getSession().getAttribute("login").toString();
				PKUserHistoImp histoImp = new PKUserHistoImp(cnxToDb);
				PKUserHisto userHisto = new PKUserHisto();
				userHisto.setUserHistoLogin(userLogin);
				userHisto.setUserHistoAction("DNX Success");
				userHisto.setUserHistoIdSession(PKSecurity.getPKSessionId());
				userHisto.setUserIpAdresse(ipAddress);
				histoImp.add(userHisto);
				
				session.removeAttribute("login");
			}
			
			request.setAttribute("msg", "you are successfully logged out ");
			Cookie cookie = new Cookie("PKSESSIONID-STOCK", "");
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
			session.invalidate();
			
		} catch (Exception e) {
			if(session.getAttribute("login")!=null)
			{
				logger.error("PKAuth - LogoutController - error login ["+userLogin + "]",e);
			}
			
			// TODO: handle exception
		} finally {
			try {
				cnxToDb.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		response.sendRedirect(response.encodeRedirectURL("login"));

	}

}
