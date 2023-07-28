package it.unipa.ingegneriasoftware.gestioneutente.ctrl;

import it.unipa.ingegneriasoftware.autenticazione.bnd.LoginBND;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestioneutente.bnd.LogoutBND;

public class LogoutCTRL {

    public static void creaSchermataConfermaBND(UtenteENT ent) {
        new LogoutBND(ent);
    }

    public static void creaLoginBND() {
        new LoginBND();
    }
}
