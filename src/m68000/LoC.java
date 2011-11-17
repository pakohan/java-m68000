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
 * The Class LoC.
 */
public final class LoC implements Cloneable {
    /** The befehl. */
    private Command befehl;

    /** The marker. */
    private String marker;

    /** The argumente. */
    private Argument argument;

    /**
     * Instantiates a new lo c.
     */
    public LoC()  {
        this.befehl = new Command("HEAD");
        this.marker = "HEAD";
        this.argument = new Argument("HEAD");
    }

    /**
     * Instantiates a new lo c.
     *
     * @param strings the strings
     */
    public LoC(final String... strings) {
        this.befehl = new Command(strings[0]);
        this.argument = new Argument(strings[1]);
        this.marker = strings[2];
    }

    /**
     * Instantiates a new lo c.
     *
     * @param com the com
     * @param arg the arg
     * @param mark the mark
     */
    public LoC(final Command com, final Argument arg, final String mark) {
        this.befehl = com;
        this.argument = arg;
        this.marker = mark;
    }

    /**
     * Gets the command.
     *
     * @return the command
     */
    public Command getCommand() {
        return this.befehl;
    }

    /**
     * Gets the argument.
     *
     * @return the argument
     */
    public Argument getArgument() {
        return this.argument;
    }

    /**
     * Gets the marker.
     *
     * @return the marker
     */
    public String getMarker() {
        return this.marker;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Der Marker ist {");
        str.append(this.marker);
        str.append("}, der Befehl ist {");
        str.append(this.befehl);
        str.append("},das Argument ist {");
        str.append(this.argument);
        str.append("}");
        return str.toString();
    }

    @Override
    public LoC clone() {
        LoC klon = new LoC(this.befehl.clone(), this.argument.clone(),
                this.marker);
        return klon;
    }
}
