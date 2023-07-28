package it.unipa.ingegneriasoftware.comuni.ent;

import java.util.Date;
import java.util.Map;

public class StipendioENT {

	private int matricola;
	private Date data_accredito;
	private double stipendio_mensile;

	public StipendioENT(int matricola, Date data_accredito, double stipendio_mensile) {
		this.setMatricola(matricola);
		this.setData_accredito(data_accredito);
		this.setStipendio_mensile(stipendio_mensile);
	}

	public StipendioENT(Map<String, Object> mappa) {
		this(Integer.parseInt(mappa.get("ref_dipendente").toString()),(Date) mappa.get("data_accredito"),
				Double.parseDouble(mappa.get("stipendio_mensile").toString()));
	}

	public int getMatricola() {
		return matricola;
	}

	public void setMatricola(int matricola) {
		this.matricola = matricola;
	}

	public Date getData_accredito() {
		return data_accredito;
	}

	public void setData_accredito(Date data_accredito) {
		this.data_accredito = data_accredito;
	}

	public double getStipendio_mensile() {
        return stipendio_mensile;
	}

	public void setStipendio_mensile(double stipendio_mensile) {
		this.stipendio_mensile = stipendio_mensile;
	}

	/*
	 * public TurnoENT(Map<String, Object> mappa) {
	 * this(Integer.parseInt(mappa.get("matricola").toString()), ? Date PARSE?,
	 * Double.valueOf(mappa.get("stipendio_mensile").toString())); //char sequence
	 * }
	 */
}