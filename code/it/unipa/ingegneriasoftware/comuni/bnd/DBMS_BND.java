package it.unipa.ingegneriasoftware.comuni;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Arrays;
import java.util.Scanner;

import it.unipa.ingegneriasoftware.autenticazione.ctrl.LoginCTRL;
import it.unipa.ingegneriasoftware.comuni.eccezioni.ErroreComunicazioneDBMSException;
import it.unipa.ingegneriasoftware.comuni.ent.InfrastrutturaENT;
import it.unipa.ingegneriasoftware.comuni.ent.ProgettoENT;
import it.unipa.ingegneriasoftware.comuni.ent.RecensioneENT;
import it.unipa.ingegneriasoftware.comuni.ent.SegnalazioneENT;
import it.unipa.ingegneriasoftware.comuni.ent.StipendioENT;
import it.unipa.ingegneriasoftware.comuni.ent.TurnoENT;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestionestipendi.bnd.CalcolaStipendioBND;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.DayOfWeek;

import java.io.File;
import java.io.FileNotFoundException;

public class DBMS_BND {

    private List<Map<String, Object>> insiemeRisultati;
    private int totaleTupleAggiornate;
    private int tentativo_riconnessione;

    private final String url = "jdbc:mysql://34.154.249.104:3306/ingegneria_software";
    private final String user = "root";
    private final String pass = "root2";

    public DBMS_BND() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void eseguiCRUD(String query, List<Object> parametri) throws ErroreComunicazioneDBMSException {
/*
      String pass = ""; String user = "root"; String url = "jdbc:mysql://localhost:3306/ingegneria_software";
      try {
         Scanner scanner = new Scanner( new File("pass.txt"), "UTF-8" );
         pass = scanner.useDelimiter("\\A").next();
      }
      catch (FileNotFoundException e) {
         e.printStackTrace();
      }
*/
        try (
                Connection connessione = DriverManager.getConnection(url, user, pass);
                PreparedStatement statement = connessione.prepareStatement(query);) {
            // Sostituisci i parametri
            for (int i = 0; i < parametri.size(); i++) {
                statement.setObject(i + 1, parametri.get(i));
            }
            System.out.println(statement);
            // SELECT
            try {
                if (query.startsWith("S") || query.startsWith("s")) {
                    ResultSet resultSet = statement.executeQuery();
                    resultSetToArrayList(resultSet);
                } else { // INSERT | UPDATE | DELETE
                    totaleTupleAggiornate = statement.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new ErroreComunicazioneDBMSException("Query sbagliata!");
            }
            tentativo_riconnessione = 0;
        } catch (SQLException ex) {
            tentativo_riconnessione++;
            if (tentativo_riconnessione <= 3) {
                for (int i = 0; i < 3; i++) {
                    if (i % 3 == 0) {
                        System.out.println("Tentativo (" + tentativo_riconnessione + ") di riconnessione in corso.");
                    }
                    if (i % 3 == 1) {
                        System.out.println("Tentativo (" + tentativo_riconnessione + ") di riconnessione in corso..");
                    }
                    if (i % 3 == 2) {
                        System.out.println("Tentativo (" + tentativo_riconnessione + ") di riconnessione in corso...");
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                eseguiCRUD(query, parametri);
            } else {
                throw new ErroreComunicazioneDBMSException("Connessione persa!");
            }
        }
    }

    private void resultSetToArrayList(ResultSet rs) {
        try {
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            insiemeRisultati = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>(columns);
                for (int i = 1; i <= columns; ++i) {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
                insiemeRisultati.add(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Map<String, Object>> getInsiemeRisultati() {
        return this.insiemeRisultati;
    }

    public int getTotaleTupleAggiornate() {
        return this.totaleTupleAggiornate;
    }

    public UtenteENT queryVerificaIdentita(UtenteENT utente) {
        Integer matricola = Integer.valueOf(utente.getMatricola());
        String query = "SELECT datore.* FROM datore WHERE datore.ref_utente=?";
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, matricola);
        try {
            this.eseguiCRUD(query, parametri);
            List<Map<String, Object>> utenti = this.getInsiemeRisultati();
            if (utenti.size() == 1) {
                return new UtenteENT(utente, utenti.get(0), 1);
            }
            else {
                query = "SELECT dipendente.* FROM dipendente WHERE dipendente.ref_utente=?"; 
                try {
                    this.eseguiCRUD(query, parametri);
                    utenti = this.getInsiemeRisultati();
                    if (utenti.size() == 1) {
                        return new UtenteENT(utente, utenti.get(0), 2);
                    }
                }
                catch (ErroreComunicazioneDBMSException eccezione) {
                    eccezione.printStackTrace();
                }
            }
        } catch (ErroreComunicazioneDBMSException eccezione) {
            eccezione.printStackTrace();
        }
        return null; // errore popolazione database
    }

    public UtenteENT queryVerificaCredenziali(String matricola, String password) {
        if (matricola.matches("[0-9]+")) {
            Integer matricolaInteger = Integer.valueOf(matricola);
            String query = "SELECT utente.* FROM utente WHERE utente.matricola=? AND utente.pass=?";
            List<Object> parametri = new ArrayList<>();
            parametri.add(0, matricolaInteger);
            parametri.add(1, MetodiUtili.generaHash(password));
            try {
                this.eseguiCRUD(query, parametri);
                List<Map<String, Object>> utenti = this.getInsiemeRisultati();
                if (utenti.size() == 1) {
                    return LoginCTRL.creaUtenteEntity(utenti.get(0));
                }
            } catch (ErroreComunicazioneDBMSException eccezione) {
                eccezione.printStackTrace();
            }
        }
        return null;
    }

    public UtenteENT querySelezionaUtente(String matricola) {
        if (matricola.matches("[0-9]+")) {
            Integer matricolaInteger = Integer.valueOf(matricola);
            String query = "SELECT utente.* FROM utente WHERE utente.matricola=?";
            List<Object> parametri = new ArrayList<>();
            parametri.add(0, matricolaInteger);
            try {
                this.eseguiCRUD(query, parametri);
                List<Map<String, Object>> utenti = this.getInsiemeRisultati();
                if (utenti.size() == 1) {
                    return LoginCTRL.creaUtenteEntity(utenti.get(0));
                }
            } catch (ErroreComunicazioneDBMSException eccezione) {
                eccezione.printStackTrace();
            }
        }
        return null;
    }

    public UtenteENT queryAggiornaPassword(UtenteENT utente, String nuovaPassword) {
        String query = "UPDATE utente SET utente.pass=? WHERE utente.matricola=?";
        Integer matricola = Integer.valueOf(utente.getMatricola());
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, MetodiUtili.generaHash(nuovaPassword));
        parametri.add(1, matricola);
        try {
            this.eseguiCRUD(query, parametri);
            int ris = this.getTotaleTupleAggiornate();
            if (ris == 1) {
                String query2 = "UPDATE utente SET utente.stato_pass=1 WHERE utente.matricola=?";
                List<Object> parametri2 = new ArrayList<>();
                parametri2.add(0, matricola);
                this.eseguiCRUD(query2, parametri2);
                int ris2 = this.getTotaleTupleAggiornate();
                if (ris2 == 1) {
                    utente.setPass(MetodiUtili.generaHash(nuovaPassword));
                    return utente;
                }
            }
        } catch (ErroreComunicazioneDBMSException eccezione) {
            eccezione.printStackTrace();
        }
        return null;
    }

    public UtenteENT queryModificaDati(UtenteENT utente, String emailNuova, String passwordNuova, String IBANnuovo) {
        Integer matricola = Integer.valueOf(utente.getMatricola());
        String query = "UPDATE utente SET utente.email=?, " +
                "utente.pass = ( case when ? = '(invariata)' then utente.pass else ? end ), " +
                "utente.iban=? WHERE utente.matricola=?";
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, emailNuova);
        parametri.add(1, passwordNuova);
        parametri.add(2, MetodiUtili.generaHash(passwordNuova));
        parametri.add(3, IBANnuovo);
        parametri.add(4, matricola);
        try {
            this.eseguiCRUD(query, parametri);
            utente.setEmail(emailNuova);
            if (!(passwordNuova.equals("(invariata)"))) {
                utente.setPass(MetodiUtili.generaHash(passwordNuova));
            }
            utente.setIban(IBANnuovo);
            return utente;
        } catch (ErroreComunicazioneDBMSException eccezione) {
            eccezione.printStackTrace();
        }
        return null;
    }


// Fine versione comune
// ---------------------------------------------------------------------------------------
// Inzio Gabriele

    public List<UtenteENT> queryEsportaLista(LocalDate data) {
        String query = "SELECT utente.matricola, utente.nome, utente.cognome, utente.email, utente.pass, utente.stato_pass, " + 
                       "utente.iban, utente.stato_utente FROM utente, turno_previsto, turno_effettivo WHERE " + 
                       "utente.matricola=turno_previsto.ref_dipendente AND turno_previsto.ref_dipendente=turno_effettivo.ref_dipendente " +
                       "AND turno_previsto.cod_turno = turno_effettivo.ref_turno AND turno_previsto.data_turno=?";
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, data);
        try {
            this.eseguiCRUD(query, parametri);
            if (insiemeRisultati.size() != 0) {
                List<UtenteENT> dipendenti = new ArrayList<>();
                for (int i = 0; i < this.getInsiemeRisultati().size(); i++) {
                    dipendenti.add(new UtenteENT(getInsiemeRisultati().get(i)));
                }
                return dipendenti;
            }
        } catch (ErroreComunicazioneDBMSException eccezione) {
                eccezione.printStackTrace();
        }
        return null;
    }

    public boolean queryInserisciDipendente(UtenteENT utente) {
        String query = "INSERT INTO utente (nome, cognome, email, pass, iban) VALUES (?, ?, ?, ?, ?)";
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, utente.getNome());
        parametri.add(1, utente.getCognome());
        parametri.add(2, utente.getEmail());
        String pass = MetodiUtili.casualeStringa(8);
        parametri.add(3, pass);
        parametri.add(4, utente.getIban());
        try {
            this.eseguiCRUD(query, parametri);
            int ris = this.getTotaleTupleAggiornate();
            if (ris == 1) {
                String query2 = "SELECT utente.matricola FROM utente WHERE utente.email=?";
                List<Object> parametri2 = new ArrayList<>();
                parametri2.add(0, utente.getEmail());
                this.eseguiCRUD(query2, parametri2);
                List<Map<String, Object>> utenti = this.getInsiemeRisultati();
                if (utenti.size() == 1) {
                    String matricola = utenti.get(0).get("matricola").toString();
                    String query3 = "INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (?, ?)";
                    List<Object> parametri3 = new ArrayList<>();
                    parametri3.add(0, Integer.valueOf(matricola));
                    parametri3.add(1, Integer.valueOf(utente.getServizio()));
                    this.eseguiCRUD(query3, parametri3);
                    int ris3 = this.getTotaleTupleAggiornate();
                    if (ris3 == 1) {
                        MetodiUtili.inviaEmail(utente, 4, new ArrayList<String>(Arrays.asList(matricola, pass)));
                        System.out.println("Inviare per email: " + matricola + " " + pass);
                        return true;
                    }
                }
            }
        } catch (ErroreComunicazioneDBMSException eccezione) {
            eccezione.printStackTrace();
        }
        return false;
    }

    public List<UtenteENT> queryCercaDipendente(String testoCercato) {
        String query = "SELECT utente.matricola, utente.nome, utente.cognome, utente.email, utente.pass, utente.stato_pass, utente.iban, utente.stato_utente " +
                        "FROM utente, dipendente " +
                        "WHERE utente.matricola = dipendente.ref_utente AND (utente.nome LIKE ? OR " +
                        "utente.cognome LIKE ? OR " +
                        "CONCAT(utente.nome, ' ', utente.cognome) LIKE ? OR " +
                        "CONCAT(utente.cognome, ' ', utente.nome) LIKE ?) ORDER BY utente.matricola ASC";
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, testoCercato);
        parametri.add(1, testoCercato);
        parametri.add(2, testoCercato);
        parametri.add(3, testoCercato);
        try {
            this.eseguiCRUD(query, parametri);
            List<UtenteENT> dipendenti = new ArrayList<>();
            for (int i = 0; i < this.getInsiemeRisultati().size(); i++) {
                dipendenti.add(new UtenteENT(getInsiemeRisultati().get(i)));
            }
            return dipendenti;
        } catch (ErroreComunicazioneDBMSException eccezione) {
            eccezione.printStackTrace();
        }
        return null;
    }

    public boolean queryRimuoviDipendente(String matricola) {
        String query = "DELETE FROM utente WHERE utente.matricola=?";
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, matricola);
        try {
            this.eseguiCRUD(query, parametri);
            int ris = this.getTotaleTupleAggiornate();
            if (ris == 1) {
                return true;
            }
        } catch (ErroreComunicazioneDBMSException eccezione) {
            eccezione.printStackTrace();
        }
        return false;
    }

    public boolean queryModificaDipendente(UtenteENT utente) {
        Integer matricola = Integer.valueOf(utente.getMatricola());
        System.out.println(utente);
        String query = "UPDATE utente SET utente.nome=?, utente.cognome=?, utente.email=?, utente.iban=? WHERE utente.matricola=?";
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, utente.getNome());
        parametri.add(1, utente.getCognome());
        parametri.add(2, utente.getEmail());
        parametri.add(3, utente.getIban());
        parametri.add(4, matricola);
        try {
            this.eseguiCRUD(query, parametri);
            int ris = this.getTotaleTupleAggiornate();
            if (ris == 1) {
                String query2 = "UPDATE dipendente SET dipendente.ref_servizio=?, dipendente.ore_da_recuperare=? WHERE dipendente.ref_utente=?";
                List<Object> parametri2 = new ArrayList<>();
                parametri2.add(0, Integer.toString(utente.getServizio()));
                parametri2.add(1, Integer.toString(utente.getOreDaRecuperare()));
                parametri2.add(2, matricola);
                this.eseguiCRUD(query2, parametri2);
                int ris2 = this.getTotaleTupleAggiornate();
                if (ris2 == 1) {
                    return true;
                }
            }
        } catch (ErroreComunicazioneDBMSException eccezione) {
            eccezione.printStackTrace();
        }
        return false;
    }

    public boolean generaTurni(int servizioInt) {
        String servizio = Integer.toString(servizioInt);
        int orarioInizio = 0, orarioFine = 0, numeroSottoturni = 0, max_per_sottoturno = 4;
        int meseCorrente = LocalDate.now().getMonthValue();
        int giornoCorrente = LocalDate.now().getDayOfMonth();
        LocalDate giornoInizio = null, giornoFine = null;
        // Dati sui servizi (era meglio metterli nel db...):
        switch (servizio) {
            case "1":
                orarioInizio = 0;
                orarioFine = 24;
                numeroSottoturni = 3;
                break;
            case "2":
                orarioInizio = 7;
                orarioFine = 23;
                numeroSottoturni = 4;
                break;
            case "3":
                orarioInizio = 6;
                orarioFine = 18;
                numeroSottoturni = 3;
                break;
            case "4":
                orarioInizio = 9;
                orarioFine = 17;
                numeroSottoturni = 2;
                break;
            default:
                return false;
        }
        // giornoCorrente = 1;
        // meseCorrente = 12; // altrimenti non lo possiamo testare, rimuovere
        // Intervalli in cui generare i turni:
        if (giornoCorrente == 1) {
            switch (meseCorrente) {
                case 12:
                    giornoInizio = LocalDate.of(LocalDate.now().getYear()+1, 1, 1);
                    giornoFine = LocalDate.of(LocalDate.now().getYear()+1, 3, 31);
                    break;
                case 3:
                    giornoInizio = LocalDate.of(LocalDate.now().getYear(), 4, 1);
                    giornoFine = LocalDate.of(LocalDate.now().getYear(), 6, 30);
                    break;
                case 6:
                    giornoInizio = LocalDate.of(LocalDate.now().getYear(), 7, 1);
                    giornoFine = LocalDate.of(LocalDate.now().getYear(), 9, 30);
                    break;
                case 9:
                    giornoInizio = LocalDate.of(LocalDate.now().getYear(), 10, 1);
                    giornoFine = LocalDate.of(LocalDate.now().getYear(), 12, 31);
                    break;
                default:
                    return false;
            }
        }
        else {
            return false;
        }
        String query = "SELECT utente.matricola, utente.nome, utente.cognome, utente.email, utente.pass, utente.stato_pass, " + 
                       "utente.iban, utente.stato_utente FROM utente, dipendente WHERE utente.matricola=dipendente.ref_utente AND dipendente.ref_servizio=?";
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, servizio);
        try {
            this.eseguiCRUD(query, parametri);
            // Insieme delle matricole (per servizio):
            List<UtenteENT> dipendenti = new ArrayList<>();
            for (int i = 0; i < this.getInsiemeRisultati().size(); i++) {
                dipendenti.add(new UtenteENT(getInsiemeRisultati().get(i)));
            }
            // Genera i turni giorno per giorno:
            while (giornoInizio.isBefore(giornoFine.plusDays(1L))) {
                // Crea i sottoturni:
                List<List<UtenteENT>> sottoturni = new ArrayList<>();
                for (int i = 0; i < numeroSottoturni; i++) {
                    sottoturni.add(new ArrayList<>());
                }
                // Crea una copia dell'insieme delle matricole (perché verrà svuotato):
                List<UtenteENT> dipendentiCopia = new ArrayList<>();
                for (int i = 0; i < dipendenti.size(); i++) {
                   dipendentiCopia.add(i, dipendenti.get(i));
                }
                // Mescola le matricole in modo da garantire turni diversi:
                Collections.shuffle(dipendentiCopia);
                // Smista ciclicamente le matricole nei sottoturni, con un massimo di 4 dipendenti in turno:
                int i = 0;
                while (!dipendentiCopia.isEmpty()) {
                    UtenteENT dipendente = dipendentiCopia.remove(0);
                    if (sottoturni.get(i).size() < max_per_sottoturno) {
                        sottoturni.get(i).add(dipendente);
                    }
                    i++;
                    i = i % numeroSottoturni;
                }
                // Inserisci i turni:
                for (int k = 0; k < numeroSottoturni; k++) {
                    LocalTime orarioInizioSottoturno = LocalTime.of((orarioInizio + k * ((orarioFine - orarioInizio) / numeroSottoturni)) % 24, 0, 0);
                    LocalTime orarioFineSottoturno = LocalTime.of((orarioInizio + (k + 1) * ((orarioFine - orarioInizio) / numeroSottoturni)) % 24, 0, 0);
                    String query2 = "INSERT INTO turno_previsto (ref_dipendente, data_turno, ora_inizio, ora_fine) VALUES (?, ?, ?, ?)";
                    List<Object> parametri2 = new ArrayList<>();
                    parametri2.add(0, "");
                    parametri2.add(1, giornoInizio);
                    parametri2.add(2, orarioInizioSottoturno);
                    parametri2.add(3, orarioFineSottoturno);
                    for (UtenteENT dipendente : sottoturni.get(k)) {
                        parametri2.set(0, dipendente.getMatricola());
                        this.eseguiCRUD(query2, parametri2);
                    }
                }
                giornoInizio = giornoInizio.plusDays(1L);
            }
            return true;
        } catch (ErroreComunicazioneDBMSException eccezione) {
            eccezione.printStackTrace();
        }
        return false;
    }

// Fine Gabriele
// ---------------------------------------------------------------------------------------
// Inzio Nicolò

public List<TurnoENT> querySelezionaTurni(UtenteENT utente, LocalDate data_inizio, LocalDate data_fine) {
        String query = "select * from turno_previsto where turno_previsto.ref_dipendente=? AND turno_previsto.data_turno BETWEEN ? AND ?";
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, Integer.valueOf(utente.getMatricola()));
        parametri.add(1, data_inizio);
        parametri.add(2, data_fine);
        try {
            this.eseguiCRUD(query, parametri);
            List<TurnoENT> turni = new ArrayList<>();
            for (int i = 0; i < this.getInsiemeRisultati().size(); ++i) {
                turni.add(new TurnoENT(getInsiemeRisultati().get(i)));
            }
            return turni;
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        return null;
    } 

	public List<TurnoENT> queryMostraTurni(LocalDate data, LocalTime ora_i, LocalTime ora_f, String matricola, Integer servizio){
		String query = "SELECT * FROM turno_previsto, dipendente WHERE dipendente.ref_utente = turno_previsto.ref_dipendente and data_turno = ? and ora_inizio >= ? and ora_fine <= ? and ref_dipendente LIKE ? and ref_servizio = ?";
		List<Object> parametri = new ArrayList<>();
		parametri.add(0, data); //data
		parametri.add(1, ora_i); //ora_i
		parametri.add(2, ora_f); //ora_f
		parametri.add(3, matricola + "%"); //matricola
		parametri.add(4, servizio); //servizio
		try
		{
			this.eseguiCRUD(query, parametri);
			List<TurnoENT> turni = new ArrayList<>();
			for(int i = 0; i < this.getInsiemeRisultati().size(); i++)
			{
				turni.add(new TurnoENT(getInsiemeRisultati().get(i)));
			}
			return turni;
		} catch (ErroreComunicazioneDBMSException eccezione) {
			eccezione.printStackTrace();
		}
		return null;
	}

	public List<StipendioENT> queryMostraStipendi(LocalDate da, LocalDate a, String matricola){
		String query = "SELECT * FROM stipendio WHERE data_accredito BETWEEN ? AND ? and ref_dipendente=?";
		List<Object> parametri = new ArrayList<>();
		parametri.add(0, da); //data i
		parametri.add(1, a); //data f
		parametri.add(2, matricola); //matricola
		try
		{
			this.eseguiCRUD(query, parametri);
			List<StipendioENT> stipendi = new ArrayList<>();
			for(int i = 0; i < this.getInsiemeRisultati().size(); i++)
			{
				stipendi.add(new StipendioENT(getInsiemeRisultati().get(i)));
			}
			return stipendi;
		} catch (ErroreComunicazioneDBMSException eccezione) {
			eccezione.printStackTrace();
		}
		return null;
	}

	public boolean queryCalcolaStipendi() {	
        String query1 = "SELECT utente.matricola, utente.nome, utente.cognome, utente.email, utente.pass, utente.stato_pass, utente.iban, utente.stato_utente FROM utente, dipendente WHERE utente.matricola = dipendente.ref_utente and ref_utente>= ?"; //seleziona dal db tutte le matricole
        List<Object> parametriFasulli = new ArrayList<>();
        parametriFasulli.add(0, "0");
        try{
            this.eseguiCRUD(query1, parametriFasulli);
        }
        catch (ErroreComunicazioneDBMSException eccezione) {
        eccezione.printStackTrace();
        }
        List <UtenteENT> matricole = new ArrayList<>();
        for(int i = 0; i<insiemeRisultati.size(); i++) {
            matricole.add(new UtenteENT(getInsiemeRisultati().get(i)));
        }
        for(int i = 0; i< matricole.size(); i++)
        {
            String query2 = "INSERT INTO stipendio (ref_dipendente, data_accredito, stipendio_mensile) VALUES (?, ?, CASE WHEN (SELECT SUM(stipendio_parziale) FROM turno_effettivo, turno_previsto WHERE ref_turno = cod_turno and turno_previsto.ref_dipendente = turno_effettivo.ref_dipendente and turno_effettivo.ref_dipendente = ? and data_turno BETWEEN ? and ?) IS NULL THEN 0.0 ELSE (SELECT SUM(stipendio_parziale) FROM turno_effettivo, turno_previsto WHERE ref_turno = cod_turno and turno_previsto.ref_dipendente = turno_effettivo.ref_dipendente and turno_effettivo.ref_dipendente = ? and data_turno BETWEEN ? and ?) END )";
            List<Object> parametriVeri = new ArrayList<>();
            parametriVeri.add(0, matricole.get(i).getMatricola()); //prende la matricola
            parametriVeri.add(1, LocalDate.now());
            parametriVeri.add(2, matricole.get(i).getMatricola());
            parametriVeri.add(3, LocalDate.now().minusMonths(1L));
            parametriVeri.add(4, LocalDate.now().minusDays(1L) );
            parametriVeri.add(5, matricole.get(i).getMatricola()); //prende la matricola
            parametriVeri.add(6, LocalDate.now().minusMonths(1L));
            parametriVeri.add(7, LocalDate.now().minusDays(1L) );
            try{
                this.eseguiCRUD(query2, parametriVeri);
            }
            catch (ErroreComunicazioneDBMSException eccezione) {
                eccezione.printStackTrace();
            }
            if(totaleTupleAggiornate==0) {
                System.out.println("------------------------");
                return false;
            }
        }
        return true;
	}

// Fine Nicolò
// ---------------------------------------------------------------------------------------
// Inzio Riccardo

    public List<InfrastrutturaENT> queryMostraInfrastrutture(String codice, String nome, String citta) {
        String query = "SELECT * FROM infrastruttura WHERE infrastruttura.cod_infrastruttura like ? and infrastruttura.nome like ? and infrastruttura.citta like ?";
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, codice);
        parametri.add(1, nome);
        parametri.add(2, citta);
        try {
            this.eseguiCRUD(query, parametri);
            List<InfrastrutturaENT> infrastruttura = new ArrayList<>();
            for (int i = 0; i < this.getInsiemeRisultati().size(); i++) {
                infrastruttura.add(new InfrastrutturaENT(getInsiemeRisultati().get(i)));
            }
            return infrastruttura;
        } catch (ErroreComunicazioneDBMSException eccezione) {
            eccezione.printStackTrace();
        }
        return null;
    }

    public List<ProgettoENT> queryMostraProgetti(String codice, String descrizione, String stato) {
        String query = "SELECT * FROM progetto WHERE progetto.cod_progetto like ? and progetto.descrizione like ? and progetto.stato_progetto like ?";
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, codice);
        parametri.add(1, descrizione);
        parametri.add(2, stato);
        try {
            this.eseguiCRUD(query, parametri);
            List<ProgettoENT> progetto = new ArrayList<>();
            for (int i = 0; i < this.getInsiemeRisultati().size(); i++) {
                progetto.add(new ProgettoENT(getInsiemeRisultati().get(i)));
            }
            return progetto;
        } catch (ErroreComunicazioneDBMSException eccezione) {
            eccezione.printStackTrace();
        }
        return null;
    }

    public List<SegnalazioneENT> queryMostraSegnalazioni(String codice, String domanda, String risposta, String categoria) {
        String query = "SELECT * FROM segnalazione WHERE segnalazione.cod_segnalazione like ? and segnalazione.domanda like ? and segnalazione.risposta like ? and segnalazione.categoria like ?";
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, codice);
        parametri.add(1, domanda);
        parametri.add(2, risposta);
        parametri.add(3, categoria);
        try {
            this.eseguiCRUD(query, parametri);
            List<SegnalazioneENT> segnalazioni = new ArrayList<>();
            for (int i = 0; i < this.getInsiemeRisultati().size(); i++) {
                segnalazioni.add(new SegnalazioneENT(getInsiemeRisultati().get(i)));
            }
            return segnalazioni;
        } catch (ErroreComunicazioneDBMSException eccezione) {
            eccezione.printStackTrace();
        }
        return null;
    }

    public List<RecensioneENT> queryMostraRecensioni(String codice, String testo, String punteggio) {
        String query = "select * from recensione where recensione.ref_segnalazione like ? and recensione.testo like ? and recensione.punteggio like ?;";
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, codice);
        parametri.add(1, testo);
        parametri.add(2, punteggio);
        try {
            this.eseguiCRUD(query, parametri);
            List<RecensioneENT> recensione = new ArrayList<>();
            for (int i = 0; i < this.getInsiemeRisultati().size(); i++) {
                recensione.add(new RecensioneENT(getInsiemeRisultati().get(i)));
            }
            return recensione;
        } catch (ErroreComunicazioneDBMSException eccezione) {
            eccezione.printStackTrace();
        }
        return null;
    }

// Fine Riccardo
// ---------------------------------------------------------------------------------------
// Inzio Giuseppe

    public LocalDate queryImpostaGiorniIndisponibili(LocalDate dataInizio, LocalDate dataFine, UtenteENT utente){
        List<Object> parametroData= new ArrayList<>();
        parametroData.add(0, Integer.valueOf(utente.getMatricola()));
        parametroData.add(1, dataInizio);
        // System.out.println(utente.getMatricola()+" c");
        while (dataInizio.isBefore(dataFine.plusDays(1))) {   
            // System.out.println(dataInizio.toString()+" a");
            try {
                eseguiCRUD("INSERT INTO giorni_indisponibili (ref_datore, giorno_indisponibile) VALUES (?, ?)", parametroData);
            } catch (ErroreComunicazioneDBMSException e) {
                e.printStackTrace();
                return dataInizio;
            }
            // System.out.println(dataInizio.toString()+" b");
            dataInizio = dataInizio.plusDays(1L);
            parametroData.set(1, dataInizio);
        }
        return null; 
    }


    public void queryPresenza(int matricola, TurnoENT turno){
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, matricola);
        parametri.add(1, turno.getCodice_turno());
        parametri.add(2, LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()));
        try {
            eseguiCRUD("INSERT INTO turno_effettivo(ref_dipendente, ref_turno, stato_entrata, stato_uscita, ora_inizio_effettiva, ora_fine_effettiva, stipendio_parziale)  VALUES(?, ?, 1, null, ?, null, null)", parametri);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
    }


    public List<UtenteENT> queryInTurno(UtenteENT ent, LocalDate dataInizio, LocalDate dataFine) {
        List<Object> parametri1= new ArrayList<>();
        parametri1.add(0, dataInizio);
        parametri1.add(1, ent.getMatricola());
        try {
            eseguiCRUD("SELECT * FROM turno_previsto WHERE data_turno=? AND ref_dipendente=?", parametri1);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        TurnoENT turno = new TurnoENT(insiemeRisultati.get(0));
        String cod_turno = turno.getCodice_turno();
        List<Object> parametri2= new ArrayList<>();
        parametri2.add(0, cod_turno);
        parametri2.add(1, cod_turno);
        try {
            eseguiCRUD("SELECT utente.matricola, utente.nome, utente.cognome, utente.email, utente.pass, utente.stato_pass, utente.iban, utente.stato_utente FROM turno_previsto, utente, dipendente where dipendente.ref_utente=utente.matricola and dipendente.ref_utente=turno_previsto.ref_dipendente and cod_turno=? and turno_previsto.ref_dipendente NOT IN (SELECT ref_dipendente_da_sostituire FROM variazione where ref_turno_da_sostituire=?  )" ,parametri2);
        } catch (ErroreComunicazioneDBMSException e1) {
            e1.printStackTrace();
        }
        List<UtenteENT> utentiInTurno = new ArrayList<>();
        for (int i = 0; i < insiemeRisultati.size(); i++) {
            utentiInTurno.add(new UtenteENT(insiemeRisultati.get(i)));
        }
        for (int i = 0; i < utentiInTurno.size(); i++) {
            System.out.println(utentiInTurno.get(i).getMatricola());
        }
        return utentiInTurno;
    }

    public ArrayList<LocalDate> queryGiorniIndisponibili(LocalDate daLD, LocalDate aLD) {
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, daLD);
        parametri.add(1, aLD);
        try {
            eseguiCRUD("SELECT giorno_indisponibile FROM giorni_indisponibili WHERE giorni_indisponibili.giorno_indisponibile>=? AND giorni_indisponibili.giorno_indisponibile<=?", parametri);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }

	ArrayList<LocalDate> giorni = new ArrayList<>();

	for(int i = 0; i<insiemeRisultati.size(); i++)
	{
		LocalDate ld = LocalDate.parse(insiemeRisultati.get(i).get("giorno_indisponibile").toString());
		giorni.add(ld);
	}
	
        return giorni;
    }
/*
    public int queryAccettaFerie(UtenteENT utente,LocalTime daLT, LocalTime aLT, LocalDate daLD, LocalDate aLD, int motivo, String testo) {
        // System.out.println(2);
        int flag=0;
        while (daLD.isBefore(aLD.plusDays(1))) {
		insiemeRisultati = null;
            List<Object> parametri2 = new ArrayList<>();
            List<Object> parametri1 = new ArrayList<>();
            // System.out.println(3);
            parametri1.add(0, utente.getMatricola());
            parametri1.add(1, daLD);
            try {
                eseguiCRUD("SELECT * FROM turno_previsto WHERE ref_dipendente=? AND data_turno=?", parametri1);
            } catch (ErroreComunicazioneDBMSException e) {
                e.printStackTrace();
            }
		if(insiemeRisultati.size() != 0 ) {
            // System.out.println(insiemeRisultati.get(0));
            TurnoENT turno = new TurnoENT(insiemeRisultati.get(0));
            String cod_turno = turno.getCodice_turno();
            parametri2.add(0, utente.getMatricola());
            parametri2.add(1, cod_turno);
            parametri2.add(2, turno.getOra_inizio());
            parametri2.add(3, turno.getOra_fine());
            parametri2.add(4, motivo);
            parametri2.add(5, testo);
            try {
                eseguiCRUD(
                        "INSERT INTO variazione(ref_dipendente_da_sostituire, ref_turno_da_sostituire, ref_dipendente_sostituto, ref_turno_sostituto, ora_inizio_variazione, ora_fine_variazione, motivo, dettagli_testo) VALUES(?, ?, null, null, ?, ?, ?, ?)", parametri2);
            } catch (ErroreComunicazioneDBMSException e) {
                e.printStackTrace();
            }
            queryAggiornaOreDaRecuperare_aggiungi(utente.getMatricola(), turno.getCodice_turno());
		}
	    else {
            flag = 1;
        }
            daLD = daLD.plusDays(1L);
        }
        return flag;
    }
*/
    public int queryAccettaFerie(UtenteENT utente, LocalTime daLT, LocalTime aLT, LocalDate daLD, LocalDate aLD, int motivo, String testo) {
        // System.out.println(2);
        int flag = 0;
        while (daLD.isBefore(aLD.plusDays(1))) {
            insiemeRisultati = null;

            String mese = Integer.toString(daLD.getMonth().getValue());
            if (Integer.parseInt(mese) <= 9) {
                mese = "0" + mese;
            }
            String giorno = Integer.toString(daLD.getDayOfMonth());
            if (Integer.parseInt(giorno) <= 9) {
                giorno = "0" + giorno;
            }
            String codTurnoAdesso = Integer.toString(daLD.getYear()) + mese + giorno;
            List<Object> parametri = new ArrayList<>();
            parametri.add(0, utente.getMatricola());
            parametri.add(1, daLD);
            parametri.add(2, utente.getMatricola());
            parametri.add(3, codTurnoAdesso + "%");
            try {
                eseguiCRUD(
                        "SELECT * FROM turno_previsto WHERE ref_dipendente=? AND data_turno=? AND cod_turno NOT IN (SELECT ref_turno_da_sostituire FROM variazione WHERE ref_dipendente_sostituto=? AND ref_turno_da_sostituire like ?)",
                        parametri);
            } catch (ErroreComunicazioneDBMSException e) {
                e.printStackTrace();
            }

            /*
             * List<Object> parametri1 = new ArrayList<>();
             * // System.out.println(3);
             * parametri1.add(0, utente.getMatricola());
             * parametri1.add(1, daLD);
             * try {
             * eseguiCRUD("SELECT * FROM turno_previsto WHERE ref_dipendente=? AND data_turno=?"
             * , parametri1);
             * } catch (ErroreComunicazioneDBMSException e) {
             * e.printStackTrace();
             * }
             */

            if (insiemeRisultati.size() != 0) {
                // System.out.println(insiemeRisultati.get(0));
                List<Object> parametri2 = new ArrayList<>();
                parametri2.add(0, utente.getMatricola());
                parametri2.add(1, insiemeRisultati.get(0).get("cod_turno"));
                parametri2.add(2, insiemeRisultati.get(0).get("ora_inizio"));
                parametri2.add(3, insiemeRisultati.get(0).get("ora_fine"));
                parametri2.add(4, motivo);
                parametri2.add(5, testo);
                try {
                    eseguiCRUD(
                            "INSERT INTO variazione(ref_dipendente_da_sostituire, ref_turno_da_sostituire, ref_dipendente_sostituto, ref_turno_sostituto, ora_inizio_variazione, ora_fine_variazione, motivo, dettagli_testo) VALUES(?, ?, null, null, ?, ?, ?, ?)",
                            parametri2);
                } catch (ErroreComunicazioneDBMSException e) {
                    e.printStackTrace();
                }
                queryAggiornaOreDaRecuperare_aggiungi(utente.getMatricola(), insiemeRisultati.get(0).get("cod_turno"));
            } else {
                flag = 1;
            }

            daLD = daLD.plusDays(1L);
        }
        return flag;
    }

    public TurnoENT queryGetTurno(UtenteENT ent, LocalDateTime dateTime) {
        List<Object> parametri1= new ArrayList<>();
        parametri1.add(0, dateTime.toLocalDate());
        parametri1.add(1, ent.getMatricola());
        parametri1.add(2, LocalTime.of(dateTime.toLocalTime().getHour(), 0, 0)); //dateTime.toLocalTime().getHour()
        try {
            eseguiCRUD("SELECT * FROM turno_previsto WHERE data_turno=? AND ref_dipendente=? AND ora_inizio=?", parametri1);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        if (insiemeRisultati.size()>0) {
            // System.out.println(insiemeRisultati.get(0));
            TurnoENT turno = new TurnoENT(insiemeRisultati.get(0));
            return turno;
        } else {
            // System.out.println("insieme dei risultati vuoto");
            return null;
        }
    }

    public void queryDipendentiTurno(LocalDateTime DateTime) {
        //UtenteENT turno = new UtenteENT(insiemeRisultati.get(0));
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, DateTime.toLocalDate());
        parametri.add(1, LocalTime.of(DateTime.getHour(), 0, 0));
        try {
            eseguiCRUD(
                    "SELECT utente.matricola, utente.nome, utente.cognome, utente.email, utente.pass, utente.stato_pass, utente.iban, utente.stato_utente FROM turno_previsto, utente WHERE utente.matricola=turno_previsto.ref_dipendente AND data_turno=? AND ora_inizio=? AND ref_dipendente NOT IN (SELECT turno_effettivo.ref_dipendente FROM turno_effettivo WHERE turno_effettivo.ref_turno=turno_previsto.cod_turno)",
                    parametri);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        // System.out.println("a");
        // System.out.println(insiemeRisultati);
        // System.out.println("a");
        List<UtenteENT> matricole = new ArrayList<>();
        for (int i = 0; i < insiemeRisultati.size(); i++) {
            matricole.add(new UtenteENT(getInsiemeRisultati().get(i)));
        }
        // System.out.println("a");
        for (int i = 0; i < matricole.size(); i++) {
            // System.out.println(i);
            TurnoENT turno= queryGetTurno(matricole.get(i), DateTime);
            List<Object> parametri1 = new ArrayList<>();
            parametri1.add(0, matricole.get(i).getMatricola());
            parametri1.add(1, turno.getCodice_turno());
            parametri1.add(2, LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()));
            try {
                eseguiCRUD(
                        "INSERT INTO turno_effettivo(ref_dipendente, ref_turno, stato_entrata, stato_uscita, ora_inizio_effettiva, ora_fine_effettiva, stipendio_parziale)  VALUES(?, ?, 0, null, ?, null, null)", parametri1);
            } catch (ErroreComunicazioneDBMSException e) {
                e.printStackTrace();
            }
        }
    }

    public List<UtenteENT> queryDipendentiPresenti(LocalDateTime dateTime, int servizio) {
        List<Object> parametri1 = new ArrayList<>();
        parametri1.add(0, dateTime.toLocalDate());
        parametri1.add(1, LocalTime.of(dateTime.toLocalTime().getHour(), 0, 0)); //dateTime.toLocalTime().getHour()
        try {
            eseguiCRUD("SELECT * FROM turno_previsto WHERE data_turno=? AND ora_inizio=?", parametri1);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        // System.out.println(insiemeRisultati);
        TurnoENT turno = new TurnoENT(insiemeRisultati.get(0));
        List<Object> parametri2 =new ArrayList<>();
        parametri2.add(0, turno.getCodice_turno());
        parametri2.add(1,servizio);
        try {
            eseguiCRUD("SELECT utente.matricola, utente.nome, utente.cognome, utente.email, utente.pass, utente.stato_pass, utente.iban, utente.stato_utente FROM utente, turno_effettivo, dipendente WHERE ref_turno=? AND ref_servizio=? AND stato_entrata=1 and ref_dipendente=ref_utente AND ref_utente=matricola", parametri2);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        // System.out.println(insiemeRisultati);
        if (insiemeRisultati.size() == 0) {
            return null;
        }
        List<UtenteENT> dipendenti = new ArrayList<>();
        for(int i = 0; i<insiemeRisultati.size(); i++) {
            dipendenti.add(new UtenteENT(getInsiemeRisultati().get(i)));
        }
        return dipendenti;
    }

    public void queryChiudiServizio(int servizio) {
        List<Object> parametri1= new ArrayList<>();
        parametri1.add(0, servizio);
        try {
            eseguiCRUD("UPDATE servizio SET stato_servizio=0 WHERE cod_servizio=?", parametri1);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
    }

    public List<UtenteENT> queryGetAssenti(LocalDateTime dateTime) {
        // System.out.println("aa");
        List<Object> parametri1= new ArrayList<>();
        parametri1.add(0, dateTime.toLocalDate());
        parametri1.add(1, LocalTime.of(dateTime.toLocalTime().getHour() - 3, 0, 0));
        try {
            eseguiCRUD("SELECT * FROM turno_previsto WHERE data_turno=? AND ora_inizio=?", parametri1);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        // System.out.println(insiemeRisultati);
        // System.out.println("bb");
        TurnoENT turno = new TurnoENT(insiemeRisultati.get(0));
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, turno.getCodice_turno());
        try {
            eseguiCRUD(
                    "SELECT utente.matricola, utente.nome, utente.cognome, utente.email, utente.pass, utente.stato_pass, utente.iban, utente.stato_utente FROM turno_effettivo, dipendente, utente WHERE turno_effettivo.ref_turno=? and stato_entrata=0 and turno_effettivo.ref_dipendente=dipendente.ref_utente and dipendente.ref_utente=utente.matricola ", parametri);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        // System.out.println(insiemeRisultati);
        List<UtenteENT> matricole = new ArrayList<>();
        for (int i = 0; i < insiemeRisultati.size(); i++) {
            matricole.add(new UtenteENT(getInsiemeRisultati().get(i)));
        }
        return matricole;
    } 

    public List<Object> queryNonInTurno(UtenteENT ent, LocalDate dataInizio, LocalDate dataFine) {
        List<Object> parametri1= new ArrayList<>();
        parametri1.add(0, dataInizio);
        parametri1.add(1, ent.getMatricola());
        try {
            eseguiCRUD("SELECT * FROM turno_previsto WHERE data_turno=? AND ref_dipendente=?", parametri1);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        // System.out.println(insiemeRisultati);
        TurnoENT turno = new TurnoENT(insiemeRisultati.get(0));
        String cod_turno = turno.getCodice_turno();
        List<Object> parametri3= new ArrayList<>();
        parametri3.add(0,  ent.getMatricola());
        try {
            eseguiCRUD("SELECT ref_servizio FROM dipendente WHERE ref_utente=?", parametri3);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        // System.out.println(insiemeRisultati);
        List<Object> parametri2= new ArrayList<>();
        parametri2.add(0, cod_turno);
        parametri2.add(1, insiemeRisultati.get(0).get("ref_servizio"));
        parametri2.add(2, dataInizio);
        try {
            eseguiCRUD("SELECT ref_dipendente, cod_turno FROM turno_previsto , dipendente WHERE cod_turno<>? and dipendente.ref_servizio=? and dipendente.ref_utente=turno_previsto.ref_dipendente and data_turno=? ORDER BY ore_da_recuperare DESC", parametri2);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        // System.out.println(insiemeRisultati.get(0));
        if (insiemeRisultati.size()==0) {
            return null;
        } else {
            List<Object> entSelezionato_codTurno = new ArrayList<>();
            entSelezionato_codTurno.add(0, insiemeRisultati.get(0).get("ref_dipendente"));
            entSelezionato_codTurno.add(1, insiemeRisultati.get(0).get("cod_turno"));
            entSelezionato_codTurno.add(2, ent.getMatricola()); // matricola e codice turno del dipendente a cui aggiornare la tupla
            entSelezionato_codTurno.add(3, cod_turno);
            List<Object> parametri4= new ArrayList<>();
            parametri4.add(0, ent.getMatricola());
            try {
                eseguiCRUD("SELECT email, ref_servizio FROM utente, dipendente WHERE ref_utente=matricola AND matricola=?", parametri4);
            } catch (ErroreComunicazioneDBMSException e) {
                e.printStackTrace();
            }
            entSelezionato_codTurno.add(4, insiemeRisultati.get(0).get("ref_servizio"));
            return entSelezionato_codTurno;
        }
    }

    public void queryAggiornaVariazione(List<Object> entSelezioanto_codTurno) {
        List<Object> parametri1 = new ArrayList<>();
        parametri1.add(0, entSelezioanto_codTurno.get(0));
        parametri1.add(1, LocalDate.of( Integer.parseInt(entSelezioanto_codTurno.get(3).toString().substring(0, 4)), 
                                        Integer.parseInt(entSelezioanto_codTurno.get(3).toString().substring(4, 6)), 
                                        Integer.parseInt(entSelezioanto_codTurno.get(3).toString().substring(6, 8))) );
        parametri1.add(2, LocalTime.of(Integer.parseInt(entSelezioanto_codTurno.get(3).toString().substring(8, 10)), 0, 0));
        parametri1.add(3, LocalTime.of(Integer.parseInt(entSelezioanto_codTurno.get(3).toString().substring(10, 12)), 0, 0));
        try {
            eseguiCRUD("INSERT INTO turno_previsto (ref_dipendente, data_turno, ora_inizio, ora_fine) VALUES (?, ?, ?, ?)", parametri1);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, entSelezioanto_codTurno.get(0));
        parametri.add(1, entSelezioanto_codTurno.get(3));
        parametri.add(2, entSelezioanto_codTurno.get(2));
        parametri.add(3, entSelezioanto_codTurno.get(3));
        try {
            eseguiCRUD("UPDATE variazione SET ref_dipendente_sostituto=?, ref_turno_sostituto=? WHERE ref_dipendente_da_sostituire=? AND ref_turno_da_sostituire=?", parametri);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
    }

    public List<Object> queryInTurnoAltriServizi(List<Object> parametri) {
        try {// PUO' RESTITUIRE DIPENDENTI CHE HANNO CHIESTO VARIAZIONI PER QUELLA DATA
            eseguiCRUD(
                    "SELECT ref_dipendente, cod_turno FROM turno_previsto, utente, dipendente WHERE dipendente.ref_servizio=? and dipendente.ref_utente=utente.matricola and dipendente.ref_utente=turno_previsto.ref_dipendente and data_turno=? ORDER BY ore_da_recuperare DESC",
                    parametri);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        List<Object> utente_selezionato;
        // System.out.println(insiemeRisultati);
        if (insiemeRisultati.size()==0) {
            return null;
        } else {
            utente_selezionato = new ArrayList<>();
            utente_selezionato.add(0, insiemeRisultati.get(0).get("ref_dipendente"));
            utente_selezionato.add(1, insiemeRisultati.get(0).get("cod_turno"));
            return utente_selezionato;
        }
    }

    public List<Object> queryNonInTurnoAltriServizi(List<Object> parametri) {

        try {// PUO' RESTITUIRE DIPENDENTI CHE HANNO CHIESTO VARIAZIONI PER QUELLA DATA
            eseguiCRUD(
                    "SELECT ref_dipendente, cod_turno FROM turno_previsto, utente, dipendente WHERE dipendente.ref_servizio=? dipendente.ref_utente=utente.matricola and dipendente.ref_utente=turno_previsto.ref_dipendente and data_turno>? ORDER BY ore_da_recuperare DESC",
                    parametri);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        List<Object> utente_selezionato;
        if (insiemeRisultati.size()==0) {
            return null;
        } else {
            utente_selezionato = new ArrayList<>();
            utente_selezionato.add(0, querySelezionaUtente(Integer.toString(new UtenteENT (insiemeRisultati.get(0)).getMatricola())));
            utente_selezionato.add(1, insiemeRisultati.get(0).get("cod_turno"));
            return utente_selezionato;
        }
    }

    public List<String> queryGetTurno2(UtenteENT utente, LocalDate data) {
        List<Object> parametri1= new ArrayList<>();
        parametri1.add(0, data);
        parametri1.add(1, utente.getMatricola());
        try {
            eseguiCRUD("SELECT * FROM turno_previsto WHERE data_turno=? AND ref_dipendente=?", parametri1);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        // System.out.println(insiemeRisultati);
        List<String> turni= new ArrayList<>();
        for (int i = 0; i < insiemeRisultati.size(); i++) {
            turni.add(i, new TurnoENT(insiemeRisultati.get(i)).getCodice_turno());
        }
        return turni;
    }

    public void queryAggiornaOreDaRecuperare_rimuovi(Object matricola, Object cod_turno) {
        System.out.println("rimuovi");
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, cod_turno);
        try {
            eseguiCRUD("SELECT * FROM turno_previsto WHERE cod_turno=?", parametri);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        TurnoENT turno = new TurnoENT(insiemeRisultati.get(0));
        int ore_da_effettuare = turno.getOra_fine().getHour() - turno.getOra_inizio().getHour();
        parametri.set(0, matricola);
        try {
            eseguiCRUD("SELECT ore_da_recuperare FROM dipendente WHERE ref_utente=?", parametri);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        int ore_finali= (((Number)insiemeRisultati.get(0).get("ore_da_recuperare")).intValue()) - ore_da_effettuare;
        if (ore_finali<0) {
            ore_finali=0;
        }
        parametri.set(0, ore_finali);
        parametri.add(1, matricola);
        try {
            eseguiCRUD("UPDATE dipendente SET ore_da_recuperare=? WHERE ref_utente=?", parametri);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
    }

    public void queryAggiornaOreDaRecuperare_aggiungi(Object matricola, Object cod_turno) {
        System.out.println("aggiungi");
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, cod_turno);
        try {
            eseguiCRUD("SELECT * FROM turno_previsto WHERE cod_turno=?", parametri);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        TurnoENT turno = new TurnoENT(insiemeRisultati.get(0));
        int ore_da_effettuare = turno.getOra_fine().getHour() - turno.getOra_inizio().getHour();
        parametri.set(0, matricola);
        try {
            eseguiCRUD("SELECT ore_da_recuperare FROM dipendente WHERE ref_utente=?", parametri);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        int ore_finali= (((Number)insiemeRisultati.get(0).get("ore_da_recuperare")).intValue()) + ore_da_effettuare;
        parametri.set(0, ore_finali);
        parametri.add(1, matricola);
        try {
            eseguiCRUD("UPDATE dipendente SET ore_da_recuperare=? WHERE ref_utente=?", parametri);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
    }

    public TurnoENT queryGetFineTurno(UtenteENT ent, LocalDateTime dateTime) {
        List<Object> parametri1 = new ArrayList<>();
        parametri1.add(0, dateTime.toLocalDate());
        parametri1.add(1, ent.getMatricola());
        parametri1.add(2, LocalTime.of(dateTime.toLocalTime().getHour(), 0, 0)); // dateTime.toLocalTime().getHour()
        try {
            eseguiCRUD("SELECT * FROM turno_previsto WHERE data_turno=? AND ref_dipendente=? AND ora_fine=?",
                    parametri1);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        if (insiemeRisultati.size() > 0) {
            // System.out.println(insiemeRisultati.get(0));
            TurnoENT turno = new TurnoENT(insiemeRisultati.get(0));
            return turno;
        } else {
            // System.out.println("non sei in turno uscita");
            return null;
        }
    }

    public void queryStatoFineTurno(UtenteENT ent, TurnoENT turno, String tipo) {
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, ent.getMatricola());
        parametri.add(1, turno.getCodice_turno());
        try {
            eseguiCRUD("SELECT ora_inizio_effettiva FROM turno_effettivo WHERE ref_dipendente=? AND ref_turno=?", parametri);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        LocalTime ora_inizio_effettiva = LocalTime.parse(String.valueOf(insiemeRisultati.get(0).get("ora_inizio_effettiva")));
        if (ora_inizio_effettiva.getMinute() >= 0 && ora_inizio_effettiva.getMinute() <= 10) {
            ora_inizio_effettiva = LocalTime.of(ora_inizio_effettiva.getHour(), 0, 0);
        }
        // System.out.println(ora_inizio_effettiva);
        long diff = LocalTime.of(LocalTime.now().getHour(), 0).getLong(ChronoField.MILLI_OF_DAY) - 
                    LocalTime.of(ora_inizio_effettiva.getHour(), ora_inizio_effettiva.getMinute()).getLong(ChronoField.MILLI_OF_DAY);
        double ore = diff/3600000.0;
        // System.out.println(ore);
        int servizio = queryServizioVariazione(ent, LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth(),
                                                     ora_inizio_effettiva.getHour(), ora_inizio_effettiva.getMinute(), ora_inizio_effettiva.getSecond()));
        if ( servizio == 0 ) { 
            List<Object> parametri3 = new ArrayList<>();
            parametri3.add(0, ent.getMatricola());
            try {
                eseguiCRUD("SELECT ref_servizio FROM dipendente WHERE ref_utente=?", parametri3);
            } catch (ErroreComunicazioneDBMSException e) {
                e.printStackTrace();
            }
            servizio = Integer.parseInt(insiemeRisultati.get(0).get("ref_servizio").toString());
        }
        // System.out.println(insiemeRisultati);
        List<Object> parametri1 = new ArrayList<>();
        // parametri1.add(0, ((Number)insiemeRisultati.get(0).get("ref_servizio")).intValue());
        parametri1.add(0, Integer.toString(servizio));
        try {
            eseguiCRUD("SELECT stipendio_base FROM servizio WHERE cod_servizio=?", parametri1);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        // System.out.println(insiemeRisultati);
        int stipendio_base = ((Number)insiemeRisultati.get(0).get("stipendio_base")).intValue();
        LocalDate giorno = LocalDate.now();
        if (LocalTime.now().getHour() == 0) {
            giorno.minusDays(1L);
        }
        double stipendio = ore * stipendio_base;
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.valueOf("SUNDAY")) { 
            stipendio += 15.0;
        }
        if ( giorno.equals(LocalDate.of(LocalDate.now().getYear(), 1, 1)) ||
             giorno.equals(LocalDate.of(LocalDate.now().getYear(), 1, 6)) ||
             giorno.equals(LocalDate.of(LocalDate.now().getYear(), 4, 25)) ||
             giorno.equals(LocalDate.of(LocalDate.now().getYear(), 5, 1)) ||
             giorno.equals(LocalDate.of(LocalDate.now().getYear(), 6, 2)) ||
             giorno.equals(LocalDate.of(LocalDate.now().getYear(), 8, 15)) ||
             giorno.equals(LocalDate.of(LocalDate.now().getYear(), 11, 1)) ||
             giorno.equals(LocalDate.of(LocalDate.now().getYear(), 12, 8)) ||
             giorno.equals(LocalDate.of(LocalDate.now().getYear(), 12, 25)) ||
             giorno.equals(LocalDate.of(LocalDate.now().getYear(), 12, 26)) ||
             CalcolaStipendioBND.isPasqua(giorno) || CalcolaStipendioBND.isPasqua(giorno.minusDays(1L))
        ) { // domenica servizio 1
            stipendio += 25.0;
        }
        // System.out.println(stipendio);
        List<Object> parametri2 = new ArrayList<>();
        parametri2.add(0, LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()));
        parametri2.add(1, stipendio);
        parametri2.add(2, ent.getMatricola());
        parametri2.add(3, turno.getCodice_turno());
        try {
            if (tipo.equals("1")) {
                eseguiCRUD("UPDATE turno_effettivo SET stato_uscita=1, ora_fine_effettiva=?, stipendio_parziale=(case when stato_entrata<>0 then ? else 0.0 end)  WHERE ref_dipendente=? AND ref_turno=?", parametri2);
            }
            else {
                if (tipo.equals("0")) {
                    eseguiCRUD("UPDATE turno_effettivo SET stato_uscita=0, ora_fine_effettiva=?, stipendio_parziale=(case when stato_entrata<>0 then ? else 0.0 end)  WHERE ref_dipendente=? AND ref_turno=?", parametri2);
                }
            }
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
    }

    public int queryPresenzaGiaEffettuata(UtenteENT utente, LocalDateTime data_ora) {
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, LocalTime.of(data_ora.getHour(), data_ora.getMinute(), data_ora.getSecond()));
        parametri.add(1, LocalTime.of(data_ora.getHour(), data_ora.getMinute(), data_ora.getSecond()));
        parametri.add(2, data_ora.toLocalDate());
        parametri.add(3, utente.getMatricola());
        TurnoENT turno=  queryTurnoDaEffettuare(parametri); 
        if (turno==null) {
            // System.out.println("non sei in turno");
            return -1;
        }
        List<Object> parametri1 = new ArrayList<>(); 
        parametri1.add(0, turno.getCodice_turno());
        parametri1.add(1, utente.getMatricola());
        try {
            eseguiCRUD("SELECT stato_entrata FROM turno_effettivo WHERE ref_turno=? AND ref_dipendente=?", parametri1);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        // System.out.println(insiemeRisultati);
        if(insiemeRisultati.size() != 0) {
            return Integer.parseInt(insiemeRisultati.get(0).get("stato_entrata").toString());
        }
        return -2;
    }

    public TurnoENT queryTurnoDaEffettuare(List<Object> parametri) {
        try {
            eseguiCRUD("SELECT * FROM turno_previsto WHERE ora_inizio<? AND ora_fine>? AND data_turno=? AND ref_dipendente=?", parametri);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        // System.out.println(insiemeRisultati); 
        if (insiemeRisultati.size()==0) {
            return null;
        }
        TurnoENT turno = new TurnoENT(insiemeRisultati.get(0));
        // System.out.println(turno);
        return turno;
    }

    public void queryAggiornaStato(TurnoENT turno, UtenteENT utente) {
        List<Object> parametri =new ArrayList<>();
        parametri.add(0, LocalTime.now());
        parametri.add(1, utente.getMatricola());
        parametri.add(2, turno.getCodice_turno());
        try {
            eseguiCRUD("UPDATE turno_effettivo SET stato_entrata=2, ora_inizio_effettiva=? WHERE ref_dipendente=? AND ref_turno=?", parametri);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
    }

    public boolean queryServizioAperto(int servizio) {
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, servizio);
        try {
            eseguiCRUD("SELECT stato_servizio FROM servizio WHERE cod_servizio=?", parametri);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        // System.out.println(insiemeRisultati);
        if (((Number)insiemeRisultati.get(0).get("stato_servizio")).intValue()==0) {
            return false;
        } else {
            return true;
        }
    }

    public void queryApriServizio(int servizio) {
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, servizio);
        try {
            eseguiCRUD("UPDATE servizio SET stato_servizio=1 WHERE cod_servizio=?", parametri);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
    }

    public List<UtenteENT> queryTurniNonChiusi(LocalDateTime tempo) {
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, tempo.toLocalDate());
        parametri.add(1, LocalTime.of(tempo.getHour(), 0, 0));
        try {
            eseguiCRUD("SELECT utente.matricola, utente.nome, utente.cognome, utente.email, utente.pass, utente.stato_pass, utente.iban, utente.stato_utente FROM utente, turno_previsto, turno_effettivo WHERE utente.matricola = turno_effettivo.ref_dipendente and ref_turno=cod_turno AND turno_previsto.ref_dipendente=turno_effettivo.ref_dipendente AND turno_effettivo.stato_uscita IS NULL AND data_turno=? AND ora_fine=?", parametri);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        System.out.println(insiemeRisultati); //
        List <UtenteENT> utenti = new ArrayList<>();
        for(int i = 0; i<insiemeRisultati.size(); i++) {
            utenti.add(new UtenteENT(getInsiemeRisultati().get(i)));
        }
        System.out.println(utenti);
        return utenti;
    }

    //

/*
    public void queryChiusuraAutomatica(int matricola, LocalDateTime tempo) {
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, tempo.toLocalDate());
        parametri.add(1, LocalTime.of(tempo.getHour(), tempo.getMinute(), tempo.getMinute()));
        parametri.add(2, matricola);
        try {
            eseguiCRUD("UPDATE turno_previsto, turno_effettivo SET stato_uscita=0 WHERE ref_turno=cod_turno AND data_turno=? AND ora_fine=? AND turno_effettivo.ref_dipendente=?", parametri);
        } catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
    }
*/

// Fine Giuseppe
// ---------------------------------------------------------------------------------------
// Inzio Gabriele

    public int queryServizioVariazione(UtenteENT ent, LocalDateTime adesso) {
        String oreAdesso = Integer.toString(adesso.getHour());
        if (Integer.parseInt(oreAdesso) <= 9) {
            oreAdesso = "0" + oreAdesso;
        }
        String mese = Integer.toString(adesso.getMonth().getValue());
        if (Integer.parseInt(mese) <= 9) {
            mese = "0" + mese;
        }
        String giorno = Integer.toString(adesso.getDayOfMonth());
        if (Integer.parseInt(giorno) <= 9) {
            giorno = "0" + giorno;
        }
        String codTurnoAdesso = Integer.toString(adesso.getYear()) + mese + giorno;
        String query = "SELECT dipendente.ref_servizio FROM dipendente, variazione " + 
                       "WHERE dipendente.ref_utente=variazione.ref_dipendente_da_sostituire AND variazione.ref_dipendente_sostituto=? " +
                        "AND ref_turno_sostituto LIKE ? AND SUBSTRING(variazione.ref_turno_da_sostituire, 9, 2) <= ? " + 
                        "AND SUBSTRING(variazione.ref_turno_da_sostituire, 11, 2) >= ?";
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, Integer.toString(ent.getMatricola()));
        parametri.add(1, codTurnoAdesso + "%");
        parametri.add(2, oreAdesso);
        parametri.add(3, oreAdesso);
        try {
            eseguiCRUD(query, parametri);
            if (insiemeRisultati.size() != 0 ) {
                return Integer.parseInt(insiemeRisultati.get(0).get("ref_servizio").toString());
            }
        }
        catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean queryGetTurno3(UtenteENT ent, LocalDateTime adesso) {
        String oreAdesso = Integer.toString(adesso.getHour());
        if (Integer.parseInt(oreAdesso) <= 9) {
            oreAdesso = "0" + oreAdesso;
        }
        String mese = Integer.toString(adesso.getMonth().getValue());
        if (Integer.parseInt(mese) <= 9) {
            mese = "0" + mese;
        }
        String giorno = Integer.toString(adesso.getDayOfMonth());
        if (Integer.parseInt(giorno) <= 9) {
            giorno = "0" + giorno;
        }
        String codTurnoAdesso = Integer.toString(adesso.getYear()) + mese + giorno;
        String query = "SELECT * FROM turno_effettivo " + 
                        "WHERE ref_dipendente=? AND stato_entrata<>0 AND ref_turno LIKE ? AND SUBSTRING(ref_turno, 9, 2) <= ? " + 
                        "AND SUBSTRING(ref_turno, 11, 2) >= ?";
        List<Object> parametri = new ArrayList<>();
        parametri.add(0, Integer.toString(ent.getMatricola()));
        parametri.add(1, codTurnoAdesso + "%");
        parametri.add(2, oreAdesso);
        parametri.add(3, oreAdesso);
        try {
            eseguiCRUD(query, parametri);
            if (insiemeRisultati.size() != 0 ) {
                return true;
            }
        }
        catch (ErroreComunicazioneDBMSException e) {
            e.printStackTrace();
        }
        return false;
    }
}