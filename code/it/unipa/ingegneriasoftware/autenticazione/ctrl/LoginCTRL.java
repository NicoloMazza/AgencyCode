package it.unipa.ingegneriasoftware.autenticazione.ctrl;

import java.util.Map;

import it.unipa.ingegneriasoftware.autenticazione.bnd.HomeBND;
import it.unipa.ingegneriasoftware.autenticazione.bnd.LoginBND;
import it.unipa.ingegneriasoftware.autenticazione.bnd.OTP_BND;
import it.unipa.ingegneriasoftware.comuni.MessaggioASchermo;
import it.unipa.ingegneriasoftware.comuni.DBMS_BND;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginCTRL {

    public static boolean confrontaOTP(String otpInserito, String otpGenerato) {
        if (otpInserito.equals(otpGenerato)) {
            return true;
        }
        return false;
    }

    public static void creaHomeBND(UtenteENT utente) {
        DBMS_BND db = new DBMS_BND();
        UtenteENT entAggiornata = db.queryVerificaIdentita(utente);
        if (entAggiornata.getServizio() == 0) {
            new HomeBND(entAggiornata);  // datore
        } 
        else {
            new HomeBND(entAggiornata); // dipendente
        }
    }

    public static void creaLoginBND() {
        new LoginBND();
    }

    public static UtenteENT controllaCredenziali(String matricola, String password) {
        DBMS_BND db = new DBMS_BND();
        return db.queryVerificaCredenziali(matricola, password);
    }

    public static String inviaOTP(UtenteENT utente) {
        String otp = Integer.toString(MetodiUtili.casualeTra(1111, 9999));
        MetodiUtili.inviaEmail(utente, 1, new ArrayList<String>(Arrays.asList(otp)));
        return otp;
    }

    public static UtenteENT creaUtenteEntity(Map<String, Object> mappa) {
        return new UtenteENT(mappa);
    }

    public static void inviaMailAvviso(UtenteENT utente) {
        MetodiUtili.inviaEmail(utente, 3, null);
    }

    public static void creaMessaggioASchermo(String testo, int tipo) {
        new MessaggioASchermo(testo, tipo);
    }

    public static void creaOTPBND(UtenteENT utente, String otp) {
        new OTP_BND(utente, otp);
    }

}