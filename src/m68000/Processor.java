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

import m68000.Argument.Arg;

/**
 * The Class Processor simulates a m68000 processor.
 */
public class Processor {

    /** REG_AMOUNT defines the amount of the registers. */
    static final int REG_AMOUNT = 8;

    private int[] dataRegister = new int[REG_AMOUNT];
    private int[] adressRegister = new int[REG_AMOUNT];


    public final int[] getDataRegister() {
        return dataRegister;
    }

    private int size;

    /** The compare. */
    private boolean compare;
    /** The exe. */
    private LinkedList<CodeLine> execute;

    /** The finished. */
    private boolean finished = false;

    private RAM ram;

    /**
     * Instantiates a new processor.
     *
     * @param prog the prog
     */
    public Processor(final Program prog) {
        this.execute = prog.getProg();
        this.ram = prog.getRAM();
        this.size = this.execute.getSize();
    }

    /**
     * The main processing unit. It searches the given program line for known
     * coms and executes it.
     *
     * @param com the com
     */
    public final void step(final CodeLine com) {
        switch (com.getCommand().getPrefix()) {
        case ORG:
            break;
        case BRA:
            this.execute = jump(com.getArgument().getPrefix().getOtherArg())
                               .getPrev();
            break;
        case EQU :
            break;
        case DC :
            break;
        case DS :
            break;
        case CLR :
            clr(com.getArgument());
            break;
        case  MOVE :
            move(com.getArgument());
            break;
        case ADD :
            add(com.getArgument());
            break;
        case SUB :
            sub(com.getArgument());
            break;
        case MUL :
            mul(com.getArgument());
            break;
        case DIV :
            div(com.getArgument());
            break;
        case CMP :
            cmp(com.getArgument());
            break;
        case HEAD :
            break;
        case BNE :
            if (!this.compare) {
                this.execute = jump(com.getArgument().getPrefix().getOtherArg())
                        .getPrev();
            }
            break;
        case END :
            this.finished = true;
            break;
        default :
            System.out.println("Befehl nicht gefunden!");
        }
    }

    /**
     * Cmp.
     *
     * @param args the args
     */
    private void cmp(final Argument args) {
        if (getData(args.getPrefix()) == getData(args.getPostfix())) {
            this.compare = true;
        } else {
            this.compare = false;
        }
    }

    /**
     * Overwrites the data stored in argument two with the data stored in
     * argument one.
     *
     * @param args the args
     */
    public final void move(final Argument args) {
        int x = getData(args.getPrefix());
        setData(args.getPostfix(), x);
    }

    /**
     * sets the data stored in the argument to 0. If there are two arguments,
     * only the first will be treated.
     *
     * @param args the args
     */
    public final void clr(final Argument args) {
        setData(args.getPrefix(), 0);
    }

    /**
     * Adds up the data stored in the first argument to the data in the second
     * one.
     *
     * @param args the args
     */
    public final void add(final Argument args) {
        int x = getData(args.getPrefix());
        x = getData(args.getPostfix()) + x;
        setData(args.getPostfix(), x);
    }

    /**
     * Sub.
     *
     * @param args the args
     */
    public final void sub(final Argument args) {
        int x = getData(args.getPrefix());
        x = getData(args.getPostfix()) - x;
        setData(args.getPostfix(), x);
    }

    /**
     * Mul.
     *
     * @param args the args
     */
    public final void mul(final Argument args) {
        int x = getData(args.getPrefix());
        x = getData(args.getPostfix()) * x;
        setData(args.getPostfix(), x);
    }

    /**
     * Div.
     *
     * @param args the args
     */
    public final void div(final Argument args) {
        int x = getData(args.getPrefix());
        x = getData(args.getPostfix()) / x;
        setData(args.getPostfix(), x);
    }

    /**
     * Searches for the place of data with the string dataPlace and overwrites
     * it with the given x.
     *
     * @param dataPlace the data place
     * @param x the x
     */
    public final void setData(final Arg dataPlace, final int x) {
        switch (dataPlace.getType()) {
        case ADDRESS_REGISTER :
            this.ram.setByteInAddress(this.adressRegister[dataPlace.getValue()], x);
            break;
        case DATA_REGISTER :
            this.dataRegister[dataPlace.getValue()] = x;
            break;
        case MEMORY :
            this.ram.setByteInAddress(dataPlace.getValue(), x);
            break;
        default :
        }
    }

    /**
     * Searches for the place of data with the string dataPlace and returns it.
     *
     * @param dataPlace the data place
     * @return the data
     */
    public final int getData(final Arg dataPlace) {
        switch (dataPlace.getType()) {
        case ADDRESS_REGISTER :
            return this.ram.getByteInAddress(
                    this.adressRegister[dataPlace.getValue()]);
        case DATA_REGISTER :
            return this.dataRegister[dataPlace.getValue()];
        case MEMORY :
            return this.ram.getByteInAddress(dataPlace.getValue());
        case CONST :
            return dataPlace.getValue();
        default :
        }
        return 0;
    }

    /**
     * Does a single step. This means that the the Processor will set the
     * execution pointer to the next element and executes it.
     */
    public final void run() {
        while (!this.hasfinished()) {
            this.execute = this.execute.getNext();
            step(this.execute.getItem());
        }
    }

    /**
     * Checks, if the program has finished.
     *
     * @return true, if it has finished
     */
    public final boolean hasfinished() {
        return finished;
    }

    /**
     * Jump.
     *
     * @param str the str
     * @return the linked list
     */
    public final LinkedList<CodeLine> jump(final String str) {
        LinkedList<CodeLine> tmp = this.execute;
        for (int i = 0; i < this.size; i++) {
            if (tmp.getItem().getLabel().equals(str)) {
                return tmp;
            }
            tmp = tmp.getNext();
        }
        return this.execute;
    }
}
