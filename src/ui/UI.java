package ui;

import java.io.IOException;

import org.gnome.gtk.Button;
import org.gnome.gtk.ErrorMessageDialog;
import org.gnome.gtk.FileChooserButton;
import org.gnome.gtk.Gtk;

import m68000.Processor;
import m68000.Program;
import m68000.RAM;

public final class UI {
	static MainWindow main;
	static Program prog;
	static Processor core1;
	static String str;
	static ErrorMessageDialog window2;

	private UI() { }
	
	public static void main(final String[] args) {
        Gtk.init(args);

		main = new MainWindow();
		
		FileChooserButton button = main.getFileChooserButton();
		button.connect(new FileChooserButton.FileSet() {
			
			@Override
			public void onFileSet(FileChooserButton source) {
				str = source.getFilename();
				try {
					prog = new Program(str);
				} catch (IOException e) { }
				core1 = new Processor(prog);


		        //UI.main.appendMessage(prog.getRAM().toString());

		        
			}
		});
		window2 = new ErrorMessageDialog(main.getWindow(), "FEHLER", "Sie müssen zuerst eine Assembler-Datei angeben");		
		
		Button action = main.getAction();
		action.connect(new Button.Clicked() {
			
			@Override
			public void onClicked(Button source) {
				if (core1 == null) {
					window2.run();
					window2.hide();
				} else {
					core1.run();
					RAM x = prog.getRAM();

					for (int i = 0; i < 10; i++) {
						main.appendMessage(new Integer(x.getByteInAddress(i)).toString());
					}
					main.appendMessage("test");
				}
			}
		});
		Gtk.main();
	}
	
	public void writeMessage(final String msg) {
		main.appendMessage(msg);
	}
}
