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
			if (this.befehle.argumente.getArg().equals("D1")) {
				this.Datenregister[1] = 0;
			}
			break;
		case  MOVE :
			if (this.befehle.argumente.getPostfix().equals("D1")) {
				this.Datenregister[1] = M68000.speicher.getData(this.befehle.argumente.getArg());
			} else if (this.befehle.argumente.getArg().equals("D1")) {
				M68000.speicher.set(this.befehle.argumente.getPostfix() ,this.Datenregister[1]);
			}
			break;
		case ADD :
			if (this.befehle.argumente.getPostfix().equals("D1")) {
				this.Datenregister[1] = this.Datenregister[1] + M68000.speicher.getData(this.befehle.argumente.getArg());
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
