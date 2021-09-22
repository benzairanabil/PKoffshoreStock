package dev.procheck.offshore.stock.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import dev.procheck.offshore.stock.dao.impl.CartonDaoImpl;
import dev.procheck.offshore.stock.models.Carton;
import dev.procheck.offshore.stock.utils.Methods;
import dev.procheck.offshore.stock.utils.PKConnect;
import dev.procheck.offshore.stock.utils.PKSecurity;

/**
 * Servlet implementation class ImportController
 */
@WebServlet("/integration")
@MultipartConfig(maxFileSize = 50*1024*1024 , maxRequestSize = 50*1024*1024 ,fileSizeThreshold = 50*1024*1024)
public class ImportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String PATH_TEMP_DIR = "C:\\Temp\\OffshoreServer";
	final static Logger logger = Logger.getLogger(ImportController.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImportController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection cnxToDb = null;
		cnxToDb = PKConnect.getCnxToDb();
		try {
			HttpSession session = request.getSession();
			String profile = session.getAttribute("profile") == null ? null
					: session.getAttribute("profile").toString();
			if (!PKSecurity.isUserAuth(request, response, cnxToDb) || !profile.equalsIgnoreCase("ADMIN")) {
				response.sendRedirect(response.encodeRedirectURL("login"));
				try {
					cnxToDb.close();
				} catch (Exception e) {
					logger.error("Error ", e);
					// TODO: handle exception
				}
				return;
			}
			this.getServletContext().getRequestDispatcher("/WEB-INF/Stock/uploadExcel.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
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
		Connection cnxToDb = null;
		cnxToDb = PKConnect.getCnxToDb();
		try {
			HttpSession session = request.getSession();
			String profile = session.getAttribute("profile") == null ? null
					: session.getAttribute("profile").toString();
			if (!PKSecurity.isUserAuth(request, response, cnxToDb) || !profile.equalsIgnoreCase("ADMIN")) {
				response.sendRedirect(response.encodeRedirectURL("login"));
				try {
					cnxToDb.close();
				} catch (Exception e) {
					logger.error("Error ", e);
					// TODO: handle exception
				}
				return;
			}

			String message = "", color = "red", className = "";
			if (ServletFileUpload.isMultipartContent(request)) {
				try {
					@SuppressWarnings("unchecked")
					List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
					
					for (FileItem item : multiparts) {
						if (!item.isFormField()) {
							String name = new File(item.getName()).getName();
							File fileToProc = new File(PATH_TEMP_DIR + File.separator + name);
							System.out.println(" start write file ");
							item.write(fileToProc);
							System.out.println("Fin write file");
							if (!Methods.checkSheet(fileToProc, "BDD Production Procheck") ) {
								message = "le feuille BDD Production Procheck n'existe pas ou elle est vide";
								color = "red";
								className = "danger";
							} else {
								System.out.println("avant if");
								if (fileToProc.isFile()) {
									System.out.println(" fileToProc.isFile() ");
									List<Carton> cartons = Methods.dataCarton2(fileToProc, "BDD Production Procheck");
									System.out.println("size cartons : " + cartons.size());
									CartonDaoImpl cartonDaoImpl=new CartonDaoImpl(cnxToDb);
									message = cartonDaoImpl.importCartons(cartons);
									if (message.equals("Les donnes bien import�")) {
										color = "green";
										className = "success";
									} else {
										color = "red";
										className = "danger";
									}

								} else {
									message = "!!!!!!!!! Probleeeeeeeeeeeme !!!!!!!!!!!! sur le ficheir :" + name;
									color = "red";
									className = "danger";
								}
							}
						}
					}
				} catch (Exception ex) {
					message = "File Upload Failed due to [" + ex + "]";
					ex.printStackTrace();
				}
			} else {
				message = "Sorry this Servlet only handles file upload request";
				color = "red";
				className = "danger";
			}
			request.setAttribute("feedback", "<font color=\"" + color + "\">" + message + "</font>");
			request.setAttribute("color", color);
			request.setAttribute("className", className);
			this.getServletContext().getRequestDispatcher("/WEB-INF/Stock/uploadExcel.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				cnxToDb.close();
			} catch (Exception e2) {
			}
		}
	}
}
