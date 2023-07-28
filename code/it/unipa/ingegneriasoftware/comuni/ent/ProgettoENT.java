package it.unipa.ingegneriasoftware.comuni.ent;

import java.util.Map;

public class ProgettoENT {

	private int codice_progetto;
	private String descrizione;
	private int stato_progetto;

	public ProgettoENT(int codice_progetto, String descrizione, int stato_progetto) {
		this.setCodice_progetto(codice_progetto);
		this.setDescrizione(descrizione);
		this.setStato_progetto(stato_progetto);
	}

	public ProgettoENT(Map<String, Object> mappa) {
		this(Integer.parseInt(mappa.get("cod_progetto").toString()), mappa.get("descrizione").toString(),
				Integer.parseInt(mappa.get("stato_progetto").toString()));
	}

	public int getCodice_progetto() {
		return codice_progetto;
	}

	public void setCodice_progetto(int codice_progetto) {
		this.codice_progetto = codice_progetto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getStato_progetto() {
		return stato_progetto;
	}

	public void setStato_progetto(int stato_progetto) {
		this.stato_progetto = stato_progetto;
	}
}