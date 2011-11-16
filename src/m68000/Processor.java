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
