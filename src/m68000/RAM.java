package m68000;

import java.util.Scanner;

public final class RAM {
	private Scanner SCANNER = new Scanner(System.in);
	Speicher Register;


	public RAM() {
		this.Register = new Speicher();
	}
	
	public final void addSpeicher(final String stelle) {
		System.out.printf("Welcher Wert ist an Stelle %s im Haupstspeicher gespeichert?", stelle);
		this.Register = this.Register.add(this.Register, stelle, SCANNER.nextInt());
	}
	
	public final void printData() {
		while (this.Register.prev != null) {
			System.out.printf("An der Speicherstelle %s beträgt der Wert %d%n%n", this.Register.Stelle, this.Register.wert);
		this.Register = this.Register.prev;
		}
	}
	
	public int getData(final String str) {
		Speicher pointer = this.Register;
		while (pointer != null) {
			if (pointer.Stelle.equals(str)) {
				return pointer.wert;
			}
			pointer = pointer.next;
		}
		return 0; 
	}

	public void set(String postfix, int i) {
		Speicher pointer = this.Register;
		pointer.set(postfix, i);
	}
	
	public void addSpeicherPlatz(final String stelle) {
		this.Register = this.Register.add(this.Register, stelle, 0);		
	}
	
	static final class Speicher {
		String Stelle;
		int wert;
		Speicher next;
		Speicher prev;

		public Speicher() {
			this.next = null;
		}
		
		public Speicher(String bezeichner, int data) {
			this.Stelle = bezeichner;
			this.wert = data;
		}

		public Speicher add(final Speicher old, final String bezeichner, final int data) {
			this.next = new Speicher(bezeichner, data);
			this.next.prev = this;
			return this.next;
		}

		public void set(String postfix, int i) {
			if (this == null) {
				System.out.println("FEHLER!");
			}
			if (this.Stelle.equals(postfix)) {
				return;
			} else {
				this.next.set(postfix, i);
			}
		}
		
		public final int search(final String arg) {
			if (this == null) {
				System.out.println("FEHLER!");
			}
			if (this.Stelle.equals(arg)) {
				return this.wert;
			} else {
				return this.next.search(arg);
			}
		}
	}

}
