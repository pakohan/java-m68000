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
    static enum ArgType	{
    						ADDRESS_REGISTER, 
							DATA_REGISTER, 
							MEMORY, 
							STRING, 
							CONST
						}


    private Arg firstOperand;

    /**
     * Only set, if twoOperands is true.
     */
    private Arg secondOperand;

    /**
     * Indicates, if the argument of the command consists of two operands.
     * I. e.: MOVE D1,D2
     */
    private boolean twoOperands;

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
            this.firstOperand    = new Arg(split[0]);
            this.secondOperand   = new Arg(split[1]);
            this.twoOperands  = true;
        } else {
            this.firstOperand    = new Arg(arg);
            this.secondOperand   = new Arg("");
            this.twoOperands  = false;
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
            this.firstOperand   = new Arg(arg[0]);
            this.secondOperand  = new Arg(arg[1]);
            this.twoOperands = true;
        } else {
            this.firstOperand   = new Arg(arg[0]);
            this.secondOperand  = new Arg("");
            this.twoOperands = false;
        }

    }

    /**
     * Instantiates a new argument.
     *
     * @param pre the pre
     * @param pos the pos
     */
    public Argument(final Arg pre, final Arg pos) {
        this.firstOperand = pre;
        this.secondOperand = pos;
        this.twoOperands = true;
    }

    /**
     * Instantiates a new argument.
     *
     * @param pre the pre
     */
    public Argument(final Arg pre) {
        this.firstOperand = pre;
        this.twoOperands = false;
    }

    public Arg getPrefix() {

        return this.firstOperand;

    }

    public void replacePrefix(final String x) {
        this.firstOperand = new Arg(x);
    }

    public void replacePostfix(final String x) {
        this.secondOperand = new Arg(x);
    }

    public Arg getPostfix() {

        return this.secondOperand;

    }

    public boolean hasTwoOperands() {

        return this.twoOperands;

    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        if (this.twoOperands) {
            return this.firstOperand + "," + this.secondOperand;
        } else {
            return this.firstOperand.toString();
        }

    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Argument clone() {
        Argument klon;

        if (this.twoOperands) {
            klon = new Argument(this.firstOperand, this.secondOperand);
        } else {
            klon = new Argument(this.firstOperand);
        }

        return klon;

    }

    /**
     * The Class Arg.
     */
    static class Arg {

        private ArgType type;
        private int value;
        private boolean isAddress;
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
                this.isAddress = false;
            } else if (argument.length() > 1 && argument.charAt(0) == '$') {
                this.value = new Integer(argument.substring(1));
                this.isAddress = true;
                this.type = ArgType.MEMORY;
            } else if (argument.length() == 2) {
                Scanner tmp2 = new Scanner(arg.substring(1));
                if (argument.charAt(0) == 'A' && tmp2.hasNextInt()) {
                    this.value = tmp2.nextInt();
                    this.isAddress = true;
                    this.type = ArgType.ADDRESS_REGISTER;
                } else if (argument.charAt(0) == 'D' && tmp2.hasNextInt()) {
                    this.value = tmp2.nextInt();
                    this.isAddress = true;
                    this.type = ArgType.DATA_REGISTER;
                } else {
                    this.isAddress = false;
                    this.type = ArgType.STRING;
                }
            } else {
                this.isAddress = false;
                this.type = ArgType.STRING;
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
            this.isAddress = true;
        }

        public boolean isAdress() {
            return this.isAddress;
        }

        public String getOtherArg() {
            return this.anotherArg;
        }

        public ArgType getType() {
            return this.type;
        }

        public int getValue() {
            return this.value;
        }
    }

}
