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

import java.util.Scanner;

/**
 * The Class Arguments cares about the Arguments of the source file.
 * <table border="1">
 * <tr>
 *   <td>Marker</td>
 *   <td>Command</td>
 *   <th>Argument</th>
 *   <td>Comment</td>
 * </tr>
 * <tr>
 *   <td>ADR1</td>
 *   <td>EQU</td>
 *   <td>$2000</td>
 *   <td>;This command links the storage value $2000 to ADR1</td>
 * </tr>
 * </table>
 */
public final class Argument implements Cloneable {

    /**
     * The Enum ArgType.
     */
    static enum ArgType { /** The AR. */
AR, /** The DR. */
 DR, /** The MEMORY. */
 MEMORY, /** The STR. */
 STR, /** The CONST. */
 CONST }

    /**
     * The prefix is the first part of the Argument. If twoparts is false,
     * the Argument is stored as one String in prefix.
     */
    private Arg prefix;

    /**
     * The postfix is the second part of the Argument, if twoparts is true.
     */
    private Arg postfix;

    /**
     * Indicates if the Argument of the LoC consists of two parts.
     * This means that the given Argument has two statements, seperated by a
     * comma.
     */
    private boolean twoparts;

    /**
     * Instantiates a new argument. It checks if the String contains a comma.
     * If it does, the String will be splitted, if not, the String will be
     * stored in the prefix.
     *
     * @param arg the Argument
     */
    public Argument(final String arg) {

        if (arg.contains(",")) {
            String[] split = arg.split(",");
            this.prefix    = new Arg(split[0]);
            this.postfix   = new Arg(split[1]);
            this.twoparts  = true;
        } else {
            this.prefix    = new Arg(arg);
            this.postfix   = new Arg("");
            this.twoparts  = false;
        }

    }

    /**
     * Instantiates a new argument. It checks if the String.length is > 1.
     * If it is, the first element will be stored in prefix, the second in
     * postfix.
     *
     * @param arg the Argument
     */
    public Argument(final String... arg) {

        if (arg.length > 1) {
            this.prefix   = new Arg(arg[0]);
            this.postfix  = new Arg(arg[1]);
            this.twoparts = true;
        } else {
            this.prefix   = new Arg(arg[0]);
            this.postfix  = new Arg("");
            this.twoparts = false;
        }

    }

    /**
     * Instantiates a new argument.
     *
     * @param pre the pre
     * @param pos the pos
     */
    public Argument(final Arg pre, final Arg pos) {
        this.prefix = pre;
        this.postfix = pos;
        this.twoparts = true;
    }

    /**
     * Instantiates a new argument.
     *
     * @param pre the pre
     */
    public Argument(final Arg pre) {
        this.prefix = pre;
        this.twoparts = false;
    }

    /**
     * Returns the Prefix of the Argument.
     *
     * @return the Prefix
     */
    public Arg getPrefix() {

        return this.prefix;

    }

    /**
     * Replace prefix.
     *
     * @param x the x
     */
    public void replacePrefix(final String x) {
        this.prefix = new Arg(x);
    }

    /**
     * Replace postfix.
     *
     * @param x the x
     */
    public void replacePostfix(final String x) {
        this.postfix = new Arg(x);
    }

    /**
     * Returns the Postfix of the Argument.
     *
     * @return the Postfix
     */
    public Arg getPostfix() {

        return this.postfix;

    }

    /**
     * Returns if the Argument consists of two parts.
     *
     * @return true, the Argument has got a Prefix AND a Postfix
     */
    public boolean hastwoparts() {

        return this.twoparts;

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
    public Argument clone() {
        Argument klon;

        if (this.twoparts) {
            klon = new Argument(this.prefix, this.postfix);
        } else {
            klon = new Argument(this.prefix);
        }

        return klon;

    }

    /**
     * The Class Arg.
     */
    static class Arg {

        /** The type. */
        private ArgType type;

        /** The value. */
        private int value;

        /** The is adress. */
        private boolean isAdress;

        /** The another arg. */
        private String anotherArg;

        /**
         * Instantiates a new arg.
         *
         * @param argument the argument
         */
        public Arg(final String argument) {
            this.anotherArg = argument;
            String arg = argument;
            Scanner tmp = new Scanner(argument);
            if (tmp.hasNextInt()) {
                this.value = tmp.nextInt();
                this.type = ArgType.CONST;
                this.isAdress = false;
            } else if (argument.length() > 1 && argument.charAt(0) == '$') {
                this.value = new Integer(argument.substring(1));
                this.isAdress = true;
                this.type = ArgType.MEMORY;
            } else if (argument.length() == 2) {
                Scanner tmp2 = new Scanner(arg.substring(1));
                if (argument.charAt(0) == 'A' && tmp2.hasNextInt()) {
                    this.value = tmp2.nextInt();
                    this.isAdress = true;
                    this.type = ArgType.AR;
                } else if (argument.charAt(0) == 'D' && tmp2.hasNextInt()) {
                    this.value = tmp2.nextInt();
                    this.isAdress = true;
                    this.type = ArgType.DR;
                } else {
                    this.isAdress = false;
                    this.type = ArgType.STR;
                }
            } else {
                this.isAdress = false;
                this.type = ArgType.STR;
            }
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return this.anotherArg;
        }

        /**
         * Instantiates a new arg.
         *
         * @param x the x
         */
        public Arg(final int x) {
            this.type = ArgType.MEMORY;
            this.value = x;
            this.isAdress = true;
        }

        /**
         * Checks if is adress.
         *
         * @return true, if is adress
         */
        public boolean isAdress() {
            return this.isAdress;
        }

        /**
         * Gets the other arg.
         *
         * @return the other arg
         */
        public String getOtherArg() {
            return this.anotherArg;
        }

        /**
         * Gets the type.
         *
         * @return the type
         */
        public ArgType getType() {
            return this.type;
        }

        /**
         * Gets the value.
         *
         * @return the value
         */
        public int getValue() {
            return this.value;
        }
    }

}
