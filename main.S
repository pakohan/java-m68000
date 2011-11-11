      ORG    $1000          ;this is a command
      BRA    START
ADR1  EQU    $2000
ADR2  EQU    $3000
KONST DC.W   50
ERG   DS.L   1
START CLR.L  D1
      MOVE.W ADR1,D1
      ADD.L  ADR2,D1
      ADD.L  KONST,D1
TEST  MOVE.L D1,ERG
END