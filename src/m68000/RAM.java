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

    private byte[] memory;
    public static final int MAX_BYTE = 4096;

    public RAM() {
        this.memory = new byte[MAX_BYTE];
    }

    public int getByteInAddress(final int address) {
        return this.memory[address];
    }

    public void setByteInAddress(final int address, final byte data) {
        this.memory[address] = data;
        ui.UI.ramdisplay.setRamValue(address, data & 0xFF);
    }

    public short getWordInAddress(final int address) {
        short x;
        x = (short) ((getByteInAddress(address) << 8) & 0xFF00);
        x = (short) (x + (getByteInAddress(address + 1) & 0x00FF));
        return x;
    }

    public void setWordInAddress(final int address, final short data) {
        short x = data;
        setByteInAddress(address + 1, (byte) x);
        x = (byte) (x >>> 8);
        setByteInAddress(address, (byte) x);
    }

    public int getLongWordInAddress(final int address) {
        int x;
        x = ((getWordInAddress(address) << 16) & 0xFFFF0000);
        x = x + ((getWordInAddress(address + 2)) & 0x0000FFFF);
        return x;
    }

    public void setLongWordInAddress(final int address, final int data) {
        int x = data;
        setWordInAddress(address + 2, (short) x);
        x = (short) (x >>> 16);
        setWordInAddress(address, (short) x);
    }
}
