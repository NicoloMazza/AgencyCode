package it.unipa.ingegneriasoftware.gestioneservizi.ctrl;

import java.util.List;

import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.comuni.ent.RecensioneENT;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestioneservizi.bnd.GestioneRecensioniBND;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;


public class GestioneRecensioniCTRL extends GestioneServiziCTRL {

    public static boolean creaGestioneRecensioniBND(UtenteENT utente) {
        if (controllaServizi(utente) == 4) {
            new GestioneRecensioniBND(utente);
            return true;
        } else {
            creaMessaggioASchermo();
            return false;
        }
    }

    public static String[][] mostraRecensioni(String codice, String testo, String punteggio) {
        codice = MetodiUtili.pulisciStrigna(codice);
        testo = MetodiUtili.pulisciStrigna(testo);
        punteggio = MetodiUtili.pulisciStrigna(punteggio);
        testo += "%";
        codice += "%";
        punteggio += "%";
        DBMS_BND db = new DBMS_BND();
        List<RecensioneENT> recensione = db.queryMostraRecensioni(codice, testo, punteggio);
        String data[][] = new String[recensione.size()][3];

        if (recensione != null) {
            for (int i = 0; i < recensione.size(); i++) {
                for (int j = 0; j < 3; j++) { // colonne
                    if (j == 0) {
                        data[i][j] = Integer.toString(recensione.get(i).getRef_segnalazione());
                    }
                    if (j == 1) {
                        data[i][j] = recensione.get(i).getTesto();
                    }
                    if (j == 2) {
                        data[i][j] = Integer.toString(recensione.get(i).getPunteggio());
                    }
                }
            }
            return data;
        }
        return null;
    }

    public static void mostraStatistiche() {
        // calcola e mostra
    }
}