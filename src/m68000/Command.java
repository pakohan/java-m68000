package m68000;

import java.util.regex.Pattern;

public final class Command {
	static enum Befehlssatz {ORG, BRA, EQU, DC, DS, CLR, MOVE, ADD, END, ZERO};

	private Befehlssatz prefix;
	private String postfix;
	private boolean twoparts;
	
	public Command(final String str) {
		Pattern p = Pattern.compile("[.]");
		p.split(str);
		if (str.contains(".")) {
			String[] parts = new String[2];
			parts = p.split(str);
			this.postfix = parts[1];
			this.prefix = recognizePrefix(parts[0]);
			this.twoparts = true;
		} else {
			this.prefix = recognizePrefix(str);
			this.twoparts = false;
		}
	}
	
	public final boolean hastwoparts() {
		return this.twoparts;
	}
	
	private static Befehlssatz recognizePrefix(final String com) {
		Befehlssatz command = Befehlssatz.ZERO;
		if (com.equals("ORG")) {
			command = Befehlssatz.ORG;
		} else if (com.equals("BRA")) {
			command = Befehlssatz.BRA;
		} else if (com.equals("EQU")) {
			command = Befehlssatz.EQU;
		} else if (com.equals("DC")) {
			command = Befehlssatz.DC;
		} else if (com.equals("DS")) {
			command = Befehlssatz.DS;
		} else if (com.equals("CLR")) {
			command = Befehlssatz.CLR;
		} else if (com.equals("MOVE")) {
			command = Befehlssatz.MOVE;
		} else if (com.equals("ADD")) {
			command = Befehlssatz.ADD;
		} else if (com.equals("END")) {
			command = Befehlssatz.END;
		}
		return command;
	}
	public final Befehlssatz getPrefix() {
		return this.prefix;
	}

	public final String getPostfix() {
		return this.postfix;
	}
	
	@Override
	public final String toString() {
		if (this.twoparts) {
			return this.prefix + "," + this.postfix;
		} else {
			return "" + this.prefix;
		}
	}
}
