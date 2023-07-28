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
import it.unipa.ingegneriasoftware.gestioneservizi.ctrl.GestioneRecensioniCTRL;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.regex.PatternSyntaxException;

public class GestioneRecensioniBND extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JButton conferma;
	private JLabel filtro1;
	private JButton indietro;
	private JLabel titolo_schermata;
	private JLabel filtro2;
	private JTextField campoFiltro1;
	private JTextField campoFiltro2;
	private JTextField campoFiltro3;
	private JScrollPane pane;
	private JLabel filtro3;
	private DefaultTableModel model;

	private JLabel label5;
	private JLabel label4;
	private JLabel label3;
	private JLabel label2;
	private JLabel label1;

	public GestioneRecensioniBND(UtenteENT ent) {

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
		conferma.setBounds(259, 324, 131, 42);

		conferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = campoFiltro1.getText();
				String text2 = campoFiltro2.getText();
				String text3 = campoFiltro3.getText();

				String data[][] = GestioneRecensioniCTRL.mostraRecensioni(text, text2, text3);

				String col[] = { "Codice:", "Testo:", "Punteggio:" };
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

				int contatore = 0;
				for (int i = 0; i < data.length; i++) {
					if (data[i][2].equals("1")) {
						contatore++;
					}
				}
				label1.setText("1 punto (" + contatore + ")");

				int contatore2 = 0;
				for (int i = 0; i < data.length; i++) {
					if (data[i][2].equals("2")) {
						contatore2++;
					}
				}
				label2.setText("2 punti (" + contatore2 + ")");

				int contatore3 = 0;
				for (int i = 0; i < data.length; i++) {
					if (data[i][2].equals("3")) {
						contatore3++;
					}
				}
				label3.setText("3 punti (" + contatore3 + ")");

				int contatore4 = 0;
				for (int i = 0; i < data.length; i++) {
					if (data[i][2].equals("4")) {
						contatore4++;
					}
				}
				label4.setText("4 punti (" + contatore4 + ")");

				int contatore5 = 0;
				for (int i = 0; i < data.length; i++) {
					if (data[i][2].equals("5")) {
						contatore5++;
					}
				}
				label5.setText("5 punti (" + contatore5 + ")");

				pane.setVisible(true);
			}
		});

		contentPane.add(conferma);

		filtro1 = new JLabel("Codice: ");
		filtro1.setHorizontalAlignment(SwingConstants.RIGHT);
		filtro1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		filtro1.setBounds(10, 174, 212, 38);
		contentPane.add(filtro1);

		campoFiltro1 = new JTextField();
		campoFiltro1.setBounds(232, 174, 185, 29);
		contentPane.add(campoFiltro1);
		campoFiltro1.setColumns(10);

		indietro = new JButton("Indietro");
		indietro.setBounds(50, 30, 120, 30);
		indietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new GestioneServiziBND(ent);
			}
		});
		contentPane.add(indietro);

		titolo_schermata = new JLabel("GESTIONE RECENSIONI\r\n");
		titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
		titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 40));
		titolo_schermata.setBounds(10, 45, 1244, 72);
		contentPane.add(titolo_schermata);

		filtro2 = new JLabel("Testo: ");
		filtro2.setHorizontalAlignment(SwingConstants.RIGHT);
		filtro2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		filtro2.setBounds(10, 223, 212, 38);
		contentPane.add(filtro2);

		campoFiltro2 = new JTextField();
		campoFiltro2.setColumns(10);
		campoFiltro2.setBounds(232, 223, 185, 29);
		contentPane.add(campoFiltro2);

		filtro3 = new JLabel("Punteggio: ");
		filtro3.setHorizontalAlignment(SwingConstants.RIGHT);
		filtro3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		filtro3.setBounds(10, 272, 212, 38);
		contentPane.add(filtro3);

		campoFiltro3 = new JTextField();
		campoFiltro3.setColumns(10);
		campoFiltro3.setBounds(232, 272, 185, 29);
		contentPane.add(campoFiltro3);

		label5 = new JLabel("5 punti:");
		label5.setHorizontalAlignment(SwingConstants.RIGHT);
		label5.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		label5.setBounds(10, 426, 134, 38);
		contentPane.add(label5);

		label4 = new JLabel("4 punti:");
		label4.setHorizontalAlignment(SwingConstants.RIGHT);
		label4.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		label4.setBounds(10, 473, 134, 38);
		contentPane.add(label4);

		label2 = new JLabel("2 punti:");
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		label2.setBounds(10, 551, 134, 38);
		contentPane.add(label2);

		label3 = new JLabel("3 punti:");
		label3.setHorizontalAlignment(SwingConstants.RIGHT);
		label3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		label3.setBounds(10, 512, 134, 38);
		contentPane.add(label3);

		label1 = new JLabel("1 punto:");
		label1.setHorizontalAlignment(SwingConstants.RIGHT);
		label1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		label1.setBounds(10, 590, 134, 38);
		contentPane.add(label1);

	}
}
