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
import org.gnome.gtk.TextTag;

public final class TopMenuBar {
    private TopMenuBar() { }
    private static Program prog;
    private static String str;
    private static MenuItem ram;

    public static MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menu_file = new Menu();

        MenuItem open = new MenuItem("Open ...");
        open.connect(new MenuItem.Activate() {
            @Override
            public void onActivate(final MenuItem source) {
                FileChooserDialog dialog = FileC.createFileChooserDialog();
                dialog.run();
                dialog.hide();
                str = dialog.getFilename();
                if (str != null) {
                	loadsource();
                }
            }
        });

        menu_file.append(open);

        ram = new MenuItem("Display RAM");
        ram.connect(new MenuItem.Activate() {
            @Override
            public void onActivate(final MenuItem source) {
                UI.ramdisplay.window.showAll();
            }
        });
        menu_file.append(ram);

        MenuItem exit = new MenuItem("Exit");
        exit.connect(new MenuItem.Activate() {

            @Override
            public void onActivate(final MenuItem arg0) {
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
            public void onActivate(final MenuItem source) {
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
    
    public static void loadsource() {
    	Scanner scan;
    	try {
			scan = new Scanner(new File(str));
            UI.printMessage("Datei \"" + str + "\" geladen");
            UI.clearFileBuffer();
            String line;
            TextIter end;
            while (scan.hasNextLine()) {
                end = UI.getFilebuffer().getIterEnd();
                line = scan.nextLine();
                if (!line.isEmpty()) {
                    UI.getFilebuffer().insert(end, line);
                    UI.getFilebuffer().insert(end, "\n");
                }
            }
		} catch (Exception e) { }
        UI.ramdisplay.rebuildTable();
        DataTable.rebuildDataTable();
        AdressTable.rebuildAddressTable();
        try {
			prog = new Program(str);
		} catch (IOException e) { }
        TextTag font = new TextTag();
        font.setFont("Monospace");
        UI.getFilebuffer().applyTag(font,
                                    UI.getFilebuffer().getIterStart(),
                                    UI.getFilebuffer().getIterEnd());
        UI.setCore1(new Processor(prog));
        UI.setSensitive(true);
    }
}
