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
package swing;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

import org.gnome.gtk.AboutDialog;

public final class About {
    private About() {
    }

    private static String[] authors = {"Patrick Kohan", "Stefano DiMartino",
            "Tobias Keh" };
    private static String[] commenters = {"Patrick Kohan" };

    public static AboutDialog createAboutDialog() {
        AboutDialog aboutdialog = new AboutDialog();
        aboutdialog.setCopyright("(c) 2011");
        aboutdialog.setProgramName("Java-M68000");
        aboutdialog.setComments("Ein einfacher Simulator für einen M68000");
        aboutdialog.setAuthors(authors);
        aboutdialog.setVersion("2011.12.04 - r100");
        aboutdialog.setDocumenters(commenters);
        aboutdialog.setWebsite("code.google.com/p/java-m68000");
        aboutdialog.setWebsiteLabel("Projet auf Google Code");
        aboutdialog.setLogo(Ressources.getIcon());

        try {
            FileReader fr = new FileReader(
                    "/home/mogli/Dokumente/Programmierung/M68000/src/COPYING");
            Scanner scan = new Scanner(fr);
            StringBuilder s = new StringBuilder();
            while (scan.hasNextLine()) {
                s.append(scan.nextLine()).append("\n");
            }
            aboutdialog.setLicense(s.toString());
        } catch (FileNotFoundException e1) {
            gtk.UI.printMessage("Fehler: Lizenz nicht gefunden");
        }

        aboutdialog.connect(new AboutDialog.ActivateLink() {

            @Override
            public boolean onActivateLink(final AboutDialog source,
                    final URI link) {
                try {
                    Runtime.getRuntime().exec(
                            "chromium-browser " + link.toString());
                } catch (IOException e) {
                    UI.printMessage("Browser konnte nicht geöffnet werden");
                    return false;
                }
                return true;
            }
        });

        return aboutdialog;
    }
}
