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

public class OTP_BND extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel pannello;
    private JLabel titolo_schermata;
    private JLabel riga1;
    private JLabel riga2;
    private JLabel otp;
    private JTextField campoOTP;
    private JButton conferma;
    private JButton indietro;

    public OTP_BND(UtenteENT ent, String otp_provenienza) {

        System.out.println(otp_provenienza);

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

        titolo_schermata = new JLabel("INSERISCI OTP");
        titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 70));
        titolo_schermata.setForeground(Color.BLACK);
        titolo_schermata.setBounds(0, 130, 1280, 100);
        titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
        pannello.add(titolo_schermata);

        riga1 = new JLabel("Ciao " + ent + "! Ti abbiamo inviato una mail contenente la OTP (One Time Password).");
        riga1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        riga1.setForeground(Color.BLACK);
        riga1.setBounds(0, 265, 1280, 25);
        riga1.setHorizontalAlignment(SwingConstants.CENTER);
        pannello.add(riga1);

        riga2 = new JLabel("Per confermare l'operazione, inserisci la OTP nel campo qui sotto.");
        riga2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        riga2.setForeground(Color.BLACK);
        riga2.setBounds(0, 315, 1280, 25);
        riga2.setHorizontalAlignment(SwingConstants.CENTER);
        pannello.add(riga2);

        otp = new JLabel("OTP:");
        otp.setFont(new Font("Times New Roman", Font.BOLD, 25));
        otp.setForeground(Color.BLACK);
        otp.setBounds(400, 380, 113, 25);
        pannello.add(otp);

        campoOTP = new JTextField("");
        campoOTP.setBounds(500, 380, 290, 25);
        pannello.add(campoOTP);

        conferma = new JButton("Conferma");
        conferma.setBounds(578, 430, 113, 30);
        conferma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String otpInserito = campoOTP.getText();
                if (LoginCTRL.confrontaOTP(otpInserito, otp_provenienza)) {
                    // Se l'otp contiene lettere OR (l'otp contiene numeri AND è il primo accesso)
                    if (!(otpInserito.matches("[0-9]+"))
                            || (otpInserito.matches("[0-9]+") && ent.getStato_pass() == 0)) {
                        RecuperoPasswordCTRL.creaCreaPasswordBND(ent, otp_provenienza);
                        dispose();
                    } else {
                        LoginCTRL.creaHomeBND(ent);
                        dispose();
                    }
                } else {
                    LoginCTRL.creaMessaggioASchermo("One Time Password errata.", 1);
                    LoginCTRL.inviaMailAvviso(ent);
                    dispose();
                    RecuperoPasswordCTRL.creaLoginBND();
                }
            }
        });
        pannello.add(conferma);

        indietro = new JButton("Indietro");
        indietro.setBounds(50, 30, 120, 30);
        indietro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginCTRL.creaLoginBND();
            }
        });
        pannello.add(indietro);
        this.add(pannello);
    }
}
