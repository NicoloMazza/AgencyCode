package it.unipa.ingegneriasoftware.gestionedipendente.ctrl;

import java.time.LocalDateTime;

import java.util.List;

import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.comuni.ent.TurnoENT;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;

public class SegnalaChiusuraCTRL {

    public static void creaMessaggioASchermo(String testo, int tipo) {
        SegnalaPresenzaCTRL.creaMessaggioASchermo(testo, tipo);
    }

    public static void impostaChiusuraAutomatica() {
        LocalDateTime tempo = LocalDateTime.now();
        List<UtenteENT> utenti = getTurniNonChiusi(tempo); //da verificare il funzionamento
        for (int i = 0; i < utenti.size(); i++) {
            setChiusuraAutomatica(utenti.get(i).getMatricola(), tempo);
        }
    }

    public static void setChiusuraAutomatica(int matricola, LocalDateTime tempo) {
        DBMS_BND db = new DBMS_BND();
        UtenteENT ent = db.querySelezionaUtente(Integer.toString(matricola));
        db.queryStatoFineTurno(ent, fineTurno(ent, LocalDateTime.now()), "0");
    }

    public static List<UtenteENT> getTurniNonChiusi(LocalDateTime tempo) {
        DBMS_BND db = new DBMS_BND();
        return db.queryTurniNonChiusi(tempo);
    }

    public static void impostaUscita(int matricola, String nome, String cognome) { // GESTIONE DIPENDENTE -> SEGNALA CHIUSURA TURNO
        UtenteENT ent = SegnalaPresenzaCTRL.checkCredenziali(matricola, nome, cognome);
        if (ent !=null) { //redenziali corrette
            LocalDateTime datetime = LocalDateTime.now();
            TurnoENT turno = fineTurno(ent, datetime);
            if (turno!=null) { // in turno
                // System.out.println("in turno");
                setStatoFineTurno(ent, turno);
                creaMessaggioASchermo("Uscita eseguita con successo", 2);
            } else { // non in turno  
                // System.out.println("non in turno");
                creaMessaggioASchermo("Non puoi eseguire l'accesso perche non in turno", 1);
            }
        } else { // credenziali non corrette
            creaMessaggioASchermo("Non puoi eseguire l'accesso perche' credenziali errate", 2);
        }
    }

    public static void setStatoFineTurno(UtenteENT ent, TurnoENT turno) {
        DBMS_BND db = new DBMS_BND();
        db.queryStatoFineTurno(ent, turno, "1");
    }

    public static TurnoENT fineTurno(UtenteENT ent, LocalDateTime dateTime) {
        DBMS_BND db = new DBMS_BND();
        return db.queryGetFineTurno(ent, dateTime);
    }
}