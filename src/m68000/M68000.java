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
 * The Class M68000 contains the main method. It checks if there is a command
 * line argument given and initializes a Program with the program
 * instructions and the Ram. Then it creates a new Processor and gives the
 * Program object to this.
 * Check the video below if you are bored with this JavaDoc.
 * <iframe width="560"
 * height="315"
 * src="http://www.youtube.com/embed/b-Cr0EWwaTk"
 * frameborder="0"
 * allowfullscreen></iframe>
 */
public final class M68000 {

    /**
     * Constructer has to be private, just run the main method.
     */
    private M68000() { }

    /**
     * The main method.
     *
     * @param args the assembler source file as first argument.
     * @throws IOException Signals if the file can't be read.
     */
    public static void main(final String[] args) throws IOException {

        if (args.length < 1) {
            System.err.println("Fehler: Assemblerdatei als Argument angeben!");
            return;
        }

        Program prog = new Program(args[0]);

        Processor core1 = new Processor(prog);

        core1.run();

        System.out.println(prog.getSpeicher());

    }
}
