        ORG     $10                               ;Gruppe 3 - Patrick Kohan (286085
        BRA     START                               ;Prozessor springt zu 'START'
EINS    DC.L    30
ZWEI    DC.L    10
AUTO    DS.L    1
STRING  DC.B    'Praktikum-Rechnerarchitektur',0    ;erzeuge String
SUMME   DS.L    1                                   ;reserviere Speicher für die Summe
MITTEL  DS.L    1                                   ;reserviere Speicher für den Mittelwert
START   CLR.L   D1                                  ;Datenregister Stelle D1 und
        SUB.L   EINS,ZWEI
        MOVE.L  #EINS,A5
        ADD.B   #10,D6
        CLR.L   D2                                  ;Datenregister Stelle D2 ausnullen
        CLR.L   SUMME                               ;Speicher an der Stelle 'SUMME'
        CLR.L   MITTEL                              ;und der Stelle 'MITTEL' ausnullen
        MOVE.W  #STRING,A1                          ;Anfangsadresse von 'STRING' (Adresse von Buchstabe 'P') ins Adressregister 1 schreiben
LOOP    MOVE.B  (A1)+,D0                            ;Zahlenwerte der Buchstaben einzelnen ins Datenregister D0 schreiben
        CMP.B   #0,D0                               ;prüfen, ob Wert in D0 gleich der Endmakierung des Strings entspricht
        BEQ     LOOP2                               ;falls der Wert 0 im Datenregister Stell D0 steht, aus Schleife springen
        ADD.L   D0,SUMME                            ;addiere Datenregister Stelle D0 auf 'SUMME'
        ADD.B   #1,D1                               ;addiere 1 auf Datenregister Stelle D1 als Schleifenzähler
        BRA     LOOP                                ;Springe wieder an Anfang der Schleife
LOOP2   MOVE.L  SUMME,D0                            ;Summe der Werte des Strings (b52) in D0 schreiben
        MOVE.L  SUMME,D7
        DIVU    D1,D0                               ;teile D0 durch schleifenzähler D1, schreibe Ergebnis nach D0
        MOVE.L  D0,MITTEL                           ;Schreibe D0 in Mittel
        END