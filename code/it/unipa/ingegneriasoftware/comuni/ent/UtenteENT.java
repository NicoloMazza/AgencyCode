package it.unipa.ingegneriasoftware.comuni.ent;

import java.util.Map;

public class UtenteENT {

    // Utente:
    private int matricola;
    private String nome;
    private String cognome;
    private String email;
    private String pass;
    private int stato_pass;
    private String iban;
    private int stato_utente;
    // Datore:
    private int sezione;
    // Dipendente:
    private int servizio;
    private int ore_da_recuperare;

    public UtenteENT(int matricola, String nome, String cognome, String email, String pass, int stato_pass, String iban, int stato_utente) {
        this.setMatricola(matricola);
        this.setNome(nome);
        this.setCognome(cognome);
        this.setEmail(email);
        this.setPass(pass);
        this.setStato_pass(stato_pass);
        this.setIban(iban);
        this.setStato_utente(stato_utente);
    }

    public UtenteENT(String nome, String cognome, String email, String iban, int servizio) {
        this.setNome(nome);
        this.setCognome(cognome);
        this.setEmail(email);
        this.setIban(iban);
        this.setServizio(servizio);
    }

    public UtenteENT(Map<String, Object> mappa) {
        this(Integer.parseInt(mappa.get("matricola").toString()), mappa.get("nome").toString(),
                mappa.get("cognome").toString(),
                mappa.get("email").toString(), mappa.get("pass").toString(),
                Integer.parseInt(mappa.get("stato_pass").toString()),
                mappa.get("iban").toString(), Integer.parseInt(mappa.get("stato_utente").toString()));
    }

   
    public UtenteENT(UtenteENT entDaAggiornare, Map<String, Object> mappa, int tipo) {
        this.setMatricola(entDaAggiornare.getMatricola());
        this.setNome(entDaAggiornare.getNome());
        this.setCognome(entDaAggiornare.getCognome());
        this.setEmail(entDaAggiornare.getEmail());
        this.setPass(entDaAggiornare.getPass());
        this.setStato_pass(entDaAggiornare.getStato_pass());
        this.setIban(entDaAggiornare.getIban());
        this.setStato_utente(entDaAggiornare.getStato_utente());
        if (tipo == 1) {
            this.setSezione(Integer.parseInt(mappa.get("sezione").toString()));
        }
        else {
            if (tipo == 2) {
                this.setServizio(Integer.parseInt(mappa.get("ref_servizio").toString()));
                this.setOreDaRecuperare(Integer.parseInt(mappa.get("ore_da_recuperare").toString()));
            }
        }
    }

    public int getMatricola() {
        return this.matricola;
    }

    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getStato_pass() {
        return this.stato_pass;
    }

    public void setStato_pass(int stato_pass) {
        this.stato_pass = stato_pass;
    }

    public String getIban() {
        return this.iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public int getStato_utente() {
        return this.stato_utente;
    }

    public void setStato_utente(int stato_utente) {
        this.stato_utente = stato_utente;
    }

    public int getServizio() {
        return servizio;
    }

    public void setServizio(int servizio) {
        this.servizio = servizio;
    }

    public int getOreDaRecuperare() {
        return ore_da_recuperare;
    }

    public void setOreDaRecuperare(int ore_da_recuperare) {
        this.ore_da_recuperare = ore_da_recuperare;
    }

    public int getSezione() {
        return sezione;
    }

    public void setSezione(int sezione) {
        this.sezione = sezione;
    }

    @Override
    public String toString() {
        return this.getNome() + " " + this.getCognome();
    }
}
