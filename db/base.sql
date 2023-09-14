CREATE DATABASE IF NOT EXISTS ingegneria_software;
USE ingegneria_software;

CREATE TABLE utente (
   matricola INT UNSIGNED NOT NULL AUTO_INCREMENT,
   nome VARCHAR(50) NOT NULL,
   cognome VARCHAR(50) NOT NULL,
   email VARCHAR(80) NOT NULL UNIQUE,
   pass VARCHAR(64) NOT NULL,
   stato_pass TINYINT UNSIGNED NOT NULL DEFAULT 0,
   iban VARCHAR(27) NOT NULL,
   stato_utente TINYINT UNSIGNED NOT NULL DEFAULT 0,
   PRIMARY KEY (matricola)
);

ALTER TABLE utente
   ADD CONSTRAINT iban CHECK (LENGTH(iban) = 27 AND
                              CONVERT(SUBSTRING(iban, 1, 2) USING BINARY) = 'IT' AND
                              SUBSTRING(iban, 3, 2) REGEXP '^[0-9]+$' AND
                              SUBSTRING(iban, 5, 1) REGEXP '^[A-Z]+$' AND
                              SUBSTRING(iban, 6, 22) REGEXP '^[0-9]+$'),
   ADD CONSTRAINT stato_pass CHECK (stato_pass BETWEEN 0 AND 1),
   ADD CONSTRAINT stato_utente CHECK (stato_utente BETWEEN 0 AND 2),
   AUTO_INCREMENT = 1111;

DELIMITER //
CREATE TRIGGER cifraPassword
BEFORE INSERT
ON utente
FOR EACH ROW
BEGIN
   SET NEW.pass = SHA2(NEW.pass, 256);
END; //
DELIMITER ;

CREATE TABLE servizio (
   cod_servizio INT UNSIGNED NOT NULL,
   nome_servizio VARCHAR(50) NOT NULL,
   stipendio_base DOUBLE NOT NULL,
   stato_servizio TINYINT UNSIGNED NOT NULL DEFAULT 0,
   PRIMARY KEY (cod_servizio)
);

ALTER TABLE servizio
   ADD CONSTRAINT cod_servizio CHECK (cod_servizio BETWEEN 1 AND 4),
   ADD CONSTRAINT stato_servizio CHECK (stato_servizio BETWEEN 0 AND 1);

CREATE TABLE dipendente (
   ref_utente INT UNSIGNED NOT NULL,
   ref_servizio INT UNSIGNED NOT NULL,
   ore_da_recuperare INT UNSIGNED NOT NULL DEFAULT 0,
   PRIMARY KEY (ref_utente),
   FOREIGN KEY (ref_utente) REFERENCES utente(matricola) ON DELETE CASCADE,
   FOREIGN KEY (ref_servizio) REFERENCES servizio(cod_servizio) -- EVITARE CASCATA!
);

CREATE TABLE datore (
   ref_utente INT UNSIGNED NOT NULL,
   sezione TINYINT UNSIGNED NOT NULL,
   PRIMARY KEY (ref_utente),
   FOREIGN KEY (ref_utente) REFERENCES utente(matricola) ON DELETE CASCADE
);

ALTER TABLE datore
   ADD CONSTRAINT sezione CHECK (sezione BETWEEN 1 AND 1);

DELIMITER //
CREATE TRIGGER esclusivaDipendente
BEFORE INSERT
ON dipendente
FOR EACH ROW
BEGIN
   IF NEW.ref_utente IN (SELECT datore.ref_utente FROM datore WHERE datore.ref_utente = NEW.ref_utente) THEN
      SIGNAL SQLSTATE '50001' SET MESSAGE_TEXT = 'EsclusivaDipendenteError';
   END IF;
END; //
DELIMITER ;

DELIMITER //
CREATE TRIGGER esclusivaDatore
BEFORE INSERT
ON datore
FOR EACH ROW
BEGIN
   IF NEW.ref_utente IN (SELECT dipendente.ref_utente FROM dipendente WHERE dipendente.ref_utente = NEW.ref_utente) THEN
      SIGNAL SQLSTATE '50001' SET MESSAGE_TEXT = 'EsclusivaDatoreError';
   END IF;
END; //
DELIMITER ;

DELIMITER //
CREATE TRIGGER totaleDipendenteInserimento
AFTER INSERT
ON dipendente
FOR EACH ROW
BEGIN
   UPDATE utente SET utente.stato_utente = 1 WHERE utente.matricola = NEW.ref_utente;
END; //
DELIMITER ;

DELIMITER //
CREATE TRIGGER totaleDatoreInserimento
AFTER INSERT
ON datore
FOR EACH ROW
BEGIN
   UPDATE utente SET utente.stato_utente = 1 WHERE utente.matricola = NEW.ref_utente;
END; //
DELIMITER ;

DELIMITER //
CREATE TRIGGER totaleDipendenteCancellazione
AFTER DELETE
ON dipendente
FOR EACH ROW
BEGIN
   DELETE FROM utente WHERE utente.matricola = OLD.ref_utente;
   -- In futuro potrebbe diventare:
   -- UPDATE utente SET utente.stato_utente = 3 WHERE utente.matricola = OLD.ref_utente;
   -- se si vogliono mantenere le credenziali dei dipendenti passati per permettere loro di accedere ad alcuni dati.
END; //
DELIMITER ;

DELIMITER //
CREATE TRIGGER totaleDatoreCancellazione
AFTER DELETE
ON datore
FOR EACH ROW
BEGIN
   DELETE FROM utente WHERE utente.matricola = OLD.ref_utente;
   -- In futuro potrebbe diventare:
   -- UPDATE utente SET utente.stato_utente = 3 WHERE utente.matricola = OLD.ref_utente;
   -- se si vogliono mantenere le credenziali dei datori passati per permettere loro di accedere ad alcuni dati.
END; //
DELIMITER ;

CREATE TABLE giorni_indisponibili (
   ref_datore INT UNSIGNED NOT NULL,
   giorno_indisponibile DATE NOT NULL,
   PRIMARY KEY (ref_datore, giorno_indisponibile),
   FOREIGN KEY (ref_datore) REFERENCES datore(ref_utente) ON DELETE CASCADE
);

CREATE TABLE progetto (
   cod_progetto INT UNSIGNED NOT NULL AUTO_INCREMENT,
   descrizione VARCHAR(400) NOT NULL,
   stato_progetto TINYINT UNSIGNED NOT NULL DEFAULT 0,
   PRIMARY KEY (cod_progetto)
);

ALTER TABLE progetto
   ADD CONSTRAINT stato_progetto CHECK (stato_progetto BETWEEN 0 AND 2);

CREATE TABLE partecipa (
   ref_dipendente INT UNSIGNED NOT NULL,
   ref_progetto INT UNSIGNED NOT NULL,
   PRIMARY KEY (ref_dipendente, ref_progetto),
   FOREIGN KEY (ref_dipendente) REFERENCES dipendente(ref_utente) ON DELETE CASCADE,
   FOREIGN KEY (ref_progetto) REFERENCES progetto(cod_progetto) ON DELETE CASCADE
);

CREATE TABLE infrastruttura (
   cod_infrastruttura INT UNSIGNED NOT NULL AUTO_INCREMENT,
   nome VARCHAR(50) NOT NULL,
   citta VARCHAR(50) NOT NULL,
   PRIMARY KEY (cod_infrastruttura)
);

CREATE TABLE afferisce (
   ref_progetto INT UNSIGNED NOT NULL,
   ref_infrastruttura INT UNSIGNED NOT NULL,
   PRIMARY KEY (ref_progetto, ref_infrastruttura),
   FOREIGN KEY (ref_progetto) REFERENCES progetto(cod_progetto) ON DELETE CASCADE,
   FOREIGN KEY (ref_infrastruttura) REFERENCES infrastruttura(cod_infrastruttura) ON DELETE CASCADE
);

CREATE TABLE segnalazione (
   cod_segnalazione INT UNSIGNED NOT NULL AUTO_INCREMENT,
   domanda VARCHAR(400) NOT NULL,
   risposta VARCHAR(400) DEFAULT NULL,
   categoria TINYINT UNSIGNED NOT NULL,
   PRIMARY KEY (cod_segnalazione)
);

ALTER TABLE segnalazione
   ADD CONSTRAINT categoria CHECK (categoria BETWEEN 1 AND 4);

CREATE TABLE riceve (
   ref_segnalazione INT UNSIGNED NOT NULL,
   ref_dipendente INT UNSIGNED NOT NULL,
   PRIMARY KEY (ref_dipendente, ref_segnalazione),
   FOREIGN KEY (ref_dipendente) REFERENCES dipendente(ref_utente) ON DELETE CASCADE,
   FOREIGN KEY (ref_segnalazione) REFERENCES segnalazione(cod_segnalazione) ON DELETE CASCADE
);

CREATE TABLE recensione (
   ref_segnalazione INT UNSIGNED NOT NULL,
   testo VARCHAR(400) NOT NULL,
   punteggio TINYINT UNSIGNED NOT NULL,
   PRIMARY KEY (ref_segnalazione),
   FOREIGN KEY (ref_segnalazione) REFERENCES segnalazione(cod_segnalazione) ON DELETE CASCADE
);

ALTER TABLE recensione
   ADD CONSTRAINT punteggio CHECK (punteggio BETWEEN 1 AND 5);

CREATE TABLE stipendio (
   ref_dipendente INT UNSIGNED NOT NULL,
   data_accredito DATE NOT NULL,
   stipendio_mensile DOUBLE NOT NULL,
   PRIMARY KEY (data_accredito, ref_dipendente),
   FOREIGN KEY (ref_dipendente) REFERENCES dipendente(ref_utente) ON DELETE CASCADE
);

ALTER TABLE stipendio
   ADD CONSTRAINT stipendio_mensile_positivo CHECK (stipendio_mensile >= 0.0);

CREATE TABLE turno_previsto (
   ref_dipendente INT UNSIGNED NOT NULL,
   cod_turno VARCHAR(12) NOT NULL,
   data_turno DATE NOT NULL,
   ora_inizio TIME NOT NULL,
   ora_fine TIME NOT NULL,
   PRIMARY KEY (ref_dipendente, cod_turno),
   FOREIGN KEY (ref_dipendente) REFERENCES dipendente(ref_utente) ON DELETE CASCADE
);

ALTER TABLE turno_previsto
   
   ADD CONSTRAINT cod_turno CHECK (LENGTH(cod_turno) = 12 AND cod_turno REGEXP '^[0-9]+$');

DELIMITER //
CREATE TRIGGER generaCodiceTurno
BEFORE INSERT
ON turno_previsto
FOR EACH ROW
BEGIN
   SET NEW.cod_turno = CONCAT(YEAR(NEW.data_turno), LPAD(MONTH(NEW.data_turno), 2, 0), LPAD(DAY(NEW.data_turno), 2, 0), LPAD(HOUR(NEW.ora_inizio), 2, 0), LPAD(HOUR(NEW.ora_fine), 2, 0));
END; //
DELIMITER ;

CREATE TABLE turno_effettivo (
   ref_dipendente INT UNSIGNED NOT NULL,
   ref_turno VARCHAR(12) NOT NULL,
   stato_entrata TINYINT UNSIGNED NOT NULL,
   stato_uscita TINYINT UNSIGNED DEFAULT NULL,
   ora_inizio_effettiva TIME NOT NULL,
   ora_fine_effettiva TIME DEFAULT NULL,
   stipendio_parziale DOUBLE DEFAULT NULL,
   PRIMARY KEY (ref_dipendente, ref_turno),
   FOREIGN KEY (ref_dipendente, ref_turno) REFERENCES turno_previsto(ref_dipendente, cod_turno) ON DELETE CASCADE
);

ALTER TABLE turno_effettivo
   ADD CONSTRAINT stato_entrata CHECK (stato_entrata BETWEEN 0 AND 2),
   ADD CONSTRAINT stato_uscita CHECK (stato_uscita BETWEEN 0 AND 1),
   ADD CONSTRAINT stipendio_parziale_positivo CHECK (stipendio_parziale >= 0.0);
   

CREATE TABLE variazione (
   ref_dipendente_da_sostituire INT UNSIGNED NOT NULL,
   ref_turno_da_sostituire VARCHAR(12) NOT NULL,
   ref_dipendente_sostituto INT UNSIGNED DEFAULT NULL,
   ref_turno_sostituto VARCHAR(12) DEFAULT NULL,
   ora_inizio_variazione TIME NOT NULL,
   ora_fine_variazione TIME NOT NULL,
   motivo TINYINT UNSIGNED NOT NULL,
   dettagli_testo VARCHAR(200) NOT NULL,
   PRIMARY KEY (ref_dipendente_da_sostituire, ref_turno_da_sostituire),
   FOREIGN KEY (ref_dipendente_da_sostituire, ref_turno_da_sostituire) REFERENCES turno_previsto(ref_dipendente, cod_turno) ON DELETE CASCADE,
   FOREIGN KEY (ref_dipendente_sostituto, ref_turno_sostituto) REFERENCES turno_previsto(ref_dipendente, cod_turno) ON DELETE CASCADE
);

ALTER TABLE variazione
   ADD CONSTRAINT sostituto CHECK (ref_dipendente_da_sostituire <> ref_dipendente_sostituto),
   ADD CONSTRAINT motivo CHECK (motivo BETWEEN 1 AND 5);

INSERT INTO servizio (cod_servizio, nome_servizio, stipendio_base) VALUES (1, "Gestione Infrastrutture", 10.0);
INSERT INTO servizio (cod_servizio, nome_servizio, stipendio_base) VALUES (2, "Gestione Progetti", 9.0);
INSERT INTO servizio (cod_servizio, nome_servizio, stipendio_base) VALUES (3, "Gestione Segnalazioni", 8.0);
INSERT INTO servizio (cod_servizio, nome_servizio, stipendio_base) VALUES (4, "Gestione Recensioni", 7.0);

INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Gabriele', 'Rossonno', 'falso1@gmail.com', 'falso2', 'IT42B4565417532589575389532'); -- 1111
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Alighiero', 'Rossi', 'rossi.alighiero@mailfalsa.com', 'zlvqxlir', 'IT33V1408876082506074685968'); -- 1112
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Galeazzo', 'Verdi', 'verdi.galeazzo@mailfalsa.com', 'qzkzyres', 'IT47J5493540029889178158366'); -- 1113
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Guglielmo', 'Neri', 'neri.guglielmo@mailfalsa.com', 'hpvhtpaa', 'IT15P7007364616089191352932'); -- 1114
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Arnaldo', 'Bianchi', 'bianchi.arnaldo@mailfalsa.com', 'nhnwjrys', 'IT67P2410277968989259699515'); -- 1115
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Procopio', 'Gialli', 'gialli.procopio@mailfalsa.com', 'ibnqfvcs', 'IT99F4548716779749977904540'); -- 1116
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Gualtiero', 'Marroni', 'marroni.gualtiero@mailfalsa.com', 'idklwibz', 'IT53E5098333694372592506558'); -- 1117
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Flavia', 'Santa', 'santa.flavia@mailfalsa.com', 'ngfdfhjh', 'IT31S6338346457654763530279'); -- 1118
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Nicola', 'Tonnara', 'tonnara.nicola@mailfalsa.com', 'awfthuol', 'IT52G5095789785659867565559'); -- 1119
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Felice', 'Campo', 'campo.felice@mailfalsa.com', 'iyjytjfb', 'IT13Z8671239569454611616615'); -- 1120
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Agata', 'Di Militello', 'dimilitello.agata@mailfalsa.com', 'kngybfvd', 'IT67G1196814186168176613655'); -- 1121
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Bianca', 'Scheda', 'scheda.bianca@mailfalsa.com', 'neeqetmu', 'IT25P9338900684072511116774'); -- 1122
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Bianca', 'Farina', 'farina.bianca@mailfalsa.com', 'xuosqaaq', 'IT97T1364848843662735590135'); -- 1123
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Margherita', 'Pizza', 'pizza.margherita@mailfalsa.com', 'amnvgazb', 'IT22Y3683257124399073980376'); -- 1124
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Alfredo', 'Dal Caldo', 'dalcaldo.alfredo@mailfalsa.com', 'zgwrdpfn', 'IT13B1547602272131192708646'); -- 1125
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Smeralda', 'Costa', 'costa.smeralda@mailfalsa.com', 'fbrmxjzx', 'IT36R5507592280793926521317'); -- 1126
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Rosa', 'Foglio', 'foglio.rosa@mailfalsa.com', 'lbgxrcuq', 'IT11H2918873014253808772580'); -- 1127
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Pino', 'Silvestre', 'silvestre.pino@mailfalsa.com', 'cgoqehvk', 'IT38G6350590568288037633084'); -- 1128
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Assunto', 'Licenziato', 'licenziato.assunto@mailfalsa.com', 'yzoupnhh', 'IT97E6848515340107857370379'); -- 1129
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Assunta', 'Manno', 'manno.assunta@mailfalsa.com', 'zojlgozd', 'IT50W4079758645976306376373'); -- 1130
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Bianco', 'Monte', 'monte.bianco@mailfalsa.com', 'sgxnmtjo', 'IT97B5596325213027810540761'); -- 1131
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Rosa', 'Monte', 'monte.rosa@mailfalsa.com', 'fzbxabgn', 'IT56I1789746455073425691357'); -- 1132
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Pino', 'Abete', 'abete.pino@mailfalsa.com', 'wkvzucst', 'IT31S6338343136873510250279'); -- 1133
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Concetto', 'Strano', 'strano.concetto@mailfalsa.com', 'jujipbhp', 'IT38E6795393988756376343020'); -- 1134
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Massimo', 'Ingegno', 'ingegno.massimo@mailfalsa.com', 'vgoeiosc', 'IT31V9858778876893951144118'); -- 1135
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Daria', 'Camera', 'camera.daria@mailfalsa.com', 'uvtgnnbj', 'IT94Y1114747287981264984217'); -- 1136
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Massimo', 'Voltaggio', 'voltaggio.massimo@mailfalsa.com', 'rmyjbwtp', 'IT67T5522196276613662498986'); -- 1137
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Guido', 'Piano', 'piano.guido@mailfalsa.com', 'lnjiiahp', 'IT13Z8675694296599937850513'); -- 1138
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Guido', 'La Barca', 'labarca.guido@mailfalsa.com', 'eiypzzvk', 'IT21B3687291276179055441898'); -- 1139
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Remo', 'La Barca', 'labarca.remo@mailfalsa.com', 'psycfbue', 'IT79F6236832455471342267814'); -- 1140
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Ester', 'Poli', 'poli.ester@mailfalsa.com', 'vsodjkpo', 'IT87L6181406873171551708265'); -- 1141
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Nunzio', 'Ciri', 'ciri.nunzio@mailfalsa.com', 'occwowxl', 'IT89T5569819822618031537283'); -- 1142
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Dina', 'Lampa', 'lampa.dina@mailfalsa.com', 'idkeovyc', 'IT68Q1196810039404454417013'); -- 1143
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Dario', 'Lampa', 'lampa.dario@mailfalsa.com', 'fsicerzd', 'IT57Y8845577336873252554726'); -- 1144
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Alberto', 'Lupo', 'lupo.alberto@mailfalsa.com', 'jseevmfx', 'IT80R7033614052794434958420'); -- 1145
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Illuminato', 'Corso', 'corso.illuminato@mailfalsa.com', 'mivxqukc', 'IT51P5776639391134246873828'); -- 1146
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Serena', 'Alba', 'alba.serena@mailfalsa.com', 'eywiovfi', 'IT42B4587567126255861389532'); -- 1147
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Stefano', 'Di Camastra', 'dicamastra.stefano@mailfalsa.com', 'wlmzwtud', 'IT56H2870678053590049570256'); -- 1148
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Orlando', 'Capo', 'capo.orlando@mailfalsa.com', 'qzgapufd', 'IT12Y3807886854015370395286'); -- 1149
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Piero', 'Patti', 'patti.piero@mailfalsa.com', 'ivsjolpe', 'IT89P7225769086040155843456'); -- 1150
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Franco', 'Bollo', 'bollo.franco@mailfalsa.com', 'hgbmccbi', 'IT71D1433084748382300700927'); -- 1151
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Nella', 'Cocci', 'cocci.nella@mailfalsa.com', 'hqkzpvsw', 'IT23G7363196432078142709411'); -- 1152
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Lino', 'Sasso', 'sasso.lino@mailfalsa.com', 'cnafarru', 'IT56L8901640117299156875224'); -- 1153
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Serena', 'Alba', 'alba.serena2@mailfalsa.com', 'tcaaskbn', 'IT64S7696310399863085161729'); -- 1154 (Omonimo)
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Lina', 'Carto', 'carto.lina@mailfalsa.com', 'ignyrzdd', 'IT46N2241447786507308793321'); -- 1155
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Gabriele', 'Bova', 'falso3@gmail.com', 'falso4', 'IT53C7231600240575241698467'); -- 1156
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Riccardo', 'Carini', 'falso9@gmail.com', 'ciminodorme87', 'IT38P2625706374730167907286'); -- 1157
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Giuseppe', 'Cimino', 'ciminchio@gmailfalsa.com', 'ajejo', 'IT49B5501424348943208054246'); -- 1158
INSERT INTO utente (nome, cognome, email, pass, iban) VALUES ('Nicolo\'', 'La Rosa Mazza', 'nicololarosamazza2@gmail.com', 'qwerty00', 'IT28F6768891534861600341873'); -- 1159

UPDATE utente SET utente.stato_pass = 1 WHERE utente.matricola BETWEEN 1157 AND 1159;

INSERT INTO datore (ref_utente, sezione) VALUES (1111, 1);

INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1112, 1);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1113, 2);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1114, 3);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1115, 4);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1116, 1);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1117, 2);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1118, 3);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1119, 4);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1120, 1);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1121, 2);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1122, 3);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1123, 4);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1124, 1);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1125, 2);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1126, 3);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1127, 4);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1128, 1);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1129, 2);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1130, 3);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1131, 4);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1132, 1);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1133, 2);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1134, 3);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1135, 4);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1136, 1);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1137, 2);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1138, 3);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1139, 4);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1140, 1);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1141, 2);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1142, 3);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1143, 4);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1144, 1);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1145, 2);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1146, 3);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1147, 4);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1148, 1);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1149, 2);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1150, 3);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1151, 4);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1152, 1);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1153, 2);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1154, 3);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1155, 4);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1156, 1);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1157, 2);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1158, 3);
INSERT INTO dipendente (ref_utente, ref_servizio) VALUES (1159, 4);

INSERT INTO infrastruttura (nome, citta)
VALUES ('Infrastruttura 1', 'Roma'),
       ('Infrastruttura 2', 'Milano'),
       ('Infrastruttura 3', 'Napoli'),
       ('Infrastruttura 4', 'Torino'),
       ('Infrastruttura 5', 'Firenze'),
       ('Infrastruttura 6', 'Bologna'),
       ('Infrastruttura 7', 'Genova'),
       ('Infrastruttura 8', 'Palermo'),
       ('Infrastruttura 9', 'Catania'),
       ('Infrastruttura 10', 'Bari'),
       ('Infrastruttura 11', 'Venezia'),
       ('Infrastruttura 12', 'Verona'),
       ('Infrastruttura 13', 'Messina'),
       ('Infrastruttura 14', 'Padova'),
       ('Infrastruttura 15', 'Trieste'),
       ('Infrastruttura 16', 'Trento'),
       ('Infrastruttura 17', 'Perugia'),
       ('Infrastruttura 18', 'Pescara'),
       ('Infrastruttura 19', 'Lecce'),
       ('Infrastruttura 20', 'Sassari');


INSERT INTO progetto ('descrizione', 'stato_progetto') VALUES ('Progetto 1', 2);
INSERT INTO progetto ('descrizione', 'stato_progetto') VALUES ('Progetto 2', 2);
INSERT INTO progetto ('descrizione', 'stato_progetto') VALUES ('Progetto 3', 2);
INSERT INTO progetto ('descrizione', 'stato_progetto') VALUES ('Progetto 4', 1);
INSERT INTO progetto ('descrizione', 'stato_progetto') VALUES ('Progetto 5', 0);
INSERT INTO progetto ('descrizione', 'stato_progetto') VALUES ('Progetto 6', 2);
INSERT INTO progetto ('descrizione', 'stato_progetto') VALUES ('Progetto 7', 1);
INSERT INTO progetto ('descrizione', 'stato_progetto') VALUES ('Progetto 8', 0);
INSERT INTO progetto ('descrizione', 'stato_progetto') VALUES ('Progetto 9', 0);
INSERT INTO progetto ('descrizione', 'stato_progetto') VALUES ('Progetto 10', 1);
INSERT INTO progetto ('descrizione', 'stato_progetto') VALUES ('Progetto 11', 1);
INSERT INTO progetto ('descrizione', 'stato_progetto') VALUES ('Progetto 12', 2);
INSERT INTO progetto ('descrizione', 'stato_progetto') VALUES ('Progetto 13', 2);
INSERT INTO progetto ('descrizione', 'stato_progetto') VALUES ('Progetto 14', 0);
INSERT INTO progetto ('descrizione', 'stato_progetto') VALUES ('Progetto 15', 0);
INSERT INTO progetto ('descrizione', 'stato_progetto') VALUES ('Progetto 16', 2);
INSERT INTO progetto ('descrizione', 'stato_progetto') VALUES ('Progetto 17', 2);
INSERT INTO progetto ('descrizione', 'stato_progetto') VALUES ('Progetto 18', 0);
INSERT INTO progetto ('descrizione', 'stato_progetto') VALUES ('Progetto 19', 1);
INSERT INTO progetto ('descrizione', 'stato_progetto') VALUES ('Progetto 20', 0);

INSERT INTO afferisce ('ref_progetto', 'ref_infrastruttura') VALUES (15, 1);
INSERT INTO afferisce ('ref_progetto', 'ref_infrastruttura') VALUES (8, 2);
INSERT INTO afferisce ('ref_progetto', 'ref_infrastruttura') VALUES (12, 2);
INSERT INTO afferisce ('ref_progetto', 'ref_infrastruttura') VALUES (1, 3);
INSERT INTO afferisce ('ref_progetto', 'ref_infrastruttura') VALUES (14, 4);
INSERT INTO afferisce ('ref_progetto', 'ref_infrastruttura') VALUES (12, 7);
INSERT INTO afferisce ('ref_progetto', 'ref_infrastruttura') VALUES (1, 8);
INSERT INTO afferisce ('ref_progetto', 'ref_infrastruttura') VALUES (11, 8);
INSERT INTO afferisce ('ref_progetto', 'ref_infrastruttura') VALUES (16, 11);
INSERT INTO afferisce ('ref_progetto', 'ref_infrastruttura') VALUES (9, 12);
INSERT INTO afferisce ('ref_progetto', 'ref_infrastruttura') VALUES (5, 13);
INSERT INTO afferisce ('ref_progetto', 'ref_infrastruttura') VALUES (19, 13);
INSERT INTO afferisce ('ref_progetto', 'ref_infrastruttura') VALUES (1, 17);
INSERT INTO afferisce ('ref_progetto', 'ref_infrastruttura') VALUES (11, 18);
INSERT INTO afferisce ('ref_progetto', 'ref_infrastruttura') VALUES (4, 19);

INSERT INTO segnalazione (domanda, risposta, categoria)
VALUES  ("Come si gestiscono i permessi degli utenti sulla piattaforma?", "I permessi degli utenti possono essere gestiti tramite l'apposita sezione del pannello di amministrazione.", 1),
        ("Come si effettua un backup dei dati aziendali?", "I backup possono essere effettuati utilizzando lo strumento di backup integrato nel sistema oppure attraverso software esterni.", 2),
        ("Come si gestiscono le fatture elettroniche?", "Le fatture elettroniche possono essere gestite tramite l'apposita sezione del sistema di gestione delle finanze aziendali o mediante software esterni specializzati.", 3),
        ("Come si risolvono i problemi di connettività della rete aziendale?", "I problemi di connettività possono essere risolti verificando lo stato dei dispositivi di rete, controllando la configurazione dei router e contattando il proprio provider di servizi internet.", 4),
        ("Come si gestiscono i progetti in corso?", "I progetti possono essere gestiti utilizzando un software di gestione dei progetti o attraverso una scheda di lavoro condivisa.", 1),
        ("Come si gestiscono le risorse umane?", "La gestione delle risorse umane può essere effettuata utilizzando un software di gestione delle risorse umane o attraverso un sistema di gestione manuale.", 2),
        ("Come si gestiscono i fornitori?", "I fornitori possono essere gestiti utilizzando un software di gestione degli acquisti o attraverso un sistema di gestione manuale.", 3),
        ("Come si gestiscono le finanze aziendali?", "Le finanze aziendali possono essere gestite utilizzando un software di contabilità o attraverso un sistema di gestione manuale.", 4);

INSERT INTO recensione (ref_segnalazione, testo, punteggio)
VALUES (1, "La risposta era esauriente", 4),
       (2, "La procedura descritta non era perfetta", 3),
       (3, "Le istruzioni di configurazione erano complesse", 2),
       (4, "Il significato dell'errore era poco chiaro", 1),
       (5, "La risposta era esauriente", 4),
       (6, "La procedura descritta non era perfetta", 3),
       (7, "Le istruzioni di configurazione erano complesse", 2),
       (8, "Il significato dell'errore era poco chiaro", 1);




