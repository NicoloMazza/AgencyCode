package it.unipa.ingegneriasoftware.comuni;

import javax.swing.JOptionPane;

public class MessaggioASchermo {

    public MessaggioASchermo(String messaggio, int tipo) {
        switch (tipo) {
            case 1:
                JOptionPane.showMessageDialog(null, messaggio, "Messaggio Errore", JOptionPane.ERROR_MESSAGE);
                break;
            case 2:
                JOptionPane.showMessageDialog(null, messaggio, "Messaggio Informazioni",
                        JOptionPane.INFORMATION_MESSAGE);
                break;
            case 3:
                JOptionPane.showMessageDialog(null, messaggio, "Messaggio Avviso", JOptionPane.WARNING_MESSAGE);
                break;
            case 4:
                JOptionPane.showMessageDialog(null, messaggio, "Messaggio Domanda", JOptionPane.QUESTION_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(null, messaggio, "Messaggio Generico", JOptionPane.PLAIN_MESSAGE);
                break;
        }
    }
}