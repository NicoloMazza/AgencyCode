package it.unipa.ingegneriasoftware.gestionedipendente.ctrl;

import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.List;
import java.util.ArrayList;

import it.unipa.ingegneriasoftware.comuni.MessaggioASchermo;
import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.comuni.ent.TurnoENT;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestionedipendente.bnd.RilevazioneEntrataUscitaBND;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

public class SegnalaPresenzaCTRL {

    public static LocalDateTime getDataOraCorrente() {
        return LocalDateTime.now();
    }

    public static int impostaPresenza(int matricola, String nome, String cognome) { // GESTIONE DIPENDENTE -> SEGNALA ENTRATA
        UtenteENT ent = checkCredenziali(matricola, nome, cognome);
        int flag=0;
        if (ent !=null) { //credenziali corrette
            flag=1;
            LocalDateTime datetime = LocalDateTime.now();
            TurnoENT turno = inTurno(ent, datetime);
            if (turno!=null) { // in turno
                flag=3;
                // System.out.println("in turno");
                setPresenza(matricola, turno);
                creaMessaggioASchermo("Entrata eseguita con successo", 2);
            } else { // non in turno
                flag=4;
                // System.out.println("non in turno");
                // creaMessaggioASchermo("Non puoi eseguire l'accesso perche non in turno", 1); doppione
            }
        } else { // credenziali non corrette
            flag=2;
            creaMessaggioASchermo("Non puoi eseguire l'accesso perche' credenziali errate", 2);
        }
        return flag;
    }

    public static void gestioneAssenze() {
        impostaAssenze();
        //DBMS_BND db = new DBMS_BND();
        for (int i = 1; i <= 4; i++) {
            if (dipendentiPresenti(i) == null) {
                chiudiServizio(i);
                // System.out.println("servizio chiuso");
            }
        }
            //UtenteENT ent = checkCredenziali(matricola, nome, cognome);
            //int servizio=ent.getServizio();
        // fine for
    }

    public static List<UtenteENT> dipendentiPresenti(int servizio) {
        LocalDateTime DateTime= LocalDateTime.now();
        DBMS_BND db = new DBMS_BND();
        return db.queryDipendentiPresenti(DateTime, servizio);
    }

    public static void creaRilevazionePresenzaBND() {
        LocalTime oraCorrente = LocalTime.now();
        if ( 
        // indagare meglio su isbefore e isafter o nel dubbio evitare di avviare il programma nell'esatto istante della transizione
        (LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isAfter(LocalTime.of(0, 0, 0)) && 
         LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isBefore(LocalTime.of(0, 9, 59))) || 
        (LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isAfter(LocalTime.of(6, 0, 0)) && 
         LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isBefore(LocalTime.of(6, 9, 59))) || 
        (LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isAfter(LocalTime.of(7, 0, 0)) && 
         LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isBefore(LocalTime.of(7, 9, 59))) || 
        (LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isAfter(LocalTime.of(8, 0, 0)) && 
         LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isBefore(LocalTime.of(8, 9, 59))) || 
        (LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isAfter(LocalTime.of(9, 0, 0)) && 
         LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isBefore(LocalTime.of(9, 9, 59))) || 
        (LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isAfter(LocalTime.of(10, 0, 0)) && 
         LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isBefore(LocalTime.of(10, 9, 59))) || 
        (LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isAfter(LocalTime.of(11, 0, 0)) && 
         LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isBefore(LocalTime.of(11, 9, 59))) || 
        (LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isAfter(LocalTime.of(13, 0, 0)) && 
         LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isBefore(LocalTime.of(13, 9, 59))) || 
        (LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isAfter(LocalTime.of(14, 0, 0)) && 
         LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isBefore(LocalTime.of(14, 9, 59))) || 
        (LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isAfter(LocalTime.of(15, 0, 0)) && 
         LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isBefore(LocalTime.of(15, 9, 59))) || 
        (LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isAfter(LocalTime.of(16, 0, 0)) && 
         LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isBefore(LocalTime.of(16, 9, 59))) || 
        (LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isAfter(LocalTime.of(17, 0, 0)) && 
         LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isBefore(LocalTime.of(17, 9, 59))) || 
        (LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isAfter(LocalTime.of(18, 0, 0)) && 
         LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isBefore(LocalTime.of(18, 9, 59))) || 
        (LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isAfter(LocalTime.of(19, 0, 0)) && 
         LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isBefore(LocalTime.of(19, 9, 59))) ||
        (LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isAfter(LocalTime.of(23, 0, 0)) && 
         LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).isBefore(LocalTime.of(23, 9, 59)))
        ) {
            new RilevazioneEntrataUscitaBND(0);
        }
        else {
            new RilevazioneEntrataUscitaBND(1);
        }
        /*
         * if (nodo.equals("nodo=TerminaleAzienda")) {
         * 
         * }
         */
    }

    public static void creaMessaggioASchermo(String testo, int tipo) {
        new MessaggioASchermo(testo, tipo);
    }

    public static void impostaAssenze() {
        getDipendentiTurno();
    }

    public static UtenteENT checkCredenziali(int matricola, String nome, String cognome) {
        DBMS_BND db = new DBMS_BND();
        UtenteENT utente = db.querySelezionaUtente(String.valueOf(matricola));
        if (utente != null) {
            System.out.println(utente.getCognome());
            System.out.println(utente.getNome());
            if (utente.getCognome().equals(cognome) && utente.getNome().equals(nome)) {
                System.out.println("corrette");
                return utente;
            } else {
                System.out.println(" non corrette o inesistente");
            }
        }
        return null;
    }

    public static TurnoENT inTurno(UtenteENT ent, LocalDateTime dateTime) {
        DBMS_BND db = new DBMS_BND();
        return db.queryGetTurno(ent, dateTime);
    }

    public static void setPresenza(int matricola, TurnoENT turno) {
        DBMS_BND db = new DBMS_BND();
        db.queryPresenza(matricola, turno);
    }

    public static void getDipendentiTurno() {
        LocalDateTime DateTime= LocalDateTime.now();
        DBMS_BND db = new DBMS_BND();
        db.queryDipendentiTurno(DateTime);
    }

    public static void chiudiServizio(int servizio) {
        DBMS_BND db = new DBMS_BND();
        db.queryChiudiServizio(servizio);
    }

    public static List<UtenteENT> getAssenti() {
        DBMS_BND db = new DBMS_BND();
        LocalDateTime dateTime= LocalDateTime.now();
        List<UtenteENT> assentiNonComunicati = db.queryGetAssenti(dateTime);
        System.out.println(assentiNonComunicati);
        List<String> assenti = new ArrayList<>();
        for (int i = 0; i < assentiNonComunicati.size(); i++) {
            assenti.add( Integer.toString(assentiNonComunicati.get(i).getMatricola()) );
        }
        MetodiUtili.inviaEmail( new UtenteENT("Gabriele", "Rossonno", "nicololarosamazza02@gmail.com", "", 0), 5, assenti );
        return assentiNonComunicati;
    }
}