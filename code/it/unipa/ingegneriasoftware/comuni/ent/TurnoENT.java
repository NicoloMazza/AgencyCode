package it.unipa.ingegneriasoftware.comuni.ent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class TurnoENT {

	// turno previsto

	private int matricola;
	private String codice_turno;
	private LocalDate data;
	private LocalTime ora_inizio;
	private LocalTime ora_fine;

	// turno effettivo

	/*
	 * private int stato_uscita;
	 * private int stato_entrata;
	 * private LocalTime ora_inizio_effettiva;
	 * private LocalTime ora_fine_effettiva;
	 * private double stipendio_parziale;
	 */
	public TurnoENT(int matricola, String codice_turno, LocalDate data, LocalTime ora_inizio, LocalTime ora_fine) {

		// int stato_entrata, int stato_uscita, LocalTime ora_inizio_effettiva,
		// LocalTime ora_fine_effettiva,
		// double stipendio_parziale) {
		this.setMatricola(matricola);
		this.setCodice_turno(codice_turno);
		this.setData(data);
		this.setOra_inizio(ora_inizio);
		this.setOra_fine(ora_fine);
		/*
		 * this.setStato_entrata(stato_entrata);
		 * this.setStato_uscita(stato_uscita);
		 * this.setOra_inizio_effettiva(ora_inizio_effettiva);
		 * this.setOra_fine_effettiva(ora_fine_effettiva);
		 * this.setStipendio_parziale(stipendio_parziale);
		 */
	}

	public TurnoENT(Map<String, Object> mappa) {
		this(Integer.parseInt(mappa.get("ref_dipendente").toString()),
				mappa.get("cod_turno").toString(),
				LocalDate.parse(mappa.get("data_turno").toString()),
				LocalTime.parse(mappa.get("ora_inizio").toString()),
				LocalTime.parse(mappa.get("ora_fine").toString())); // ); da levare e mettere la ,
		/*
		 * Integer.parseInt(mappa.get("stato_uscita").toString()),
		 * Integer.parseInt(mappa.get("stato_entrata").toString()),
		 * LocalTime.parse(mappa.get("ora_inizio_effettiva").toString()),
		 * LocalTime.parse(mappa.get("ora_fine_effettiva").toString()),
		 * Double.parseDouble(mappa.get("stipendio_parziale").toString()));
		 */
	}

	public int getMatricola() {
		return matricola;
	}

	public void setMatricola(int matricola) {
		this.matricola = matricola;
	}

	public String getCodice_turno() {
		return codice_turno;
	}

	public void setCodice_turno(String codice_turno) {
		this.codice_turno = codice_turno;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getOra_inizio() {
		return ora_inizio;
	}

	public void setOra_inizio(LocalTime ora_inizio) {
		this.ora_inizio = ora_inizio;
	}

	public LocalTime getOra_fine() {
		return ora_fine;
	}

	public void setOra_fine(LocalTime ora_fine) {
		this.ora_fine = ora_fine;
	}

	/*
	 * public int getStato_uscita() {
	 * return stato_uscita;
	 * }
	 * 
	 * public void setStato_uscita(int stato_uscita) {
	 * this.stato_uscita = stato_uscita;
	 * }
	 * 
	 * public int getStato_entrata() {
	 * return stato_entrata;
	 * }
	 * 
	 * public void setStato_entrata(int stato_entrata) {
	 * this.stato_entrata = stato_entrata;
	 * }
	 * 
	 * public LocalTime getOra_inizio_effettiva() {
	 * return ora_inizio_effettiva;
	 * }
	 * 
	 * public void setOra_inizio_effettiva(LocalTime ora_inizio_effettiva) {
	 * this.ora_inizio_effettiva = ora_inizio_effettiva;
	 * }
	 * 
	 * public LocalTime getOra_fine_effettiva() {
	 * return ora_fine_effettiva;
	 * }
	 * 
	 * public void setOra_fine_effettiva(LocalTime ora_fine_effettiva) {
	 * this.ora_fine_effettiva = ora_fine_effettiva;
	 * }
	 * 
	 * public double getStipendio_parziale() {
	 * return stipendio_parziale;
	 * }
	 * 
	 * public void setStipendio_parziale(double stipendio_parziale) {
	 * this.stipendio_parziale = stipendio_parziale;
	 * }
	 */

	/*
	 * public TurnoENT(Map<String, Object> mappa) {
	 * this(Integer.parseInt(mappa.get("matricola").toString()),
	 * mappa.get("codice_turno").toString(),
	 * ?LocalDate PARSE ?, ?LocalTime PARSE ?, ?LocalTime PARSE ?); //char sequence
	 * }
	 */
}