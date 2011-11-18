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

/**
 * The Class Program reads the code source file and initializes a new RAM
 * Object. The resulting Ram and LinkedList<LoC> will be stored in this Program
 * object.
 */
public final class Program {

    /**
     * The Constant MAXTOKENS is used because of the magic numbers check. It
     * describes of how many different items a assember source line of code can
     * consist. (marker, command and argument)
     */
    static final int MAXTOKENS = 3;

    /**
     * The prog value is the linked list which represents the program.
     */
    private LinkedList<LoC> prog;

    /**
     * The speicher value is the Ram connected to the given assembler source
     * file.
     */
    private RAM data;

    /**
     * Instantiates a new Program.
     *
     * @param arg source file
     * @throws IOException Signals if the file can't be read.
     */
    public Program(final String arg) throws IOException {
        this.prog = readSourceFile(arg);
        this.data = new RAM();
        linker();
    }

    /**
     * Gets the program list.
     *
     * @return the prog
     */
    public LinkedList<LoC> getProg() {
        return prog;
    }

    /**
     * Gets the speicher.
     *
     * @return the speicher
     */
    public RAM getSpeicher() {
        return this.data;
    }

    /**
     * Reads the source File and stores it in a linked list.
     *
     * @param sourcefile the sourcefile
     * @return the program stored in a linked list
     * @throws IOException Signals if the file can't be read.
     */
    private LinkedList<LoC> readSourceFile(final String sourcefile)
            throws IOException {
        FileReader file = new FileReader(sourcefile);
        LineNumberReader source = new LineNumberReader(file);
        String[] part;
        String line;
        this.prog = new LinkedList<LoC>(new LoC());

        while ((line = source.readLine()) != null) {
            part = line.split(";");
            addCommand(recognizeLine(part[0]));
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
    public RAM linker() {
        LinkedList<LoC> tmp = this.prog;
        RAM ram = new RAM();
        Arg tmp_arg;
        for (int i = this.prog.getSize(); i > 0; --i) {
            tmp = tmp.getNext();
            tmp_arg = tmp.getItem().getArgument().getPrefix();
            switch (tmp.getItem().getCommand().getPrefix()) {
            case EQU :
                replaceSymbolicConstant(tmp.getItem().getMarker(),
                        "$" + tmp_arg.getValue());
                Scanner scan = new Scanner(System.in);
                System.out.printf("Was ist an Stelle %d im RAM gespeichert?",
                        tmp_arg.getValue());
                this.data.setByte(tmp_arg.getValue(), scan.nextInt());
                break;
            case DC :
            	int x2 = this.data.addSpeicher(tmp_arg.getValue());
                StringBuilder tmp_str = new StringBuilder();
                tmp_str.append("$");
                tmp_str.append(x2);
                tmp.getItem().getArgument().replacePrefix(tmp_str.toString());
                replaceSymbolicConstant(tmp.getItem().getMarker(), "$" + x2);
                break;
            case DS :
                int[] x = new int[tmp_arg.getValue()];
                int y = this.data.addSpeicher(x);
                replaceSymbolicConstant(tmp.getItem().getMarker(), "$" + y);
                break;
            case ORG:
            case BRA:
            case CLR :
            case MOVE :
            case ADD :
            case HEAD :
            case END :
            case MUL :
            case SUB :
            case DIV :
            case CMP :
            case BNE:
                break;
            default :
                System.out.println("Befehl nicht gefunden!"
            + tmp.getItem().getCommand().getPrefix());
            }
        }
        return ram;
    }

    /**
     * Replace symbolic constant.
     *
     * @param str the str
     * @param newvalue the newvalue
     */
    private void replaceSymbolicConstant(final String str,
            final String newvalue) {
        LinkedList<LoC> tmp2 = this.prog;
        for (int i = this.prog.getSize(); i > 0; --i) {
            tmp2 = tmp2.getNext();
            if (tmp2.getItem().getArgument().getPrefix().
                    getOtherArg().equals(str)) {
                tmp2.getItem().getArgument().replacePrefix(newvalue);
            } else if (tmp2.getItem().getArgument().getPostfix().
                    getOtherArg().equals(str)) {
                tmp2.getItem().getArgument().replacePostfix(newvalue);
            }
        }
    }
    /**
     * Recognize the line of source code. The comments have to be deleted
     * before!
     *
     * @param str the line of source code
     * @return the string[]
     */
    private static String[] recognizeLine(final String str) {
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
     * Adds the commands one for one to the "compiled" program.
     *
     * @param befehlsfolge the line of code
     */
    private void addCommand(final String[] befehlsfolge) {
        switch (befehlsfolge.length) {
            case 1:
                this.prog.add(new LoC(befehlsfolge[0], "", ""));
                break;
            case 2:
                this.prog.add(new LoC(befehlsfolge[0], befehlsfolge[1], ""));
                break;
            case MAXTOKENS:
                this.prog.add(new LoC(befehlsfolge[1], befehlsfolge[2],
                        befehlsfolge[0]));
                break;
            default:
        }
    }
}
