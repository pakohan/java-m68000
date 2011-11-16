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
 * The Class Speicher.
 */
public class Speicher {

    /** The stelle. */
    private String stelle;

    /** The wert. */
    private int wert;

    /**
     * Instantiates a new speicher.
     */
    public Speicher() {
        this.wert = 0;
        this.stelle = "HEAD";
    }

    /**
     * Instantiates a new speicher.
     *
     * @param bezeichner the bezeichner
     * @param data the data
     */
    public Speicher(final String bezeichner, final int data) {
        this.stelle = bezeichner;
        this.wert = data;
    }

    /**
     * Gets the wert.
     *
     * @return the wert
     */
    public final int getWert() {
        return wert;
    }

    /**
     * Sets the wert.
     *
     * @param x the new wert
     */
    public final void setWert(final int x) {
        this.wert = x;
    }

    /**
     * Gets the stelle.
     *
     * @return the stelle
     */
    public final String getStelle() {
        return stelle;
    }
}
