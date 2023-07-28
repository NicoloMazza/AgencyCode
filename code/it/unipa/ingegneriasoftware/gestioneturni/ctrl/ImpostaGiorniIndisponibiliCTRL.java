package it.unipa.ingegneriasoftware.gestioneturni.ctrl;

import java.time.LocalDate;
import java.time.ZoneId;

import java.util.Date;

import it.unipa.ingegneriasoftware.comuni.MessaggioASchermo;
import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.gestioneturni.bnd.GestioneTurniBND;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestioneturni.bnd.ImpostaGiorniIndisponibiliBND;

public class ImpostaGiorniIndisponibiliCTRL {

    public static LocalDate getDataCorrente() {
        return TempoCTRL.getDataCorrente();
    }

    public static void creaMessaggioASchermo(String testo, int tipo) {
        new MessaggioASchermo(testo, tipo);
    }

    public static void creaImpostaGiorniIndisponibiliBND(UtenteENT utente) {
        new ImpostaGiorniIndisponibiliBND(utente);
    }

    public static void creaGestioneTurniBND(UtenteENT ent) {
        new GestioneTurniBND(ent);
    }

    public static LocalDate impostaGiorniIndisponibili(Date giornoInizio, Date giornoFine, UtenteENT utente) {
        LocalDate giornoFineLocalDate = LocalDate.ofInstant(giornoFine.toInstant(), ZoneId.systemDefault());
        LocalDate giornoInizioLocalDate = LocalDate.ofInstant(giornoInizio.toInstant(), ZoneId.systemDefault());
        DBMS_BND db = new DBMS_BND();
        return db.queryImpostaGiorniIndisponibili(giornoInizioLocalDate, giornoFineLocalDate, utente);
    }
}