/*
 * @author Patrick "Tux" Kohan
 * @version 2011.04.19
 */
package m68000;

/**
 * The Class Program.
 */
public final class Program {

    /** The prev. */
    private Program prev;

    /** The next. */
    private Program next;

    /** The befehl. */
    private Command befehl;

    /** The marker. */
    private String marker;

    /** The argumente. */
    private Arguments argumente;

    /**
     * Instantiates a new program.
     */
    public Program() {
        prev = null;
        next = null;
        befehl = new Command("HEAD");
        marker = "HEAD";
        argumente = new Arguments("HEAD");
    }

    /**
     * Instantiates a new program.
     *
     * @param com the com
     * @param arg the arg
     * @param mark the mark
     */
    public Program(final Command com, final Arguments arg, final String mark) {
        this.marker = mark;
        this.befehl = com;
        this.argumente = arg;
    }

    /**
     * Instantiates a new program.
     *
     * @param pre the pre
     * @param nex the nex
     * @param com the com
     * @param arg the arg
     * @param mark the mark
     */
    public Program(final Program pre, final Program nex, final String com,
        final String arg, final String mark) {
        this.prev = pre;
        this.next = nex;
        this.marker = mark;
        this.befehl = new Command(com);
        this.argumente = new Arguments(arg);
        this.prev.setNext(this);
    }

    /**
     * Jump.
     *
     * @return the program
     */
    public Program jump() {
        Program helppointer = this;
        String search = this.argumente.getPrefix();
        helppointer = this.next;
        while (helppointer != null && !helppointer.marker.equals(search)) {
            helppointer = helppointer.next;
        }
        return helppointer;
    }

    /**
     * Sets the prev.
     *
     * @param pre the new prev
     */
    public void setPrev(final Program pre) {
        this.prev = pre;
    }

    /**
     * Gets the next.
     *
     * @return the next
     */
    public Program getNext() {
        return this.next;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Der Marker ist {");
        str.append(this.marker);
        str.append("}, der Befehl ist {");
        str.append(this.befehl);
        str.append("},das Argument ist {");
        str.append(this.argumente);
        str.append("}");
        return str.toString();
    }

    @Override
    public Program clone() {
        Program klon = new Program(this.befehl, this.argumente, this.marker);
        return klon;
    }

    /**
     * Gets the befehl.
     *
     * @return the befehl
     */
    public Command getBefehl() {
        return befehl;
    }

    /**
     * Sets the befehl.
     *
     * @param com the new befehl
     */
    public void setBefehl(final Command com) {
        this.befehl = com;
    }

    /**
     * Gets the marker.
     *
     * @return the marker
     */
    public String getMarker() {
        return marker;
    }

    /**
     * Sets the marker.
     *
     * @param mark the new marker
     */
    public void setMarker(final String mark) {
        this.marker = mark;
    }

    /**
     * Gets the argumente.
     *
     * @return the argumente
     */
    public Arguments getArgumente() {
        return argumente;
    }

    /**
     * Sets the argumente.
     *
     * @param arg the new argumente
     */
    public void setArgumente(final Arguments arg) {
        this.argumente = arg;
    }

    /**
     * Gets the prev.
     *
     * @return the prev
     */
    public Program getPrev() {
        return prev;
    }

    /**
     * Sets the next.
     *
     * @param nex the new next
     */
    public void setNext(final Program nex) {
        this.next = nex;
    }
}
