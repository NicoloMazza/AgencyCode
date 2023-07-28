package it.unipa.ingegneriasoftware.gestioneturni.bnd;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;

import com.toedter.calendar.JCalendar;

import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestioneturni.ctrl.VisualizzaTurniPersonaliCTRL;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.Date;
import java.util.regex.PatternSyntaxException;

import java.time.LocalDate;
import java.time.ZoneId;

public class VisualizzaTurniPersonaliBND extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JCalendar campoDataFine;
	private JCalendar campoDataInizio;
	private JScrollPane pane;
	private JButton conferma;
	private JLabel dataInizio;
	private JLabel dataFine;
	private JButton indietro;
	private JLabel titolo_schermata;
	private DefaultTableModel model;

	public VisualizzaTurniPersonaliBND(UtenteENT ent) {
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

		titolo_schermata = new JLabel("TURNI PERSONALI\r\n");
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

		dataInizio = new JLabel("Data Inizio: ");
		dataInizio.setHorizontalAlignment(SwingConstants.RIGHT);
		dataInizio.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		dataInizio.setBounds(10, 140, 212, 38);
		contentPane.add(dataInizio);

		dataFine = new JLabel("Data Fine: ");
		dataFine.setHorizontalAlignment(SwingConstants.RIGHT);
		dataFine.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		dataFine.setBounds(21, 330, 201, 42);
		contentPane.add(dataFine);

		campoDataInizio = new JCalendar();
		campoDataInizio.setBounds(260, 140, 250, 160);
        switch(LocalDate.now().getMonthValue()) {
            case 12:
            case 1:
            case 2:
                campoDataInizio.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 3, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 3:
            case 4:
            case 5:
                campoDataInizio.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 6, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 6:
            case 7:
            case 8:
                campoDataInizio.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 9, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 9:
            case 10:
            case 11:
                campoDataInizio.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
        }
/*
		campoDataInizio.addPropertyChangeListener("calendar", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				campoDataInizio.getDate();
				// System.out.println(campoDataInizio.getDate()); test di cosa prende
			}
		});
*/
		contentPane.add(campoDataInizio);

		campoDataFine = new JCalendar();
		campoDataFine.setBounds(260, 330, 250, 160);
        switch(LocalDate.now().getMonthValue()) {
            case 12:
            case 1:
            case 2:
                campoDataFine.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 3, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 3:
            case 4:
            case 5:
                campoDataFine.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 6, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 6:
            case 7:
            case 8:
                campoDataFine.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 9, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
            case 9:
            case 10:
            case 11:
                campoDataFine.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                break;
        }
/*
		campoDataFine.addPropertyChangeListener("calendar", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				campoDataFine.getDate();
				// System.out.println(campoDataFine.getDate()); test di cosa prende
		}});
*/
		contentPane.add(campoDataFine);

		conferma = new JButton("Conferma\r\n");
		conferma.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		conferma.setBounds(320, 550, 131, 42);
		conferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date dataInizio = campoDataInizio.getDate();
				Date dataFine = campoDataFine.getDate();
				if (dataFine.compareTo(dataInizio) < 0) {
					VisualizzaTurniPersonaliCTRL.creaMessaggioASchermo("Non puoi inserire una data inizio maggiore di data fine", 1);
				} 
                else {
                    String data[][] = VisualizzaTurniPersonaliCTRL.mostraTurniPersonali(ent, MetodiUtili.DateToLocalDate(dataInizio), MetodiUtili.DateToLocalDate(dataFine));
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
		}});
		contentPane.add(conferma);
	}
}