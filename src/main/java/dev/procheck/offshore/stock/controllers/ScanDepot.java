package dev.procheck.offshore.stock.controllers;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import dev.procheck.offshore.stock.utils.PKConnect;
import dev.procheck.offshore.stock.utils.PKSecurity;

/**
 * Servlet implementation class ScanController
 */
@WebServlet("/scandepot")
public class ScanDepot extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(ScanDepot.class);
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ScanDepot() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection cnxToDb = null;
		cnxToDb = PKConnect.getCnxToDb();
		try {
			HttpSession session = request.getSession();
			String profile = session.getAttribute("profile") == null ? null
					: session.getAttribute("profile").toString();
			if (!PKSecurity.isUserAuth(request, response, cnxToDb)
					|| !profile.equalsIgnoreCase("DEPOT")) {
				response.sendRedirect(response.encodeRedirectURL("login"));
				try {
					cnxToDb.close();
				} catch (Exception e) {
					logger.error("Error ", e);
					// TODO: handle exception
				}
				return;
			}
			this.getServletContext().getRequestDispatcher("/WEB-INF/Stock/depot.jsp").forward(request, response);
		
		} catch (Exception e) {
			logger.error("#Exceptiob#",e);
		} finally {
			try {
				cnxToDb.close();
			} catch (Exception e2) {
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
