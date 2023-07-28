package it.unipa.ingegneriasoftware.autenticazione.bnd;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import it.unipa.ingegneriasoftware.autenticazione.ctrl.LoginCTRL;
import it.unipa.ingegneriasoftware.autenticazione.ctrl.RecuperoPasswordCTRL;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import javax.swing.JButton;
import javax.swing.JLabel;

public class RecuperoPasswordBND extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel pannello;
    private JTextField campoMatricola;
    private JButton indietro;
    private JButton conferma;
    private JLabel messaggio1;
    private JLabel messaggio2;
    private JLabel titolo_schermata;

    public RecuperoPasswordBND() {
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

        this.add(pannello);

        campoMatricola = new JTextField();
        campoMatricola.setHorizontalAlignment(SwingConstants.CENTER);
        campoMatricola.setBounds(506, 329, 247, 35);
        pannello.add(campoMatricola);
        campoMatricola.setColumns(10);

        conferma = new JButton("Conferma");
        conferma.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        conferma.setBounds(546, 409, 161, 57);
        conferma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String matricola = campoMatricola.getText();
                UtenteENT utente = RecuperoPasswordCTRL.selezionaUtente(matricola);
                if (utente == null) {
                    LoginCTRL.creaMessaggioASchermo("Matricola non trovata.", 1);
                    campoMatricola.setText("");
                } else {
                    String otp = RecuperoPasswordCTRL.inviaOTP(utente);
                    dispose();
                    RecuperoPasswordCTRL.creaOTPBND(utente, otp);
                }
            }
        });
        pannello.add(conferma);

        messaggio1 = new JLabel("Inserisci la matricola qui sotto per permetterci di recuperare la tua email");
        messaggio1.setHorizontalAlignment(SwingConstants.CENTER);
        messaggio1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        messaggio1.setBounds(0, 227, 1280, 43);
        pannello.add(messaggio1);

        titolo_schermata = new JLabel("RECUPERO PASSWORD");
        titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 70));
        titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
        titolo_schermata.setBounds(0, 122, 1280, 73);
        pannello.add(titolo_schermata);

        messaggio2 = new JLabel("Inserisci matricola\r\n:");
        messaggio2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        messaggio2.setHorizontalAlignment(SwingConstants.CENTER);
        messaggio2.setBounds(334, 329, 162, 35);
        pannello.add(messaggio2);

        indietro = new JButton("Indietro");
        indietro.setBounds(50, 30, 120, 30);
        indietro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                RecuperoPasswordCTRL.creaLoginBND();
            }
        });
        pannello.add(indietro);
    }
}
