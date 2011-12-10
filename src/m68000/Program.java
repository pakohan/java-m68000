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

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Scanner;

import m68000.Argument.Arg;
import m68000.Argument.ArgType;

/**
 * The Class Program reads the code source file and initializes a new RAM
 * Object. The resulting RAM and LinkedList<LoC> will be stored in this Program
 * object.
 */
public final class Program {

    /**
     * It describes, of how many different items an assembler code line can
     * consist. (label, command and argument)
     */
    private static final int MAX_TOKENS = 3;

    /**
     * The prog value is the linked list which represents the program.
     */
    private LinkedList<CodeLine> prog;

    private int counter = 0;

    private RAM memory;

    private int rampointer;

    /**
     * Instantiates a new Program.
     *
     * @param arg
     *            source file
     * @throws IOException
     *             Signals if the file can't be read.
     */
    public Program(final String arg) throws IOException {
        this.prog = readSourceFile(arg);
        this.memory = new RAM();
        linker();
    }

    /**
     * Gets the program list.
     *
     * @return the prog
     */
    public LinkedList<CodeLine> getProg() {
        return prog;
    }

    public RAM getRAM() {
        return this.memory;
    }

    /**
     * Reads the source File and stores it in a linked list.
     *
     * @param sourcefile
     *            the sourcefile
     * @return the program stored in a linked list
     * @throws IOException
     *             Signals if the file can't be read.
     */
    private LinkedList<CodeLine> readSourceFile(final String sourcefile)
            throws IOException {
        FileReader file = new FileReader(sourcefile);
        LineNumberReader source = new LineNumberReader(file);
        String[] part;
        String line;
        this.prog = new LinkedList<CodeLine>(new CodeLine());

        while ((line = source.readLine()) != null) {
            part = line.split(";");
            addCommand(deleteComments(part[0]));
            counter++;
        }
        source.close();
        file.close();

        return prog;
    }

    /**
     * The Linker isn't a real linker. It just initializes the RAM, so every
     * memory adress used is known to the created RAM Object.
     *
     * @return the finished ram object
     */
    private void linker() {
        LinkedList<CodeLine> tmp = this.prog;
        Arg tmparg;
        rampointer = 0;
        for (int i = this.prog.getSize(); i > 0; --i) {
            tmp = tmp.getNext();
            tmparg = tmp.getItem().getArgument().getPrefix();
            switch (tmp.getItem().getCommand().getPrefix()) {
            case EQU:
                replaceSymbolicHexConstant(tmp.getItem().getLabel(),
                        tmparg.getValue());
                break;
            case DC:
                dc(tmp.getItem());
                break;
            case DS:
                replaceSymbolicHexConstant(tmp.getItem().getLabel(),
                        rampointer);
                rampointer += (tmparg.getValue() * tmp.getItem().getCommand()
                        .getPostfix().ordinal());
                break;
            case ORG:
                rampointer = tmparg.getValue();
                break;
            case BRA:
            case CLR:
            case MOVE:
            case ADD:
            case HEAD:
            case END:
            case MUL:
            case SUB:
            case DIVS:
            case CMP:
            case BNE:
            case BEQ:
            case DIVU:
            case SWAP:
                break;
            default:
                gtk.UI.printMessage("Command not found! "
                        + tmp.getItem().getCommand().getPrefix());
            }
            if ((rampointer % 2) != 0) {
                rampointer++;
            }
        }
    }

    private void dc(final CodeLine tmp) {
        StringBuilder tmpstr = new StringBuilder();
        tmpstr.append("$");
        tmpstr.append(rampointer);
        replaceSymbolicHexConstant(tmp.getLabel(), rampointer);

        if (tmp.getArgument().getPrefix().getType()
                == ArgType.CONST) {
            int x = tmp.getArgument().getPrefix().getValue();
            switch (tmp.getCommand().getPostfix()) {
            case B:
                this.memory.setByteInAddress(rampointer, (byte) x);
                rampointer += 1;
                break;
            case W:
                this.memory.setWordInAddress(rampointer, (short) x);
                rampointer += 2;
                break;
            case L:
                this.memory.setLongWordInAddress(rampointer, x);
                rampointer += 4;
                break;
            default:
                gtk.UI.printMessage("Fehler: falsche Kommandoendung!");
                return;
            }
        } else if (tmp.getArgument().getPrefix().getType()
                == ArgType.VALUEARRAY) {
            byte[] x = tmp.getArgument().getPrefix().getValuearray();
            for (int i = 0; i < x.length; i++) {
                this.memory.setByteInAddress(rampointer, x[i]);
                rampointer++;
            }
        }
        tmp.getArgument().replacePrefix(tmpstr.toString());
    }

    /**
     * Replace symbolic constant.
     *
     * @param str
     *            the str
     * @param newvalue
     *            the newvalue
     */
    private void replaceSymbolicHexConstant(final String str, final int x) {
        String newvalue = "$" + Integer.toHexString(x);
        LinkedList<CodeLine> tmp2 = this.prog;
        for (int i = this.prog.getSize(); i > 0; --i) {
            Argument tmparg = tmp2.getItem().getArgument();
            tmp2 = tmp2.getNext();
            if (tmparg.getPrefix().getOtherArg().equals(str)) {
                tmparg.replacePrefix(newvalue);
            } else if (tmparg.getPostfix().getOtherArg().equals(str)) {
                tmparg.replacePostfix(newvalue);
            }
        }
    }

    /**
     * Deletes the comments of the source code and returns the commands only.
     *
     * @param str
     *            code line
     * @return the string[]
     */
    private String[] deleteComments(final String str) {
        Scanner scan = new Scanner(str);
        String[] parts;
        String[] parts2 = new String[MAX_TOKENS];
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
     * Adds the commands one for one to the "compiled" program.
     *
     * @param befehlsfolge
     *            the line of code
     */
    private void addCommand(final String[] befehlsfolge) {
        switch (befehlsfolge.length) {
        case 1:
            this.prog.add(new CodeLine(counter, befehlsfolge[0], "", ""));
            break;
        case 2:
            this.prog.add(new CodeLine(counter, befehlsfolge[0],
                    befehlsfolge[1], ""));
            break;
        case MAX_TOKENS:
            this.prog.add(new CodeLine(counter, befehlsfolge[1],
                    befehlsfolge[2], befehlsfolge[0]));
            break;
        default:
        }
    }
}
