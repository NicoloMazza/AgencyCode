package it.unipa.ingegneriasoftware.gestionedipendenti.bnd;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestionedipendenti.ctrl.CercaDipendenteCTRL;
import it.unipa.ingegneriasoftware.gestionedipendenti.ctrl.ModificaDatiDipendenteCTRL;
import it.unipa.ingegneriasoftware.gestionedipendenti.ctrl.RimuoviDipendenteCTRL;
import it.unipa.ingegneriasoftware.gestionedipendenti.ctrl.VisualizzaDipendenteCTRL;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

import javax.swing.table.TableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import java.util.regex.PatternSyntaxException;


public class CercaDipendenteBND extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private JButton visualizza;
	private JButton elimina;
	private JTextField filtroTestuale;
	private JButton filtro;
	private JButton modifica;
	private JScrollPane pane;
	private JButton indietro;

	public CercaDipendenteBND(UtenteENT ent) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1280, 720);
        MetodiUtili.centraFinestra(this);
        this.setVisible(true);
        this.setResizable(false);

        contentPane = new JPanel();
        contentPane.setLocation(0, 0);
        contentPane.setSize(1280, 720);
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(null);
		setContentPane(contentPane);

		visualizza = new JButton("Visualizza");
		visualizza.setBounds(979, 119, 165, 51);
		visualizza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try {
                    String matricola = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
                    VisualizzaDipendenteCTRL.creaDatiDipendenteBND(ent, matricola);
                    dispose();
                }
                catch (ArrayIndexOutOfBoundsException ex) {
                    RimuoviDipendenteCTRL.creaMessaggioASchermo("Devi prima selezionare una riga o filtrare se non lo hai gia' fatto!", 1);
                }
		}});
		contentPane.add(visualizza);

		elimina = new JButton("Elimina");
		elimina.setBounds(979, 255, 165, 51);
		elimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try {
                    String matricola = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
                    if ( RimuoviDipendenteCTRL.rimuoviDipendente(matricola) ) {
                        model.removeRow(table.getSelectedRow());
                        RimuoviDipendenteCTRL.creaMessaggioASchermo("Rimosso con successo.", 2);
                        dispose();
                        new CercaDipendenteBND(ent);
                    }
                    else {
                        RimuoviDipendenteCTRL.creaMessaggioASchermo("Errore.", 1);
                    }
                }
                catch (ArrayIndexOutOfBoundsException ex) {
                    RimuoviDipendenteCTRL.creaMessaggioASchermo("Devi prima selezionare una riga o filtrare se non lo hai gia' fatto!", 1);
                }
			}
		});
		contentPane.add(elimina);

		filtroTestuale = new JTextField("");
		filtroTestuale.setBounds(900, 515, 300, 25);
		contentPane.add(filtroTestuale);

        table = new JTable();

        pane = new JScrollPane(table);
        pane.setBounds(61, 100, 725, 554); 
        pane.setVisible(false);
        getContentPane().add(pane);
        setSize(1280, 720);

		filtro = new JButton("Filtra");
		filtro.setBounds(979, 615, 165, 51);
		filtro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                pane.setVisible(true);
				String text = filtroTestuale.getText();
/*
                String data[][] = { { "1111", "Mario", "Rossi" },
                        { "1112", "Pippo", "Baudo" },
                        { "1113", "Filippo", "Bruni" },
                        { "1114", "R", "Q" },
                        { "1115", "V", "H" },
                        { "1116", "V", "K" },
                        { "1117", "W", "I" },
                        { "1118", "P", "O" },
                        { "1119", "E", "I" },
                        { "Raju", "P", "Q" },
                        { "Ranju", "FR", "B" },
                        { "1120", "M", "N" }
                };
*/
                String data[][] = CercaDipendenteCTRL.mostraDipendenti(text);

                String col[] = { "Matricola:", "Nome:", "Cognome:" };
                model = new DefaultTableModel(data, col) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                       return false;
                }};
                table.setModel(model);
                //final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                
                //table.setRowSorter(sorter);
/*
				if (text.length() == 0) {
					sorter.setRowFilter(null);
				} 
                else {
*/
/*
					try {
						sorter.setRowFilter(RowFilter.regexFilter(""));
					} catch (PatternSyntaxException pse) {
						System.out.println("Bad regex pattern");
					}
*/
				//}
			}
		});
		contentPane.add(filtro);

		modifica = new JButton("Modifica");
		modifica.setBounds(979, 415, 165, 51);
		modifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try {
                    String matricola = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
                    ModificaDatiDipendenteCTRL.creaModificaDatiDipendenteBND(ent, matricola);
                    dispose();
                }
                catch (ArrayIndexOutOfBoundsException ex) {
                    RimuoviDipendenteCTRL.creaMessaggioASchermo("Devi prima selezionare una riga o filtrare se non lo hai gia' fatto!", 1);
                }
			}
		});
		contentPane.add(modifica);

		indietro = new JButton("Indietro");
		indietro.setBounds(50, 30, 120, 30);
		indietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new GestioneDipendentiBND(ent);
			}
		});
		contentPane.add(indietro);

	}


}
