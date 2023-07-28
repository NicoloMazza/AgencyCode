package it.unipa.ingegneriasoftware.comuni.metodiutili;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import java.util.Random;
import java.util.Properties;
import java.util.List;
import java.util.Date;

import java.time.LocalDate;
import java.time.ZoneId;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.AddressException;

import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.comuni.MessaggioASchermo;

import com.itextpdf.text.pdf.PdfPTable;

/**
* Classe MetodiUtili contenente metodi utili per l'applicazione.
*/
public class MetodiUtili {

    /**
     * Genera l'hash di una password utilizzando l'algoritmo SHA-256.
     * @param pass La password di cui generare l'hash.
     * @return L'hash generato.
     */
    public static String generaHash(String pass) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] passBytes = pass.getBytes();
            byte[] passHash = sha256.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < passHash.length; i++) {
                sb.append(Integer.toString((passHash[i] & 0xff) + 0x100, 16).substring(1));
            }
            String generatedPassword = sb.toString();
            return generatedPassword;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Centra una finestra sullo schermo.
     * @param frame La finestra da centrare.
     */
    public static void centraFinestra(JFrame frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    /**
    * Genera un numero intero casuale compreso tra min e max
    * @param min numero minimo del range
    * @param max numero massimo del range
    * @return numero intero generato casualmente
    */
    public static int casualeTra(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    /**
    * Metodo che genera una stringa casuale di lunghezza specificata
    * @param lunghezza la lunghezza della stringa casuale da generare
    * @return la stringa casuale generata
    */
    public static String casualeStringa(int lunghezza) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1).limit(lunghezza)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        return generatedString;
    }

    /**
    * Metodo che pulisce una stringa eliminando spazi iniziali e finali e sostituendo eventuali spazi doppi con uno solo
    * @param stringa la stringa da pulire
    * @return la stringa pulita
    */
    public static String pulisciStrigna(String stringa) {
        return stringa.trim().replaceAll(" +", " ");
    }

    /**
    * Metodo che invia una mail a un utente specifico con un determinato tipo di contenuto
    * @param utente l'utente a cui inviare la mail
    * @param tipo il tipo di mail da inviare usato per impostare corpo e oggetto
    * @param parametri specifici per il tipo di mail scelta da inserire nel corpo
    */
    public static void inviaEmail(UtenteENT utente, int tipo, List<String> parametri) {
        final String emailMittente = "emailmittente89@gmail.com";
        final String passMittente = "fbyuqpbtabwnpkzs";
        String emailDestinatario, oggetto, corpo;
        emailDestinatario = utente.getEmail();
        switch (tipo) {
            case 1:
                oggetto = "One Time Password - Login";
                corpo = "Ciao " + utente
                        + "!<br><br>Ecco la tua One Time Password necessaria per confermare l'operazione Login:" +
                        "<br><br><B><font size='5'>" + parametri.get(0)
                        + "</font></B><br><br>Ti preghiamo di non condividerla con nessuno." +
                        "<br><br>Cordiali saluti,<br>Il Sistema.<br><br><br>Azienda di servizi al cittadino<br>" +
                        "Via del tutto eccezionale, 42<br>Pietrammare 81009, Italia";
                MetodiUtili.sendMail(emailMittente, passMittente, emailDestinatario, oggetto, corpo);
                break;
            case 2:
                oggetto = "One Time Password - Recupero Password";
                corpo = "Ciao " + utente
                        + "!<br><br>Ecco la tua One Time Password necessaria per confermare l'operazione Recupero Password:" +
                        "<br><br><B><font size='5'>" + parametri.get(0)
                        + "</font></B><br><br>Ti preghiamo di non condividerla con nessuno." +
                        "<br><br>Cordiali saluti,<br>Il Sistema.<br><br><br>Azienda di servizi al cittadino<br>" +
                        "Via del tutto eccezionale, 42<br>Pietrammare 81009, Italia";
                MetodiUtili.sendMail(emailMittente, passMittente, emailDestinatario, oggetto, corpo);
                break;
            case 3:
                oggetto = "Tentativo Accesso";
                corpo = "Ciao " + utente + "!<br><br>E' stato rilevato un tentativo di accesso al tuo account. " +
                        "Se sei stato tu, puoi tranquillamente ignorare questa email. In caso contrario, ti preghiamo immediatamente " +
                        "di modificare la tua password in modo da proteggere il tuo account." +
                        "<br><br>Cordiali saluti,<br>Il Sistema.<br><br><br>Azienda di servizi al cittadino<br>" +
                        "Via del tutto eccezionale, 42<br>Pietrammare 81009, Italia";
                MetodiUtili.sendMail(emailMittente, passMittente, emailDestinatario, oggetto, corpo);
                break;
            case 4:
                oggetto = "Primo accesso";
                corpo = "Ciao " + utente + "!<br><br>Benvenuto in Azienda. " +
                        "Di seguito sono riportate le credenziali che dovrai utilizzare per effettuare il primo accesso al software:<br><br> " +
                        "Matricola: <B><font size='5'>" + parametri.get(0) + "</font></B><br>" + 
                        "Password: <B><font size='5'>" + parametri.get(1) + "</font></B><br>" + 
                        "<br><br>Cordiali saluti,<br>Il Sistema.<br><br><br>Azienda di servizi al cittadino<br>" +
                        "Via del tutto eccezionale, 42<br>Pietrammare 81009, Italia";
                MetodiUtili.sendMail(emailMittente, passMittente, emailDestinatario, oggetto, corpo);
                break;
            case 5:
                oggetto = "Assenti non comunicati";
                corpo = "Gentile " + utente + "!<br><br>I seguenti dipendenti in data odierna non hanno effettuato la " +
                "rilevazione entrata pur non avendo effettuato in anticipo una richiesta di variazione: <br><br>"; 
                for (int i = 0; i < parametri.size(); i++) {
                    corpo += parametri.get(i) + "<br>";
                }
                corpo += "<br><br>Cordiali saluti,<br>Il Sistema.<br><br><br>Azienda di servizi al cittadino<br>" +
                         "Via del tutto eccezionale, 42<br>Pietrammare 81009, Italia";
                if (parametri.size() != 0) {
                    MetodiUtili.sendMail(emailMittente, passMittente, emailDestinatario, oggetto, corpo);
                }
                break;
            case 6:
                oggetto = "Presenza Ritardo";
                corpo = "Gentile " + utente + "!<br><br>Il seguente dipendente in data odierna ha effettuato la " +
                "rilevazione entrata in ritardo: <br><br>" + parametri.get(0) + " " + parametri.get(1) + " " + parametri.get(2); 
                corpo += "<br><br>Cordiali saluti,<br>Il Sistema.<br><br><br>Azienda di servizi al cittadino<br>" +
                         "Via del tutto eccezionale, 42<br>Pietrammare 81009, Italia";
                MetodiUtili.sendMail(emailMittente, passMittente, emailDestinatario, oggetto, corpo);
                break;
            case 7:
                oggetto = "Futura chiusura del servizio";
                corpo = "Gentile " + utente + "!<br><br>Il servizio " + parametri.get(0) + " sara' costretto a chiudere in data " + parametri.get(1) +
                " per mancanza di personale.";
                corpo += "<br><br>Cordiali saluti,<br>Il Sistema.<br><br><br>Azienda di servizi al cittadino<br>" +
                         "Via del tutto eccezionale, 42<br>Pietrammare 81009, Italia";
                MetodiUtili.sendMail(emailMittente, passMittente, emailDestinatario, oggetto, corpo);
                break;
            case 8:
                oggetto = "Sostituzione";
                corpo = "Gentile " + utente + "!<br><br>Ti comunichiamo che giorno " + parametri.get(1).substring(7, 8) + "/" + parametri.get(1).substring(5, 6) + "/" + parametri.get(1).substring(0, 4) +
                        " dovrai sostituire un dipendente appartenente al servizio " + parametri.get(3) + " dalle ore " + parametri.get(1).substring(8, 10) +
                        " alle ore " + parametri.get(1).substring(10, 12) + "<br><br>";
                corpo += "<br><br>Cordiali saluti,<br>Il Sistema.<br><br><br>Azienda di servizi al cittadino<br>" +
                         "Via del tutto eccezionale, 42<br>Pietrammare 81009, Italia";
                MetodiUtili.sendMail(emailMittente, passMittente, emailDestinatario, oggetto, corpo);
                break;
        }
    }

    /**
    * Questo metodo invia una email dall'indirizzo specificato nella stringa "from" utilizzando la password specificata nella stringa "password",
    * all'indirizzo specificato nella stringa "to", con oggetto specificato nella stringa "subject" e con il testo del corpo specificato nella stringa "body".
    * @param from indirizzo mittente
    * @param password password mittente
    * @param to indirizzo destinatario
    * @param subject oggetto dell'email
    * @param body testo del corpo dell'email
    */
    private static void sendMail(String from, String password, String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            message.setContent(body, "text/html; charset=utf-8");
            System.out.println("Invio email in corso...");
            Transport.send(message);
            System.out.println("Email inviata con successo");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
    * Questo metodo crea un file PDF contenente la lista dei dipendenti e la data specificata.
    * @param dipendenti La lista dei dipendenti da inserire nel file PDF.
    * @param giorno La data da inserire nel file PDF e nel nome del file stesso.
    */
    public static void creaPDF(List<UtenteENT> dipendenti, LocalDate giorno) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document,
                    new FileOutputStream("lista_dipendenti_" + giorno.toString() + ".pdf"));
            document.open();
            document.add(new Paragraph("Lista Dipendenti " + giorno));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(3);
            table.addCell("Matricola");
            table.addCell("Nome");
            table.addCell("Cognome");
            for (int i = 0; i < dipendenti.size(); i++) {
                table.addCell(String.valueOf(dipendenti.get(i).getMatricola()));
                table.addCell(dipendenti.get(i).getNome());
                table.addCell(dipendenti.get(i).getCognome());
            }
            document.add(table);
            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
            new MessaggioASchermo("Errore durante la creazione del pdf", 1);
        }
        new MessaggioASchermo("Pdf creato con successo in ./", 2);
        System.out.println("Documento creato in ./");
    }

    /**
    * Converte una data java.util.Date in java.time.LocalDate
    * @param data data da convertire
    * @return data convertita in LocalDate
    */
    public static LocalDate DateToLocalDate(Date data) {
        return LocalDate.ofInstant(data.toInstant(), ZoneId.systemDefault());
    }

    /**
    * Verifica se una email è valida
    * @param email email di cui si vuole verificare il formato
    * @return true se la email passata come parametro è valida, false altrimenti
    */
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}