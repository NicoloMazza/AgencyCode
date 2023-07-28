package it.unipa.ingegneriasoftware.gestionestipendi.bnd;

import java.time.LocalDateTime;
import java.time.LocalDate;

/**
* Classe che contiene metodi per il calcolo dello stipendio
*/
public class CalcolaStipendioBND {

    /**
    * Restituisce data e ora corrente
    * @return la data e l'ora corrente
    */
    public static LocalDateTime getDataOraCorrente() {
        return LocalDateTime.now();
    }

    /**
    * Verifica se la data passata come parametro è Pasqua
    * @param oggi data di cui si vuole verificare se è Pasqua
    * @return true se la data passata come parametro è Pasqua, false altrimenti
    */
    public static boolean isPasqua(LocalDate oggi) {
        int year = oggi.getYear();
        int a = year % 19,
            b = year / 100,
            c = year % 100,
            d = b / 4,
            e = b % 4,
            g = (8 * b + 13) / 25,
            h = (19 * a + b - d - g + 15) % 30,
            j = c / 4,
            k = c % 4,
            m = (a + 11 * h) / 319,
            r = (2 * e + 2 * j - k - h + m + 32) % 7,
            n = (h - m + r + 90) / 25,
            p = (h - m + r + n + 19) % 32;
        // p = giorno
        // n = mese
        System.out.println(p + "/" + n + "/" + year);
        return LocalDate.now().equals(LocalDate.of(year, n, p));
    }

}