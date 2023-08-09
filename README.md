# Cos'è AgencyCode?

• AgencyCode nasce come progetto universitario per il corso 'Ingegneria del Software' tenuto dall'università degli studi di Palermo.

• È un software Java based per personal computer, in grado di gestire (in modo quasi del tutto autonomo) gli aspetti della vita aziendale di un dipendente (es: gestione e generazione dei turni, generazione e successivo accredito degli stipendi, richiesta di ferie e successiva sostituzione del dipendente in questione, ingressi e uscite, etc.)

• È compatibile con i seguenti sistemi operativi:
- Windows
- MacOS
- Linux

• Utilizza uno stile minimal poiché si tratta di un contesto lavorativo; ciò lo rende semplice, intuitivo e rapido. Un sistema di gestione senza fronzoli.

# Membri
La Rosa Mazza Nicolò, 
Gabriele Bova, 
Giuseppe Cimino, 
Riccardo Carini.

# Tema
Si richiede di progettare e sviluppare un software di gestione della sezione impiegati di un’azienda di servizi al cittadino. Il sistema deve gestire tutte le informazioni riguardanti gli impiegati dei quali è necessario gestire gli stipendi e i turni. Gli impiegati dell’azienda hanno mansioni diverse e possono essere in regime di part-time o full-time. Gli impiegati full-time lavorano in media 36 ore a settimana con turni di 8 ore mentre quelli part-time lavorano 18 ore a settimana con turni o di 4 o di 6 ore.
L’azienda fornisce quattro servizi diversi. Ogni servizio ha un ruolo assegnato e l’impiegato che ricopre quel ruolo percepisce una gratifica sullo stipendio in percentuale alle ore di lavoro svolte per quel servizio. Ogni servizio ha un livello di priorità su un altro ed il software dovrà garantire che ogni giorno il servizio a priorità più alta sia svolto per un tempo maggiore rispetto agli altri. 
Il software deve gestire i turni dei dipendenti in modo da rispettare le richieste di periodi di astensione dal lavoro per ogni dipendente. Fanno parte dei periodi di astensione: le ferie, la 
malattia, il congedo parentale e lo sciopero. Il datore di lavoro può scegliere, in base a particolari periodi di incremento di attività, di non concedere ferie ai dipendenti. 
Il software deve predisporre una proposta di turnazione con cadenza trimestrale, deve gestire la presa di servizio ogni giorno e deve gestire le comunicazioni di variazione sulla presenza. Per esempio, se un impiegato comunica di essere impossibilitato a recarsi al lavoro per malattia o 
problemi di altro tipo, il sistema deve gestire la sua assenza chiamando un altro impiegato (compatibilmente con i turni già fatti). Devono essere tenute in considerazione i vincoli sui turni che possono far riferimento a norme di legge in funzione del settore in cui opera l’azienda.
In caso di assenza di personale in numero tale da non poter garantire tutti i servizi, il servizio a 
priorità più bassa deve essere chiuso.
Gli impiegati possono svolgere i servizi in base al loro ruolo. Sono previste 4 figure diverse di 
impiegato ed ognuna può svolgere il servizio assegnato al suo livello e quelli di livello più basso. 
Per la rilevazione della presenza sono previste un paio di postazioni all’ingresso dell’azienda nelle 
quali l’impiegato, entro 10 minuti dall’inizio dell’orario lavorativo, inserisce nome, cognome e matricola. Il sistema in automatico rileva l’orario e, se l’impiegato risulta effettivamente in turno, abilita l’ingresso. Lo stesso vale per l’uscita. È permessa la rilevazione di presenza da remoto solo in caso di ritardo o per giustificati motivi.
Il software deve permettere agli impiegati di consultare la propria situazione lavorativa e stipendiale, per esempio consultare i turni, comunicare ritardi o assenze etc.
Ogni mese il software procede al calcolo degli stipendi in funzione del lavoro svolto; si devono tenere in considerazione i turni ordinari, gli straordinari, le ferie e tutto quanto contribuisce al calcolo dello stipendio.
All’atto dell’assunzione l’impiegato viene inserito nel sistema con tutti i dati utili per garantire una corretta assegnazione dei turni di lavoro e degli stipendi.

Si ricorda che da specifiche NON è richiesta un’applicazione web o una particolare architettura, gli studenti dovranno condurre la fase di analisi senza far riferimento ad una particolare architettura né ipotizzare soluzioni specifiche.

In questa repository è dunque possibile trovare:
- Documentazione (RAD, SDD, ODD)
- Codice sorgente
- Mockups
- File per la popolazione del database

Grazie dell'attenzione.
