package dev.procheck.offshore.stock.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import dev.procheck.offshore.stock.dao.impl.CartonDaoImpl;
import dev.procheck.offshore.stock.dao.impl.CartonHistoDaoImpl;
import dev.procheck.offshore.stock.models.CartonHisto;
import dev.procheck.offshore.stock.utils.PKConnect;
import dev.procheck.offshore.stock.utils.PKSecurity;

@WebServlet("/ChangePositionCarton")
public class ChangePositionCartonController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(ChangePositionCartonController.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangePositionCartonController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection cnxToDb = null;
		cnxToDb = PKConnect.getCnxToDb();
		try {
			HttpSession session = request.getSession();
			String profile = session.getAttribute("profile") == null ? null
					: session.getAttribute("profile").toString();
			if (!PKSecurity.isUserAuth(request, response, cnxToDb) || (!profile.equalsIgnoreCase("DEPOT")
					&& !profile.equalsIgnoreCase("PRODSCAN") && !profile.equalsIgnoreCase("PRODCRTL"))) {
				response.sendRedirect(response.encodeRedirectURL("login"));
				try {
					cnxToDb.close();
				} catch (Exception e) {
					logger.error("Error ", e);
					// TODO: handle exception
				}
				return;
			}
			// TODO Auto-generated method stub
			JSONObject jo = new JSONObject();
			String code = request.getParameter("code");
			int statusC = Integer.parseInt(request.getParameter("status"));
			String codeArchivage = request.getParameter("code_archive");
			int statusCarton;
			CartonHisto histo = new CartonHisto();
			histo.setCartonNum(code);
			if (codeArchivage != null)
			histo.setCodeBarreArchivage(codeArchivage);
			histo.setLocale(session.getAttribute("locale").toString());
			histo.setUserLogin(session.getAttribute("login").toString());
			Date date = new Date();
			histo.setDateHisto(date);
			histo.setStatus(statusC);
			CartonDaoImpl cartonDaoImpl = new CartonDaoImpl(cnxToDb);
			CartonHistoDaoImpl cartonHistoDaoImpl = new CartonHistoDaoImpl(cnxToDb);
			boolean isCommit = true;
			cnxToDb.setAutoCommit(false);
			if (code != null) {
				try {
					if (cartonDaoImpl.checkCarton(code)) {
						statusCarton = cartonDaoImpl.getStatus(code);
						if (statusC == 1) {
							switch (statusCarton) {
							case 1:
								jo.put("msg", "Le carton (" + code + ") est d�ja dans votre d�p�t !");
								jo.put("status", false);
								break;
							case 99:
								jo.put("msg", "Le carton(" + code + ") est archiv� !");
								jo.put("status", false);
								break;
							default:
								if (cartonDaoImpl.updateStatus(code, statusC, codeArchivage)
										&& cartonHistoDaoImpl.addHistoCarton(histo)) {
									isCommit = true;
									jo.put("msg", "Le carton (" + code + ") est bien enregistr� ");
									jo.put("status", true);
								} else {
									isCommit = false;
									jo.put("msg", "is commit = false");
									jo.put("status", false);
								}
								
							}
						} else if (statusC == 2) {

							switch (statusCarton) {

							case 2:
								jo.put("msg", "Le carton(" + code + ") D�ja envoyer !");
								jo.put("status", false);
								break;
							case 99:
								jo.put("msg", "Le carton(" + code + ") est archiv� !");
								jo.put("status", false);
								break;
							default:
								if (cartonDaoImpl.updateStatus(code, statusC, codeArchivage)
										&& cartonHistoDaoImpl.addHistoCarton(histo)) {
									isCommit = true;
									jo.put("msg", "Le carton(" + code + ") Bien envoyer !");
									jo.put("status", true);
								} else {
									isCommit = false;
									
								}
								
							}

						} else if (statusC == 3) {

							switch (statusCarton) {
							case 3:
								jo.put("msg", "Le carton(" + code + ") D�ja receptioner !");
								jo.put("status", false);
								break;
							case 99:
								jo.put("msg", "Le carton(" + code + ") est archiv� !");
								jo.put("status", false);
								break;
							default:
								if (cartonDaoImpl.updateStatus(code, statusC, codeArchivage)
										&& cartonHistoDaoImpl.addHistoCarton(histo)) {
									jo.put("msg", "Le carton(" + code + ") bien receptioner ");
									jo.put("status", true);
									isCommit = true;
									
								} else {
									
									jo.put("msg", "is commit = false");
									jo.put("status", false);
									isCommit = false;
								}
								
								
							}

						} else if (statusC == 4) {

							switch (statusCarton) {
							case 4:
								jo.put("msg", "Le carton(" + code + ") D�ja envoyer !");
								jo.put("status", false);
								break;
							case 99:
								jo.put("msg", "Le carton(" + code + ") est archiv� !");
								jo.put("status", false);
								break;
							default:
								if (cartonDaoImpl.updateStatus(code, statusC, codeArchivage)
										&& cartonHistoDaoImpl.addHistoCarton(histo)) {
									isCommit = true;
									
								} else {
									isCommit = false;
								}
								jo.put("msg", "Le carton(" + code + ") Bien envoyer !");
								jo.put("status", true);
								
							}
						} else if (statusC == 5) {

							switch (statusCarton) {
							case 5:
								jo.put("msg", "Le carton(" + code + ") D�ja receptionner !");
								jo.put("status", false);
								break;
							case 99:
								jo.put("msg", "Le carton(" + code + ") est archiv� !");
								jo.put("status", false);
								break;
							default:
								if (cartonDaoImpl.updateStatus(code, statusC, codeArchivage)
										&& cartonHistoDaoImpl.addHistoCarton(histo)) {
									isCommit = true;
									
								} else {
									isCommit = false;
								}
								jo.put("msg", "Le carton(" + code + ") Bien receptioner !");
								jo.put("status", true);
								
							}
						} else if (statusC == 6) {

							switch (statusCarton) {
							case 6:
								jo.put("msg", "Le carton(" + code + ") D�ja envoyer !");
								jo.put("status", false);
								break;
							case 99:
								jo.put("msg", "Le carton(" + code + ") est archiv� !");
								jo.put("status", false);
								break;
							default:
								if (cartonDaoImpl.updateStatus(code, statusC, codeArchivage)
										&& cartonHistoDaoImpl.addHistoCarton(histo)) {
									isCommit = true;
									
								} else {
									isCommit = false;
								}
								jo.put("msg", "Le carton(" + code + ") Bien envoyer !");
								jo.put("status", true);
								
							}
						} else if (statusC == 99) {
							if (code == codeArchivage) {
								jo.put("msg", "Vous avez scanner le meme code barre !");
								jo.put("status", false);
							} else {
								switch (statusCarton) {
								case 99:
									jo.put("msg", "Le carton(" + code + ") D�ja archiv� !");
									jo.put("status", false);
									break;
								default:
									if (cartonDaoImpl.updateStatus(code, statusC, codeArchivage)
											&& cartonHistoDaoImpl.addHistoCarton(histo)) {
										isCommit = true;
										
									} else {
										isCommit = false;
									}
									jo.put("msg", "Le carton(" + code + ") Bien archiver !");
									jo.put("status", true);
								}
							}
						} else if (statusC == -9999) {
							jo.put("msg", "Probleme technique !!!!!");
							jo.put("status", false);
						} 
					}
					else {
						jo.put("msg", "Le carton(" + code + ") Non trouv� !");
						jo.put("status", false);
					}
				} catch (Exception ex) {
					jo.put("msg", ex.getMessage());
					jo.put("status", false);
				}
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jo.toString());
			}
			if (isCommit) {
				cnxToDb.commit();
			} else {
				cnxToDb.rollback();
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
