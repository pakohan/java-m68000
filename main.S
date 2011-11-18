      ORG    $1000          ;this is a command
      BRA    START
KONST DC.L   50
COUNT DC.L   10
ONE   DC.L   1
START MOVE.W KONST,D1
LOOP  ADD.L  1,D1
      SUB.L  1,COUNT
      CMP    COUNT,0
      BNE    LOOP
      MOVE.W D1,COUNT
      END