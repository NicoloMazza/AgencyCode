package it.unipa.ingegneriasoftware.gestionedipendente.ctrl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestionedipendente.bnd.RichiestaVariazioneTurnoBND;
import it.unipa.ingegneriasoftware.gestioneturni.ctrl.TempoCTRL;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import java.util.Arrays;

public class RichiestaVariazioneTurnoCTRL {

    public static LocalDateTime getDataOraCorrente() {
        return SegnalaPresenzaCTRL.getDataOraCorrente();
    }

    public static void creaVariazioneTurnoBND(UtenteENT ent) {
        new RichiestaVariazioneTurnoBND(ent);
    }

    public static void creaMessaggioASchermo(String testo, int tipo) {
        SegnalaPresenzaCTRL.creaMessaggioASchermo(testo, tipo);
    }

    public static void inserisciVariazione(UtenteENT utente, LocalTime daLT, LocalTime aLT, LocalDate daLD, LocalDate aLD, int motivo, String dettagli) {
        if (motivo==2) {
            LocalDate dataOggi= TempoCTRL.getDataCorrente();
            //int fineMeseTrimestreCorrente;
            int meseCorrente=dataOggi.getMonthValue();
            //int meseCorrente=3;
            if ((meseCorrente%3==0) && ((daLD.getMonthValue()>meseCorrente && daLD.getMonthValue()<meseCorrente+3) && (aLD.getMonthValue()>meseCorrente && aLD.getMonthValue()<meseCorrente+3))) {
                ArrayList<LocalDate> giorni = checkGiorniIndisponibili(daLD, aLD);
                if (giorni.size()>0) {
                    System.out.println("giorni>0");
                    creaMessaggioASchermo("Nell'intervallo selezionato sono presenti i seguenti giorni indisponibili:" + giorni + "." , 1);
                } else {
                    System.out.println("giorni==0");
                    accettaFerie(utente,daLT, aLT, daLD, aLD, motivo, dettagli);
                    creaMessaggioASchermo("Giorni aggiornati correttamente", 2);
                }
            }else{
                creaMessaggioASchermo("Il mese corrente non e' l'ultimo mese del trimestre corrente o i giorni scelti non sono del trimestre successivo", 1);
            }
        } else {
            System.out.println(0);
            int flag = accettaFerie(utente, daLT, aLT, daLD, aLD, motivo, dettagli);
            if (flag == 0) {
                creaMessaggioASchermo("Variazione registrata correttamente", 2);
            }
            else {
                creaMessaggioASchermo("In almeno un giorno non sei in turno", 3);
            }
        }
        //db.inserisciVariazione(UtenteENT utente, LocalTime da, LocalTime a, int motivo, String dettagli);
    }

    public static List<UtenteENT> getInTurno(UtenteENT ent, LocalDate dataInizio, LocalDate dataFine) {
        DBMS_BND db= new DBMS_BND();
        return db.queryInTurno(ent, dataInizio, dataFine);
        
    }

    public static List<Object> getNonInTurno(UtenteENT ent, LocalDate dataInizio, LocalDate dataFine) {
        DBMS_BND db= new DBMS_BND();
        return db.queryNonInTurno(ent, dataInizio, dataFine);
    }

    public static boolean gestisciVariazioneTurno(UtenteENT utente, LocalTime daLT, LocalTime aLT, LocalDate daLD, LocalDate aLD) {
        while (daLD.isBefore(aLD.plusDays(1))) {
            boolean flagA=false;
            List<String> turni = getTurno2(utente, daLD);
            for (int i = 0; i < turni.size(); i++) {
                List<UtenteENT> matricole;
                matricole = getInTurno(utente, daLD, aLD);
                if (matricole.size()==0) { // matricole.size()==0
                    List<Object> entSelezioanto_codTurno = getNonInTurno(utente, daLD, aLD);
                    if (entSelezioanto_codTurno != null) { //entSelezioanto_codTurno != null
                        // System.out.println("è entrato");
                        aggiornaVariazione(entSelezioanto_codTurno);
                        RichiestaVariazioneTurnoCTRL.inviaEmailSostituto(entSelezioanto_codTurno);
                        aggiornaOreDaRecuperare_rimuovi(entSelezioanto_codTurno.get(0), entSelezioanto_codTurno.get(1));
                        flagA=true;
                    } else {
                        int servizio = utente.getServizio();
                        for (int b = 1; b < 5; b++) {
                            if (servizio != b && flagA!=true) {
                                List<Object> parametri = new ArrayList<>();
                                parametri.add(0, b);
                                parametri.add(1, daLD);
                                List<Object> ut_selezionato = getInTurnoAltriServizi(parametri);
                                if (ut_selezionato != null) {
                                    List<Object> parametri2 = new ArrayList<>();
                                    parametri2.add(0, ut_selezionato.get(0));
                                    parametri2.add(1, ut_selezionato.get(1));
                                    parametri2.add(2, utente.getMatricola());
                                    parametri2.add(3, turni.get(i));
                                    aggiornaVariazione(parametri2);
                                    RichiestaVariazioneTurnoCTRL.inviaEmailSostituto(parametri2);
                                    aggiornaOreDaRecuperare_rimuovi(parametri2.get(0), parametri2.get(1));
                                    flagA=true;
                                }
                            }
                        }
                        if (flagA!=true) {
                            for (int a = 1; a < 5; a++) {
                                if (servizio != a && flagA!=true) {
                                    List<Object> parametri = new ArrayList<>();
                                    parametri.add(0, a);
                                    parametri.add(1, daLD);
                                    List<Object> ut_selezionato = getNonInTurnoAltriServizi(parametri);
                                    if (ut_selezionato != null) {
                                        List<Object> parametri2 = new ArrayList<>();
                                        parametri2.add(0, ut_selezionato.get(0));
                                        parametri2.add(1, ut_selezionato.get(1));
                                        parametri2.add(2, utente.getMatricola());
                                        parametri2.add(3, turni.get(i));
                                        aggiornaVariazione(parametri2);
                                        RichiestaVariazioneTurnoCTRL.inviaEmailSostituto(parametri2);
                                        aggiornaOreDaRecuperare_rimuovi(parametri2.get(0), parametri2.get(1));
                                        flagA=true;
                                    }
                                }
                            }
                        }
                    }
                    if (flagA==false) {
                        MetodiUtili.inviaEmail(new UtenteENT("Gabriele", "Rossonno", "nicololarosamazza02@gmail.com", "", 0), 7, new ArrayList<>(Arrays.asList(Integer.toString(utente.getServizio()), daLD.toString())) );
                        chiudiServizio(utente.getServizio());
                        // System.out.println("mail, chiusura servizio"+daLD);
                    }
                }
            }
            daLD = daLD.plusDays(1L);
        }
        return false;
    }

    public static void chiudiServizio(int servizio) {
        DBMS_BND db = new DBMS_BND();
        db.queryChiudiServizio(servizio);
    }

    public static void aggiornaOreDaRecuperare_rimuovi(Object matricola, Object cod_turno) {
        DBMS_BND db=new DBMS_BND();
        db.queryAggiornaOreDaRecuperare_rimuovi(matricola, cod_turno);
    }

    public static void aggiornaVariazione(List<Object> entSelezioanto_codTurno) {
        DBMS_BND db =new DBMS_BND();
        db.queryAggiornaVariazione(entSelezioanto_codTurno);
    }

    public static ArrayList<LocalDate> checkGiorniIndisponibili(LocalDate daLD, LocalDate aLD) {
        DBMS_BND db = new DBMS_BND();
        ArrayList<LocalDate> ret= db.queryGiorniIndisponibili(daLD, aLD);
        return ret;
    }

    public static int accettaFerie(UtenteENT utente, LocalTime daLT, LocalTime aLT, LocalDate daLD, LocalDate aLD, int motivo, String testo) {
        DBMS_BND db = new DBMS_BND();
        // System.out.println(1);
        return db.queryAccettaFerie(utente,daLT, aLT, daLD, aLD, motivo, testo);
    }
    
    public static List<Object> getInTurnoAltriServizi(List<Object> parametri) {
        DBMS_BND db = new DBMS_BND();
        return db.queryInTurnoAltriServizi(parametri);
    }

    public static List<Object> getNonInTurnoAltriServizi(List<Object> parametri) {
        DBMS_BND db = new DBMS_BND();
        return db.queryNonInTurnoAltriServizi(parametri);
    }

    public static List<String> getTurno2(UtenteENT ent, LocalDate data) {
        DBMS_BND db = new DBMS_BND();
        return db.queryGetTurno2(ent, data);
    }

    public static void inviaEmailSostituto(List<Object> entSelezioanto_codTurno) {
        List<String> mail = new ArrayList<>();
        DBMS_BND db = new DBMS_BND();
        mail.add(entSelezioanto_codTurno.get(0).toString()); // matricola del sostituto
        mail.add(entSelezioanto_codTurno.get(3).toString()); // codice turno del sostituto (= codice turno del da sostituire)
        mail.add(entSelezioanto_codTurno.get(2).toString()); // matricola del da sostituire
        mail.add(Integer.toString(db.queryVerificaIdentita(db.querySelezionaUtente(entSelezioanto_codTurno.get(2).toString())).getServizio()));
        MetodiUtili.inviaEmail( db.querySelezionaUtente(entSelezioanto_codTurno.get(0).toString()), 8, mail);
    }
    
}