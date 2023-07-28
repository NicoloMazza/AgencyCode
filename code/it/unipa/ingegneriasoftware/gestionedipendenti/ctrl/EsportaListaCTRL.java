package it.unipa.ingegneriasoftware.gestionedipendenti.ctrl;

import java.time.LocalDate;
import java.util.List;

import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestionedipendenti.bnd.EsportaListaBND;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

public class EsportaListaCTRL {

    public static void creaEsportaListaBND(UtenteENT ent) {
        new EsportaListaBND(ent);
    }

    public static boolean esportaLista(LocalDate data) {
        DBMS_BND db = new DBMS_BND();
        List<UtenteENT> dipendenti = db.queryEsportaLista(data);
        if (dipendenti != null) {
            MetodiUtili.creaPDF(dipendenti, data);
            return true;
        }
        return false;
    }
}