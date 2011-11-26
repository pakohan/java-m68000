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
 * Represents a code line.
 */
public final class CodeLine implements Cloneable {

    private Command command;
    private String label;
    private Argument argument;
	private int lineindex;

    /**
     * Instantiates a new line of code.
     */
    public CodeLine()  {
        this.command = new Command("HEAD");
        this.label = "HEAD";
        this.argument = new Argument("HEAD");
    }

    /**
     * Instantiates a new line of code.
     *
     * @param strings the line of code
     */
    public CodeLine( final int n, final String... strings) {
        this.command = new Command(strings[0]);
        this.argument = new Argument(strings[1]);
        this.label = strings[2];
        this.lineindex = n;
    }

    /**
     * Instantiates a new code line.
     *
     * @param com the command
     * @param arg the argument
     * @param label the label
     */
    public CodeLine(final int n,
    				final Command com,
                    final Argument arg,
                    final String marker) {
        this.command = com;
        this.argument = arg;
        this.label = marker;
        this.lineindex = n;
    }

    public Command getCommand() {
        return this.command;
    }

    public Argument getArgument() {
        return this.argument;
    }

    public String getLabel() {
        return this.label;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("The label ist {");
        str.append(this.label);
        str.append("}, the command is {");
        str.append(this.command);
        str.append("}, the argument is {");
        str.append(this.argument);
        str.append("}");
        return str.toString();
    }

    @Override
    public CodeLine clone() {
        CodeLine clone = new CodeLine(this.lineindex,
        							  this.command.clone(),
                                      this.argument.clone(),
                                      this.label);
        return clone;
    }

	public final int getLineindex() {
		return lineindex;
	}
}
