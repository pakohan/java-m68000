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

import java.util.Scanner;

/**
 * The Class RAM.
 */
public final class RAM {

    /** The SCANNER. */
    private static final Scanner SCANNER = new Scanner(System.in);

    /** The register. */
    private Speicher register;


    /**
     * Instantiates a new rAM.
     */
    public RAM() {
        this.register = new Speicher();
    }

    /**
     * Adds the speicher.
     *
     * @param stelle the stelle
     */
    public void addSpeicher(final String stelle) {
        System.out.printf("Welcher Wert ist an stelle %s im Haupstspeicher"
              + " gespeichert?", stelle);
        this.register.next = new Speicher(this.register.next, stelle,
                SCANNER.nextInt());
    }
    
    public void stepnext() {
    	this.register = this.register.next;
    }

    /**
     * Prints the data.
     */
    public String toString() {
        return "An der Speicherstelle " + this.register.stelle +
        		" beträgt der Wert " + this.register.wert;
    }

    /**
     * Gets the data.
     *
     * @param str the str
     * @return the data
     */
    public int getData(final String str) {
    	return this.register.getData(str);
    }

    /**
     * Sets the.
     *
     * @param postfix the postfix
     * @param i the i
     */
    public void set(final String postfix, final int i) {
    	this.register.setData(postfix, i);
    }
    public String getStelle() {
    	return this.register.stelle;
    }
    
    public Speicher getNext() {
    	return this.register.next;
    }
    /**
     * The Class Speicher.
     */
    static final class Speicher {

        /** The stelle. */
        private String stelle;

        /** The wert. */
        private int wert;

		private Speicher next;

        /**
         * Instantiates a new speicher.
         */
        public Speicher() {
            this.wert = 0;
            this.stelle = "HEAD";
            this.next = this;
        }

        /**
         * Instantiates a new speicher.
         *
         * @param bezeichner the bezeichner
         * @param data the data
         */
        public Speicher(final Speicher head, final String bezeichner, final int data) {
            this.stelle = bezeichner;
            this.wert = data;
            this.next = head;
        }
        
        public String getStelle() {
        	return this.stelle;
        }
        
        public Speicher getNext() {
        	return this.next;
        }

        /**
         * Sets the data at the given storage adress.
         *
         * @param postfix the postfix
         * @param i the i
         */
        public void setData(final String speicheradresse, final int i) {
            if (this == null) {
                System.err.println("FEHLER!");
            }
            if (this.stelle.equals(speicheradresse)) {
            	this.wert = i;
                return;
            } else {
                this.next.setData(speicheradresse, i);
            }
        }

        /**
         * Search for the storage adress and return its contents.
         *
         * @param arg the arg
         * @return the int
         */
        public int getData(final String arg) {
            if (this == null) {
                System.out.println("FEHLER!");
                return 0;
            }
            if (this.stelle.equals(arg)) {
                return this.wert;
            } else {
                return this.next.getData(arg);
            }
        }
    }

	public void addSpeicher(String marker, String prefix) {
		Scanner sc = new Scanner(prefix);
		int x;
		if (sc.hasNextInt()) {
			x = sc.nextInt();
		} else {
		System.out.printf("Welcher Wert ist an stelle %s im Haupstspeicher"
                + " gespeichert?", marker);
		x = SCANNER.nextInt();
		}
          this.register.next = new Speicher(this.register.next, marker, x);		
	}

}
