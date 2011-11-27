           ORG $2000
           BRA START
COUNT      DC.B 200
COUNT2     DC.B 100
KONST1     DC.B 1500
KONST2     DC.L 2000
SUMME      DS.L 1
ERGEB      DS.L 1
ANFANG    EQU $2500
START      MOVE.W #ANFANG,A0
           MOVE.W A0,D0
           MOVE.W KONST1,SUMME
           ADD.W  KONST2,SUMME
LOOP       MOVE.W SUMME,(A0)+
           ADD.W #1,SUMME
           SUB.W #1,COUNT
           CMP   #0,COUNT
           BNE LOOP
LOOP2      MOVE.W #ANFANG,A0
           
           END
