package m68000;

import m68000.Command.Befehlssatz;

public final class Program {
	private static Program pointer;
	private Program prev;
	private Program next;
	private Command befehl;
	private String marker;
	private Arguments argumente;
	
	static {
		pointer = new Program();
	}
	
	public Program() {
		this.setPrev(null);
		this.setNext(null);
		pointer.setPrev(this);
	}
	
	public Program(final String com, final String arg, final String mark) {
		this.befehl    = new Command(com); 
		this.argumente = new Arguments(arg);
		this.marker    = mark;
	}
	
	public void addCommand(final String com, final String arg, final String mark) {
		pointer = new Program(com, arg, mark);
		pointer.setPrev(this);
		this.setNext(pointer);
		pointer = this.getNext().getNext();
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
	
	public Befehlssatz getPrefix() {
		return this.befehl.getPrefix();
	}
	
	public void setPrev(final Program com) {
		this.prev = com;
	}
	
	public Program getNext() {
		return this.next;
	}
	
	public void printData() {
		System.out.printf("Der Marker ist {%s},"
				        + "der Befehl ist {%s},"
				        + "das Argument ist {%s}%n",
				        this.marker,
				        this.befehl,
				        this.argumente);
				

	}

	public final Program getPointer() {
		return pointer;
	}

	public final void setPointer(Program pointer) {
		this.pointer = pointer;
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