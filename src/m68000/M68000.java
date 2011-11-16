/*
 * Copyright (C) Patrick Kohan 2011 <patrick.kohan@googlemail.com>
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
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
    	if (args.length < 1) {
    		System.err.println("Fehler: Assemblerdatei als Argument angeben!");
    		return;
    	}
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
