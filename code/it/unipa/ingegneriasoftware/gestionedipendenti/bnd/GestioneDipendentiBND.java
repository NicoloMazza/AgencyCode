package it.unipa.ingegneriasoftware.gestionedipendenti.bnd;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import it.unipa.ingegneriasoftware.autenticazione.bnd.HomeBND;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestionedipendenti.ctrl.AggiungiDipendenteCTRL;
import it.unipa.ingegneriasoftware.gestionedipendenti.ctrl.CercaDipendenteCTRL;
import it.unipa.ingegneriasoftware.gestionedipendenti.ctrl.EsportaListaCTRL;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class GestioneDipendentiBND extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel pannello;
    private JButton indietro;
    private JLabel titolo_schermata;
    private JButton cercaDipendente;
    private JButton aggiungiDipendente;
    private JButton esportaDipendentiData;

    public GestioneDipendentiBND(UtenteENT ent) {
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

        titolo_schermata = new JLabel("GESTIONE DIPENDENTI\r\n");
        titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 70));
        titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
        titolo_schermata.setBounds(0, 84, 1280, 103);
        pannello.add(titolo_schermata);

        aggiungiDipendente = new JButton("Aggiungi Dipendente");
        aggiungiDipendente.setBounds(519, 266, 280, 69);
        aggiungiDipendente.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        aggiungiDipendente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                AggiungiDipendenteCTRL.creaAggiungiDipendenteBND(ent);
            }
        });
        pannello.add(aggiungiDipendente);

        cercaDipendente = new JButton("Cerca Dipendente");
        cercaDipendente.setBounds(519, 373, 280, 69);
        cercaDipendente.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        cercaDipendente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                CercaDipendenteCTRL.creaCercaDipendenteBND(ent);
            }
        });
        pannello.add(cercaDipendente);

        esportaDipendentiData = new JButton("Esporta Dipendenti Data");
        esportaDipendentiData.setBounds(519, 477, 280, 69);
        esportaDipendentiData.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        esportaDipendentiData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                EsportaListaCTRL.creaEsportaListaBND(ent);
            }
        });
        pannello.add(esportaDipendentiData);

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
