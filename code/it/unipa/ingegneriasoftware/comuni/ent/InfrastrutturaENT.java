package it.unipa.ingegneriasoftware.comuni.ent;

import java.util.Map;

public class InfrastrutturaENT {

	private int codice_infrastruttura;
	private String nome;
	private String citta;

	public InfrastrutturaENT(int codice_infrastruttura, String nome, String citta) {
		this.setCodice_infrastruttura(codice_infrastruttura);
		this.setNome(nome);
		this.setCitta(citta);
	}

	public InfrastrutturaENT(Map<String, Object> mappa) {
		this(Integer.parseInt(mappa.get("cod_infrastruttura").toString()), mappa.get("nome").toString(),
				mappa.get("citta").toString());
	}

	public int getCodice_infrastruttura() {
		return codice_infrastruttura;
	}

	public void setCodice_infrastruttura(int codice_infrastruttura) {
		this.codice_infrastruttura = codice_infrastruttura;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}
}