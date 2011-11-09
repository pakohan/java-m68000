package m68000;

import m68000.Command.Befehlssatz;

public final class Program {
	Program prev;
	Program next;
	Command befehl;
	String marker;
	Arguments argumente;
	
	public Program() {
		this.prev = null;
		this.next = null;
	}
	
	public Program(final String com, final String arg, final String mark) {
		this.befehl = new Command(com); 
		this.argumente = new Arguments(arg);
		this.marker = mark;
		this.next = null;
	}
	
	public Program addCommand(final String com, final String arg, final String mark) {
		this.next = new Program(com, arg, mark);
		this.next.prev = this;
		return this.next;
	}
	
	public void setPrev(final Program com) {
		this.prev = com;
	}
	
	public Program jump() {
		Program pointer = new Program();
		String search = this.argumente.getArg();
		pointer = this.next;
		while(pointer != null && !pointer.marker.equals(search)) {
			pointer = pointer.next;
		}
		return pointer;
	}
	
	public Befehlssatz getPrefix() {
		return this.befehl.getPrefix();
	}
	
	public Program getNext() {
		return this.next;
	}
	
	public void printData() {
		System.out.printf("Der Marker ist {%s}, der Befehl ist {%s}, das Argument ist {%s}%n",
				this.marker, this.befehl, this.argumente);
				

	}
}