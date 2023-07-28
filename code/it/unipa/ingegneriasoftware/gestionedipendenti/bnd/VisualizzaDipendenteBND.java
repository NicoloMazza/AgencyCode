package it.unipa.ingegneriasoftware.gestionedipendenti.bnd;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VisualizzaDipendenteBND extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel pannello;
    private JLabel titolo_schermata;
    private JLabel nome;
    private JTextField campoNome;
    private JLabel cognome;
    private JTextField campoCognome;
    private JLabel ruolo;
    private JTextField campoRuolo;
    private JLabel iban;
    private JTextField campoIban;
    private JLabel email;
    private JTextField campoEmail;
    private JLabel password;
    private JTextField campoPassword;
    private JLabel ore;
    private JTextField campoOre;
    private JButton indietro;

    public VisualizzaDipendenteBND(UtenteENT ent, UtenteENT datiCampi) {

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

        titolo_schermata = new JLabel("VISUALIZZA DATI DIPENDENTE");
        titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
        titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 70));
        titolo_schermata.setForeground(Color.BLACK);
        titolo_schermata.setBounds(0, 138, 1280, 100);
        pannello.add(titolo_schermata);

        nome = new JLabel("Nome:");
        nome.setFont(new Font("Times New Roman", Font.BOLD, 25));
        nome.setForeground(Color.BLACK);
        nome.setBounds(370, 265, 186, 25);
        pannello.add(nome);

        campoNome = new JTextField(datiCampi.getNome());
        campoNome.setBounds(500, 265, 290, 25);
        campoNome.setEditable(false);
        pannello.add(campoNome);

        cognome = new JLabel("Cognome:");
        cognome.setFont(new Font("Times New Roman", Font.BOLD, 25));
        cognome.setForeground(Color.BLACK);
        cognome.setBounds(350, 315, 225, 25);
        pannello.add(cognome);

        campoCognome = new JTextField(datiCampi.getCognome());
        campoCognome.setBounds(500, 315, 290, 25);
        campoCognome.setEditable(false);
        pannello.add(campoCognome);

        ruolo = new JLabel("Servizio:");
        ruolo.setFont(new Font("Times New Roman", Font.BOLD, 25));
        ruolo.setForeground(Color.BLACK);
        ruolo.setBounds(350, 365, 225, 25);
        pannello.add(ruolo);

        campoRuolo = new JTextField(Integer.toString(datiCampi.getServizio()));
        campoRuolo.setBounds(500, 365, 290, 25);
        campoRuolo.setEditable(false);
        pannello.add(campoRuolo);

        iban = new JLabel("IBAN:");
        iban.setFont(new Font("Times New Roman", Font.BOLD, 25));
        iban.setForeground(Color.BLACK);
        iban.setBounds(370, 415, 225, 25);
        pannello.add(iban);

        campoIban = new JTextField(datiCampi.getIban());
        campoIban.setBounds(500, 415, 290, 25);
        campoIban.setEditable(false);
        pannello.add(campoIban);

        email = new JLabel("Email:");
        email.setFont(new Font("Times New Roman", Font.BOLD, 25));
        email.setForeground(Color.BLACK);
        email.setBounds(370, 465, 225, 25);
        pannello.add(email);

        campoEmail = new JTextField(datiCampi.getEmail());
        campoEmail.setBounds(500, 465, 290, 25);
        campoEmail.setEditable(false);
        pannello.add(campoEmail);

        password = new JLabel("Matricola:");
        password.setFont(new Font("Times New Roman", Font.BOLD, 25));
        password.setForeground(Color.BLACK);
        password.setBounds(350, 515, 225, 25);
        pannello.add(password);

        campoPassword = new JTextField(Integer.toString(datiCampi.getMatricola()));
        campoPassword.setBounds(500, 515, 290, 25);
        campoPassword.setEditable(false);
        pannello.add(campoPassword);

        ore = new JLabel("Ore da recuperare:");
        ore.setFont(new Font("Times New Roman", Font.BOLD, 25));
        ore.setForeground(Color.BLACK);
        ore.setBounds(240, 565, 225, 25);
        pannello.add(ore);

        campoOre = new JTextField(Integer.toString(datiCampi.getOreDaRecuperare()));
        campoOre.setBounds(500, 565, 290, 25);
        campoOre.setEditable(false);
        pannello.add(campoOre);
/*
        conferma = new JButton("Conferma");
        conferma.setBounds(578, 580, 113, 30);
        conferma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                //GestioneUtenteCTRL.creaGestioneUtenteBND(ent);
        }});
        pannello.add(conferma);
*/
        indietro = new JButton("Indietro");
        indietro.setBounds(50, 30, 120, 30);
        indietro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CercaDipendenteBND(ent);
            }
        });
        pannello.add(indietro);

        this.add(pannello);
    }
}