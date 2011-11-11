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
     * The main processing unit. It searches the given program line for known
     * commands and executes it.
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
            clr(command.getArgumente());
            break;
        case  MOVE :
            move(command.getArgumente());
            break;
        case ADD :
            add(command.getArgumente());
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
     * Overwrites the data stored in argument two with the data stored in
     * argument one.
     *
     * @param args the args
     */
    public final void move(final Arguments args) {
        int x = getData(args.getPrefix());
        setData(args.getPostfix(), x);
    }

    /**
     * sets the data stored in the argument to 0. If there are two arguments,
     * only the first will be treated.
     *
     * @param args the args
     */
    public final void clr(final Arguments args) {
        setData(args.getPrefix(), 0);
    }

    /**
     * Adds up the data stored in the first argument to the data in the second
     * one.
     *
     * @param args the args
     */
    public final void add(final Arguments args) {
        int x = getData(args.getPrefix());
        x = x + getData(args.getPostfix());
        setData(args.getPostfix(), x);
    }

    /**
     * Searches for the place of data with the string dataPlace and overwrites
     * it with the given x.
     *
     * @param dataPlace the data place
     * @param x the x
     */
    public final void setData(final String dataPlace, final int x) {
        if (dataPlace.equals("D1")) {
            datenregister[1] = x;
        } else {
            M68000.speicher.set(dataPlace, x);
        }
    }

    /**
     * Searches for the place of data with the string dataPlace and returns it.
     *
     * @param dataPlace the data place
     * @return the data
     */
    public final int getData(final String dataPlace) {
        if (dataPlace.equals("D1")) {
            return datenregister[1];
        } else {
            return M68000.speicher.getData(dataPlace);
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
