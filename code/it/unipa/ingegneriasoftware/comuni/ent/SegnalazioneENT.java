package it.unipa.ingegneriasoftware.comuni.ent;

import java.util.Map;

public class SegnalazioneENT {

	private int codice_segnalazione;
	private String domanda;
	private String risposta;
	private int categoria;

	public SegnalazioneENT(int codice_segnalazione, String domanda, String risposta, int categoria) {
		this.setCodice_segnalazione(codice_segnalazione);
		this.setDomanda(domanda);
		this.setRisposta(risposta);
		this.setCategoria(categoria);
	}

	public SegnalazioneENT(Map<String, Object> mappa) {
		this(Integer.parseInt(mappa.get("cod_segnalazione").toString()), mappa.get("domanda").toString(),
				mappa.get("risposta").toString(), Integer.parseInt(mappa.get("categoria").toString()));
	}

	public int getCodice_segnalazione() {
		return codice_segnalazione;
	}

	public void setCodice_segnalazione(int codice_segnalazione) {
		this.codice_segnalazione = codice_segnalazione;
	}

	public String getDomanda() {
		return domanda;
	}

	public void setDomanda(String domanda) {
		this.domanda = domanda;
	}

	public String getRisposta() {
		return risposta;
	}

	public void setRisposta(String risposta) {
		this.risposta = risposta;
	}

	public int getCategoria() {
		return categoria;
	}

	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}

}