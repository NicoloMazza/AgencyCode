package it.unipa.ingegneriasoftware.gestioneutente.bnd;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import it.unipa.ingegneriasoftware.autenticazione.bnd.HomeBND;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestioneutente.ctrl.LogoutCTRL;
import it.unipa.ingegneriasoftware.gestioneutente.ctrl.ModificaDatiAccountCTRL;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class GestioneUtenteBND extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel pannello;
    private JButton indietro;
    private JLabel titolo_schermata;
    private JButton modificaDatiAccount;
    private JButton logout;

    public GestioneUtenteBND(UtenteENT ent) {
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

        titolo_schermata = new JLabel("GESTIONE UTENTE\r\n");
        titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 70));
        titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
        titolo_schermata.setBounds(0, 84, 1280, 103);
        pannello.add(titolo_schermata);

        modificaDatiAccount = new JButton("Modifica Dati Account");
        modificaDatiAccount.setBounds(519, 266, 280, 69);
        modificaDatiAccount.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        modificaDatiAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                ModificaDatiAccountCTRL.creaModificaDatiAccountBND(ent);
            }
        });
        pannello.add(modificaDatiAccount);

        logout = new JButton("Logout");
        logout.setBounds(519, 350, 280, 69);
        logout.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                LogoutCTRL.creaSchermataConfermaBND(ent);
            }
        });
        pannello.add(logout);

        indietro = new JButton("Indietro");
        indietro.setBounds(50, 30, 120, 30);
        indietro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (ent.getServizio() == 0) {
                    new HomeBND(ent);  // datore
                } 
                else {
                    new HomeBND(ent); // dipendente
                }
            }
        });
        pannello.add(indietro);
    }
}
