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

public class Memory {

    private String position;
    private int value;

    /**
     * Instantiates a new speicher.
     */
    public Memory() {
        this.value = 0;
        this.position = "HEAD";
    }

    /**
     * Instantiates a new memory.
     *
     * @param bezeichner
     *            the bezeichner
     * @param data
     *            the data
     */
    public Memory(final String bezeichner, final int data) {
        this.position = bezeichner;
        this.value = data;
    }

    public final int getValue() {
        return value;
    }

    public final void setValue(final int givenValue) {
        this.value = givenValue;
    }

    public final String getPosition() {
        return position;
    }
}
