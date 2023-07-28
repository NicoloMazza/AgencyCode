package it.unipa.ingegneriasoftware.gestioneutente.ctrl;

import it.unipa.ingegneriasoftware.comuni.MessaggioASchermo;
import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestioneutente.bnd.ModificaDatiAccountBND;

public class ModificaDatiAccountCTRL  {

    public static void creaModificaDatiAccountBND(UtenteENT utente) {
        new ModificaDatiAccountBND(utente);
    }

    public static void creaMessaggioASchermo(String messaggio, int tipo) {
        new MessaggioASchermo(messaggio, tipo);
    }

    public static UtenteENT modificaDati(UtenteENT utente, String emailNuova, String passwordNuova, String IBANnuovo) {
        DBMS_BND db = new DBMS_BND();
        return db.queryModificaDati(utente, emailNuova, passwordNuova, IBANnuovo);
    }
}
