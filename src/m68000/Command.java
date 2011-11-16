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
public final class Command {

    /**
     * The Enum Befehlssatz.
     */
    static enum Befehlssatz {
        ORG,  BRA,  EQU,  DC,  DS,  CLR,  MOVE,  ADD, END, ZERO, HEAD };

    /** The prefix. */
    private Befehlssatz prefix;

    /** The postfix. */
    private String postfix;

    /** The twoparts. */
    private boolean twoparts;

    /**
     * Instantiates a new command.
     *
     * @param str the str
     */
    public Command(final String str) {
        Pattern p = Pattern.compile("[.]");
        if (str.contains(".")) {
            String[] parts = new String[2];
            parts = p.split(str);
            this.postfix = parts[1];
            this.prefix = recognizePrefix(parts[0]);
            this.twoparts = true;
        } else {
            this.prefix = recognizePrefix(str);
            this.twoparts = false;
        }
    }

    /**
     * Hastwoparts.
     *
     * @return true, if successful
     */
    public boolean hastwoparts() {
        return this.twoparts;
    }

    /**
     * Recognize prefix.
     *
     * @param com the com
     * @return the befehlssatz
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
        }
        return command;
    }

    /**
     * Gets the prefix.
     *
     * @return the prefix
     */
    public Befehlssatz getPrefix() {
        return this.prefix;
    }

    /**
     * Gets the postfix.
     *
     * @return the postfix
     */
    public String getPostfix() {
        return this.postfix;
    }

    @Override
    public String toString() {
        if (this.twoparts) {
            return this.prefix + "," + this.postfix;
        } else {
            return "" + this.prefix;
        }
    }
}
