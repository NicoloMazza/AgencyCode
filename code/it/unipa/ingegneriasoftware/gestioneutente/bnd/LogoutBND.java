package it.unipa.ingegneriasoftware.gestioneutente.bnd;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestioneutente.ctrl.LogoutCTRL;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LogoutBND extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel pannello;
    private JLabel messaggio;
    private JButton conferma;
    private JButton indietro;

    public LogoutBND(UtenteENT ent) {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1280, 720);
        MetodiUtili.centraFinestra(this);
        this.setVisible(true);
        this.setResizable(false);

        pannello = new JPanel();
        pannello.setLocation(0, 0);
        pannello.setSize(1280, 720);
        pannello.setBackground(Color.LIGHT_GRAY);
        pannello.setLayout(null);

        messaggio = new JLabel("Sei sicuro di voler effettuare il logout?");
        messaggio.setHorizontalAlignment(SwingConstants.CENTER);
        messaggio.setFont(new Font("Times New Roman", Font.BOLD, 20));
        messaggio.setForeground(Color.BLACK);
        messaggio.setBounds(0, 266, 1280, 36);

        conferma = new JButton("Conferma");
        conferma.setBounds(555, 364, 140, 60);
        conferma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                LogoutCTRL.creaLoginBND();
            }
        });

        pannello.add(messaggio);
        pannello.add(conferma);

        this.add(pannello);

        indietro = new JButton("Indietro");
        indietro.setBounds(32, 33, 124, 36);
        indietro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GestioneUtenteBND(ent);
            }
        });
        pannello.add(indietro);
    }
}
