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
import m68000.Argument.ArgType;
import m68000.Command.CommandPostfix;

public class Processor {

    public static final int REG_AMOUNT = 8;

    private int[] dataRegister = new int[REG_AMOUNT];
    private int[] adressRegister = new int[REG_AMOUNT];
    private int size;
    private boolean compare;
    private LinkedList<CodeLine> execute;
    private boolean finished = false;
    private RAM ram;

    public Processor(final Program prog) {
        this.execute = prog.getProg();
        this.ram = prog.getRAM();
        this.size = this.execute.getSize();
    }

    private int getAdressRegister(final Arg adr, final CommandPostfix cpf) {
        int x = 0;
        switch (adr.getInk()) {
        case POSTINKREMENT:
            x = getRAM(this.adressRegister[adr.getValue()], cpf);
            this.adressRegister[adr.getValue()] += cpf.ordinal();
            break;
        case PREINKREMENT:
            this.adressRegister[adr.getValue()] += cpf.ordinal();
            x = getRAM(adressRegister[adr.getValue()], cpf);
            break;
        case POSTDEKREMENT:
            x = getRAM(this.adressRegister[adr.getValue()], cpf);
            this.adressRegister[adr.getValue()] -= cpf.ordinal();
            break;
        case PREDEKREMENT:
            this.adressRegister[adr.getValue()] -= cpf.ordinal();
            x = getRAM(this.adressRegister[adr.getValue()], cpf);
            break;
        case NOINKREMENT:
            x = getRAM(this.adressRegister[adr.getValue()], cpf);
            break;
        case NONE:
            x = this.adressRegister[adr.getValue()];
            break;
        default:
        }
        ui.AdressTable.setadresstable(adr.getValue(),
                this.adressRegister[adr.getValue()]);
        return x;
    }

    private void setAdressRegister(final Arg adr, final int x, final CommandPostfix cpf) {
        switch (adr.getInk()) {
        case NOINKREMENT:
            setRAM(this.adressRegister[adr.getValue()], x, cpf);
            break;
        case NONE:
            int adrold = this.adressRegister[adr.getValue()];
            if (cpf != CommandPostfix.NONE) {
                int shift = 32 - (8 *
                    cpf.ordinal());
                adrold = ((adrold >>> shift) << shift);
            }
            adrold = adrold + x;
            this.adressRegister[adr.getValue()] = x;
            break;
        case POSTINKREMENT:
            setRAM(this.adressRegister[adr.getValue()], x, cpf);
            this.adressRegister[adr.getValue()] += cpf.ordinal();
            break;
        case PREINKREMENT:
            this.adressRegister[adr.getValue()] += cpf.ordinal();
            setRAM(this.adressRegister[adr.getValue()], x, cpf);
            break;
        case POSTDEKREMENT:
            setRAM(this.adressRegister[adr.getValue()], x, cpf);
            this.adressRegister[adr.getValue()] -= cpf.ordinal();
            break;
        case PREDEKREMENT:
            this.adressRegister[adr.getValue()] -= cpf.ordinal();
            setRAM(this.adressRegister[adr.getValue()], x, cpf);
            break;
        default:
        }
        ui.AdressTable.setadresstable(adr.getValue(),
                this.adressRegister[adr.getValue()]);
    }

    private void setDataRegister(final int adr, final int val) {
        int adrold = this.dataRegister[adr];
        if (this.execute.getItem().getCommand().getPostfix()
                != CommandPostfix.NONE) {
            int shift = 32 - (8 *
                this.execute.getItem().getCommand().getPostfix().ordinal());
            adrold = ((adrold >>> shift) << shift);
        }
        adrold = adrold + val;
        ui.DataTable.setdatatable(adr, val);
    }

    private void setRAM(final int adr, final int x, final CommandPostfix pf) {
        switch (pf) {
        case B:
            this.ram.setByteInAddress(adr, (byte) x);
            break;
        case W:
            this.ram.setWordInAddress(adr, (short) x);
            break;
        case L:
            this.ram.setLongWordInAddress(adr, x);
            break;
        }
    }

    private int getRAM(final int adr, final CommandPostfix pf) {
        switch (pf) {
        case B:
            return this.ram.getByteInAddress(adr);
        case W:
            return this.ram.getWordInAddress(adr);
        case L:
            return this.ram.getLongWordInAddress(adr);
        default:
        }
        return 0;
    }

    private void setData(final Arg dataPlace, final int x, final CommandPostfix pf) {
        switch (dataPlace.getType()) {
        case ADDRESS_REGISTER:
            setAdressRegister(dataPlace, x, pf);
            break;
        case DATA_REGISTER:
            setDataRegister(dataPlace.getValue(), x);
            break;
        case MEMORY:
            setRAM(dataPlace.getValue(), x, pf);
            break;
        default:
        }
    }

    private int getData(final Arg dataPlace, final CommandPostfix pf) {
        int x = 0;
        switch (dataPlace.getType()) {
        case ADDRESS_REGISTER:
            x = getAdressRegister(dataPlace, pf);
            break;
        case DATA_REGISTER:
            x = this.dataRegister[dataPlace.getValue()];
            break;
        case MEMORY:
            x = getRAM(dataPlace.getValue(), pf);
            break;
        case CONST:
            x = dataPlace.getValue();
            break;
        case ADRESSOFMARKER:
            x = dataPlace.getValue();
            break;
        default:
        }
        return x;
    }

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

    public final void run() {
        while (!this.hasfinished()) {
            this.execute = this.execute.getNext();
            step(this.execute.getItem());
        }
    }

    public final boolean hasfinished() {
        return finished;
    }

    private int getNextLine(final LinkedList<CodeLine> com) {
        switch (com.getItem().getCommand().getPrefix()) {
        case BRA:
            return jump(com.getItem().getArgument().getPrefix()
                    .getOtherArg()).getItem().getLineindex();
        case BEQ:
            if (this.compare) {
                return jump(com.getItem().getArgument().getPrefix()
                        .getOtherArg()).getItem().getLineindex();
            }
            break;
        case BNE:
            if (!this.compare) {
                return jump(com.getItem().getArgument().getPrefix()
                        .getOtherArg()).getItem().getLineindex();
            }
            break;
        default:
        }
        return com.getNext().getItem().getLineindex();
    }

    public final void step() {
        this.execute = this.execute.getNext();
        step(this.execute.getItem());
        ui.UI.markLine(getNextLine(this.execute));
    }

    private void step(final CodeLine com) {
        switch (com.getCommand().getPrefix()) {
        case ORG:
        case EQU:
        case DC:
        case DS:
        case HEAD:
            break;
        case BRA:
            this.execute = jump(com.getArgument().getPrefix().getOtherArg())
                    .getPrev();
            break;
        case CLR:
            clr(com.getArgument(), com.getCommand().getPostfix());
            break;
        case MOVE:
            move(com.getArgument(), com.getCommand().getPostfix());
            break;
        case ADD:
            add(com.getArgument(), com.getCommand().getPostfix());
            break;
        case SUB:
            sub(com.getArgument(), com.getCommand().getPostfix());
            break;
        case MUL:
            mul(com.getArgument(), com.getCommand().getPostfix());
            break;
        case DIVU:
            divu(com.getArgument());
            break;
        case DIVS:
            divs(com.getArgument());
            break;
        case CMP:
            cmp(com.getArgument());
            break;
        case BEQ:
            if (this.compare) {
                this.execute = jump(com.getArgument().getPrefix().getOtherArg())
                        .getPrev();
            }
            break;
        case BNE:
            if (!this.compare) {
                this.execute = jump(com.getArgument().getPrefix().getOtherArg())
                        .getPrev();
            }
            break;
        case SWAP:
            swap(com.getArgument().getPrefix());
            break;
        case END:
            this.finished = true;
            break;
        default:
            ui.UI.printMessage("Command '" + com.getCommand().getPrefix()
                    + "' not found!");
        }
    }

    private void cmp(final Argument args) {
        if (getData(args.getPrefix(),
                this.execute.getItem().getCommand().getPostfix())
                == getData(args.getPostfix(),
                        this.execute.getItem().getCommand().getPostfix())) {
            this.compare = true;
        } else {
            this.compare = false;
        }
    }

    private void move(final Argument args, final CommandPostfix cpf) {
        int x = getData(args.getPrefix(), cpf);
        setData(args.getPostfix(), x, cpf);
    }

    private void clr(final Argument args, final CommandPostfix cpf) {
        setData(args.getPrefix(), 0, cpf);
    }

    private void add(final Argument args, final CommandPostfix cpf) {
        int x = getData(args.getPrefix(), cpf);
        x += getData(args.getPostfix(), cpf);
        setData(args.getPostfix(), x, cpf);
    }

    private void sub(final Argument args, final CommandPostfix cpf) {
        int x = getData(args.getPrefix(), cpf);
        x -= getData(args.getPostfix(), cpf);
        setData(args.getPostfix(), x, cpf);
    }

    private void mul(final Argument args, final CommandPostfix cpf) {
        int x = getData(args.getPrefix(), cpf);
        x = getData(args.getPostfix(), cpf) * x;
        setData(args.getPostfix(), x, cpf);
    }

    private void divs(final Argument args) {
        int x = getData(args.getPrefix(), CommandPostfix.W);
        if (x != 0) {
            int n = getData(args.getPostfix(), CommandPostfix.L);
            int newval = (short) (n % x);
            newval = newval << 16;
            newval = newval + ((short) (n / x));
            setData(args.getPostfix(), newval, CommandPostfix.L);
        }
    }

    private void divu(final Argument args) {
        int x = getData(args.getPrefix(), CommandPostfix.W);
        if (x != 0) {
            int newval = getData(args.getPostfix(), CommandPostfix.L);
            newval = (newval / x);
            setData(args.getPostfix(), newval, CommandPostfix.L);
        }
    }


    private void swap(final Arg prefix) {
        if (prefix.getType() == ArgType.DATA_REGISTER) {
            int x = this.dataRegister[prefix.getValue()];
            int begin = (short) x;
            int end = (short) (x >>> 16);
            x = (begin << 16);
            x += ((short) end);
            setDataRegister(prefix.getValue(), x);
        }
    }
}
