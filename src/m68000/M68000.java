/*
 * @author Patrick "Tux" Kohan
 * @version 2011.04.19
 */
package m68000;

import java.io.IOException;

/**
 * The Class M68000.
 */
public final class M68000 {

    /**
     * Instantiates a new m68000.
     */
    private M68000() { }

    /** The speicher. */
    public static RAM speicher = new RAM();

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void main(final String[] args) throws IOException {
        Program prog = Interpreter.readSourceFile(args[0]);

        Processor core1 = new Processor(prog);

        while (!core1.isfinished()) {
            core1.step();
        }
        while (!speicher.getNext().getStelle().equals("HEAD")) {
        	speicher.stepnext();
        	System.out.println(speicher);
        }
    }
}
