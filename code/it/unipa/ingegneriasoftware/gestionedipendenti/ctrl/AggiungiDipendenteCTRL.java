package it.unipa.ingegneriasoftware.gestionedipendenti.ctrl;

import it.unipa.ingegneriasoftware.comuni.MessaggioASchermo;
import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestionedipendenti.bnd.AggiungiDipendenteBND;

public class AggiungiDipendenteCTRL {

    public static void creaMessaggioASchermo(String testo, int tipo) {
        new MessaggioASchermo(testo, tipo);
    }

    public static void creaAggiungiDipendenteBND(UtenteENT ent) {
        new AggiungiDipendenteBND(ent);
    }

    public static boolean inserisciDipendente(UtenteENT utente) {
        DBMS_BND db = new DBMS_BND();
        return db.queryInserisciDipendente(utente);
    }
}