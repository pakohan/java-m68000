      ORG    $1000          ;this is a command
      BRA    START
ADR1  EQU    $5
COUNT EQU    $7
ZERO  DC.W   0
KONST DC.W   50
ERG   DS.L   1
START CLR.L  D1
      MOVE.W KONST,D1
LOOP  ADD.L  ADR1,D1
      SUB.L  ADR1,COUNT
      CMP    COUNT,ZERO
      BNE    LOOP
      MOVE.L D1,ERG
      END