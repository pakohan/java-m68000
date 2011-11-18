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

import java.util.regex.Pattern;

/**
 * The Class Command.
 */
public final class Command implements Cloneable {

    /**
     * The Enum Befehlssatz defines the commands known by our processor.
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
             * "EQU" needs a marker and an argument, which is an memory adress.
             * In real, the assembler-compiler replaces every string in the
             * source file, which is equal to the marker with the argument.
             * We use it in a different way: Every time EQU appears in the
             * source file, a new "Speicher" Object will be created with the
             * marker as searching String. The content of this memory area will
             * be asked before running the program.
             */
            EQU,
            /** The DC. */
            DC,
            /** The DS. */
            DS,
            /** The CLR. */
            CLR,
            /** The MOVE. */
            MOVE,
            /** The ADD. */
            ADD,
            /** The END. */
            END,
            /** The ZERO. */
            ZERO,
            /** The HEAD. */
            HEAD,
            /** The SUB. */
            SUB,
            /** The MUL. */
            MUL,
            /** The DIV. */
            DIV };

    /**
     * The Enum CommandPostfix.
     */
    static enum CommandPostfix { /** The B. */
B, /** The W. */
 W, /** The L. */
 L, /** The ZERO. */
 ZERO }

    /**
     * The prefix is one of the enum Befehlssatz.
     */
    private InstructionSet instruction;

    /**
     * The postfix is the part of the command, which comes after an comma.
     * It is not used at this time, but already stored, so the it will not be
     * hard to implement its function in this class.
     */
    private CommandPostfix secondOperand;

    /**
     * Indicates if the Command consists of a prefix and a postfix. It is not
     * used at this time (same as the postfix).
     */
    private boolean twoOperands;

    /**
     * Instantiates a new command. Checks if the given String contains a dot,
     * and splits the String if necessary.
     *
     * @param str the whole Command (postfix and prefix)
     */
    public Command(final String str) {

        Pattern p = Pattern.compile("[.]");

        if (str.contains(".")) {
            String[] parts = p.split(str);
            this.secondOperand   = recognizePostfix(parts[1]);
            this.instruction    = getInstruction(parts[0]);
            this.twoOperands  = true;
        } else {
            this.instruction    = getInstruction(str);
            this.twoOperands  = false;
        }

    }

    /**
     * Recognize postfix.
     *
     * @param str the str
     * @return the command postfix
     */
    private static CommandPostfix recognizePostfix(final String str) {
        CommandPostfix post = CommandPostfix.ZERO;

        if (str.equals("B")) {
            post = CommandPostfix.B;
        } else if (str.equals("W")) {
            post = CommandPostfix.W;
        } else if (str.equals("L")) {
            post = CommandPostfix.L;
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
            this.twoOperands = true;
            this.secondOperand  = recognizePostfix(str[0]);
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
        this.secondOperand = pos;
        this.twoOperands = true;
    }

    /**
     * Returns, if the Command consists of two parts. Not used at this time.
     *
     * @return true, if the Command consists of two parts
     */
    public boolean hastwoparts() {
        return this.twoOperands;
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
        	command = InstructionSet.ZERO;
        }

        return command;
    }

    /**
     * Gets the prefix of the Command.
     *
     * @return the prefix
     */
    public InstructionSet getPrefix() {

        return this.instruction;

    }

    /**
     * Gets the postfix of the Command. Not used at this time.
     *
     * @return the postfix
     */
    public CommandPostfix getPostfix() {

        return this.secondOperand;

    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        if (this.twoOperands) {
            return this.instruction + "," + this.secondOperand;
        } else {
            return this.instruction.toString();
        }

    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Command clone() {

        Command klon;

        if (this.twoOperands) {
            klon = new Command(this.instruction, this.secondOperand);
        } else {
            klon = new Command(this.instruction);
        }

        return klon;

    }
}
