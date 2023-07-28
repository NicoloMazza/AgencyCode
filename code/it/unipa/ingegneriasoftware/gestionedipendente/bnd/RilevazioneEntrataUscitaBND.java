package it.unipa.ingegneriasoftware.gestionedipendente.bnd;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import it.unipa.ingegneriasoftware.gestionedipendente.ctrl.SegnalaChiusuraCTRL;
import it.unipa.ingegneriasoftware.gestionedipendente.ctrl.SegnalaPresenzaCTRL;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import java.util.List;
import java.util.ArrayList;

public class RilevazioneEntrataUscitaBND extends JFrame {

    private static final long serialVersionUID = 1L;

    // Schermata Rilevazione Entrata/Uscita
    private JPanel pannello;
    private JLabel titolo_schermata;
    private JLabel nome;
    private JTextField campoNome;
    private JLabel cognome;
    private JTextField campoCognome;
    private JLabel matricola;
    private JTextField campoMatricola;
    private JButton conferma;
    private JLabel orologio;

    // Schermata Terminale Indisponibile
    private JPanel pannello2;
    private JLabel titolo_schermata2;
    private JLabel countdownL;

    // Card
    private JPanel cardPane;
    private CardLayout card;

    public RilevazioneEntrataUscitaBND(int inizio) {

        cardPane = new JPanel();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280, 720);
        MetodiUtili.centraFinestra(this);
        this.setVisible(true);
        this.setResizable(false);

        pannello = new JPanel();
        pannello.setLocation(0, 0);
        pannello.setSize(1280, 720);
        pannello.setBackground(Color.WHITE);
        pannello.setLayout(null);

        titolo_schermata = new JLabel("RILEVAZIONE ENTRATA USCITA");
        titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
        titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 70));
        titolo_schermata.setForeground(Color.BLACK);
        titolo_schermata.setBounds(0, 138, 1280, 100);
        pannello.add(titolo_schermata);

        nome = new JLabel("Nome:");
        nome.setFont(new Font("Times New Roman", Font.BOLD, 25));
        nome.setForeground(Color.BLACK);
        nome.setBounds(410, 265, 186, 25);
        pannello.add(nome);

        campoNome = new JTextField("");
        campoNome.setBounds(500, 265, 290, 25);
        pannello.add(campoNome);

        cognome = new JLabel("Cognome:");
        cognome.setFont(new Font("Times New Roman", Font.BOLD, 25));
        cognome.setForeground(Color.BLACK);
        cognome.setBounds(370, 315, 225, 25);
        pannello.add(cognome);

        campoCognome = new JTextField("");
        campoCognome.setBounds(500, 315, 290, 25);
        pannello.add(campoCognome);

        matricola = new JLabel("Matricola:");
        matricola.setFont(new Font("Times New Roman", Font.BOLD, 25));
        matricola.setForeground(Color.BLACK);
        matricola.setBounds(370, 365, 225, 25);
        pannello.add(matricola);

        campoMatricola = new JTextField("");
        campoMatricola.setBounds(500, 365, 290, 25);
        pannello.add(campoMatricola);

        conferma = new JButton("Conferma");
        conferma.setBounds(578, 430, 113, 30);
        conferma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (campoMatricola.getText().matches("[0-9]+")) {
                    int flag = SegnalaPresenzaCTRL.impostaPresenza(Integer.parseInt(campoMatricola.getText()), campoNome.getText(), campoCognome.getText());
                    if (flag!=2) {
                        if (flag==4) {
                            SegnalaChiusuraCTRL.impostaUscita(Integer.parseInt(campoMatricola.getText()), campoNome.getText(), campoCognome.getText());
                        }
                    }
                    campoNome.setText("");
                    campoCognome.setText("");
                    campoMatricola.setText("");
                }
            }
        });
        pannello.add(conferma);

        pannello2 = new JPanel();
        pannello2.setLocation(0, 0);
        pannello2.setSize(1280, 720);
        pannello2.setBackground(Color.WHITE);
        pannello2.setLayout(null);

        titolo_schermata2 = new JLabel("TERMINALE INDISPONIBILE");
        titolo_schermata2.setHorizontalAlignment(SwingConstants.CENTER);
        titolo_schermata2.setFont(new Font("Times New Roman", Font.BOLD, 70));
        titolo_schermata2.setForeground(Color.BLACK);
        titolo_schermata2.setBounds(0, 138, 1280, 100);
        pannello2.add(titolo_schermata2);

        countdownL = new JLabel("");
        countdownL.setHorizontalAlignment(SwingConstants.CENTER);
        countdownL.setFont(new Font("Times New Roman", Font.BOLD, 70));
        countdownL.setForeground(Color.BLACK);
        countdownL.setBounds(0, 300, 1280, 100);
        pannello2.add(countdownL);

        orologio = new JLabel("");
        orologio.setFont(new Font("Times New Roman", Font.BOLD, 50));
        orologio.setForeground(Color.BLACK);
        orologio.setBounds(1000, 50, 225, 50);
        pannello.add(orologio);
        // pannello2.add(orologio);

        card = new CardLayout();

        cardPane.setLayout(card);
        cardPane.add(pannello, "Rilevazione Entrata Uscita");
        cardPane.add(pannello2, "Terminale Indisponibile");

        this.add(cardPane);

        int delay = 1000; // millisecondi
        List<LocalTime> orariTransizione = new ArrayList<>();
        orariTransizione.add(LocalTime.of(0, 0, 0)); // 0
        orariTransizione.add(LocalTime.of(0, 10, 0)); // 1
        orariTransizione.add(LocalTime.of(6, 0, 0)); // 2
        orariTransizione.add(LocalTime.of(6, 10, 0)); // 3
        orariTransizione.add(LocalTime.of(7, 0, 0)); // 4
        orariTransizione.add(LocalTime.of(7, 10, 0)); // 5
        orariTransizione.add(LocalTime.of(8, 0, 0)); // 6
        orariTransizione.add(LocalTime.of(8, 10, 0)); // 7
        orariTransizione.add(LocalTime.of(9, 0, 0)); // 8
        orariTransizione.add(LocalTime.of(9, 10, 0)); // 9
        orariTransizione.add(LocalTime.of(10, 0, 0)); // 10
        orariTransizione.add(LocalTime.of(10, 10, 0)); // 11
        orariTransizione.add(LocalTime.of(11, 0, 0)); // 12
        orariTransizione.add(LocalTime.of(11, 10, 0)); // 13
        orariTransizione.add(LocalTime.of(13, 0, 0)); // 14
        orariTransizione.add(LocalTime.of(13, 10, 0)); // 15
        orariTransizione.add(LocalTime.of(14, 0, 0)); // 16
        orariTransizione.add(LocalTime.of(14, 10, 0)); // 17
        orariTransizione.add(LocalTime.of(15, 0, 0)); // 18
        orariTransizione.add(LocalTime.of(15, 10, 0)); // 19
        orariTransizione.add(LocalTime.of(16, 0, 0)); // 20
        orariTransizione.add(LocalTime.of(16, 10, 0)); // 21
        orariTransizione.add(LocalTime.of(17, 0, 0)); // 22
        orariTransizione.add(LocalTime.of(17, 10, 0)); // 23
        orariTransizione.add(LocalTime.of(18, 0, 0)); // 24
        orariTransizione.add(LocalTime.of(18, 10, 0)); // 25
        orariTransizione.add(LocalTime.of(19, 0, 0)); // 26
        orariTransizione.add(LocalTime.of(19, 10, 0)); // 27
        orariTransizione.add(LocalTime.of(23, 0, 0)); // 28
        orariTransizione.add(LocalTime.of(23, 10, 0)); // 29

        if (inizio == 0) {
            card.show(cardPane, "Rilevazione Entrata Uscita");
        } else {
            card.show(cardPane, "Terminale Indisponibile");
        }

        Timer timer = new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                int i = 0;
                int hhCorrente = LocalTime.now().getHour();
                int mmCorrente = LocalTime.now().getMinute();
                int ssCorrente = LocalTime.now().getSecond();
                while (i < orariTransizione.size()
                        && orariTransizione.get(i).isBefore(LocalTime.of(hhCorrente, mmCorrente, ssCorrente))) {
                    i++;
                }
                i = i % orariTransizione.size();
                // System.out.println(i);

                LocalTime oraCorrente = LocalTime.now();
                // System.out.println(oraCorrente);
                long secondi;
                if (i != 0) {
                    secondi = LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond())
                            .until(orariTransizione.get(i), ChronoUnit.SECONDS);
                } else { // la differenza tra le 23 e le "24" è la stessa delle 22-23
                    secondi = LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond())
                            .minus(1, ChronoUnit.HOURS).until(LocalTime.of(23, 0, 0), ChronoUnit.SECONDS);
                }
                LocalTime timeOfDay = LocalTime.ofSecondOfDay(secondi);

                if (timeOfDay.toString().length() == 5) {
                    countdownL.setText(timeOfDay.toString() + ":00");
                } else {
                    countdownL.setText(timeOfDay.toString());
                }

                if (LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).toString()
                        .length() == 5) {
                    orologio.setText(
                            LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond())
                                    .toString() + ":00");
                } else {
                    orologio.setText(LocalTime
                            .of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()).toString());
                }

                if (orariTransizione.contains(
                        LocalTime.of(oraCorrente.getHour(), oraCorrente.getMinute(), oraCorrente.getSecond()))) {
                    card.next(cardPane);
                    campoNome.setText("");
                    campoCognome.setText("");
                    campoMatricola.setText("");
                }

                if (LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(0, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(6, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(7, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(8, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(9, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(10, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(11, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(13, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(14, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(15, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(16, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(19, 10, 0)) 
                ) {
                    new Thread() {
                        public void run() {
                            SegnalaPresenzaCTRL.gestioneAssenze();
                        }
                    }.start();
                }

                if (LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(3, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(9, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(10, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(11, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(12, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(13, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(14, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(16, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(17, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(18, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(19, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(22, 10, 0)) 
                ) {
                    new Thread() {
                        public void run() {
                            SegnalaPresenzaCTRL.getAssenti();
                        }
                    }.start();
                }

                if (LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(8, 10, 0)) || 
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(10, 10, 0)) || 
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(11, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(13, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(14, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(15, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(16, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(17, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(18, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(19, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(23, 10, 0)) ||
                    LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(0, 10, 0)) 
                ) {
                    new Thread() {
                        public void run() {
                            SegnalaChiusuraCTRL.impostaChiusuraAutomatica();
                        }
                    }.start();
                }

            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();
    }
}