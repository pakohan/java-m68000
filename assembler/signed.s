        ORG     $10
        BRA     START
T       DC.L    14
TT      DC.W    3
START   divs    TT,T
        END
