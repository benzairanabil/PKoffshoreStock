package dev.procheck.offshore.stock.models;

public class PKUser {
	String sLogin;
	String sPassword;
	String sLocale; //
	String sProfile; //
	String sPhone;
	String sMail;
	String sLastSessionId;
	int nNbCnxFailed;
	int nIsActive;

	public PKUser() {
		// TODO Auto-generated constructor stub
	}

	public String getsLogin() {
		return sLogin;
	}

	public void setsLogin(String sLogin) {
		this.sLogin = sLogin;
	}

	public String getsPassword() {
		return sPassword;
	}

	public void setsPassword(String sPassword) {
		this.sPassword = sPassword;
	}

	public String getsPhone() {
		return sPhone;
	}

	public void setsPhone(String sPhone) {
		this.sPhone = sPhone;
	}

	public String getsMail() {
		return sMail;
	}

	public void setsMail(String sMail) {
		this.sMail = sMail;
	}

	public String getsLastSessionId() {
		return sLastSessionId;
	}

	public void setsLastSessionId(String sLastSessionId) {
		this.sLastSessionId = sLastSessionId;
	}

	public int getnNbCnxFailed() {
		return nNbCnxFailed;
	}

	public void setnNbCnxFailed(int nNbCnxFailed) {
		this.nNbCnxFailed = nNbCnxFailed;
	}

	public int getnIsActive() {
		return nIsActive;
	}

	public void setnIsActive(int nIsActive) {
		this.nIsActive = nIsActive;
	}

	public String getsLocale() {
		return sLocale;
	}

	public void setsLocale(String sLocale) {
		this.sLocale = sLocale;
	}

	public String getsProfile() {
		return sProfile;
	}

	public void setsProfile(String sProfile) {
		this.sProfile = sProfile;
	}
}
