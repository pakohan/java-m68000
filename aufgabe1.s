		ORG		$3000
		BRA		START
STRING	DC.B	'Praktikum-Rechnerarchitektur',0	;erzeuge String
SUMME	DS.L	1									;reserviere Speicher für die Summe
MITTEL	DS.L	1									;reserviere Speicher für den Mittelwert

START	CLR.L	D1
		CLR.L	D2
		CLR.L	D3
		CLR.L	D4
		CLR.L	SUMME
		CLR.L	MITTEL
		MOVE.W	#STRING,A1

LOOP	MOVE.B	(A1)+,D0
		CMP.B	#0,D0
		BEQ		LOOP2
		ADD.L	D0,SUMME
		ADD.B	#1,D1
		BRA		LOOP
LOOP2	MOVE.L	SUMME,D0
		DIVU	D1,D0
		MOVE.L	D0,MITTEL
		END
