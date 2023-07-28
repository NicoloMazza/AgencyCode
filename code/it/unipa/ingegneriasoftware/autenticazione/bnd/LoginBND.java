package it.unipa.ingegneriasoftware.autenticazione.bnd;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.DocumentListener;

import it.unipa.ingegneriasoftware.autenticazione.ctrl.LoginCTRL;
import it.unipa.ingegneriasoftware.autenticazione.ctrl.RecuperoPasswordCTRL;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import javax.swing.event.DocumentEvent;

public class LoginBND extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel pannello;
    private JLabel titolo_schermata;
    private JLabel matricola;
    private JTextField campoMatricola;
    private JLabel password;
    private JPasswordField campoPassword;
    private JButton conferma;
    private JLabel passdim;
    private JButton recupera;

    public LoginBND() {

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

        titolo_schermata = new JLabel("LOGIN");
        titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 70));
        titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
        titolo_schermata.setForeground(Color.BLACK);
        titolo_schermata.setBounds(0, 147, 1280, 100);
        pannello.add(titolo_schermata);

        matricola = new JLabel("Matricola:");
        matricola.setFont(new Font("Times New Roman", Font.BOLD, 25));
        matricola.setForeground(Color.BLACK);
        matricola.setBounds(377, 265, 113, 25);
        pannello.add(matricola);

        password = new JLabel("Password:");
        password.setFont(new Font("Times New Roman", Font.BOLD, 25));
        password.setForeground(Color.BLACK);
        password.setBounds(377, 315, 113, 25);
        pannello.add(password);

        campoMatricola = new JTextField("");
        campoMatricola.setBounds(500, 265, 290, 25);
        pannello.add(campoMatricola);

        campoPassword = new JPasswordField("");
        campoPassword.setBounds(500, 315, 290, 25);
        campoPassword.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }
            public void warn() {
                if ( String.valueOf(campoPassword.getPassword()).equals(String.valueOf(campoPassword.getPassword()).toUpperCase()) ) { // input is all upper case
                    System.out.println("MAIUSC");
                }
                else {
                    if ( String.valueOf(campoPassword.getPassword()).equals(String.valueOf(campoPassword.getPassword()).toLowerCase()) ) { // input is all lower case
                        System.out.println("minusc");
                    }
                    else { // input is mixed case
                        System.out.println("MAIUSC");
                    }
                }
            }
        });
        pannello.add(campoPassword);

        conferma = new JButton("Conferma");
        conferma.setBounds(578, 362, 113, 30);
        conferma.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        String matricola = campoMatricola.getText();
                        String password = String.valueOf(campoPassword.getPassword());
                        UtenteENT utente = LoginCTRL.controllaCredenziali(matricola, password);
                        if (utente == null) {
                                LoginCTRL.creaMessaggioASchermo("Credenziali errate.", 1);
                                campoMatricola.setText("");
                                campoPassword.setText("");
                        } else {
                                String otp = LoginCTRL.inviaOTP(utente);
                                dispose();
                                LoginCTRL.creaOTPBND(utente, otp);
                        }
                }
        });
        pannello.add(conferma);

        passdim = new JLabel("Password dimenticata?");
        passdim.setFont(new Font("Times New Roman", Font.BOLD, 13));
        passdim.setForeground(Color.BLACK);
        passdim.setBounds(516, 425, 130, 25);
        pannello.add(passdim);

        recupera = new JButton("Recupera");
        recupera.setBounds(644, 425, 120, 25);
        recupera.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        dispose();
                        RecuperoPasswordCTRL.creaRecuperoPasswordBND();
                }
        });
        pannello.add(recupera);

        this.add(pannello);
    }
}
