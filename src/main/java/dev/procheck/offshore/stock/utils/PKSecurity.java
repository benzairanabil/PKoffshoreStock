package dev.procheck.offshore.stock.utils;

import java.sql.Connection;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dev.procheck.offshore.stock.dao.impl.PKUserImp;
import dev.procheck.offshore.stock.models.PKUser;
import procheck.dev.lib.PCHDateFunc;

public class PKSecurity {
	public static boolean isUserAuth(HttpServletRequest request, HttpServletResponse response, 
			Connection cnxToDb) {

		boolean isOk = false;
		String pkSessionId = null;
		try {

			System.out.println("Start isUserAuth => ");
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				System.out.println("cookies != null");
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("PKSESSIONID-STOCK")) {
						System.out.println("PKSESSIONID found : " + cookie.getValue());
						pkSessionId = cookie.getValue();
						PKUser user = new PKUser();
						PKUserImp pkUserImp = new PKUserImp(cnxToDb);
						String ipAddress = null;
						ipAddress = request.getHeader("X-FORWARDED-FOR"); // proxy
						if (ipAddress == null) {
							ipAddress = request.getRemoteAddr();
						}
						user = pkUserImp.findByPKSessionId(pkSessionId + "-From-" + ipAddress);
						if (user != null) {

							if (request.getSession().getAttribute("login") == null) {
								request.getSession().setAttribute("login", user.getsLogin());
							}
							if (request.getSession().getAttribute("locale") == null) {
								request.getSession().setAttribute("locale", user.getsLocale());
							}
							if (request.getSession().getAttribute("profile") == null) {
								request.getSession().setAttribute("profile", user.getsProfile());
							}
						}
						pkUserImp.updateLastCnx(user.getsLogin(), pkSessionId);
						isOk = true;
					}
				}
			}
			long timeStart = (new Date()).getTime();
			System.out.println("User ["
					+ (request.getSession().getAttribute("login") != null
							? request.getSession().getAttribute("login").toString()
							: "???????")
					+ " | " + request.getRemoteAddr() + "] Session data ID[" + request.getSession().getId()
					+ "] timeconnect[" + ((timeStart - request.getSession().getCreationTime()) / 1000)
					+ "(s)] getMaxInactiveInterval[" + request.getSession().getMaxInactiveInterval() + "] ["
					+ request.getQueryString() + "] ==> " + isOk + " <==");
		} catch (Exception e) {
			isOk = false;
			e.printStackTrace();
		}
		if (!isOk) {
			try {
				System.out.println("Delete Session & Cookies");
				request.getSession().invalidate();
				Cookie cookie = new Cookie("PKSESSIONID", "");
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
				request.setAttribute("error", "Vous n'avez pas le droit d'accès à cette application !");
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				cnxToDb.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return isOk;
	}

	public static String getPKSessionId() {
		String uniqueID = UUID.randomUUID().toString();
		return uniqueID + "-pk-" + PCHDateFunc.getDateTimeddMMyyHHmmssS();
	}
}
