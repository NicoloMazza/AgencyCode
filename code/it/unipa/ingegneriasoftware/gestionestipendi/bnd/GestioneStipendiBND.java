package it.unipa.ingegneriasoftware.gestionestipendi.bnd;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import it.unipa.ingegneriasoftware.autenticazione.bnd.HomeBND;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestionestipendi.ctrl.MostraStipendioCTRL;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class GestioneStipendiBND extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel pannello;
    private JLabel titolo_schermata;
    private JButton mostraStipendio;
    private JButton indietro;

    public GestioneStipendiBND(UtenteENT ent) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1280, 720);
        MetodiUtili.centraFinestra(this);
        this.setVisible(true);
        this.setResizable(false);

        pannello = new JPanel();
        pannello.setLocation(0, 0);
        pannello.setSize(1264, 681);
        pannello.setBackground(Color.WHITE);
        pannello.setLayout(null);

        this.add(pannello);

        titolo_schermata = new JLabel("GESTIONE STIPENDI\r\n");
        titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 55));
        titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
        titolo_schermata.setBounds(10, 73, 1244, 103);
        pannello.add(titolo_schermata);

        mostraStipendio = new JButton("Mostra Stipendio");
        mostraStipendio.setBounds(500, 298, 255, 69);
        mostraStipendio.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        mostraStipendio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                MostraStipendioCTRL.creaMostraStipendioBND(ent);
            }
        });
        pannello.add(mostraStipendio);

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