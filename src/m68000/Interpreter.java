/*
 * @author Patrick "Tux" Kohan
 * @version 2011.04.19
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
    private static Program prog;

    static {
        prog = new Program();
        prog.setNext(prog);
        prog.setPrev(prog);

    }

    /**
     * Reads the source File and stores it in a linked list.
     *
     * @param sourcefile the sourcefile
     * @return the program
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static Program readSourceFile(final String sourcefile)
            throws IOException {
        LineNumberReader source =
                new LineNumberReader(new FileReader(sourcefile));
        String[] part = new String[MAXTOKENS];
        String line;


        while ((line = source.readLine()) != null) {
            part = line.split(";");
            addCommand(recognizeLine(part[0]));
        }

        while (prog.getNext().getBefehl().getPrefix() != Befehlssatz.HEAD) {
            prog = prog.getNext();
            switch (prog.getBefehl().getPrefix()) {
            case ORG:
                break;
            case BRA:
                break;
            case EQU :
                M68000.speicher.addSpeicher(prog.getMarker());
                break;
            case DC :
                M68000.speicher.addSpeicher(prog.getMarker(), prog.getArgumente().getPrefix());
                break;
            case DS :
                M68000.speicher.addSpeicher(prog.getMarker(), "0");
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
                break;
            default :
                System.err.println("Befehl nicht gefunden!"
            + prog.getBefehl().getPrefix());
            }
        }
        prog = prog.getNext();
        return prog;
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
                prog.setPrev(new Program(prog.getPrev(), prog,
                        befehlsfolge[0], "", ""));
                break;
            case 2:
                prog.setPrev(new Program(prog.getPrev(), prog,
                        befehlsfolge[0], befehlsfolge[1], ""));
                break;
            case MAXTOKENS:
                prog.setPrev(new Program(prog.getPrev(), prog,
                        befehlsfolge[1], befehlsfolge[2], befehlsfolge[0]));
                break;
            default:
        }
    }
}
