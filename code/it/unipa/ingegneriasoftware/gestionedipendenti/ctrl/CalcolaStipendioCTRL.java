package it.unipa.ingegneriasoftware.gestionestipendi.ctrl;

import java.time.LocalDateTime;

import it.unipa.ingegneriasoftware.comuni.DBMS_BND;

public class CalcolaStipendioCTRL {

    public static LocalDateTime getDataOraCorrente() {
        return LocalDateTime.now();
    }

    public static boolean calcolaStipendi() {
   		DBMS_BND db = new DBMS_BND();
		return db.queryCalcolaStipendi();
	}
}