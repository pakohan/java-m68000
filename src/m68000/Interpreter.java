package m68000;

import java.io.*;

public class Interpreter {
	private Interpreter() {	}
	static Program prog = new Program();

	public final static Program einlesen(final String sourcefile) throws IOException {
		LineNumberReader source = new LineNumberReader(new FileReader(sourcefile));
		String[] part = new String[3];
		String line;


		while((line = source.readLine()) != null) {
			part = line.split(";");
			//part = part.split(" ", 3);
			addCommand(part);
			System.out.println(part.length);
		}
		
		/*Program link = first;
		while (link != null) {
			switch (link.getPrefix()) {
			case ORG:
				break;
			case BRA:
				break;
			case EQU :
				M68000.speicher.addSpeicher(link.marker);
				break;
			case DC :
				M68000.speicher.addSpeicher(link.marker);
				break;
			case DS :
				M68000.speicher.addSpeicherPlatz(link.marker);
				break;
			case CLR :
				break;
			case MOVE :
				break;
			case ADD :
				break;
			default :
				System.err.println("Befehl nicht gefunden!");
			}
			link = link.next;
		}*/
		return prog;
	}
	
	public final static void addCommand(String[] befehlsfolge) {
		switch (befehlsfolge.length) {
			case 1:
				prog.addCommand(befehlsfolge[0], "", "");
				break;				
			case 2:
				prog.addCommand(befehlsfolge[0], befehlsfolge[1], "");
				break;
			case 3:
				prog.addCommand(befehlsfolge[1], befehlsfolge[2], befehlsfolge[0]);
				break;
			default:
		}
	}
}
