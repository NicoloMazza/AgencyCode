package it.unipa.ingegneriasoftware.gestionedipendente.bnd;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JCalendar;

import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestionedipendente.ctrl.RichiestaVariazioneTurnoCTRL;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import java.util.Date;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

@SuppressWarnings("deprecation")

public class RichiestaVariazioneTurnoBND extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel titolo_schermata;
	private JRadioButton malattia;
	private JRadioButton ferie;
	private JRadioButton congedoParentale;
	private JRadioButton sciopero;
	private JRadioButton permesso;
	private ButtonGroup gruppo;
	private JButton indietro;
	private JTextArea campoMotivazioni;
	private JLabel motivazioni;
	private JLabel giorno_inizio;
	private JLabel giorno_fine;
	private JLabel ora_inizio;
	private JLabel ora_fine;
	private JComboBox<LocalTime> menu_ora_inizio;
	private JComboBox<LocalTime> menu_ora_fine;
	private JButton conferma;
	private JCalendar cal1;
	private JCalendar cal2;
	private JCalendar cal3;
	private JCalendar cal4;
    private int jradioButtonSelected; //1 malattia, 2 ferie, 3congedoParentale, 4sciopero, 5permesso

	public RichiestaVariazioneTurnoBND(UtenteENT ent) {

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

		titolo_schermata = new JLabel("RICHIESTA VARIAZIONE TURNO\r\n");
		titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 39));
		titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
		titolo_schermata.setBounds(10, 46, 1244, 73);
		contentPane.add(titolo_schermata);

		giorno_inizio = new JLabel("Giorno Inizio:");
		giorno_inizio.setFont(new Font("Times New Roman", Font.BOLD, 20));
		giorno_inizio.setBounds(590, 100, 250, 70);
		giorno_inizio.setVisible(false);
		contentPane.add(giorno_inizio);

		giorno_fine = new JLabel("Giorno Fine:");
		giorno_fine.setFont(new Font("Times New Roman", Font.BOLD, 20));
		giorno_fine.setBounds(880, 100, 250, 70);
		giorno_fine.setVisible(false);
		contentPane.add(giorno_fine);

		ora_inizio = new JLabel("Ora Inizio:");
		ora_inizio.setFont(new Font("Times New Roman", Font.BOLD, 20));
		ora_inizio.setBounds(590, 366, 250, 20);
		ora_inizio.setVisible(false);
		contentPane.add(ora_inizio);

		LocalTime[] optionsToChoose = new LocalTime[25];
		for (int i = 1; i < 25; i++) {
			optionsToChoose[i] = LocalTime.of(i-1, 0, 0);
		}
		menu_ora_inizio = new JComboBox<>(optionsToChoose);
		menu_ora_inizio.setBounds(700, 366, 140, 20);
		menu_ora_inizio.setVisible(false);
		contentPane.add(menu_ora_inizio);

		ora_fine = new JLabel("Ora Fine:");
		ora_fine.setFont(new Font("Times New Roman", Font.BOLD, 20));
		ora_fine.setBounds(880, 366, 250, 20);
		ora_fine.setVisible(false);
		contentPane.add(ora_fine);

		menu_ora_fine = new JComboBox<>(optionsToChoose);
		menu_ora_fine.setBounds(990, 366, 140, 20);
		menu_ora_fine.setVisible(false);
		contentPane.add(menu_ora_fine);

		gruppo = new ButtonGroup();

		// da giorno corrente
		cal1 = new JCalendar();
		cal1.setBounds(590, 179, 250, 160);
		cal1.setMinSelectableDate(new Date());
        switch(LocalDate.now().getMonthValue()) {
            case 12:
            case 1:
            case 2:
                cal1.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 3, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 3:
            case 4:
            case 5:
                cal1.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 6, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 6:
            case 7:
            case 8:
                cal1.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 9, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 9:
            case 10:
            case 11:
                cal1.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
        }
		cal1.setVisible(false);
		contentPane.add(cal1);

		// dal giorno successivo
		cal2 = new JCalendar();
		cal2.setBounds(590, 179, 250, 160);
		cal2.setMinSelectableDate(new Date(new Date().getTime() + (1000 * 60 * 60 * 24)));
        switch(LocalDate.now().getMonthValue()) {
            case 12:
            case 1:
            case 2:
                cal2.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 3, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 3:
            case 4:
            case 5:
                cal2.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 6, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 6:
            case 7:
            case 8:
                cal2.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 9, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 9:
            case 10:
            case 11:
                cal2.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
        }
		cal2.setVisible(false);
		contentPane.add(cal2);

		// da giorno corrente
		cal3 = new JCalendar();
		cal3.setBounds(880, 179, 250, 160);
		cal3.setMinSelectableDate(new Date());
        switch(LocalDate.now().getMonthValue()) {
            case 12:
            case 1:
            case 2:
                cal3.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 3, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 3:
            case 4:
            case 5:
                cal3.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 6, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 6:
            case 7:
            case 8:
                cal3.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 9, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 9:
            case 10:
            case 11:
                cal3.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
        }
		cal3.setVisible(false);
		contentPane.add(cal3);

		// dal giorno successivo
		cal4 = new JCalendar();
		cal4.setBounds(880, 179, 250, 160);
		cal4.setMinSelectableDate(new Date(new Date().getTime() + (1000 * 60 * 60 * 24)));
        switch(LocalDate.now().getMonthValue()) {
            case 12:
            case 1:
            case 2:
                cal4.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 3, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 3:
            case 4:
            case 5:
                cal4.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 6, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 6:
            case 7:
            case 8:
                cal4.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 9, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 9:
            case 10:
            case 11:
                cal4.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
        }
		cal4.setVisible(false);
		contentPane.add(cal4);

		malattia = new JRadioButton("Malattia");
		malattia.setBounds(300, 252, 109, 23);
		malattia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jradioButtonSelected=1;
				cal1.setVisible(true);
				cal2.setVisible(false);
				cal3.setVisible(true);
				cal4.setVisible(false);
				giorno_inizio.setVisible(true);
				giorno_fine.setVisible(true);
				menu_ora_inizio.setVisible(false);
				menu_ora_fine.setVisible(false);
				ora_inizio.setVisible(false);
				ora_fine.setVisible(false);
				motivazioni.setVisible(true);
				campoMotivazioni.setVisible(true);

			}
		});
		contentPane.add(malattia);

		ferie = new JRadioButton("Ferie");
		ferie.setBounds(300, 306, 109, 23);
		ferie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jradioButtonSelected=2;
				cal1.setVisible(true);
				cal2.setVisible(false);
				cal3.setVisible(true);
				cal4.setVisible(false);
				giorno_inizio.setVisible(true);
				giorno_fine.setVisible(true);
				menu_ora_inizio.setVisible(false);
				menu_ora_fine.setVisible(false);
				ora_inizio.setVisible(false);
				ora_fine.setVisible(false);
				motivazioni.setVisible(false);
				campoMotivazioni.setVisible(false);
				campoMotivazioni.setText("");
			}
		});
		contentPane.add(ferie);

		congedoParentale = new JRadioButton("Congedo Parentale");
		congedoParentale.setBounds(300, 366, 181, 23);
		congedoParentale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jradioButtonSelected=3;
				cal1.setVisible(false);
				cal2.setVisible(true);
				cal3.setVisible(false);
				cal4.setVisible(true);
				giorno_inizio.setVisible(true);
				giorno_fine.setVisible(true);
				ora_inizio.setVisible(true);
				ora_fine.setVisible(true);
				menu_ora_inizio.setVisible(true);
				menu_ora_fine.setVisible(true);
				motivazioni.setVisible(true);
				campoMotivazioni.setVisible(true);
			}
		});
		contentPane.add(congedoParentale);

		sciopero = new JRadioButton("Sciopero\r\n");
		sciopero.setBounds(300, 433, 169, 23);
		sciopero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jradioButtonSelected=4;
				cal1.setVisible(false);
				cal2.setVisible(true);
				cal3.setVisible(false);
				cal4.setVisible(true);
				giorno_inizio.setVisible(true);
				giorno_fine.setVisible(true);
				ora_inizio.setVisible(true);
				ora_fine.setVisible(true);
				menu_ora_inizio.setVisible(true);
				menu_ora_fine.setVisible(true);
				motivazioni.setVisible(true);
				campoMotivazioni.setVisible(true);
			}
		});
		contentPane.add(sciopero);

		permesso = new JRadioButton("Permesso");
		permesso.setBounds(300, 505, 109, 23);
		permesso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jradioButtonSelected=5;
				cal1.setVisible(false);
				cal2.setVisible(true);
				cal3.setVisible(false);
				cal4.setVisible(true);
				giorno_inizio.setVisible(true);
				giorno_fine.setVisible(true);
				ora_inizio.setVisible(true);
				ora_fine.setVisible(true);
				menu_ora_inizio.setVisible(true);
				menu_ora_fine.setVisible(true);
				motivazioni.setVisible(true);
				campoMotivazioni.setVisible(true);
			}
		});
		contentPane.add(permesso);

		indietro = new JButton("Indietro");
		indietro.setBounds(50, 30, 120, 30);
		indietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new GestioneDipendenteBND(ent);
			}
		});

		malattia.setBackground(Color.WHITE);
		ferie.setBackground(Color.WHITE);
		congedoParentale.setBackground(Color.WHITE);
		sciopero.setBackground(Color.WHITE);
		permesso.setBackground(Color.WHITE);
		contentPane.add(indietro);

		gruppo.add(malattia);
		gruppo.add(ferie);
		gruppo.add(congedoParentale);
		gruppo.add(sciopero);
		gruppo.add(permesso);

		campoMotivazioni = new JTextArea();
		campoMotivazioni.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		campoMotivazioni.setBounds(723, 462, 434, 73);
		campoMotivazioni.setVisible(false);
		contentPane.add(campoMotivazioni);

		motivazioni = new JLabel("Motivazioni:");
		motivazioni.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		motivazioni.setBounds(724, 428, 107, 23);
		motivazioni.setVisible(false);
		contentPane.add(motivazioni);

		conferma = new JButton("Conferma\r\n");
		conferma.setBounds(1040, 596, 120, 30);
		conferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean riempito = false;
				if (malattia.isSelected()) {
					if (!(campoMotivazioni.getText().equals(""))) {
						riempito = true;
					}
				} else {
					if (ferie.isSelected()) {
						// verifica tante cose
						// se va a buon fine
						riempito = true;
					} else {

						if (!(new Date(cal2.getDate().getYear(), cal2.getDate().getMonth(), cal2.getDate().getDay())
								.equals(new Date(new Date().getYear(), new Date().getMonth(), new Date().getDay()))) &&
								!(new Date(cal4.getDate().getYear(), cal4.getDate().getMonth(), cal4.getDate().getDay())
										.equals(new Date(new Date().getYear(), new Date().getMonth(),
												new Date().getDay())))
								&&
								!(String.valueOf(menu_ora_inizio.getSelectedItem()).equals("")) &&
								!(String.valueOf(menu_ora_fine.getSelectedItem()).equals("")) &&
								!(campoMotivazioni.getText().equals(""))) {
							riempito = true;
						}
					}
				}
				if (riempito) {
					System.out.println(cal1.getDate());
					System.out.println(cal2.getDate());
					System.out.println(cal3.getDate());
					System.out.println(cal4.getDate());
					System.out.println(String.valueOf(menu_ora_inizio.getSelectedItem()));
					System.out.println(String.valueOf(menu_ora_fine.getSelectedItem()));
					System.out.println(campoMotivazioni.getText());

                    LocalDate dataInizioLD_cal1 = LocalDate.ofInstant(cal1.getDate().toInstant(), ZoneId.systemDefault());
					LocalDate dataFineLD_cal3 = LocalDate.ofInstant(cal3.getDate().toInstant(), ZoneId.systemDefault());

					LocalDate dataInizioLD_cal2 = LocalDate.ofInstant(cal2.getDate().toInstant(), ZoneId.systemDefault());
					LocalDate dataFineLD_cal4 = LocalDate.ofInstant(cal4.getDate().toInstant(), ZoneId.systemDefault());                 

					if (jradioButtonSelected==2 || jradioButtonSelected==1) {
						RichiestaVariazioneTurnoCTRL.inserisciVariazione(ent, (LocalTime)menu_ora_inizio.getSelectedItem(), (LocalTime)menu_ora_fine.getSelectedItem(), dataInizioLD_cal1, dataFineLD_cal3, jradioButtonSelected, campoMotivazioni.getText());
					} else {
						RichiestaVariazioneTurnoCTRL.inserisciVariazione(ent, (LocalTime)menu_ora_inizio.getSelectedItem(), (LocalTime)menu_ora_fine.getSelectedItem(), dataInizioLD_cal2, dataFineLD_cal4, jradioButtonSelected, campoMotivazioni.getText());
					}
					if (jradioButtonSelected==2 || jradioButtonSelected==1) {
						RichiestaVariazioneTurnoCTRL.gestisciVariazioneTurno(ent, (LocalTime)menu_ora_inizio.getSelectedItem(), (LocalTime)menu_ora_fine.getSelectedItem(), dataInizioLD_cal1, dataFineLD_cal3);
					} else {
						RichiestaVariazioneTurnoCTRL.gestisciVariazioneTurno(ent, (LocalTime)menu_ora_inizio.getSelectedItem(), (LocalTime)menu_ora_fine.getSelectedItem(), dataInizioLD_cal2, dataFineLD_cal4);
					}
				} else {
					RichiestaVariazioneTurnoCTRL.creaMessaggioASchermo("Non hai riempito tutti i campi!", 1);
				}
			}
		});
		conferma.setVisible(true);
		contentPane.add(conferma);
	}
	
}