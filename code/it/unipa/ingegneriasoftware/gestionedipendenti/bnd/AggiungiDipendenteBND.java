package it.unipa.ingegneriasoftware.gestionedipendenti.bnd;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestionedipendenti.ctrl.AggiungiDipendenteCTRL;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
* Classe per la creazione della finestra di inserimento di un nuovo dipendente.
* Estende la classe JFrame per la creazione della finestra.
* Utilizza diverse label e text field per la raccolta dei dati del nuovo dipendente.
* Utilizza i bottoni "Conferma" e "Indietro" per la conferma o l'annullamento dell'inserimento.
*/
public class AggiungiDipendenteBND extends JFrame {

    /** 
    * ID univoco per la serializzazione 
    */
    private static final long serialVersionUID = 1L;

    /** 
    * Pannello principale della finestra 
    */
    private JPanel pannello;

    /** 
    * Titolo della finestra 
    */
    private JLabel titolo_schermata;

    /** 
    * Label per il nome del dipendente 
    */
    private JLabel nome;

    /** 
    * Campo di testo per l'inserimento del nome del dipendente 
    */
    private JTextField campoNome;

    /** 
    * Label per il cognome del dipendente 
    */
    private JLabel cognome;

    /** 
    * Campo di testo per l'inserimento del cognome del dipendente 
    */
    private JTextField campoCognome;

    /** 
    * Label per il servizio del dipendente 
    */
    private JLabel ruolo;

    /** 
    * Campo di testo per l'inserimento del servizio del dipendente 
    */
    private JTextField campoRuolo;

    /** 
    * Label per l'iban del dipendente 
    */
    private JLabel iban;

    /** 
    * Campo di testo per l'inserimento dell'iban del dipendente 
    */
    private JTextField campoIban;

    /** 
    * Label per l'email del dipendente 
    */
    private JLabel email;

    /** 
    * Campo di testo per l'inserimento dell'email del dipendente 
    */
    private JTextField campoEmail;

    /** 
    * Pulsante per confermare l'inserimento del nuovo dipendente 
    */
    private JButton conferma;

    /** 
    * Pulsante per tornare alla schermata precedente 
    */
    private JButton indietro;

    /**
    * Costruttore della schermata
    * @param ent parametro di tipo UtenteENT dell'utente che ha aperto la schermata
    */
    public AggiungiDipendenteBND(UtenteENT ent) {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1280, 720);
        MetodiUtili.centraFinestra(this);
        this.setVisible(true);
        this.setResizable(false);

        pannello = new JPanel();
        pannello.setLocation(0, 0);
        pannello.setSize(1280, 720);
        pannello.setBackground(Color.WHITE);
        pannello.setLayout(null);

        titolo_schermata = new JLabel("AGGIUNGI DIPENDENTE");
        titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
        titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 70));
        titolo_schermata.setForeground(Color.BLACK);
        titolo_schermata.setBounds(0, 138, 1280, 100);

        nome = new JLabel("Nome:");
        nome.setFont(new Font("Times New Roman", Font.BOLD, 25));
        nome.setForeground(Color.BLACK);
        nome.setBounds(370, 265, 186, 25);

        campoNome = new JTextField("");
        campoNome.setBounds(500, 265, 290, 25);

        cognome = new JLabel("Cognome:");
        cognome.setFont(new Font("Times New Roman", Font.BOLD, 25));
        cognome.setForeground(Color.BLACK);
        cognome.setBounds(370, 315, 225, 25);

        campoCognome = new JTextField("");
        campoCognome.setBounds(500, 315, 290, 25);

        ruolo = new JLabel("Servizio:");
        ruolo.setFont(new Font("Times New Roman", Font.BOLD, 25));
        ruolo.setForeground(Color.BLACK);
        ruolo.setBounds(370, 365, 225, 25);

        campoRuolo = new JTextField("");
        campoRuolo.setBounds(500, 365, 290, 25);

        iban = new JLabel("IBAN:");
        iban.setFont(new Font("Times New Roman", Font.BOLD, 25));
        iban.setForeground(Color.BLACK);
        iban.setBounds(370, 415, 225, 25);

        campoIban = new JTextField("");
        campoIban.setBounds(500, 415, 290, 25);

        email = new JLabel("Email:");
        email.setFont(new Font("Times New Roman", Font.BOLD, 25));
        email.setForeground(Color.BLACK);
        email.setBounds(370, 465, 225, 25);

        campoEmail = new JTextField("");
        campoEmail.setBounds(500, 465, 290, 25);

        conferma = new JButton("Conferma");
        conferma.setBounds(578, 535, 113, 30);
        conferma.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if ( campoNome.getText().equals("") || campoCognome.getText().equals("") || campoEmail.getText().equals("") || campoIban.getText().equals("") || campoRuolo.getText().equals("") ) {
                    AggiungiDipendenteCTRL.creaMessaggioASchermo("Almeno uno dei campi è vuoto!", 1);
                }
                else {
                    UtenteENT aggiungi = new UtenteENT( campoNome.getText(), campoCognome.getText(), campoEmail.getText(), campoIban.getText(), Integer.parseInt(campoRuolo.getText()) );
                    if ( AggiungiDipendenteCTRL.inserisciDipendente(aggiungi) ) {
                        AggiungiDipendenteCTRL.creaMessaggioASchermo("Utente aggiunto con successo.", 2);
                    }
                    else {
                        AggiungiDipendenteCTRL.creaMessaggioASchermo("Errore durante l'aggiunta dell'utente.", 1);
                    }
                    dispose();
                    AggiungiDipendenteCTRL.creaAggiungiDipendenteBND(ent);
                }
        }});

        indietro = new JButton("Indietro");
        indietro.setBounds(50, 30, 120, 30);
        indietro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GestioneDipendentiBND(ent);
        }});

        pannello.add(titolo_schermata);
        pannello.add(nome);
        pannello.add(campoNome);
        pannello.add(cognome);
        pannello.add(campoCognome);
        pannello.add(ruolo);
        pannello.add(campoRuolo);
        pannello.add(iban);
        pannello.add(campoIban);
        pannello.add(email);
        pannello.add(campoEmail);
        pannello.add(conferma);
        pannello.add(indietro);
        this.add(pannello);
    }
}