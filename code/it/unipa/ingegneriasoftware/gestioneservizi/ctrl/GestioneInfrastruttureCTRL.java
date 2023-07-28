package it.unipa.ingegneriasoftware.gestioneservizi.ctrl;

import java.util.List;

import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.comuni.ent.InfrastrutturaENT;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestioneservizi.bnd.GestioneInfrastruttureBND;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;


public class GestioneInfrastruttureCTRL extends GestioneServiziCTRL {

    public static boolean creaGestioneInfrastruttureBND(UtenteENT utente) {

        if (controllaServizi(utente) == 1) {
            new GestioneInfrastruttureBND(utente);
            return true;
        } else {
            GestioneServiziCTRL.creaMessaggioASchermo();
            return false;
        }

    }

    public static String[][] mostraInfrastrutture(String codice, String nome, String citta) {
        codice = MetodiUtili.pulisciStrigna(codice);
        nome = MetodiUtili.pulisciStrigna(nome);
        citta = MetodiUtili.pulisciStrigna(citta);
        nome += "%";
        codice += "%";
        citta += "%";
        DBMS_BND db = new DBMS_BND();
        List<InfrastrutturaENT> infrastruttura = db.queryMostraInfrastrutture(codice, nome, citta);
        String data[][] = new String[infrastruttura.size()][3];

        if (infrastruttura != null) {
            for (int i = 0; i < infrastruttura.size(); i++) {
                for (int j = 0; j < 3; j++) { // colonne
                    if (j == 0) {
                        data[i][j] = Integer.toString(infrastruttura.get(i).getCodice_infrastruttura());
                    }
                    if (j == 1) {
                        data[i][j] = infrastruttura.get(i).getNome();
                    }
                    if (j == 2) {
                        data[i][j] = infrastruttura.get(i).getCitta();
                    }
                }
            }
            return data;
        }

        return null;
    }

}