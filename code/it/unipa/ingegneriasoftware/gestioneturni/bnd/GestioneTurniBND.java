package it.unipa.ingegneriasoftware.gestioneturni.bnd;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import it.unipa.ingegneriasoftware.autenticazione.bnd.HomeBND;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestioneturni.ctrl.ImpostaGiorniIndisponibiliCTRL;
import it.unipa.ingegneriasoftware.gestioneturni.ctrl.VisualizzaBachecaTurniCTRL;
import it.unipa.ingegneriasoftware.gestioneturni.ctrl.VisualizzaTurniPersonaliCTRL;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import java.time.LocalDate;

public class GestioneTurniBND extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel pannello;
    private JButton indietro;
    private JLabel titolo_schermata;
    private JButton impostaGiorniIndisponibili;
    private JButton visualizzaBachecaTurni;
    private JButton visualizzaTurniPersonali;

    public GestioneTurniBND(UtenteENT ent) {
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
            titolo_schermata = new JLabel("GESTIONE TURNI DATORE\r\n");
            titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 70));
            titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
            titolo_schermata.setBounds(0, 84, 1280, 103);
            pannello.add(titolo_schermata);

            impostaGiorniIndisponibili = new JButton("Imposta Giorni Indisponibili");
            impostaGiorniIndisponibili.setBounds(519, 266, 280, 69);
            impostaGiorniIndisponibili.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            impostaGiorniIndisponibili.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int meseCorrente = LocalDate.now().getMonthValue();
                    if (meseCorrente%3==0) {
                        ImpostaGiorniIndisponibiliCTRL.creaMessaggioASchermo("La data corrente supera i 2/3 del trimestre", 1);
                    }else{
                        dispose();
                        ImpostaGiorniIndisponibiliCTRL.creaImpostaGiorniIndisponibiliBND(ent);
                    }
                    
                }
            });
            pannello.add(impostaGiorniIndisponibili);

            visualizzaBachecaTurni = new JButton("Visualizza Bacheca Turni");
            visualizzaBachecaTurni.setBounds(519, 373, 280, 69);
            visualizzaBachecaTurni.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            visualizzaBachecaTurni.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    VisualizzaBachecaTurniCTRL.creaBachecaTurniBND(ent);
                }
            });
            pannello.add(visualizzaBachecaTurni);

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
        else {
            titolo_schermata = new JLabel("GESTIONE TURNI DIPENDENTE\r\n");
            titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 70));
            titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
            titolo_schermata.setBounds(0, 84, 1280, 103);
            pannello.add(titolo_schermata);

            visualizzaTurniPersonali = new JButton("Visualizza Turni Personali");
            visualizzaTurniPersonali.setBounds(519, 266, 280, 69);
            visualizzaTurniPersonali.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            visualizzaTurniPersonali.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    VisualizzaTurniPersonaliCTRL.creaVisualizzaTurniPersonaliBND(ent);
                }
            });
            pannello.add(visualizzaTurniPersonali);

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
}