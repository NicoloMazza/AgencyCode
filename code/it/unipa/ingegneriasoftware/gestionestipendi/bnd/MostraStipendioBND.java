package it.unipa.ingegneriasoftware.gestionestipendi.bnd;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;

import com.toedter.calendar.JCalendar;

import it.unipa.ingegneriasoftware.gestioneturni.bnd.GestioneTurniBND;
import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestionestipendi.ctrl.MostraStipendioCTRL;
import it.unipa.ingegneriasoftware.gestioneturni.ctrl.ImpostaGiorniIndisponibiliCTRL;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.time.LocalDate;
import java.time.ZoneId;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.Date;
import java.util.regex.PatternSyntaxException;

public class MostraStipendioBND extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JScrollPane pane;
	private JCalendar da;
	private JCalendar a;
	private JLabel da_giorno;
	private JLabel a_giorno; 
	private JButton conferma;
	private JButton indietro;
	private JLabel titolo_schermata;
	private LocalDate data1;
	private LocalDate data2;
	private DefaultTableModel model;

	public MostraStipendioBND(UtenteENT ent) {

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

		titolo_schermata = new JLabel("MOSTRA STIPENDI\r\n");
		titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
		titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 40));
		titolo_schermata.setBounds(10, 45, 1244, 72);
		contentPane.add(titolo_schermata);

		da_giorno = new JLabel("Da giorno: ");
		da_giorno.setHorizontalAlignment(SwingConstants.RIGHT);
		da_giorno.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		da_giorno.setBounds(10, 140, 212, 38);
		contentPane.add(da_giorno);

		da = new JCalendar();
		da.setBounds(260, 150, 200, 110);
        da.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 2).minusDays(1L).atStartOfDay(ZoneId.systemDefault()).toInstant()));
/*
		da.addPropertyChangeListener("calendar", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				data1 = MetodiUtili.DateToLocalDate(da.getDate());
			}
		});
*/
		contentPane.add(da);

		a_giorno = new JLabel("A giorno: ");
		a_giorno.setHorizontalAlignment(SwingConstants.RIGHT);
		a_giorno.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		a_giorno.setBounds(10, 300, 212, 38);
		contentPane.add(a_giorno);

		a = new JCalendar();
		a.setBounds(260, 310, 200, 110);
        a.setMaxSelectableDate(Date.from(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 2).minusDays(1L).atStartOfDay(ZoneId.systemDefault()).toInstant()));
/*
		a.addPropertyChangeListener("calendar", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				data2 = MetodiUtili.DateToLocalDate(a.getDate());
			}
		});
*/
		contentPane.add( a);

		conferma = new JButton("Conferma\r\n");
		conferma.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		conferma.setBounds(265, 500, 131, 42);
		conferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
            data1 = MetodiUtili.DateToLocalDate(da.getDate());
            data2 = MetodiUtili.DateToLocalDate(a.getDate());
            if (data1 != null && data2 != null) {
                if (data1.isAfter(data2)) {
                    ImpostaGiorniIndisponibiliCTRL.creaMessaggioASchermo("La prima data deve essere precedente alla seconda!",1);
                }
                else {
                    String data[][] = MostraStipendioCTRL.mostraStipendi(data1, data2, String.valueOf(ent.getMatricola()));
                    String col[] = {"Matricola:" , "Data accredito:" , "Stipendio:"};
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
	    }});		
		contentPane.add(conferma);

		indietro = new JButton("Indietro");
		indietro.setBounds(50, 30, 120, 30);
		indietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new GestioneStipendiBND(ent);
			}
		});
		contentPane.add(indietro);
	}
}