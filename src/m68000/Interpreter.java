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

import java.io.*;
import java.util.Scanner;

import m68000.Command.Befehlssatz;

/**
 * The Class Interpreter reads the code source file.
 */
public final class Interpreter {

    /** The Constant MAXTOKENS. */
    static final int MAXTOKENS = 3;
    /**
     * Instantiates a new interpreter. private! nobody wants interpreters!
     */
    private Interpreter() { }

    /** The prog is the linked list which represents the program. */
    private static LinkedList<LoC> prog;

    /**
     * Reads the source File and stores it in a linked list.
     *
     * @param sourcefile the sourcefile
     * @return the program
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static LinkedList<LoC> readSourceFile(final String sourcefile)
            throws IOException {
        FileReader file = new FileReader(sourcefile);
        LineNumberReader source = new LineNumberReader(file);
        String[] part;
        String line;
        prog = new LinkedList<LoC>(new LoC());
        prog.setNext(prog);
        prog.setPrev(prog);

        while ((line = source.readLine()) != null) {
            part = line.split(";");
            addCommand(recognizeLine(part[0]));
        }
        source.close();
        file.close();

        return prog;
    }

    /**
     * Linker.
     *
     * @param pro the pro
     * @return the rAM
     */
    public static RAM linker(final LinkedList<LoC> pro) {
        LinkedList<LoC> tmp = pro;
        RAM ram = new RAM();
        while (tmp.getNext().getItem().getCommand().getPrefix()
                != Befehlssatz.HEAD) {
            tmp = tmp.getNext();
            switch (tmp.getItem().getCommand().getPrefix()) {
            case ORG:
                break;
            case BRA:
                break;
            case EQU :
                ram.addSpeicher(tmp.getItem().getMarker());
                break;
            case DC :
                ram.addSpeicher(tmp.getItem().getMarker(),
                        tmp.getItem().getArgument().getPrefix());
                break;
            case DS :
                ram.addSpeicher(tmp.getItem().getMarker(), "0");
                break;
            case CLR :
                break;
            case MOVE :
                break;
            case ADD :
                break;
            case HEAD :
                break;
            case END :
            case MUL :
            case SUB :
            case DIV :
                break;
            default :
                System.err.println("Befehl nicht gefunden!"
            + tmp.getItem().getCommand().getPrefix());
            }
        }
        return ram;
    }
    /**
     * Recognize the line of source code. The comments have to be deleted
     * before!
     *
     * @param str the line of source code
     * @return the string[]
     */
    public static String[] recognizeLine(final String str) {
        Scanner scan = new Scanner(str);
        String[] parts;
        String[] parts2 = new String[MAXTOKENS];
        int j = 0;
        while (scan.hasNext()) {
            parts2[j] = scan.next();
            j++;
        }
        parts = new String[j];
        for (int i = 0; i < j; i++) {
            parts[i] = parts2[i];
        }
        return parts;
    }

    /**
     * Adds the command to the "compiled" program.
     *
     * @param befehlsfolge the befehlsfolge
     */
    public static void addCommand(final String[] befehlsfolge) {
        switch (befehlsfolge.length) {
            case 1:
                prog.add(new LoC(befehlsfolge[0], "", ""));
                break;
            case 2:
                prog.add(new LoC(befehlsfolge[0], befehlsfolge[1], ""));
                break;
            case MAXTOKENS:
                prog.add(new LoC(befehlsfolge[1], befehlsfolge[2],
                        befehlsfolge[0]));
                break;
            default:
        }
    }
}
