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
 *   <td>;This command links the storage adress $2000 to ADR1</td>
 * </tr>
 * </table>
 */
public final class Argument implements Cloneable {

    /**
     * The prefix is the first part of the Argument. If twoparts is false,
     * the Argument is stored as one String in prefix.
     */
    private String prefix;

    /**
     * The postfix is the second part of the Argument, if twoparts is true.
     */
    private String postfix;

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
            this.prefix    = split[0];
            this.postfix   = split[1];
            this.twoparts  = true;
        } else {
            this.prefix    = arg;
            this.postfix   = "";
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
            this.prefix   = arg[0];
            this.postfix  = arg[1];
            this.twoparts = true;
        } else {
            this.prefix   = arg[0];
            this.postfix  = "";
            this.twoparts = false;
        }

    }

    /**
     * Returns the Prefix of the Argument.
     *
     * @return the Prefix
     */
    public String getPrefix() {

        return this.prefix;

    }

    /**
     * Returns the Postfix of the Argument.
     *
     * @return the Postfix
     */
    public String getPostfix() {

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

    @Override
    public String toString() {

        if (this.twoparts) {
            return this.prefix + "," + this.postfix;
        } else {
            return this.prefix;
        }

    }

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

}
