package m68000;

import java.io.IOException;

import m68000.Command.Befehlssatz;

public final class M68000 {
	public static RAM speicher = new RAM();

	public static void main(String[] args) throws IOException {
		Program prog = Interpreter.einlesen(args[0]);
		
		/*
		Processor core_1 = new Processor(prog);
		Program pro2 = prog;
		
		while (prog.getBefehl().getPrefix() != Befehlssatz.END) {
			System.out.println(prog);
			prog = prog.getNext();
		}
		
		while (pro2 != null) {
			System.out.println("pro2");
			pro2 = pro2.getNext();
		}
		try {
			while(!core_1.nextFinish()) {
				core_1.step();
			}
		} catch (NullPointerException x) {
			System.err.println("ERR");
		} finally {
			speicher.printData();
		}*/
		
	}
}
