package it.unipa.ingegneriasoftware.gestionedipendenti.bnd;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import java.util.Date;

import com.toedter.calendar.JCalendar;

import it.unipa.ingegneriasoftware.comuni.ent.UtenteENT;
import it.unipa.ingegneriasoftware.gestionedipendenti.ctrl.AggiungiDipendenteCTRL;
import it.unipa.ingegneriasoftware.gestionedipendenti.ctrl.EsportaListaCTRL;
import it.unipa.ingegneriasoftware.comuni.metodiutili.MetodiUtili;

public class EsportaListaBND extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JCalendar campoData;
	private JButton conferma;
	// private JButton stampa;
	private JButton indietro;
	private JLabel titolo_schermata;

	public EsportaListaBND(UtenteENT ent) {
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
/*
		JLabel pdf_pronto = new JLabel("PDF Pronto:");
		pdf_pronto.setFont(new Font("Times New Roman", Font.BOLD, 20));
		pdf_pronto.setBounds(590, 450, 250, 70);
		pdf_pronto.setVisible(false);
		contentPane.add(pdf_pronto);
*/
		conferma = new JButton("Conferma\r\n");
		conferma.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		conferma.setBounds(583, 350, 131, 42);
		conferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ( EsportaListaCTRL.esportaLista(MetodiUtili.DateToLocalDate(campoData.getDate())) == false ) {
                    AggiungiDipendenteCTRL.creaMessaggioASchermo("Nel giorno selezionato non ci sono dipendenti in turno effettivo.", 1);
                }
	    }});
		contentPane.add(conferma);

		campoData = new JCalendar();
		campoData.setBounds(515, 170, 250, 160);
		campoData.setMaxSelectableDate(new Date());
		contentPane.add(campoData);
/*
		stampa = new JButton("Stampa\r\n");
		stampa.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		stampa.setBounds(583, 519, 131, 42);
		stampa.setVisible(false);
		stampa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ( EsportaListaCTRL.creaPDF(dipendenti, MetodiUtili.DateToLocalDate(campoData.getDate())) ) {
                    AggiungiDipendenteCTRL.creaMessaggioASchermo("Pdf creato con successo in ./", 2);
                }
                else {
                    AggiungiDipendenteCTRL.creaMessaggioASchermo("Errore durante la creazione del pdf", 1);
                }
			}
		});
		contentPane.add(stampa);
*/
		indietro = new JButton("Indietro");
		indietro.setBounds(50, 30, 120, 30);
		indietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new GestioneDipendentiBND(ent);
		}});
		contentPane.add(indietro);

		titolo_schermata = new JLabel("ESPORTA LISTA DIPENDENTI");
		titolo_schermata.setHorizontalAlignment(SwingConstants.CENTER);
		titolo_schermata.setFont(new Font("Times New Roman", Font.BOLD, 40));
		titolo_schermata.setBounds(10, 45, 1244, 72);
		contentPane.add(titolo_schermata);
		setSize(1280, 720);
	}
}