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
    static enum ArgType    {
        ADDRESS_REGISTER,
        DATA_REGISTER,
        MEMORY,
        STRING,
        CONST,
        VALUEARRAY,
        ADRESSOFMARKER
    }

    static enum Inkrement {
        POSTINKREMENT,
        PREINKREMENT,
        POSTDEKREMENT,
        PREDEKREMENT,
        NOINKREMENT,
        NONE
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
        if (this.firstOperand.type != ArgType.ADRESSOFMARKER) {
            this.firstOperand = new Arg(x);
        } else {
            this.firstOperand = new Arg(x);
            this.firstOperand.setType(ArgType.ADRESSOFMARKER);
        }
    }

    public void replacePostfix(final String x) {
        if (this.secondOperand.type != ArgType.ADRESSOFMARKER) {
            this.secondOperand = new Arg(x);
        } else {
            this.secondOperand = new Arg(x);
            this.secondOperand.setType(ArgType.ADRESSOFMARKER);
        }
    }

    public Arg getPostfix() {

        return this.secondOperand;

    }

    public boolean hasTwoOperands() {

        return this.twoOperands;

    }

    @Override
    public String toString() {

        if (this.twoOperands) {
            return this.firstOperand + "," + this.secondOperand;
        } else {
            return this.firstOperand.toString();
        }

    }

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
        private String anotherArg;
        private byte[] valuearray;
        private Inkrement ink;

        /**
         * Instantiates a new arg.
         *
         * @param argument the argument
         */
        public Arg(final String argument) {
            this.anotherArg = argument;
            if (argument.length() == 0) {
                return;
            }
            this.ink = Inkrement.NONE;
            switch (argument.charAt(0)) {
            case '#':
                Scanner scan = new Scanner(argument.substring(1));
                if (scan.hasNextInt()) {
                    this.type = ArgType.CONST;
                    this.value = scan.nextInt();
                } else {
                    this.type = ArgType.ADRESSOFMARKER;
                    this.anotherArg = scan.next();
                }
                break;
            case '(':
                if (argument.length() == 5) {
                    if (argument.charAt(4) == '+') {
                        this.ink = Inkrement.POSTINKREMENT;
                    } else {
                        this.ink = Inkrement.POSTDEKREMENT;
                    }
                } else {
                    this.ink = Inkrement.NOINKREMENT;
                }

                if (argument.charAt(1) == 'A') {
                    this.type = ArgType.ADDRESS_REGISTER;
                } else {
                    this.type = ArgType.DATA_REGISTER;
                }
                this.value = new Scanner("" + argument.charAt(2)).nextInt();
                break;
            case '+':
                this.ink = Inkrement.PREINKREMENT;
                if (argument.charAt(2) == 'A') {
                    this.type = ArgType.ADDRESS_REGISTER;
                } else {
                    this.type = ArgType.DATA_REGISTER;
                }
                this.value = new Scanner("" + argument.charAt(3)).nextInt();
                break;
            case '-':
                this.ink = Inkrement.PREDEKREMENT;
                if (argument.charAt(2) == 'A') {
                    this.type = ArgType.ADDRESS_REGISTER;
                } else {
                    this.type = ArgType.DATA_REGISTER;
                }
                this.value = new Scanner("" + argument.charAt(3)).nextInt();
                break;
            case '\'':
                this.type = ArgType.VALUEARRAY;
                String[] str = argument.split("'");
                this.valuearray = new byte[str[1].length() + 1];
                for (int i = 0; i < str[1].length(); i++) {
                    this.valuearray[i] = (byte) Character.getNumericValue(str[1].charAt(i));
                }
                this.valuearray[this.valuearray.length - 1] = 0;
                break;
            case '$':
                Scanner scan2 = new Scanner(argument.substring(1));
                this.type = ArgType.MEMORY;
                this.value = scan2.nextInt();
                break;
            default :
                Scanner scan3 = new Scanner(argument);
                if (scan3.hasNextInt()) {
                    this.type = ArgType.CONST;
                    this.value = scan3.nextInt();
                } else if (argument.length() == 2
                           && new Scanner("" + argument.charAt(1)).hasNextInt()) { //WTF?! Scanner(true or false)
                    Scanner scan4 = new Scanner(argument.substring(1));
                    if (argument.charAt(0) == 'A') {
                        this.type = ArgType.ADDRESS_REGISTER;
                    }

                    if (argument.charAt(0) == 'D') {
                        this.type = ArgType.DATA_REGISTER;
                    }
                    this.value = scan4.nextInt();
                } else {
                    this.type = ArgType.STRING;
                }
            }
        }

        public final Inkrement getInk() {
            return ink;
        }

        public final byte[] getValuearray() {
            return valuearray;
        }

        @Override
        public String toString() {
            return this.anotherArg + "(" + this.type + "_" + this.ink + ")";
        }

        public Arg(final int x) {
            this.type = ArgType.MEMORY;
            this.value = x;
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

        public final void setType(final ArgType argtype) {
            this.type = argtype;
        }
    }

}
