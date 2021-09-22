package dev.procheck.offshore.stock.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.simple.JSONObject;

import dev.procheck.offshore.stock.dao.impl.CartonDaoImpl;
import dev.procheck.offshore.stock.dao.impl.CartonHistoDaoImpl;
import dev.procheck.offshore.stock.models.Carton;
import dev.procheck.offshore.stock.models.CartonHisto;
import dev.procheck.offshore.stock.utils.PKConnect;
import dev.procheck.offshore.stock.utils.PKSecurity;

/**
 * Servlet implementation class ConsultationCartonController
 */
@WebServlet("/ConsultationCarton")
public class ConsultationCartonController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(ConsultationCartonController.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ConsultationCartonController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings({ "unused", "unchecked" })
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
					|| (!profile.equalsIgnoreCase("DEPOT") && !profile.equalsIgnoreCase("PRODSCAN")
							&& !profile.equalsIgnoreCase("PRODCTRL") && !profile.equalsIgnoreCase("ADMIN"))) {
				response.sendRedirect(response.encodeRedirectURL("login"));
				try {
					cnxToDb.close();
				} catch (Exception e) {
					logger.error("Error ", e);
					// TODO: handle exception
				}
				return;
			}
			String numero = request.getParameter("numero");
			String action = request.getParameter("action");
			if (action != null && action.equals("steps")) {
				List<CartonHisto> histos = new ArrayList<CartonHisto>();
				CartonHistoDaoImpl cartonHistoDaoImpl = new CartonHistoDaoImpl(cnxToDb);
				histos = cartonHistoDaoImpl.getHistoCarton(numero);
				JSONArray ja = new JSONArray(histos);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(ja.toString());
			} else {
				List<Carton> cartons = new ArrayList<Carton>();
				CartonDaoImpl cartonDaoImpl = new CartonDaoImpl(cnxToDb);
				cartons = cartonDaoImpl.allCarton();
				request.setAttribute("cartons", cartons);
				this.getServletContext().getRequestDispatcher("/WEB-INF/Stock/ConsultationCartons.jsp").forward(request,
						response);
			}
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
