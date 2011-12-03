/*
 * Copyright (C) Patrick Kohan 2011 <patrick.kohan@googlemail.com>
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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

import ui.UI.MemoryDisplay;

public final class TopMenuBar {
    private TopMenuBar() {
    }

    private static Program prog;
    private static String str;
    private static MenuItem settings;
    private static MemoryDisplay mdtmp = MemoryDisplay.BIN;
    private static RamDisplay ramdisplay;
    private static SettingsWindow sw;

    public static MenuBar createMenuBar() {
        ramdisplay = new RamDisplay();
        sw = new SettingsWindow();

        MenuBar menuBar = new MenuBar();
        Menu menufile = new Menu();

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

        menufile.append(open);

        MenuItem ram = new MenuItem("Display RAM");
        ram.connect(new MenuItem.Activate() {
            @Override
            public void onActivate(final MenuItem source) {
                ramdisplay.showAll();
            }
        });
        menufile.append(ram);

        settings = new MenuItem("Einstellungen");
        settings.connect(new MenuItem.Activate() {
            @Override
            public void onActivate(final MenuItem source) {
                sw.showAll();
            }
        });
        menufile.append(settings);

        MenuItem exit = new MenuItem("Exit");
        exit.connect(new MenuItem.Activate() {

            @Override
            public void onActivate(final MenuItem arg0) {
                Gtk.mainQuit();
            }
        });
        menufile.append(exit);

        MenuItem menu1 = new MenuItem("File");
        menu1.setSubmenu(menufile);
        menuBar.append(menu1);

        Menu menuhelp = new Menu();
        MenuItem about = new MenuItem("About ...");
        about.connect(new MenuItem.Activate() {
            @Override
            public void onActivate(final MenuItem source) {
                AboutDialog aboutdialog = About.createAboutDialog();
                aboutdialog.run();
                aboutdialog.hide();
            }
        });
        menuhelp.append(about);
        MenuItem menu2 = new MenuItem("About");
        menu2.setSubmenu(menuhelp);
        menuBar.append(menu2);
        return menuBar;
    }

    public static void ramdisplaySetValue(final int n, final int x) {
        ramdisplay.setRamValue(n, x);
    }

    public static void setMdtmp(final MemoryDisplay memory) {
        TopMenuBar.mdtmp = memory;
    }

    public static void loadsource() {
        Scanner scan;
        UI.setMd(mdtmp);
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
        } catch (Exception e) {
            UI.printMessage("Datei \""
                    + str
                    + "\" konnte nicht geladen werden");
        }
        ramdisplay.rebuildTable();
        DataTable.rebuildDataTable();
        AdressTable.rebuildAddressTable();
        try {
            prog = new Program(str);
        } catch (IOException e) {
            UI.printMessage("Datei \""
                    + str
                    + "\" konnte nicht geladen werden (2. Versuch)");
        }
        TextTag font = new TextTag();
        font.setFont("Monospace");
        UI.getFilebuffer().applyTag(font, UI.getFilebuffer().getIterStart(),
                UI.getFilebuffer().getIterEnd());
        UI.setCore1(new Processor(prog));
        UI.setSensitive(true);
    }
}
