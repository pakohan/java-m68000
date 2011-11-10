/*
 * @author Patrick "Tux" Kohan
 * @version 2011.04.19
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
        this.register = this.register.add(this.register, stelle,
                SCANNER.nextInt());
    }

    /**
     * Prints the data.
     */
    public void printData() {
        while (this.register.prev != null) {
            System.out.printf("An der Speicherstelle %s beträgt der Wert "
                  + "%d%n%n", this.register.stelle, this.register.wert);
        this.register = this.register.prev;
        }
    }

    /**
     * Gets the data.
     *
     * @param str the str
     * @return the data
     */
    public int getData(final String str) {
        Speicher pointer = this.register;
        while (pointer != null) {
            if (pointer.stelle.equals(str)) {
                return pointer.wert;
            }
            pointer = pointer.next;
        }
        return 0;
    }

    /**
     * Sets the.
     *
     * @param postfix the postfix
     * @param i the i
     */
    public void set(final String postfix, final int i) {
        Speicher pointer = this.register;
        pointer.set(postfix, i);
    }

    /**
     * Adds the speicher platz.
     *
     * @param stelle the stelle
     */
    public void addSpeicherPlatz(final String stelle) {
        this.register = this.register.add(this.register, stelle, 0);
    }

    /**
     * The Class Speicher.
     */
    static final class Speicher {

        /** The stelle. */
        private String stelle;

        /** The wert. */
        private int wert;

        /** The next. */
        private Speicher next;

        /** The prev. */
        private Speicher prev;

        /**
         * Instantiates a new speicher.
         */
        public Speicher() {
            this.next = null;
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
         * Adds the.
         *
         * @param old the old
         * @param bezeichner the bezeichner
         * @param data the data
         * @return the speicher
         */
        public Speicher add(final Speicher old, final String bezeichner,
                final int data) {
            this.next = new Speicher(bezeichner, data);
            this.next.prev = this;
            return this.next;
        }

        /**
         * Sets the.
         *
         * @param postfix the postfix
         * @param i the i
         */
        public void set(final String postfix, final int i) {
            if (this == null) {
                System.out.println("FEHLER!");
            }
            if (this.stelle.equals(postfix)) {
                return;
            } else {
                this.next.set(postfix, i);
            }
        }

        /**
         * Search.
         *
         * @param arg the arg
         * @return the int
         */
        public int search(final String arg) {
            if (this == null) {
                System.out.println("FEHLER!");
            }
            if (this.stelle.equals(arg)) {
                return this.wert;
            } else {
                return this.next.search(arg);
            }
        }
    }

}
