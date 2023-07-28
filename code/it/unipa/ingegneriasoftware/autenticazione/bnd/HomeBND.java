package it.unipa.ingegneriasoftware.autenticazione.bnd;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import it.unipa.ingegneriasoftware.gestioneturni.bnd.GestioneTurniBND;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestionedipendente.bnd.GestioneDipendenteBND;
import it.unipa.ingegneriasoftware.gestionedipendenti.bnd.GestioneDipendentiBND;
import it.unipa.ingegneriasoftware.gestioneservizi.ctrl.GestioneServiziCTRL;
import it.unipa.ingegneriasoftware.gestionestipendi.bnd.GestioneStipendiBND;
import it.unipa.ingegneriasoftware.gestioneutente.bnd.GestioneUtenteBND;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class HomeBND extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel pannello;
    private JLabel titolo_schermata;
    private JButton gestioneTurni;
    private JButton gestioneUtente;
    private JButton gestioneDipendenti;
    private JButton gestioneDipendente;
    private JButton gestioneServizi;
    private JButton gestioneStipendi;

    public HomeBND(UtenteENT ent) {
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

        if (ent.getServizio() == 0) { // datore
            titolo_schermata = new JLabel("SCHERMATA HOME DATORE\r\n");
            titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 70));
            titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
            titolo_schermata.setBounds(0, 73, 1280, 103);
            pannello.add(titolo_schermata);

            gestioneTurni = new JButton("Gestione Turni");
            gestioneTurni.setBounds(519, 358, 224, 69);
            gestioneTurni.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            gestioneTurni.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new GestioneTurniBND(ent);
                }
            });
            pannello.add(gestioneTurni);

            gestioneUtente = new JButton("Gestione Utente\r\n");
            gestioneUtente.setBounds(519, 468, 224, 69);
            gestioneUtente.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            gestioneUtente.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new GestioneUtenteBND(ent);
                }
            });
            pannello.add(gestioneUtente);

            gestioneDipendenti = new JButton("Gestione Dipendenti\r\n");
            gestioneDipendenti.setBounds(519, 248, 224, 69);
            gestioneDipendenti.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            gestioneDipendenti.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new GestioneDipendentiBND(ent);
                }
            });
            pannello.add(gestioneDipendenti);
        } 
        else { // dipendente
            titolo_schermata = new JLabel("SCHERMATA HOME DIPENDENTE\r\n");
            titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 55));
            titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
            titolo_schermata.setBounds(10, 73, 1244, 103);
            pannello.add(titolo_schermata);

            gestioneTurni = new JButton("Gestione Turni");
            gestioneTurni.setBounds(519, 298, 224, 69);
            gestioneTurni.setFont(new Font("Times New Roman", Font.PLAIN, 22));
            gestioneTurni.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new GestioneTurniBND(ent);
                }
            });
            pannello.add(gestioneTurni);

            gestioneUtente = new JButton("Gestione Utente\r\n");
            gestioneUtente.setBounds(519, 378, 224, 69);
            gestioneUtente.setFont(new Font("Times New Roman", Font.PLAIN, 22));
            gestioneUtente.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new GestioneUtenteBND(ent);
                }
            });
            pannello.add(gestioneUtente);

            gestioneDipendente = new JButton("Gestione Dipendente\r\n");
            gestioneDipendente.setBounds(519, 218, 224, 69);
            gestioneDipendente.setFont(new Font("Times New Roman", Font.PLAIN, 22));
            gestioneDipendente.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new GestioneDipendenteBND(ent);
                }
            });
            pannello.add(gestioneDipendente);

            gestioneServizi = new JButton("Gestione Servizi\r\n");
            gestioneServizi.setBounds(519, 458, 224, 69);
            gestioneServizi.setFont(new Font("Times New Roman", Font.PLAIN, 22));
            gestioneServizi.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if ( GestioneServiziCTRL.creaGestioneServiziBND(ent) ) {
                        dispose();
                    }
                }
            });
            pannello.add(gestioneServizi);

            gestioneStipendi = new JButton("Gestione Stipendi");
            gestioneStipendi.setBounds(519, 538, 224, 69);
            gestioneStipendi.setFont(new Font("Times New Roman", Font.PLAIN, 22));
            gestioneStipendi.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new GestioneStipendiBND(ent);
                }
            });
            pannello.add(gestioneStipendi);
        }
    }
}