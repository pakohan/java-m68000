package m68000;

import java.io.IOException;

public final class M68000 {
	public static RAM speicher = new RAM();

	public static void main(String[] args) throws IOException {
		Program prog = Interpreter.einlesen(args[0]);
		/*
		while (prog.getNext().getBefehl().getPrefix() != Befehlssatz.HEAD) {
			prog = prog.getNext();
			System.out.println(prog);
		}*/
		Processor core_1 = new Processor(prog);
		
		while(!core_1.isfinished()) {
			core_1.step();
		}
		speicher.printData();
		
		
	}
}
