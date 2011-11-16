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
 * The Class Arguments.
 */
public final class Arguments {

    /** The prefix. */
    private String prefix;

    /** The postfix. */
    private String postfix;

    /** The twoparts. */
    private boolean twoparts;

    /**
     * Instantiates a new argument.
     *
     * @param arg the arg
     */
    public Arguments(final String arg) {
        if (arg.contains(",")) {
            String[] split = new String[2];
            split = arg.split(",");
            this.prefix = split[0];
            this.postfix = split[1];
            this.twoparts = true;
        } else {
            this.prefix = arg;
            this.postfix = "";
            this.twoparts = false;
        }
    }

    /**
     * Gets the arg.
     *
     * @return the arg
     */
    public String getPrefix() {
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

    /**
     * Hastwoparts.
     *
     * @return true, if successful
     */
    public boolean hastwoparts() {
        return this.twoparts;
    }

    @Override
    public String toString() {
        if (this.twoparts) {
            return this.prefix + "," + this.postfix;
        } else {
            return this.prefix;
        }
    }
}
