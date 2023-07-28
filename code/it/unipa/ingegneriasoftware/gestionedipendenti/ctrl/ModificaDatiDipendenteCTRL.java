package it.unipa.ingegneriasoftware.gestionedipendenti.ctrl;

import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestionedipendenti.bnd.ModificaDatiDipendenteBND;

public class ModificaDatiDipendenteCTRL {

    public static void creaModificaDatiDipendenteBND(UtenteENT ent, String matricola) {
        DBMS_BND db = new DBMS_BND();
        UtenteENT datiCampi = db.querySelezionaUtente(matricola); // attenzione query login ctrl
        UtenteENT datiCampi2 = db.queryVerificaIdentita(datiCampi);
        new ModificaDatiDipendenteBND(ent, datiCampi2);
    }

    public static boolean modificaDipendente(UtenteENT utente) {
        DBMS_BND db = new DBMS_BND();
        return db.queryModificaDipendente(utente);
    }

    public static void creaCercaDipendenteBND(UtenteENT ent) {
        CercaDipendenteCTRL.creaCercaDipendenteBND(ent);
    }
}