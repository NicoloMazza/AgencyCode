package it.unipa.ingegneriasoftware.gestionedipendenti.ctrl;

import it.unipa.ingegneriasoftware.comuni.DBMS_BND;

public class RimuoviDipendenteCTRL {

    public static void creaMessaggioASchermo(String testo, int tipo) {
        AggiungiDipendenteCTRL.creaMessaggioASchermo(testo, tipo);
    }

    public static boolean rimuoviDipendente(String matricola) {
        DBMS_BND db = new DBMS_BND();
        return db.queryRimuoviDipendente(matricola);
    }
}