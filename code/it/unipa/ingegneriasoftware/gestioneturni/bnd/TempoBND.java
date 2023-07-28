package it.unipa.ingegneriasoftware.gestioneturni.bnd;

import java.time.LocalDateTime;

public class TempoBND {
    public static LocalDateTime getDataOraCorrente() {
        return LocalDateTime.now();
    }
}