package m68000;

public class Processor {
	private int[] Datenregister = new int[8];
	private Program befehle;
	
	public Processor(final Program programm) {
		this.befehle = programm;
	}
	
	public final void step() {
		this.befehle.printData();
		if (this.befehle.getNext() == null) {
			throw new NullPointerException("Programm fertig");
		}
		switch (this.befehle.getPrefix()) {
		case ORG:
			break;
		case BRA:
			this.befehle = this.befehle.jump();
			this.befehle.printData();
			break;
		case EQU :
			break;
		case DC :
			break;
		case DS :
			break;
		case CLR :
			if (this.befehle.getArgumente().getArg().equals("D1")) {
				this.Datenregister[1] = 0;
			}
			break;
		case  MOVE :
			if (this.befehle.getArgumente().getPostfix().equals("D1")) {
				this.Datenregister[1] = M68000.speicher.getData(this.befehle.getArgumente().getArg());
			} else if (this.befehle.getArgumente().getArg().equals("D1")) {
				M68000.speicher.set(this.befehle.getArgumente().getPostfix() ,this.Datenregister[1]);
			}
			break;
		case ADD :
			if (this.befehle.getArgumente().getPostfix().equals("D1")) {
				this.Datenregister[1] = this.Datenregister[1] + M68000.speicher.getData(this.befehle.getArgumente().getArg());
			}
			break;
		default :
			System.err.println("Befehl nicht gefunden!");
		}
		this.befehle = this.befehle.getNext();
	}
	
	public final boolean nextFinish() {
		return (this.befehle == null);
	}
}
