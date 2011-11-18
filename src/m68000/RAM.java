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
 * The Class RAMArray.
 */
public final class RAM {

    /** The speicher. */
    private int[] speicher;

    /** The Constant MAXBYTE. */
    static final int MAXBYTE = 1024;

    /** The pointer. */
    private int pointer = 0;

    /**
     * Instantiates a new rAM array.
     */
    public RAM() {
        this.speicher = new int[MAXBYTE];
    }

    /**
     * Adds the speicher.
     *
     * @param data the data
     * @return the int
     */
    public int addSpeicher(final int... data) {
        for (int i = 0; i < data.length; i++) {
            this.speicher[i + this.pointer] = data[i];
        }
        this.pointer = this.pointer + data.length;
        return this.pointer - data.length;
    }

    /**
     * Gets the byte.
     *
     * @param adress the adress
     * @return the byte
     */
    public int getByte(final int adress) {
        return this.speicher[adress];
    }

    /**
     * Sets the byte.
     *
     * @param adress the adress
     * @param data the data
     */
    public void setByte(final int adress, final int data) {
        this.speicher[adress] = data;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < this.pointer; i++) {
            tmp.append(this.speicher[i]).append("\n");
        }
        return tmp.toString();
    }
}
