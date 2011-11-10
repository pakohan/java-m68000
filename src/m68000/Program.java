package m68000;

public final class Program {
	private Program prev;
	private Program next;
	private Command befehl;
	private String marker;
	private Arguments argumente;
	
	public Program() {
		this(null, null, "HEAD", "HEAD", "HEAD");
	}
	
	public Program(final Program pre, final Program nex, final String com, final String arg, final String mark) {
		this.prev = pre;
		this.next = nex;
		this.marker = mark;
		this.befehl = new Command(com);
		this.argumente = new Arguments(arg);
	}
	
	public Program jump() {
		Program helppointer = this;
		String search = this.argumente.getArg();
		helppointer = this.next;
		while(helppointer != null && !helppointer.marker.equals(search)) {
			helppointer = helppointer.next;
		}
		return helppointer;
	}
	
	public void setPrev(final Program pre) {
		this.prev = pre;
	}
	
	public Program getNext() {
		return this.next;
	}
	
	@Override
	public final String toString() {
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

	public final Command getBefehl() {
		return befehl;
	}

	public final void setBefehl(Command befehl) {
		this.befehl = befehl;
	}

	public final String getMarker() {
		return marker;
	}

	public final void setMarker(String marker) {
		this.marker = marker;
	}

	public final Arguments getArgumente() {
		return argumente;
	}

	public final void setArgumente(Arguments argumente) {
		this.argumente = argumente;
	}

	public final Program getPrev() {
		return prev;
	}

	public final void setNext(Program next) {
		this.next = next;
	}
}