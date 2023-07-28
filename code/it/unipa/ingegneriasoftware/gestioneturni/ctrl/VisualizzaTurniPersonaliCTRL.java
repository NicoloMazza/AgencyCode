package it.unipa.ingegneriasoftware.gestioneturni.ctrl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import it.unipa.ingegneriasoftware.comuni.MessaggioASchermo;
import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.comuni.ent.TurnoENT;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestioneturni.bnd.VisualizzaTurniPersonaliBND;

public class VisualizzaTurniPersonaliCTRL {

    public static void creaVisualizzaTurniPersonaliBND(UtenteENT ent) {
        new VisualizzaTurniPersonaliBND(ent);
    }

    public static String[][] mostraTurniPersonali(UtenteENT ent, LocalDate data_inizio, LocalDate data_fine) {
        DBMS_BND db = new DBMS_BND();
        List<TurnoENT> turni = db.querySelezionaTurni(ent, data_inizio, data_fine);
        String data[][] = new String[turni.size()][5];
        if (turni != null) {
            for (int i = 0; i < turni.size(); i++) {
                for (int j = 0; j < 5; j++) {
                    if (j == 0) {
                        data[i][j] = Integer.toString(turni.get(0).getMatricola());
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
            // printMatrix(data); verifica cosa fa
            return data;
        }
        return null;
    }

    public static void creaMessaggioASchermo(String testo, int tipo) {
        new MessaggioASchermo(testo, tipo);
    }

    public static void controllaFiltri(Date da, Date a) {
        /*
         * da < a
         * if (filtri validi)
         * VisualizzaTurniPersonaliCTRL.mostraTurniPersonali();
         * else {
         * ImpostaGiorniIndisponibiliCTRL.creaMessaggioASchermo("Filtri non validi.",
         * 1);
         * }
         */
    }

    /*
     * private static void printMatrix(String[][] matrix) {
     * System.out.println("siamo qua");
     * for (int row = 0; row < matrix.length; row++) {
     * for (int col = 0; col < matrix[row].length; col++) {
     * System.out.printf(matrix[row][col]);
     * }
     * System.out.println();
     * }
     * }
     */
}