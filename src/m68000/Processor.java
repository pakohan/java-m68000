package m68000;

public class Processor {
	private int[] Datenregister = new int[8];
	private Program exe;
	
	public Processor(final Program programm) {
		this.exe = programm;
	}
	
	public final void step() {
		System.out.println(this.exe);
		if (this.exe.getNext() == null) {
			throw new NullPointerException("Programm fertig");
		}
		switch (this.exe.getBefehl().getPrefix()) {
		case ORG:
			break;
		case BRA:
			this.exe = this.exe.jump();
			System.out.println(this.exe);
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
		default :
			System.err.println("Befehl nicht gefunden!");
		}
		this.exe = this.exe.getNext();
	}
	
	public final boolean nextFinish() {
		return (this.exe == null);
	}
}
