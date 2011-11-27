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

    private int[] memory;
    public static final int MAX_BYTE = 1024;

    /**
     * Instantiates a new RAM array.
     */
    public RAM() {
        this.memory = new int[MAX_BYTE];
    }

    public int getByteInAddress(final int address) {
        return this.memory[address];
    }

    public int getWordInAddress(final int address) {
        //TODO
        return 0;
    }

    public long getLongWordInAddress(final int address) {
        //TODO
        return 0;
    }

    public void setByteInAddress(final int address, final int data) {
        this.memory[address] = data;
        ui.UI.ramdisplay.setRamValue(address, data);
    }

    public void setWordInAddress(final int address, final int data) {
        //TODO
    }

    public void setLongWordInAddress(final int address, final int data) {
        this.memory[address] = data;
    }

    /*
    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < this.pointer; i++) {
            tmp.append(this.memory[i]).append("\n");
        }
        return tmp.toString();
    }*/
}
