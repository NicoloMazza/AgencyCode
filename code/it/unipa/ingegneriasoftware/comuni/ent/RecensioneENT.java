package it.unipa.ingegneriasoftware.comuni.ent;

import java.util.Map;

public class RecensioneENT {

	private String testo;
	private int punteggio;
	private int ref_segnalazione;

	public RecensioneENT(int ref_segnalazione, String testo, int punteggio) {
		this.setRef_segnalazione(ref_segnalazione);
		this.setPunteggio(punteggio);
		this.setTesto(testo);
	}

	public RecensioneENT(Map<String, Object> mappa) {
		this(Integer.parseInt(mappa.get("ref_segnalazione").toString()),mappa.get("testo").toString(),
				Integer.parseInt(mappa.get("punteggio").toString()));
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public int getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}

	public int getRef_segnalazione() {
		return ref_segnalazione;
	}

	public void setRef_segnalazione(int ref_segnalazione) {
		this.ref_segnalazione = ref_segnalazione;
	}


}