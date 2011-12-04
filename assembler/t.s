        ORG     $10
        BRA     START
VAL     DS.B    202
COUNT   DC.L    200
START   MOVE.B  #VAL,A0
LOOP    ADD.B   #1,D1
        MOVE.B  D1,(A0)+
        ADD.L   #1,D0
        CMP.L   COUNT,D0
        BNE     LOOP
        END
