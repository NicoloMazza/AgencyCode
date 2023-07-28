package it.unipa.ingegneriasoftware.gestionedipendenti.ctrl;

import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestionedipendenti.bnd.VisualizzaDipendenteBND;

public class VisualizzaDipendenteCTRL {

    public static void creaDatiDipendenteBND(UtenteENT ent, String matricola) {
        DBMS_BND db = new DBMS_BND();
        UtenteENT datiCampi = db.querySelezionaUtente(matricola); // attenzione query login ctrl
        UtenteENT datiCampi2 = db.queryVerificaIdentita(datiCampi);
        new VisualizzaDipendenteBND(ent, datiCampi2);
    }

    public static void visualizzaDipendente(UtenteENT utente) {

    }

    public static void creaCercaDipendenteBND(UtenteENT ent) {
        CercaDipendenteCTRL.creaCercaDipendenteBND(ent);
    }
}