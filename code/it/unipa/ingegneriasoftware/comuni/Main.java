package it.unipa.ingegneriasoftware.comuni;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

import it.unipa.ingegneriasoftware.autenticazione.bnd.LoginBND;
import it.unipa.ingegneriasoftware.gestionestipendi.ctrl.CalcolaStipendioCTRL;
import it.unipa.ingegneriasoftware.gestioneturni.ctrl.TempoCTRL;
import it.unipa.ingegneriasoftware.gestionedipendente.ctrl.SegnalaPresenzaCTRL;

import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;

import java.time.LocalTime;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Scanner scanner = new Scanner( new File("./nodo.txt"), "UTF-8" );
                    String nodo = scanner.useDelimiter(";").next();
                    System.out.println(nodo);
                    if (nodo.equals("nodo=PCPersonaleDipendente") || 
                        nodo.equals("nodo=PCPersonaleDatore")) {
                        new LoginBND();
                    }
                    else {
                        if (nodo.equals("nodo=TerminaleAzienda")) {
                            SegnalaPresenzaCTRL.creaRilevazionePresenzaBND();
                        }
                        else {
                            if (nodo.equals("nodo=PCAziendale")) {
                                new LoginBND();
                                int delay = 1000;
                                Timer timer = new Timer(delay, new ActionListener() {
                                    public void actionPerformed(ActionEvent ae) {
                                        if (LocalDate.now().getDayOfMonth() == 1 && 
                                            LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(1, 10, 0))) {
                                            new Thread() {
                                                public void run() {
                                                    CalcolaStipendioCTRL.calcolaStipendi();
                                                }
                                            }.start();
                                        }
                                        if (LocalDate.now().getMonthValue() % 3 == 0 && 
                                            LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond()).equals(LocalTime.of(0, 0, 0))) {
                                            new Thread() {
                                                public void run() {
                                                    TempoCTRL.generaTurni();
                                                }
                                            }.start();
                                        }
                                }});
                                timer.setRepeats(true);
                                timer.setCoalesce(true);
                                timer.setInitialDelay(0);
                                timer.start();
                            }
                            else {
                                System.out.println("File nodo non presente | Nodo sconosciuto.");
                            }
                        }
                    }
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}