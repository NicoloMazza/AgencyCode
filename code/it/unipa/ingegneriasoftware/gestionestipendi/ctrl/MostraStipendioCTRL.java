package it.unipa.ingegneriasoftware.gestionestipendi.ctrl;

import java.time.LocalDate;
import java.util.List;

import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.comuni.ent.StipendioENT;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestionestipendi.bnd.MostraStipendioBND;

public class MostraStipendioCTRL {

    public static void creaMostraStipendioBND(UtenteENT ent) {
        new MostraStipendioBND(ent);
    }

    public static String[][] mostraStipendi(LocalDate da, LocalDate a, String matricola) {
        DBMS_BND db = new DBMS_BND();
        List<StipendioENT> stipendi = db.queryMostraStipendi(da, a, matricola);
        if (stipendi != null) {
        String data[][] = new String[stipendi.size()][3];
                for (int i = 0; i < stipendi.size(); i++) {
                    for (int j = 0; j < 3; j++) {
                        if (j == 0) {
                            data[i][j] = Integer.toString(stipendi.get(i).getMatricola());
                        }
                        if (j == 1) {
                            data[i][j] = stipendi.get(i).getData_accredito().toString();
                        }
                        if (j == 2) {
                            String stipendio = String.valueOf(stipendi.get(i).getStipendio_mensile());
                            int dotIndex = stipendio.indexOf(".");
                            if (!(dotIndex == -1 || stipendio.length() - dotIndex <= 2)) {
                            stipendio = stipendio.substring(0, dotIndex + 3);
                            }
                            data[i][j] = stipendio;
                        }
                    }
                }
                return data;
            }
            return null;
        }
}