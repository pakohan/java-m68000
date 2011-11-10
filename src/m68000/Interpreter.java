package m68000;

import java.io.*;
import java.util.Scanner;

import m68000.Command.Befehlssatz;

public class Interpreter {
	private Interpreter() {	}
	static Program prog;
	
	static {
		prog = new Program();
		prog.setNext(prog);
		prog.setPrev(prog);

	}

	public final static Program einlesen(final String sourcefile) throws IOException {
		LineNumberReader source = new LineNumberReader(new FileReader(sourcefile));
		String[] part = new String[3];
		String line;


		while((line = source.readLine()) != null) {
			part = line.split(";");
			addCommand(recognizeLine(part[0]));
		}
		
		while (prog.getNext().getBefehl().getPrefix() != Befehlssatz.HEAD) {
			prog = prog.getNext();
			switch (prog.getBefehl().getPrefix()) {
			case ORG:
				break;
			case BRA:
				break;
			case EQU :
				M68000.speicher.addSpeicher(prog.getMarker());
				break;
			case DC :
				M68000.speicher.addSpeicher(prog.getMarker());
				break;
			case DS :
				M68000.speicher.addSpeicherPlatz(prog.getMarker());
				break;
			case CLR :
				break;
			case MOVE :
				break;
			case ADD :
				break;
			case HEAD :
				break;
			case END :
				break;
			default :
				System.err.println("Befehl nicht gefunden!" + prog.getBefehl().getPrefix());
			}
		}
		prog = prog.getNext();
		return prog;
	}
	
	public final static String[] recognizeLine(final String str) {
		Scanner scan = new Scanner(str);
		String[] parts;
		String[] parts2 = new String[3];
		int j = 0;
		while (scan.hasNext()) {
			parts2[j] = scan.next();
			j++;
		}
		parts = new String[j];
		for (int i = 0; i < j; i++) {
			parts[i] = parts2[i];
		}
		return parts;
	}
	
	public final static void addCommand(String[] befehlsfolge) {
		switch (befehlsfolge.length) {
			case 1:
				prog.setPrev(new Program(prog.getPrev(), prog, befehlsfolge[0], "", ""));
				break;				
			case 2:
				prog.setPrev(new Program(prog.getPrev(), prog, befehlsfolge[0], befehlsfolge[1], ""));
				break;
			case 3:
				prog.setPrev(new Program(prog.getPrev(), prog, befehlsfolge[1], befehlsfolge[2], befehlsfolge[0]));
				break;
			default:
		}
	}
}
