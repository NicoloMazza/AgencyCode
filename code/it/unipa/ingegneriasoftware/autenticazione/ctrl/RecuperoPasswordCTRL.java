package it.unipa.ingegneriasoftware.autenticazione.ctrl;

import java.util.ArrayList;
import java.util.Arrays;

import it.unipa.ingegneriasoftware.autenticazione.bnd.CreaPasswordBND;
import it.unipa.ingegneriasoftware.autenticazione.bnd.RecuperoPasswordBND;
import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

public class RecuperoPasswordCTRL {

    public static void creaRecuperoPasswordBND() {
        new RecuperoPasswordBND();
    }

    public static void creaLoginBND() {
        LoginCTRL.creaLoginBND();
    }

    public static UtenteENT selezionaUtente(String matricola) {
        DBMS_BND db = new DBMS_BND();
        UtenteENT utente = db.querySelezionaUtente(matricola);
        if (utente == null) {
            return null;
        }
        return utente;
    }

    public static String inviaOTP(UtenteENT utente) {
        String otp = MetodiUtili.casualeStringa(8);
        MetodiUtili.inviaEmail(utente, 2, new ArrayList<String>(Arrays.asList(otp)));
        return otp;
    }

    public static void creaOTPBND(UtenteENT utente, String otp) {
        LoginCTRL.creaOTPBND(utente, otp);
    }

    public static boolean confrontaOTP(String otpInserito, String otpGenerato) {
        return LoginCTRL.confrontaOTP(otpInserito, otpGenerato);
    }

    public static UtenteENT aggiornaPassword(UtenteENT ent, String password) {
        DBMS_BND db = new DBMS_BND();
        UtenteENT entAggiornata = db.queryAggiornaPassword(ent, password);
        return entAggiornata;
    }

    public static void creaCreaPasswordBND(UtenteENT ent, String otp_provenienza) {
        new CreaPasswordBND(ent, otp_provenienza);
    }

    public static void creaMessaggioASchermo(String testo, int tipo) {
        LoginCTRL.creaMessaggioASchermo(testo, tipo);
    }
}