package it.unipa.ingegneriasoftware.gestioneservizi.ctrl;

import java.util.List;

import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.comuni.ent.ProgettoENT;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestioneservizi.bnd.GestioneProgettiBND;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;


public class GestioneProgettiCTRL extends GestioneServiziCTRL {

    public static boolean creaGestioneProgettiBND(UtenteENT utente) {
        if (controllaServizi(utente) == 2) {
            new GestioneProgettiBND(utente);
            return true;
        } else {
            creaMessaggioASchermo();
            return false;
        }
    }

    public static String[][] mostraProgetti(String codice, String descrizione, String stato) {
        codice = MetodiUtili.pulisciStrigna(codice);
        descrizione = MetodiUtili.pulisciStrigna(descrizione);
        stato = MetodiUtili.pulisciStrigna(stato);
        descrizione += "%";
        codice += "%";
        stato += "%";
        DBMS_BND db = new DBMS_BND();
        List<ProgettoENT> progetto = db.queryMostraProgetti(codice, descrizione, stato);
        String data[][] = new String[progetto.size()][3];

        if (progetto != null) {
            for (int i = 0; i < progetto.size(); i++) {
                for (int j = 0; j < 3; j++) { // colonne
                    if (j == 0) {
                        data[i][j] = Integer.toString(progetto.get(i).getCodice_progetto());
                    }
                    if (j == 1) {
                        data[i][j] = progetto.get(i).getDescrizione();
                    }
                    if (j == 2) {
                        data[i][j] = Integer.toString(progetto.get(i).getStato_progetto());
                    }
                }
            }
            return data;
        }
        return null;
    }


    
}