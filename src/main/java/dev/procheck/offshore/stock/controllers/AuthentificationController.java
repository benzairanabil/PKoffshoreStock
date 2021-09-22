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
import dev.procheck.offshore.stock.dao.impl.PKUserImp;
import dev.procheck.offshore.stock.models.PKUser;
import dev.procheck.offshore.stock.models.PKUserHisto;
import dev.procheck.offshore.stock.utils.CommonFunc;
import dev.procheck.offshore.stock.utils.CryptWithMD5;
import dev.procheck.offshore.stock.utils.EncryptionPassword;
import dev.procheck.offshore.stock.utils.PKConnect;
import dev.procheck.offshore.stock.utils.PKSecurity;

/**
 * Servlet implementation class AuthentificationController
 */
@WebServlet({ "/authentification", "/login" })
public class AuthentificationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(AuthentificationController.class);

	public AuthentificationController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		System.out.println("LogOUt :  "
				+ (session.getAttribute("login") != null ? session.getAttribute("login").toString() : null));
		if (session.getAttribute("login") != null && session.getAttribute("locale") != null
				&& session.getAttribute("profile") != null) {
			Connection cnxToDb = null;
			cnxToDb = PKConnect.getCnxToDb();

			try {

				switch (session.getAttribute("profile").toString()) {
				case "DEPOT":
					response.sendRedirect("scandepot");
					break;
				case "PRODSCAN":
					response.sendRedirect("scanproduction");
					break;
				case "PRODCTRL":
					response.sendRedirect("ControleProduction");
					break;
				case "ADMIN":
					response.sendRedirect("integration");
					break;
				default:
					response.sendRedirect("logout");
				}

			} catch (Exception e) {
				logger.error("PKAuth - LoginController - error login [" + session.getAttribute("login") + "]", e);
				this.getServletContext().getRequestDispatcher("/WEB-INF/Stock/login.jsp").forward(request, response);
				e.printStackTrace();

			} finally {
				try {
					cnxToDb.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} else {
			// request.getServletContext().getSessionCookieConfig().setMaxAge(72000);
			this.getServletContext().getRequestDispatcher("/WEB-INF/Stock/login.jsp").forward(request, response);

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection cnxToDb = null;
		cnxToDb = PKConnect.getCnxToDb();
		String local, profile, login = "", password;
		try {

			PKUserImp pkUserImp = new PKUserImp(cnxToDb);
			HttpSession session = request.getSession(true);

			String authData = request.getParameter("qrcodepass");
			System.out.println(authData);
			EncryptionPassword encryptionPassword = new EncryptionPassword();
			if (authData == null
					|| encryptionPassword.decrypt(CommonFunc.getBase64Decode(authData)).split(";").length != 4) {
				logger.warn("Invalide Authentication data !!");
				request.setAttribute("error", "Invalide Authentication data !!");
				this.getServletContext().getRequestDispatcher("/WEB-INF/Stock/login.jsp").forward(request, response);
				return;
			}

			String data[] = encryptionPassword.decrypt(CommonFunc.getBase64Decode(authData)).split(";");
			System.out.println("Login : " + data[2]);
			System.out.println("Password : " + data[3]);

			local = data[0];
			profile = data[1];
			login = data[2];
			password = data[3];

			PKUser currentUser = pkUserImp.find(login, local, profile);

			if (currentUser != null && currentUser.getsLogin() != null
					&& !currentUser.getsPassword().equals(CryptWithMD5.cryptWithMD5(password))) {

				System.out.println("Password DB : " + currentUser.getsPassword());
				System.out.println("Password Crypted : " + CryptWithMD5.cryptWithMD5(password));
				if (!currentUser.getsPassword().equals(CryptWithMD5.cryptWithMD5(password))) {
					System.out.println("TFO OUI");
				} else {
					System.out.println("TFO NON");
				}
				if (currentUser.getnNbCnxFailed() < 3) {
					pkUserImp.CNXFailed(login);
					request.setAttribute("error", "incorrect Login or password !");
					request.setAttribute("login", login);

					logger.info("PKAuth - LoginController - login [" + login + "] incorrect Login or password !");

					String pkSessionId = PKSecurity.getPKSessionId();
					String ipAddress = null;
					ipAddress = request.getHeader("X-FORWARDED-FOR"); // proxy
					if (ipAddress == null) {
						ipAddress = request.getRemoteAddr();
					}
					PKUserHistoImp histoImp = new PKUserHistoImp(cnxToDb);
					PKUserHisto userHisto = new PKUserHisto();
					userHisto.setUserHistoLogin(currentUser.getsLogin());
					userHisto.setUserHistoAction("CNX Failed");
					userHisto.setUserHistoIdSession(pkSessionId);
					userHisto.setUserIpAdresse(ipAddress);
					histoImp.add(userHisto);

					doGet(request, response);

				} else {
					pkUserImp.lockAccount(login);
					request.setAttribute("error", "your account is locked !");
					request.setAttribute("login", login);
					logger.info("PKAuth - LoginController - login [" + login + "] account is locked !");

					doGet(request, response);
				}

			} else if (currentUser != null && currentUser.getsLogin() != null && currentUser.getnIsActive() == 0) {
				request.setAttribute("error", "your account is locked !");
				request.setAttribute("login", login);
				logger.info("PKAuth - LoginController - login [" + currentUser.getsLogin() + "] account is locked !");
				doGet(request, response);

			} else if (currentUser != null && currentUser.getsLogin() != null && currentUser.getnIsActive() == 1
					&& currentUser.getsPassword().equals(CryptWithMD5.cryptWithMD5(password))) {
				// request.getServletContext().getSessionCookieConfig().setMaxAge(72000);
				session.setAttribute("login", currentUser.getsLogin());
				session.setAttribute("locale", currentUser.getsLocale());
				session.setAttribute("profile", currentUser.getsProfile());
				String ipAddress = null;
				ipAddress = request.getHeader("X-FORWARDED-FOR"); // proxy
				if (ipAddress == null) {
					ipAddress = request.getRemoteAddr();
				}
				String pkSessionId = PKSecurity.getPKSessionId();
				Cookie cookie = new Cookie("PKSESSIONID-STOCK", pkSessionId);
				cookie.setMaxAge(60 * 60 * 8);
				cookie.setPath("/");
				response.addCookie(cookie);
				pkUserImp.CNXSuccess(login, pkSessionId + "-From-" + ipAddress);
				switch (currentUser.getsProfile()) {
				case "DEPOT":
					response.sendRedirect("scandepot");
					break;
				case "PRODSCAN":
					response.sendRedirect("scanproduction");
					break;
				case "PRODCTRL":
					response.sendRedirect("ControleProduction");
					break;
				case "ADMIN":
					response.sendRedirect("integration");
					break;
				default:
					response.sendRedirect("logout");
				}

			} else {
				request.setAttribute("error", "incorrect Login or password !");
				request.setAttribute("login", login);
				doGet(request, response);
			}
		} catch (Exception e) {
			logger.error("PKAuth - LoginController - error !", e);
			request.setAttribute("error", "incorrect Login or password !");
			request.setAttribute("login", login);
			this.getServletContext().getRequestDispatcher("/WEB-INF/Stock/login.jsp").forward(request, response);
			e.printStackTrace();

		} finally {
			try {
				cnxToDb.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
