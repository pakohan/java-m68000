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
import m68000.Command.CommandPostfix;

public class Processor {

    /** REG_AMOUNT defines the amount of the registers. */
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

    public final void step() {
        this.execute = this.execute.getNext();
        step(this.execute.getItem());
        ui.UI.markLine(getNextLine(this.execute));
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

    public final int[] getDataRegister() {
        return dataRegister;
    }

    private int getAdressRegister(final Arg adr) {
        int x = 0;
        switch (adr.getInk()) {
        case POSTINKREMENT:
            x = getRAM(this.adressRegister[adr.getValue()]);
            this.adressRegister[adr.getValue()]
                += this.execute.getItem().getCommand().getPostfix().ordinal();
            break;
        case PREINKREMENT:
            this.adressRegister[adr.getValue()]
                += this.execute.getItem().getCommand().getPostfix().ordinal();
            x = getRAM(adressRegister[adr.getValue()]);
            break;
        case POSTDEKREMENT:
            x = getRAM(this.adressRegister[adr.getValue()]);
            this.adressRegister[adr.getValue()]
                -= this.execute.getItem().getCommand().getPostfix().ordinal();
            break;
        case PREDEKREMENT:
            this.adressRegister[adr.getValue()]
                -= this.execute.getItem().getCommand().getPostfix().ordinal();
            x = getRAM(this.adressRegister[adr.getValue()]);
            break;
        case NOINKREMENT:
            x = getRAM(this.adressRegister[adr.getValue()]);
            break;
        case NONE:
            x = this.adressRegister[adr.getValue()];
        default:
        }
        ui.AdressTable.setadresstable(adr.getValue(),
                this.adressRegister[adr.getValue()]);
        return x;
    }

    private void setAdressRegister(final Arg adr, final int x) {
        switch (adr.getInk()) {
        case NOINKREMENT:
            setRAM(this.adressRegister[adr.getValue()], x);
            break;
        case NONE:
            this.adressRegister[adr.getValue()] = x;
            break;
        case POSTINKREMENT:
            setRAM(this.adressRegister[adr.getValue()],
                    x);
            this.adressRegister[adr.getValue()]
                += this.execute.getItem().getCommand().getPostfix().ordinal();
            break;
        case PREINKREMENT:
            this.adressRegister[adr.getValue()]
                += this.execute.getItem().getCommand().getPostfix().ordinal();
            setRAM(this.adressRegister[adr.getValue()],
                    x);
            break;
        case POSTDEKREMENT:
            setRAM(this.adressRegister[adr.getValue()],
                    x);
            this.adressRegister[adr.getValue()]
                -= this.execute.getItem().getCommand().getPostfix().ordinal();
            break;
        case PREDEKREMENT:
            this.adressRegister[adr.getValue()]
                -= this.execute.getItem().getCommand().getPostfix().ordinal();
            setRAM(this.adressRegister[adr.getValue()],
                    x);
            break;
        default:
        }
        ui.AdressTable.setadresstable(adr.getValue(),
                this.adressRegister[adr.getValue()]);
    }

    private void setRAM(final int adr, final int x) {
        switch (this.execute.getItem().getCommand().getPostfix()) {
        case B:
            this.ram.setByteInAddress(adr, (byte) x);
            break;
        case W:
            this.ram.setWordInAddress(adr, (short) x);
            break;
        case L:
            this.ram.setLongWordInAddress(adr, x);
            break;
            default:
        }
    }

    private int getRAM(final int adr) {
        switch (this.execute.getItem().getCommand().getPostfix()) {
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
            clr(com.getArgument());
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
            break;
        case DIV:
            div(com.getArgument());
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
        case END:
            this.finished = true;
            break;
        default:
            ui.UI.printMessage("Command '" + com.getCommand().getPrefix()
                    + "' not found!");
        }
    }

    private void cmp(final Argument args) {
        if (getData(args.getPrefix()) == getData(args.getPostfix())) {
            this.compare = true;
        } else {
            this.compare = false;
        }
    }

    private void move(final Argument args, final CommandPostfix cpf) {
        int x = getData(args.getPrefix());
        x = recognizeCommandPostfix(x, cpf);
        setData(args.getPostfix(), x);
    }

    private void clr(final Argument args) {
        setData(args.getPrefix(), 0);
    }

    private void add(final Argument args, final CommandPostfix cpf) {
        int x = getData(args.getPrefix());
        x = recognizeCommandPostfix(x, cpf);
        x = getData(args.getPostfix()) + x;
        setData(args.getPostfix(), x);
    }

    private void sub(final Argument args, final CommandPostfix cpf) {
        int x = getData(args.getPrefix());
        x = recognizeCommandPostfix(x, cpf);
        x = getData(args.getPostfix()) - x;
        setData(args.getPostfix(), x);
    }

    private void mul(final Argument args, final CommandPostfix cpf) {
        int x = getData(args.getPrefix());
        x = recognizeCommandPostfix(x, cpf);
        x = getData(args.getPostfix()) * x;
        setData(args.getPostfix(), x);
    }

    private void div(final Argument args) {
        int x = getData(args.getPrefix());
        if (x != 0) {
            x = getData(args.getPostfix()) / x;
            setData(args.getPostfix(), x);
        }
    }

    private int recognizeCommandPostfix(final int x, final CommandPostfix cpf) {
        int z;
        switch (cpf) {
        case B:
            z = ((byte) x) & 0xFF;
            break;
        case W:
            z = ((short) x) & 0xFFFF;
            break;
        case L:
            z = x;
            break;
        default:
            ui.UI.printMessage("Fehler: falsche Kommandoendung!");
            return 0;
        }
        return z;
    }

    private void setData(final Arg dataPlace, final int x) {
        switch (dataPlace.getType()) {
        case ADDRESS_REGISTER:
            setAdressRegister(dataPlace, x);
            break;
        case DATA_REGISTER:
            this.dataRegister[dataPlace.getValue()] = x;
            ui.DataTable.setdatatable(dataPlace.getValue(), x);
            break;
        case MEMORY:
            switch (this.execute.getItem().getCommand().getPostfix()) {
            case B:
                this.ram.setByteInAddress(dataPlace.getValue(), (byte) x);
                break;
            case W:
                this.ram.setWordInAddress(dataPlace.getValue(), (short) x);
                break;
            case L:
                this.ram.setLongWordInAddress(dataPlace.getValue(), x);
                break;
                default:
            }
            break;
        default:
        }
    }

    private int getData(final Arg dataPlace) {
        switch (dataPlace.getType()) {
        case ADDRESS_REGISTER:
            return getAdressRegister(dataPlace);
        case DATA_REGISTER:
            return this.dataRegister[dataPlace.getValue()];
        case MEMORY:
            return getRAM(dataPlace.getValue());
        case CONST:
            return dataPlace.getValue();
        case ADRESSOFMARKER:
            return dataPlace.getValue();
        default:
            return 0;
        }
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
