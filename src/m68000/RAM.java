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
    private LinkedList<Speicher> register;


    /**
     * Instantiates a new rAM.
     */
    public RAM() {
        this.register = new LinkedList<Speicher>(new Speicher());
    }

    /**
     * Adds the speicher.
     *
     * @param stelle the stelle
     */
    public void addSpeicher(final String stelle) {
        System.out.printf("Welcher Wert ist an stelle %s im Haupstspeicher"
              + " gespeichert?", stelle);
        this.register.add(new Speicher(stelle, SCANNER.nextInt()));
    }

    @Override
    public String toString() {
        LinkedList<Speicher> tmp = this.register.getNext();
        StringBuilder str = new StringBuilder();
        while (!tmp.getItem().getStelle().equals("HEAD")) {
            str.append("An der Speicherstelle ")
                .append(tmp.getItem().getStelle())
                .append(" beträgt der Wert ")
                .append(tmp.getItem().getWert())
                .append("\n");
            tmp = tmp.getNext();
        }
        return str.toString();
    }

    /**
     * Gets the data.
     *
     * @param str the str
     * @return the data
     */
    public int getData(final String str) {
        LinkedList<Speicher> tmp = this.register.getNext();
        while (!tmp.getItem().getStelle().equals("HEAD")) {
            if (tmp.getItem().getStelle().equals(str)) {
                return tmp.getItem().getWert();
            }
            tmp = tmp.getNext();
        }
        return 0;
    }

    /**
     * Sets the.
     *
     * @param str the postfix
     * @param x the x
     */
    public void set(final String str, final int x) {
        LinkedList<Speicher> tmp = this.register.getNext();
        while (!tmp.getItem().getStelle().equals("HEAD")) {
            if (tmp.getItem().getStelle().equals(str)) {
                tmp.getItem().setWert(x);
                break;
            }
            tmp = tmp.getNext();
        }
    }

    /**
     * Adds the speicher.
     *
     * @param stelle the stelle
     * @param prefix the prefix
     */
    public void addSpeicher(final String stelle, final String prefix) {
        Scanner sc = new Scanner(prefix);
        int x;
        if (sc.hasNextInt()) {
            x = sc.nextInt();
        } else {
        System.out.printf("Welcher Wert ist an stelle %s im Haupstspeicher"
                + " gespeichert?", stelle);
        x = SCANNER.nextInt();
        }
        this.register.add(new Speicher(stelle, x));
    }

}
