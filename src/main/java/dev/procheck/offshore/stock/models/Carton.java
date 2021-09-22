package dev.procheck.offshore.stock.models;

import java.util.Date;


public class Carton  {
	
	private String numPalette;
    private String numero;
    private double poidsNet;
    private double poidsBrut;
    private String codeBarre;
    private String typeCarton;
    private String cb_archivage;
    private Date dateCaractirisation;
    public String getCb_archivage() {
		return cb_archivage;
	}
	public void setCb_archivage(String cb_archivage) {
		this.cb_archivage = cb_archivage;
	}
	
    
	public String getTypeCarton() {
		return typeCarton;
	}
	public void setTypeCarton(String typeCarton) {
		this.typeCarton = typeCarton;
	}
	public Date getDateCaractirisation() {
		return dateCaractirisation;
	}
	public void setDateCaractirisation(Date dateCaractirisation) {
		this.dateCaractirisation = dateCaractirisation;
	}
	public String getNumPalette() {
		return numPalette;
	}
	public void setNumPalette(String numPalette) {
		this.numPalette = numPalette;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public double getPoidsNet() {
		return poidsNet;
	}
	public void setPoidsNet(double poidsNet) {
		this.poidsNet = poidsNet;
	}
	public double getPoidsBrut() {
		return poidsBrut;
	}
	public void setPoidsBrut(double poidsBrut) {
		this.poidsBrut = poidsBrut;
	}
	public String getCodeBarre() {
		return codeBarre;
	}
	public void setCodeBarre(String codeBarre) {
		this.codeBarre = codeBarre;
	}
}
