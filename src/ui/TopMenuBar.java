package ui;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import m68000.Processor;
import m68000.Program;

import org.gnome.gtk.AboutDialog;
import org.gnome.gtk.FileChooserDialog;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Menu;
import org.gnome.gtk.MenuBar;
import org.gnome.gtk.MenuItem;
import org.gnome.gtk.TextIter;

public class TopMenuBar {
	private TopMenuBar() { }
	
	public static MenuBar createMenuBar() {
		MenuBar menuBar = new MenuBar();
		Menu menu_file = new Menu();
		
		MenuItem open = new MenuItem("Open ...");
		open.connect(new MenuItem.Activate() {
			@Override
			public void onActivate(MenuItem source) {
				FileChooserDialog dialog = FileC.createFileChooserDialog();
				dialog.run();
				dialog.hide();
				String str = dialog.getFilename();
				if (str != null) {
					try {
						TextIter end;
						Scanner scan = new Scanner(new File(str));
						while (scan.hasNextLine()) {
							end = UI.filebuffer.getIterEnd();
							UI.filebuffer.insert(end, scan.nextLine());
							UI.filebuffer.insert(end, "\n");
						}
						UI.prog = new Program(str);
					} catch (IOException e) { }
					UI.printMessage("Datei \""+ str +"\" geladen");
					UI.core1 = new Processor(UI.prog);
					UI.run.setSensitive(true);
					UI.step.setSensitive(true);
				}
			}
		});

		menu_file.append(open);
		
		MenuItem exit = new MenuItem("Exit");
		exit.connect(new MenuItem.Activate() {
			
			@Override
			public void onActivate(MenuItem arg0) {
                Gtk.mainQuit();
			}
		});
		
		menu_file.append(exit);
		MenuItem menu1 = new MenuItem("File");
		menu1.setSubmenu(menu_file);
		menuBar.append(menu1);
		
		Menu menu_help = new Menu();
		MenuItem about = new MenuItem("About ...");
		about.connect(new MenuItem.Activate() {
			@Override
			public void onActivate(MenuItem source) {
				AboutDialog aboutdialog = About.createAboutDialog();
				aboutdialog.run();
				aboutdialog.hide();
			}
		});
		menu_help.append(about);
		MenuItem menu2 = new MenuItem("About");
		menu2.setSubmenu(menu_help);
		menuBar.append(menu2);
		return menuBar;
	}
}
