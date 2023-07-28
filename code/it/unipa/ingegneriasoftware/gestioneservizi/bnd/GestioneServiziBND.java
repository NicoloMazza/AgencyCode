package it.unipa.ingegneriasoftware.gestioneservizi.bnd;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import it.unipa.ingegneriasoftware.autenticazione.bnd.HomeBND;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestioneservizi.ctrl.GestioneInfrastruttureCTRL;
import it.unipa.ingegneriasoftware.gestioneservizi.ctrl.GestioneProgettiCTRL;
import it.unipa.ingegneriasoftware.gestioneservizi.ctrl.GestioneRecensioniCTRL;
import it.unipa.ingegneriasoftware.gestioneservizi.ctrl.GestioneSegnalazioniCTRL;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class GestioneServiziBND extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel pannello;
    private JLabel titolo_schermata;
    private JLabel servizio1;
    private JButton gestioneInfrastrutture;
    private JLabel servizio2;
    private JButton gestioneProgetti;
    private JLabel servizio3;
    private JLabel servizio4;
    private JButton gestioneSegnalazioni;
    private JButton gestioneRecensioni;
    private JButton indietro;

    public GestioneServiziBND(UtenteENT ent) {
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

        titolo_schermata = new JLabel("GESTIONE SERVIZI\r\n");
        titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 70));
        titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
        titolo_schermata.setBounds(0, 84, 1280, 103);
        pannello.add(titolo_schermata);

        // SERVIZIO 1

        servizio1 = new JLabel("Servizio 1\r\n");
        servizio1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        servizio1.setBounds(340, 200, 1280, 103);
        pannello.add(servizio1);

        gestioneInfrastrutture = new JButton("Gestione Infrastrutture");
        gestioneInfrastrutture.setBounds(250, 280, 280, 45);
        gestioneInfrastrutture.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        gestioneInfrastrutture.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (GestioneInfrastruttureCTRL.creaGestioneInfrastruttureBND(ent)) {
                    dispose();
                }
            }
        });
        pannello.add(gestioneInfrastrutture);

        // SERVIZIO 2
        servizio2 = new JLabel("Servizio 2\r\n");
        servizio2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        servizio2.setBounds(340, 340, 1280, 103);
        pannello.add(servizio2);

        gestioneProgetti = new JButton("Gestione Progetti");
        gestioneProgetti.setBounds(250, 420, 280, 45);
        gestioneProgetti.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        gestioneProgetti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (GestioneProgettiCTRL.creaGestioneProgettiBND(ent)) {
                    dispose();
                }
            }
        });
        pannello.add(gestioneProgetti);

        // SERVIZIO 3
        servizio3 = new JLabel("Servizio 3\r\n");
        servizio3.setFont(new Font("Times New Roman", Font.BOLD, 20));
        servizio3.setBounds(790, 200, 1280, 103);
        pannello.add(servizio3);

        gestioneSegnalazioni = new JButton("Gestione Segnalazioni");
        gestioneSegnalazioni.setBounds(700, 280, 280, 45);
        gestioneSegnalazioni.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        gestioneSegnalazioni.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if( GestioneSegnalazioniCTRL.creaGestioneSegnalazioniBND(ent) ) {
                    dispose();
                }
            }
        });
        pannello.add(gestioneSegnalazioni);

        // SERVIZIO 4
        servizio4 = new JLabel("Servizio 4\r\n");
        servizio4.setFont(new Font("Times New Roman", Font.BOLD, 20));
        servizio4.setBounds(790, 340, 1280, 103);
        pannello.add(servizio4);

        gestioneRecensioni = new JButton("Gestione Recensioni");
        gestioneRecensioni.setBounds(700, 420, 280, 45);
        gestioneRecensioni.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        gestioneRecensioni.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (GestioneRecensioniCTRL.creaGestioneRecensioniBND(ent)) {
                    dispose();
                }
            }
        });
        pannello.add(gestioneRecensioni);

        indietro = new JButton("Indietro");
        indietro.setBounds(50, 30, 120, 30);
        indietro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HomeBND(ent);
            }
        });
        pannello.add(indietro);
    }


}