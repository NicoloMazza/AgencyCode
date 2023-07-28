package it.unipa.ingegneriasoftware.gestioneservizi.bnd;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestioneservizi.ctrl.GestioneInfrastruttureCTRL;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.regex.PatternSyntaxException;
import java.awt.event.ActionEvent;

public class GestioneInfrastruttureBND extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JScrollPane pane;
	private JButton conferma;
	private JLabel filtro1;
	private JTextField campoFiltro1;
	private JButton indietro;
	private JLabel titolo_schermata;
	private JLabel filtro2;
	private JTextField campoFiltro2;
	private JLabel filtro3;
	private JTextField campoFiltro3;
	private DefaultTableModel model;

	public GestioneInfrastruttureBND(UtenteENT ent) {
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

		table = new JTable();
		
		pane = new JScrollPane(table);
		pane.setBounds(626, 174, 602, 469);
		pane.setVisible(false);
		getContentPane().add(pane);

		conferma = new JButton("Conferma\r\n");
		conferma.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		conferma.setBounds(259, 501, 131, 42);
		conferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = campoFiltro1.getText();
				String text2 = campoFiltro2.getText();
				String text3 = campoFiltro3.getText();

				String data[][] = GestioneInfrastruttureCTRL.mostraInfrastrutture(text, text2, text3);

				String col[] = { "Codice:", "Nome:", "Citta':" };
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
		});
		contentPane.add(conferma);

		indietro = new JButton("Indietro");
		indietro.setBounds(50, 30, 120, 30);
		indietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new GestioneServiziBND(ent);
			}
		});
		contentPane.add(indietro);

		titolo_schermata = new JLabel("GESTIONE INFRASTRUTTURE\r\n");
		titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
		titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 40));
		titolo_schermata.setBounds(10, 45, 1244, 72);
		contentPane.add(titolo_schermata);

		filtro1 = new JLabel("Codice: ");
		filtro1.setHorizontalAlignment(SwingConstants.RIGHT);
		filtro1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		filtro1.setBounds(10, 282, 212, 38);
		contentPane.add(filtro1);

		campoFiltro1 = new JTextField();
		campoFiltro1.setColumns(10);
		campoFiltro1.setBounds(232, 282, 185, 29);

		contentPane.add(campoFiltro1);

		filtro2 = new JLabel("Nome: ");
		filtro2.setHorizontalAlignment(SwingConstants.RIGHT);
		filtro2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		filtro2.setBounds(10, 346, 212, 38);
		contentPane.add(filtro2);

		campoFiltro2 = new JTextField();
		campoFiltro2.setColumns(10);
		campoFiltro2.setBounds(232, 346, 185, 29);
		contentPane.add(campoFiltro2);

		filtro3 = new JLabel("Citta': ");
		filtro3.setHorizontalAlignment(SwingConstants.RIGHT);
		filtro3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		filtro3.setBounds(10, 412, 212, 38);
		contentPane.add(filtro3);

		campoFiltro3 = new JTextField();
		campoFiltro3.setColumns(10);
		campoFiltro3.setBounds(232, 412, 185, 29);
		contentPane.add(campoFiltro3);
		setSize(1280, 720);

	}
}
