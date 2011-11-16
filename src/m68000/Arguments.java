/*
 * @author Patrick "Tux" Kohan
 * @version 2011.04.19
 */
package m68000;
/**
 * The Class Arguments.
 */
public final class Arguments {

    /** The prefix. */
    private String prefix;

    /** The postfix. */
    private String postfix;

    /** The twoparts. */
    private boolean twoparts;

    /**
     * Instantiates a new argument.
     *
     * @param arg the arg
     */
    public Arguments(final String arg) {
        if (arg.contains(",")) {
            String[] split = new String[2];
            split = arg.split(",");
            this.prefix = split[0];
            this.postfix = split[1];
            this.twoparts = true;
        } else {
            this.prefix = arg;
            this.postfix = "";
            this.twoparts = false;
        }
    }

    /**
     * Gets the arg.
     *
     * @return the arg
     */
    public String getPrefix() {
        return this.prefix;
    }

    /**
     * Gets the postfix.
     *
     * @return the postfix
     */
    public String getPostfix() {
        return this.postfix;
    }

    /**
     * Hastwoparts.
     *
     * @return true, if successful
     */
    public boolean hastwoparts() {
        return this.twoparts;
    }

    @Override
    public String toString() {
        if (this.twoparts) {
            return this.prefix + "," + this.postfix;
        } else {
            return this.prefix;
        }
    }
}
