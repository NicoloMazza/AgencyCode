package it.unipa.ingegneriasoftware.gestioneturni.ctrl;

import java.time.LocalTime;
import java.time.LocalDate;

import java.util.Date;
import java.util.List;

import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.comuni.ent.TurnoENT;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestioneturni.bnd.VisualizzaBachecaTurniBND;

public class VisualizzaBachecaTurniCTRL {

    public static void creaBachecaTurniBND(UtenteENT ent) {
        new VisualizzaBachecaTurniBND(ent);
    }

	public static void controllaFiltri(Date da, Date a, int servizio) {
        /*
         * da < a
         * if (filtri validi)
         * VisualizzaBachecaTurniCTRL.mostraTurni();
         * else {
         * ImpostaGiorniIndisponibiliCTRL.creaMessaggioASchermo("Filtri non validi.",
         * 1);
         * }
         */
    }
	
	public static String[][] mostraTuttiTurni(LocalDate date, LocalTime ora_i, LocalTime ora_f, String matricola, Integer servizio) {
        DBMS_BND db = new DBMS_BND();
        List<TurnoENT> turni = db.queryMostraTurni(date, ora_i, ora_f, matricola, servizio);
        String data[][] = new String[turni.size()][5];
       	if (turni != null) {
            for (int i = 0; i < turni.size(); i++) {
                for (int j = 0; j < 5; j++) {
                    if (j == 0) {
                        data[i][j] = Integer.toString(turni.get(i).getMatricola());
                    }
                    if (j == 1) {
                        data[i][j] = turni.get(i).getCodice_turno();
                    }
                    if (j == 2) {
                        data[i][j] = turni.get(i).getData().toString();
                    }
                    if (j == 3) {
                        data[i][j] = turni.get(i).getOra_inizio().toString();
                    }
                    if (j == 4) {
                        data[i][j] = turni.get(i).getOra_fine().toString();
                    }
                }
            }
            return data;
        }
        return null;
    }
}