package m68000;

public class Processor {
	private int[] Datenregister = new int[8];
	private Program exe;
	private boolean finished = false;
	
	public Processor(final Program programm) {
		this.exe = programm;
	}
	
	public final void step() {
		
		exe = exe.getNext();
		System.err.println(this.exe);

		switch (this.exe.getBefehl().getPrefix()) {
		case ORG:
			break;
		case BRA:
			this.exe = this.exe.jump();
			break;
		case EQU :
			break;
		case DC :
			break;
		case DS :
			break;
		case CLR :
			if (this.exe.getArgumente().getArg().equals("D1")) {
				this.Datenregister[1] = 0;
			}
			break;
		case  MOVE :
			if (this.exe.getArgumente().getPostfix().equals("D1")) {
				this.Datenregister[1] = M68000.speicher.getData(this.exe.getArgumente().getArg());
			} else if (this.exe.getArgumente().getArg().equals("D1")) {
				M68000.speicher.set(this.exe.getArgumente().getPostfix() ,this.Datenregister[1]);
			}
			break;
		case ADD :
			if (this.exe.getArgumente().getPostfix().equals("D1")) {
				this.Datenregister[1] = this.Datenregister[1] + M68000.speicher.getData(this.exe.getArgumente().getArg());
			}
			break;
		case HEAD :
			break;
		case END :
			this.finished = true;
			break;
		default :
			System.err.println("Befehl nicht gefunden!");
		}
	}
	
	public final boolean isfinished() {
		return finished;
	}
}
