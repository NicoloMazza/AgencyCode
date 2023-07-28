package it.unipa.ingegneriasoftware.gestioneturni.bnd;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;

import com.toedter.calendar.JCalendar;

import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestioneturni.ctrl.ImpostaGiorniIndisponibiliCTRL;
import it.unipa.ingegneriasoftware.gestioneturni.ctrl.VisualizzaBachecaTurniCTRL;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.ZoneId;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.Date;
import java.util.regex.PatternSyntaxException;

public class VisualizzaBachecaTurniBND extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JCalendar campoData;
	private JScrollPane pane;
	private JButton conferma;
	private JLabel data;
	private JButton indietro;
	private JLabel titolo_schermata;
	private JTextField campoServizio;
	private JTextField campoMatricola;
	private JLabel servizio;
	private JLabel matricola;
	private JComboBox<LocalTime> menu_ora_inizio;
	private JComboBox<LocalTime> menu_ora_fine;
	private JLabel ora_inizio;
	private JLabel ora_fine;
	private DefaultTableModel model;

	public VisualizzaBachecaTurniBND(UtenteENT ent) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(1280, 720);
		MetodiUtili.centraFinestra(this);
		this.setVisible(true);
		this.setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		table = new JTable();
		setSize(1280, 720);
		pane = new JScrollPane(table);
		pane.setBounds(626, 140, 602, 469);
		pane.setVisible(false);
		getContentPane().add(pane);

		titolo_schermata = new JLabel("BACHECA TURNI\r\n");
		titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
		titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 40));
		titolo_schermata.setBounds(10, 45, 1244, 72);
		contentPane.add(titolo_schermata);

		indietro = new JButton("Indietro");
		indietro.setBounds(50, 30, 120, 30);
		indietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new GestioneTurniBND(ent);
			}
		});
		contentPane.add(indietro);

		data = new JLabel("Data: ");
		data.setHorizontalAlignment(SwingConstants.RIGHT);
		data.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		data.setBounds(10, 140, 212, 38);
		contentPane.add(data);

		campoData = new JCalendar();
		campoData.setBounds(260, 150, 200, 110);
        switch(LocalDate.now().getMonthValue()) {
            case 12:
            case 1:
            case 2:
                campoData.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 3, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 3:
            case 4:
            case 5:
                campoData.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 6, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 6:
            case 7:
            case 8:
                campoData.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 9, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 9:
            case 10:
            case 11:
                campoData.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
        }
/*
		campoData.addPropertyChangeListener("calendar", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				campoData.getDate();
				// System.out.println(campoData.getDate()); test di cosa prende
			}
		});
*/
		contentPane.add(campoData);
		
		LocalTime[] optionsToChoose = new LocalTime[25];
		for (int i = 1; i < 25; i++) {
			optionsToChoose [i] = LocalTime.of(i-1,0,0);
		}
		
		ora_inizio = new JLabel("Ora inizio: ");
		ora_inizio.setHorizontalAlignment(SwingConstants.RIGHT);
		ora_inizio.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		ora_inizio.setBounds(10, 290, 212, 38);
		contentPane.add(ora_inizio);
		
		menu_ora_inizio = new JComboBox<>(optionsToChoose);
		menu_ora_inizio.setBounds(260, 300, 140, 20);
		menu_ora_inizio.setVisible(true);
		contentPane.add(menu_ora_inizio);
		
		ora_fine = new JLabel("Ora fine: ");
		ora_fine.setHorizontalAlignment(SwingConstants.RIGHT);
		ora_fine.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		ora_fine.setBounds(10, 340, 212, 38);
		contentPane.add(ora_fine);

		menu_ora_fine = new JComboBox<>(optionsToChoose);
		menu_ora_fine.setBounds(260, 350, 140, 20);
		menu_ora_fine.setVisible(true);
		contentPane.add(menu_ora_fine);

		matricola = new JLabel("Matricola: ");
		matricola.setHorizontalAlignment(SwingConstants.RIGHT);
		matricola.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		matricola.setBounds(10, 390, 212, 38);
		contentPane.add(matricola);

		campoMatricola = new JTextField();
		campoMatricola.setBounds(260, 400, 140, 20);
		campoMatricola.setVisible(true);
		contentPane.add(campoMatricola);	
	
		servizio = new JLabel("Servizio: ");
		servizio.setHorizontalAlignment(SwingConstants.RIGHT);
		servizio.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		servizio.setBounds(10, 440, 212, 38);
		contentPane.add(servizio);

		campoServizio = new JTextField();
		campoServizio.setBounds(260, 450, 140, 20);
		campoServizio.setVisible(true);
		contentPane.add(campoServizio);

		conferma = new JButton("Conferma\r\n");
		conferma.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		conferma.setBounds(265, 500, 131, 42);
		conferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LocalDate dataInizio = MetodiUtili.DateToLocalDate(campoData.getDate()); 
				LocalTime ora_i = (LocalTime) (menu_ora_inizio.getSelectedItem());
				LocalTime ora_f = (LocalTime) (menu_ora_fine.getSelectedItem());
				String matricola = campoMatricola.getText();
                if (!(campoServizio.getText().equals("")) && campoServizio.getText().matches("[0-9]+")) {
                    Integer servizio = Integer.parseInt(campoServizio.getText().toString());
                    if (servizio >= 1 && servizio <= 4) {
                        if (ora_i == null || ora_f == null) {
                            ImpostaGiorniIndisponibiliCTRL.creaMessaggioASchermo("I campi ora devono essere riempiti.", 1);
                        }
                        else {
                            if( (ora_i.isAfter(ora_f)) && !(ora_f.equals(LocalTime.of(0, 0, 0))) ) {
                                ImpostaGiorniIndisponibiliCTRL.creaMessaggioASchermo("L'ora di inizio deve essere minore dell'ora di fine.", 1);
                            }
                            else {
                                String data[][] = VisualizzaBachecaTurniCTRL.mostraTuttiTurni(dataInizio, ora_i, ora_f, matricola, servizio);
                                String col[] = {"Matricola:" , "Codice turno:" , "Data turno:", "Ora inizio:", "Ora fine:"};
                                model = new DefaultTableModel(data, col) {
                                    @Override
                                    public boolean isCellEditable(int row, int column) {
                                       return false;
                                }};
                                table.setModel(model);
                                final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                                table.setRowSorter(sorter);
                                try {
                                    sorter.setRowFilter(RowFilter.regexFilter(""));
                                } catch (PatternSyntaxException pse) {
                                    System.out.println("Bad regex pattern");
                                }
                                pane.setVisible(true);
                            }
                        }
                    }
                    else {
                        ImpostaGiorniIndisponibiliCTRL.creaMessaggioASchermo("Il servizio deve essere compreso tra 1 e 4.", 1);
                    }
                }
                else {
                    ImpostaGiorniIndisponibiliCTRL.creaMessaggioASchermo("Il campo servizio deve essere riempito.", 1);
                }
		}});
		contentPane.add(conferma);
	}

}