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
 * The Class Command.
 */
public final class Command implements Cloneable {

    /**
     * The Enum InstructionSet defines the commands known by our processor.
     */
    static enum InstructionSet {
            /**
             * "ORG" defines at a real M68000 where the program will be stored
             * in the main memory.
             */
            ORG,
            /**
             * "BRA" needs an argument. BRA jumps to the line of code in which
             * the marker is equal to this argument.
             */
            BRA,
            /**
             * "EQU" needs a label and an argument, which is an memory address.
             * In real, the assembler-compiler replaces every string in the
             * source file, which is equal to the marker with the argument.
             * We use it in a different way: Every time EQU appears in the
             * source file, a new "Memory" Object will be created with the
             * label as searching String. The content of this memory area will
             * be asked before running the program.
             */
            EQU,
            DC,
            DS,
            CLR,
            MOVE,
            ADD,
            END,
            ZERO,
            HEAD,
            SUB,
            MUL,
            DIV,
            CMP,
            BNE };

    /**
     * The Enum CommandPostfix.
     */
    static enum CommandPostfix    {
                                    B,
                                    W,
                                    L,
                                    ZERO
                                }

    /**
     * The prefix is one of the enum InstructionSet.
     */
    private InstructionSet instruction;

    /**
     * The postfix is the part of the command, which comes after an comma.
     * It is not used at this time, but already stored, so the it will not be
     * hard to implement its function in this class.
     */
    private CommandPostfix postfix;

    /**
     * Indicates if the Command consists of a prefix and a postfix, i. e. MOVE.L
     * It is not used at this time (same as the postfix).
     */
    private boolean twoParts;

    /**
     * Instantiates a new command. Checks if the given String contains a dot,
     * and splits the String if necessary.
     *
     * @param str the whole Command (postfix and prefix)
     */
    public Command(final String str) {
        if (str.contains(".")) {
            String[] parts = str.split("[.]");
            this.postfix   = getPostfix(parts[1]);
            this.instruction    = getInstruction(parts[0]);
            this.twoParts  = true;
        } else {
            this.instruction    = getInstruction(str);
            this.twoParts  = false;
        }
    }

    /**
     * @param instruction instruction with postfix
     * @return postfix of an instruction.
     */
    private static CommandPostfix getPostfix(final String instruction) {
        CommandPostfix post;

        try {
            post = CommandPostfix.valueOf(instruction);
        } catch (IllegalArgumentException e) {
            ui.UI.printMessage(instruction + " is not a value postfix!");
            post = CommandPostfix.ZERO;
        }

        return post;
    }

    /**
     * Instantiates a new command. Checks if the given String arraylength is
     * greater than zero, and stores this arrays first element as Command
     * postfix, if necessary.
     *
     * @param com the Command
     * @param str the Postfix
     */
    public Command(final InstructionSet com, final String... str) {

        if (str.length > 0) {
            this.twoParts = true;
            this.postfix  = getPostfix(str[0]);
        }

        this.instruction = com;
    }

    /**
     * Instantiates a new command.
     *
     * @param pre the pre
     * @param pos the pos
     */
    public Command(final InstructionSet pre, final CommandPostfix pos) {
        this.instruction = pre;
        this.postfix = pos;
        this.twoParts = true;
    }

    /**
     * Returns, if the Command consists of two parts. Not used at this time.
     *
     * @return true, if the Command consists of two parts
     */
    public boolean hastwoparts() {
        return this.twoParts;
    }

    /**
     * @param com the Command Prefix as String
     * @return the Command Prefix as one of the enum InstructionSet
     */
    private static InstructionSet getInstruction(final String com) {
        InstructionSet command;

        try {
            command = InstructionSet.valueOf(com);
        } catch (IllegalArgumentException e) {
        	ui.UI.printMessage(com + " is not a command!");
            command = InstructionSet.ZERO;
        }

        return command;
    }

    public InstructionSet getPrefix() {
        return this.instruction;
    }

    /**
     * Gets the postfix of the Command. Not used at this time.
     *
     * @return the postfix
     */
    public CommandPostfix getPostfix() {
        return this.postfix;
    }

    @Override
    public String toString() {
        if (this.twoParts) {
            return this.instruction + "," + this.postfix;
        } else {
            return this.instruction.toString();
        }
    }

    @Override
    public Command clone() {
        Command clone;

        if (this.twoParts) {
            clone = new Command(this.instruction, this.postfix);
        } else {
            clone = new Command(this.instruction);
        }

        return clone;
    }
}
