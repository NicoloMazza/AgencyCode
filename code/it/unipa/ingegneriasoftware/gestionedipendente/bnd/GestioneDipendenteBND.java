package it.unipa.ingegneriasoftware.gestionedipendente.bnd;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import it.unipa.ingegneriasoftware.autenticazione.bnd.HomeBND;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestionedipendente.ctrl.RichiestaVariazioneTurnoCTRL;
import it.unipa.ingegneriasoftware.gestionedipendente.ctrl.SegnalaPresenzaRitardoCTRL;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class GestioneDipendenteBND extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel pannello;
    private JLabel titolo_schermata;
    private JButton richiestaVariazioneTurno;
    private JButton segnalaPresenzaRitardo;
    private JButton indietro;

    public GestioneDipendenteBND(UtenteENT ent) {
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

        titolo_schermata = new JLabel("GESTIONE DIPENDENTE\r\n");
        titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 70));
        titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
        titolo_schermata.setBounds(0, 73, 1280, 103);
        pannello.add(titolo_schermata);

        richiestaVariazioneTurno = new JButton("Richiesta Variazione Turno");
        richiestaVariazioneTurno.setBounds(500, 298, 280, 69);
        richiestaVariazioneTurno.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        richiestaVariazioneTurno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                RichiestaVariazioneTurnoCTRL.creaVariazioneTurnoBND(ent);
            }
        });
        pannello.add(richiestaVariazioneTurno);

        segnalaPresenzaRitardo = new JButton("Segnala Presenza Ritardo");
        segnalaPresenzaRitardo.setBounds(500, 400, 280, 69);
        segnalaPresenzaRitardo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        segnalaPresenzaRitardo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                SegnalaPresenzaRitardoCTRL.impostaPresenzaRitardo(ent);
            }
        });
        pannello.add(segnalaPresenzaRitardo);

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