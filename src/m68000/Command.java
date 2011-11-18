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
    static enum Befehlssatz {
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
    private Befehlssatz prefix;

    /**
     * The postfix is the part of the command, which comes after an comma.
     * It is not used at this time, but already stored, so the it will not be
     * hard to implement its function in this class.
     */
    private CommandPostfix postfix;

    /**
     * Indicates if the Command consists of a prefix and a postfix. It is not
     * used at this time (same as the postfix).
     */
    private boolean twoparts;

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
            this.postfix   = recognizePostfix(parts[1]);
            this.prefix    = recognizePrefix(parts[0]);
            this.twoparts  = true;
        } else {
            this.prefix    = recognizePrefix(str);
            this.twoparts  = false;
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
    public Command(final Befehlssatz com, final String... str) {

        if (str.length > 0) {
            this.twoparts = true;
            this.postfix  = recognizePostfix(str[0]);
        }

        this.prefix = com;
    }

    /**
     * Instantiates a new command.
     *
     * @param pre the pre
     * @param pos the pos
     */
    public Command(final Befehlssatz pre, final CommandPostfix pos) {
        this.prefix = pre;
        this.postfix = pos;
        this.twoparts = true;
    }

    /**
     * Returns, if the Command consists of two parts. Not used at this time.
     *
     * @return true, if the Command consists of two parts
     */
    public boolean hastwoparts() {

        return this.twoparts;

    }

    /**
     * This static function will recognize, which Command is stored in the
     * Prefix of the given Command. It uses a lot of "if" instructions to
     * search for the right string, because a "switch-case" which compares
     * a string to many others will first be supported by Java 7. No need for
     * it at this time.
     *
     * @param com the Command Prefix as String
     * @return the Command Prefix as one of the enum Befehlssatz
     */
    private static Befehlssatz recognizePrefix(final String com) {

        Befehlssatz command = Befehlssatz.ZERO;

        if (com.equals("ORG")) {
            command = Befehlssatz.ORG;
        } else if (com.equals("BRA")) {
            command = Befehlssatz.BRA;
        } else if (com.equals("EQU")) {
            command = Befehlssatz.EQU;
        } else if (com.equals("DC")) {
            command = Befehlssatz.DC;
        } else if (com.equals("DS")) {
            command = Befehlssatz.DS;
        } else if (com.equals("CLR")) {
            command = Befehlssatz.CLR;
        } else if (com.equals("MOVE")) {
            command = Befehlssatz.MOVE;
        } else if (com.equals("ADD")) {
            command = Befehlssatz.ADD;
        } else if (com.equals("END")) {
            command = Befehlssatz.END;
        } else if (com.equals("HEAD")) {
            command = Befehlssatz.HEAD;
        } else if (com.equals("SUB")) {
            command = Befehlssatz.SUB;
        } else if (com.equals("MUL")) {
            command = Befehlssatz.MUL;
        } else if (com.equals("DIV")) {
            command = Befehlssatz.DIV;
        }

        return command;

    }

    /**
     * Gets the prefix of the Command.
     *
     * @return the prefix
     */
    public Befehlssatz getPrefix() {

        return this.prefix;

    }

    /**
     * Gets the postfix of the Command. Not used at this time.
     *
     * @return the postfix
     */
    public CommandPostfix getPostfix() {

        return this.postfix;

    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        if (this.twoparts) {
            return this.prefix + "," + this.postfix;
        } else {
            return this.prefix.toString();
        }

    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Command clone() {

        Command klon;

        if (this.twoparts) {
            klon = new Command(this.prefix, this.postfix);
        } else {
            klon = new Command(this.prefix);
        }

        return klon;

    }
}
