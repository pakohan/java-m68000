/*
 * @author Patrick "Tux" Kohan
 * @version 2011.04.19
 */
package m68000;

/**
 * The Class Processor simulates a m68000 processor.
 */
public class Processor {

    /** The Constant REGSIZE defines the size of the registers. */
    static final int REGSIZE = 8;
    /** The datenregister. */
    private int[] datenregister = new int[REGSIZE];

    /** The exe. */
    private Program exe;

    /** The finished. */
    private boolean finished = false;

    /**
     * Instantiates a new processor.
     *
     * @param programm the programm
     */
    public Processor(final Program programm) {
        this.exe = programm;
    }

    /**
     * Step.
     *
     * @param com the com
     */
    public final void step(final Program com) {
        Program command = com.clone();
        switch (command.getBefehl().getPrefix()) {
        case ORG:
            break;
        case BRA:
            command = command.jump();
            break;
        case EQU :
            break;
        case DC :
            break;
        case DS :
            break;
        case CLR :
            if (command.getArgumente().getArg().equals("D1")) {
                this.datenregister[1] = 0;
            }
            break;
        case  MOVE :
            if (command.getArgumente().getPostfix().equals("D1")) {
                this.datenregister[1] = M68000.speicher
                        .getData(command.getArgumente().getArg());
            } else if (command.getArgumente().getArg().equals("D1")) {
                M68000.speicher.set(command.getArgumente().getPostfix(),
                        this.datenregister[1]);
            }
            break;
        case ADD :
            if (command.getArgumente().getPostfix().equals("D1")) {
                this.datenregister[1] = this.datenregister[1]
                        + M68000.speicher
                        .getData(command.getArgumente().getArg());
            }
            break;
        case HEAD :
            break;
        case END :
            this.finished = true;
            break;
        default :
            System.err.println("Befehl nicht gefunden!");
        }
    }

    /**
     * Does a single step. This means that the the Processor will set the
     * execution pointer to the next element and executes it.
     */
    public final void step() {
        exe = exe.getNext();
        step(exe);
    }

    /**
     * Checks if the program is finished.
     *
     * @return true, if it is finished
     */
    public final boolean isfinished() {
        return finished;
    }
}
