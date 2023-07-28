package it.unipa.ingegneriasoftware.gestioneturni.ctrl;

import java.time.LocalDate;

import java.util.Scanner;

import it.unipa.ingegneriasoftware.comuni.DBMS_BND;

import java.io.File;
import java.io.FileNotFoundException;

public class TempoCTRL {

    public static LocalDate getDataCorrente() {
        return LocalDate.now();
    }

    public static boolean generaTurni() {
        String nodo = "";
        try {
            Scanner scanner = new Scanner( new File("./nodo.txt"), "UTF-8" );
            nodo = scanner.useDelimiter(";").next();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (nodo.equals("nodo=PCAziendale")) {
            DBMS_BND db = new DBMS_BND();
            for (int i = 1; i <= 4; i++) {
                System.out.println("Inizio generazione turni servizio " + i);
                if (db.generaTurni(i) == false) {
                    return false;
                }
                System.out.println("Fine generazione turni servizio " + i);
            }
            return true;
        }
        return false;
    }

    public static void gestisciVariazioniSegnalate() {
        // deve partire automaticamente ogni tot
        /*
         * if (nodo.equals("nodo=TerminaleAzienda")) {
         * 
         * }
         */
    }
}