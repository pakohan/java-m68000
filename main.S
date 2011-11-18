      ORG    $1000          ;this is a command
      BRA    START
ADR1  EQU    $5
ADR2  EQU    $7
KONST DC.W   50
KONS  DC.W   20
ERG   DS.L   1
START CLR.L  D1
      MOVE.W KONST,D1
      ADD.L  KONS,D1
      ADD.L  ADR1,D1
      SUB.L  ADR2,D1
TEST  MOVE.L D1,ERG
END