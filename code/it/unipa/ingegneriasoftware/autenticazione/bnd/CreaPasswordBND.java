package it.unipa.ingegneriasoftware.autenticazione.bnd;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import it.unipa.ingegneriasoftware.autenticazione.ctrl.LoginCTRL;
import it.unipa.ingegneriasoftware.autenticazione.ctrl.RecuperoPasswordCTRL;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreaPasswordBND extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel pannello;
    private JLabel titolo_schermata;
    private JLabel password1;
    private JTextField campoPassword1;
    private JLabel password2;
    private JTextField campoPassword2;
    private JButton conferma;
    private JButton indietro;

    public CreaPasswordBND(UtenteENT ent, String otp_provenienza) {

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

        titolo_schermata = new JLabel("CREA PASSWORD");
        titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
        titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 70));
        titolo_schermata.setForeground(Color.BLACK);
        titolo_schermata.setBounds(0, 138, 1280, 100);

        password1 = new JLabel("Nuova password:");
        password1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        password1.setForeground(Color.BLACK);
        password1.setBounds(334, 265, 186, 25);

        password2 = new JLabel("Conferma Password:");
        password2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        password2.setForeground(Color.BLACK);
        password2.setBounds(305, 315, 225, 25);

        campoPassword1 = new JTextField("");
        campoPassword1.setBounds(500, 265, 290, 25);
        campoPassword2 = new JTextField("");
        campoPassword2.setBounds(500, 315, 290, 25);

        conferma = new JButton("Conferma");
        conferma.setBounds(578, 362, 113, 30);
        conferma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String p1 = campoPassword1.getText();
                String p2 = campoPassword2.getText();
                if (p1.equals(p2)) {
                    UtenteENT entAggiornata = RecuperoPasswordCTRL.aggiornaPassword(ent, p1);
                    if (entAggiornata != null) {
                        LoginCTRL.creaMessaggioASchermo("Password modificata con successo.", 2);
                        if (otp_provenienza.matches("[0-9]+")) {
                            dispose();
                            LoginCTRL.creaHomeBND(entAggiornata);
                        } else {
                            dispose();
                            LoginCTRL.creaLoginBND();
                        }
                    } else {
                        LoginCTRL.creaMessaggioASchermo("Errore durante la modifica della password.", 1);
                    }
                } else {
                    LoginCTRL.creaMessaggioASchermo("Le password inserite non coincidono.", 1);
                    campoPassword1.setText("");
                    campoPassword2.setText("");
                }
            }
        });

        indietro = new JButton("Indietro");
        indietro.setBounds(50, 30, 120, 30);
        indietro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginCTRL.creaLoginBND();
            }
        });

        pannello.add(titolo_schermata);
        pannello.add(password1);
        pannello.add(password2);
        pannello.add(campoPassword1);
        pannello.add(campoPassword2);
        pannello.add(conferma);
        pannello.add(indietro);

        this.add(pannello);
    }
}
