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
     * The Enum Befehlssatz.
     */
    static enum Befehlssatz {

        /** The ORG. */
        ORG,
  /** The BRA. */
  BRA,
  /** The EQU. */
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
            String[] parts;
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
     * Instantiates a new command.
     *
     * @param com the com
     * @param str the str
     */
    public Command(final Befehlssatz com, final String... str) {
        if (str.length > 1) {
            this.twoparts = true;
            this.postfix = str[0];
        }
        this.prefix = com;
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
