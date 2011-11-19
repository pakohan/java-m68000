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
    private final int MAX_BYTE = 1024;
    private int pointer = 0;

    /**
     * Instantiates a new RAM array.
     */
    public RAM() {
        this.memory = new int[MAX_BYTE];
    }

    /**
     * Adds memory.
     *
     * @param data the data
     * @return the int
     */
    public int addDataInMemory(final int... data) {
        for (int i = 0; i < data.length; i++) {
            this.memory[i + this.pointer] = data[i];
        }
        this.pointer = this.pointer + data.length;
        return this.pointer - data.length;
    }

    public byte getByteInAddress(final int address) {
        return (byte) (this.memory[address]>>24);
    }
    
    public int getWordInAddress(final int address) {
        return (this.memory[address]>>16);
    }
    
    public long getLongWordInAddress(final int address) {
        return this.memory[address];
    }

    public void setByteInAddress(final int address, int data) {
    	int tmp = 0;
    	tmp = ~tmp; //negate 32 zeros to 32 to ones
    	tmp = tmp >>8; //Higher 8 bits becomes zero.
    	
    	//Clear first 8 Bits:
    	this.memory[address] = this.memory[address] & tmp;
    	
    	//Set only the first 8 Bits:
    	data = data<<24;
        this.memory[address] = data | this.memory[address];
        
        System.out.println("Wert: " + getWordInAddress(address)); //Kommt momentan noch Blödsinn raus.
    }
    
    public void setWordInAddress(final int address, final int data) {
        //ToDO
    }
    
    public void setLongWordInAddress(final int address, final int data) {
        this.memory[address] = data;
    }
    

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < this.pointer; i++) {
            tmp.append(this.memory[i]).append("\n");
        }
        return tmp.toString();
    }
}
