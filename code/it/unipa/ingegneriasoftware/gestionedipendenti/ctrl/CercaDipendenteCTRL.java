package it.unipa.ingegneriasoftware.gestionedipendenti.ctrl;

import java.util.List;

import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestionedipendenti.bnd.CercaDipendenteBND;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

public class CercaDipendenteCTRL {

    public static void creaCercaDipendenteBND(UtenteENT ent) {
        new CercaDipendenteBND(ent);
    }

    public static String [][] mostraDipendenti(String testoCercato) {
        testoCercato = MetodiUtili.pulisciStrigna(testoCercato);
        testoCercato += "%";
        DBMS_BND db = new DBMS_BND();
        List<UtenteENT> dipendenti = db.queryCercaDipendente(testoCercato);
        String data [][] = new String[dipendenti.size()][3];
        if (dipendenti != null) {
            for (int i = 0; i < dipendenti.size(); i++) {   // righe
                for (int j = 0; j < 3; j++) {               // colonne
                    if ( j == 0 ) {
                        data[i][j] = Integer.toString(dipendenti.get(i).getMatricola());
                    }
                    if ( j == 1 ) {
                        data[i][j] = dipendenti.get(i).getNome();
                    }
                    if ( j == 2 ) {
                        data[i][j] = dipendenti.get(i).getCognome();
                    }
                }
            }
            //printMatrix(data);
            return data;
        }
        return null;
    }

}