package it.unipa.ingegneriasoftware.gestionedipendente.ctrl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.List;

import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.comuni.ent.TurnoENT;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import java.util.Arrays;

public class SegnalaPresenzaRitardoCTRL {

    public static LocalDateTime getDataOraCorrente() {
        return SegnalaPresenzaCTRL.getDataOraCorrente();
    }

    public static void creaMessaggioASchermo(String testo, int tipo) {
        SegnalaPresenzaCTRL.creaMessaggioASchermo(testo, tipo);
    }

    public static void impostaPresenzaRitardo(UtenteENT utente) {
        LocalDateTime data_ora = LocalDateTime.now();
        int result= presenzaGiaEffettuata(utente, data_ora);
        switch(result) {
            case -2:
                creaMessaggioASchermo("Devi effettuare la presenza dal terminale", 1);
                break;
            case -1:
                creaMessaggioASchermo("Non in turno", 1);
                break;
            case 0:
                List<Object> parametri = new ArrayList<>();
                parametri.add(0, LocalTime.of(data_ora.getHour(), data_ora.getMinute(), data_ora.getSecond()));
                parametri.add(1, LocalTime.of(data_ora.getHour(), data_ora.getMinute(), data_ora.getSecond()));
                parametri.add(2, data_ora.toLocalDate());
                parametri.add(3, utente.getMatricola());
                TurnoENT turno = getTurnoDaEffettuare(parametri);
                Long orePerse=ChronoUnit.HOURS.between(turno.getOra_inizio(),LocalTime.of(data_ora.getHour(), data_ora.getMinute(), data_ora.getSecond()));
                //long oreSforate = Duration.between(data_ora.toLocalTime(), turno.getOra_inizio()).toHours();
                System.out.println(orePerse +" ore");
                if (orePerse>=3) { //se sono passate tre ore dallinizio del turno
                    creaMessaggioASchermo("Sono passate tre ore dall'inizio del turno", 1);
                }else{
                    creaMessaggioASchermo("Il datore verra' informato del ritardo", 3);
                    MetodiUtili.inviaEmail( new UtenteENT("Gabriele", "Rossonno", "nicololarosamazza02@gmail.com", "", 0), 6, 
                        new ArrayList<>(Arrays.asList(Integer.toString(utente.getMatricola()), utente.getNome(), utente.getCognome())) );
                    aggiornaStato(turno, utente);
                    // System.out.println("a");
                    if (!servizioAperto(utente.getServizio())) {
                        // System.out.println("b");
                        apriServizio(utente.getServizio());
                        // System.out.println("c");
                    }
                    creaMessaggioASchermo("Presenza ritardo effettuata con successo.", 2);
                }
                break;
            case 1:
                creaMessaggioASchermo("Presenza gia' effettuata regolarmente da terminale", 1);
                break;
            case 2:
                creaMessaggioASchermo("Presenza da remoto gia' effettuata", 1);
                break;
        }
    }

    public static void apriServizio(int servizio) {
        DBMS_BND db= new DBMS_BND();
        db.queryApriServizio(servizio);
    }

    public static boolean servizioAperto(int servizio) {
        DBMS_BND db= new DBMS_BND();
        return db.queryServizioAperto(servizio);
    }

    public static void aggiornaStato(TurnoENT turno, UtenteENT utente) {
        DBMS_BND db= new DBMS_BND();
        db.queryAggiornaStato(turno, utente);
    }

    public static TurnoENT getTurnoDaEffettuare(List<Object> parametri) {
        DBMS_BND db= new DBMS_BND();
        return db.queryTurnoDaEffettuare(parametri);
    }

    public static int presenzaGiaEffettuata(UtenteENT utente, LocalDateTime data_ora) {
        DBMS_BND db= new DBMS_BND();
        return db.queryPresenzaGiaEffettuata(utente, data_ora);
    }

}