package it.unipa.ingegneriasoftware.gestioneservizi.ctrl;

import java.time.LocalDateTime;

import it.unipa.ingegneriasoftware.comuni.MessaggioASchermo;
import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestioneservizi.bnd.GestioneServiziBND;

public class GestioneServiziCTRL {

    public static void creaMessaggioASchermo() {
        new MessaggioASchermo("Non hai i requisiti per svolgere questo servizio.", 3);
    }

    public static int controllaServizi(UtenteENT utente) {
        DBMS_BND db = new DBMS_BND();
        int servizio = db.queryServizioVariazione(utente, LocalDateTime.now());
        if ( servizio != 0 ) { // sto sostituendo qualcuno e ritorna il servizio del dipendente da sostituire in quel momento come intero
            return servizio;
        }
        return utente.getServizio();
    }

    public static boolean creaGestioneServiziBND(UtenteENT ent) {
        DBMS_BND db = new DBMS_BND();
        if ( db.queryGetTurno3(ent, LocalDateTime.now()) ) {
            new GestioneServiziBND(ent);
            return true;
        }
        new MessaggioASchermo("Al momento non sei in turno!", 3);
        return false;
    }

}