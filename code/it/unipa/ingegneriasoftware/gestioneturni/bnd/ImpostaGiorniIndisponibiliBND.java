package it.unipa.ingegneriasoftware.gestioneturni.bnd;

import com.toedter.calendar.JCalendar;

import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestioneturni.ctrl.ImpostaGiorniIndisponibiliCTRL;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.time.LocalDate;
import java.time.ZoneId;

import java.util.Date;

public class ImpostaGiorniIndisponibiliBND extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JCalendar campoDataFine;
	private JCalendar campoDataInizio;
	private JButton conferma;
	private JLabel dataInizio;
	private JLabel dataFine;
	private JButton indietro;
	private JLabel titolo_schermata;

	public ImpostaGiorniIndisponibiliBND(UtenteENT ent) {
        
        // System.out.println(ent+"sg");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(1280, 720);
		MetodiUtili.centraFinestra(this);
		this.setVisible(true);
		this.setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(null);

		dataInizio = new JLabel("Data Inizio: ");
		dataInizio.setHorizontalAlignment(SwingConstants.LEFT);
		dataInizio.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		dataInizio.setBounds(300, 120, 250, 70);
		contentPane.add(dataInizio);

		dataFine = new JLabel("Data Fine: ");
		dataFine.setHorizontalAlignment(SwingConstants.LEFT);
		dataFine.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		dataFine.setBounds(750, 120, 250, 70);
		contentPane.add(dataFine);

		indietro = new JButton("Indietro");
		indietro.setBounds(50, 30, 120, 30);
		indietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new GestioneTurniBND(ent);
			}
		});
		contentPane.add(indietro);

		titolo_schermata = new JLabel("IMPOSTA GIORNI INDISPONIBLI");
		titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
		titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 40));
		titolo_schermata.setBounds(10, 45, 1244, 72);
		contentPane.add(titolo_schermata);
		setSize(1280, 720);

		campoDataInizio = new JCalendar();
		campoDataInizio.setBounds(300, 179, 250, 160);
        switch(LocalDate.now().getMonthValue()) {
            case 1:
            case 2:
                campoDataInizio.setMinSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 4, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                campoDataInizio.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 6, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 4:
            case 5:
                campoDataInizio.setMinSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 7, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                campoDataInizio.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 9, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 7:
            case 8:
                campoDataInizio.setMinSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 10, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                campoDataInizio.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 10:
            case 11:
                campoDataInizio.setMinSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                campoDataInizio.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 3, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
        }
		contentPane.add(campoDataInizio);

		campoDataFine = new JCalendar();
		campoDataFine.setBounds(750, 179, 250, 160);
        switch(LocalDate.now().getMonthValue()) {
            case 1:
            case 2:
                campoDataFine.setMinSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 4, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                campoDataFine.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 6, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 4:
            case 5:
                campoDataFine.setMinSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 7, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                campoDataFine.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 9, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 7:
            case 8:
                campoDataFine.setMinSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 10, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                campoDataFine.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 10:
            case 11:
                campoDataFine.setMinSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                campoDataFine.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 3, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
        }
		contentPane.add(campoDataFine);

		conferma = new JButton("Conferma\r\n");
		conferma.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		conferma.setBounds(583, 419, 131, 42);
		conferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                LocalDate giornoIncriminato = ImpostaGiorniIndisponibiliCTRL.impostaGiorniIndisponibili(campoDataInizio.getDate(), campoDataFine.getDate(), ent);
				if (giornoIncriminato == null){
					ImpostaGiorniIndisponibiliCTRL.creaMessaggioASchermo("Giorni impostati correttamente", 2);
				}else{
					ImpostaGiorniIndisponibiliCTRL.creaMessaggioASchermo("Giorno gia' indisponibile: " + giornoIncriminato, 3);
				}
	    }});
		contentPane.add(conferma);
	}

	
}