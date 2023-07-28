package it.unipa.ingegneriasoftware.gestioneservizi.ctrl;

import java.util.List;

import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.comuni.ent.SegnalazioneENT;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestioneservizi.bnd.GestioneSegnalazioniBND;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;


public class GestioneSegnalazioniCTRL extends GestioneServiziCTRL {

    public static boolean creaGestioneSegnalazioniBND(UtenteENT utente) {
        if (controllaServizi(utente) == 3) {
            new GestioneSegnalazioniBND(utente);
            return true;
        } else {
            creaMessaggioASchermo();
            return false;
        }
    }

    public static String[][] mostraSegnalazioni(String codice, String domanda, String risposta, String categoria) {
        codice = MetodiUtili.pulisciStrigna(codice);
        domanda = MetodiUtili.pulisciStrigna(domanda);
        risposta = MetodiUtili.pulisciStrigna(risposta);
        categoria = MetodiUtili.pulisciStrigna(categoria);
        domanda += "%";
        codice += "%";
        risposta += "%";
        categoria += "%";
        DBMS_BND db = new DBMS_BND();
        List<SegnalazioneENT> segnalazione = db.queryMostraSegnalazioni(codice, domanda, risposta, categoria);
        String data[][] = new String[segnalazione.size()][4];

        if (segnalazione != null) {
            for (int i = 0; i < segnalazione.size(); i++) {
                for (int j = 0; j < 4; j++) { // colonne
                    if (j == 0) {
                        data[i][j] = Integer.toString(segnalazione.get(i).getCodice_segnalazione());
                    }
                    if (j == 1) {
                        data[i][j] = segnalazione.get(i).getDomanda();
                    }
                    if (j == 2) {
                        data[i][j] = segnalazione.get(i).getRisposta();
                    }
                    if (j == 3) {
                        data[i][j] = Integer.toString(segnalazione.get(i).getCategoria());
                    }
                }
            }
            return data;
        }
        return null;
    }
}