package ui;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

import org.gnome.gdk.Pixbuf;
import org.gnome.gtk.AboutDialog;

public final class About {
    private About() { }
    private static String[] authors = {"Patrick Kohan",
        "Stefano DiMartino",
        "Tobias Keh"};
    private static String[] commenters = {"Patrick Kohan"};

    public static AboutDialog createAboutDialog() {
        AboutDialog aboutdialog = new AboutDialog();
        aboutdialog.setCopyright("(c) 2011");
        aboutdialog.setProgramName("Java-M68000");
        aboutdialog.setComments("Ein einfacher Simulator für einen M68000");
        aboutdialog.setAuthors(authors);
        aboutdialog.setVersion("2011.11.27 - r71");
        aboutdialog.setDocumenters(commenters);
        aboutdialog.setWebsite("code.google.com/p/java-m68000");
        aboutdialog.setWebsiteLabel("Projet auf Google Code");
        
        try {
            aboutdialog.setLogo(new Pixbuf("/home/mogli/Dokumente/Programmierung/M68000/res/gnome-ccperiph.png"));
        	FileReader fr = new FileReader("/home/mogli/Dokumente/Programmierung/M68000/src/COPYING");
			Scanner scan = new Scanner(fr);
			StringBuilder s = new StringBuilder();
			while (scan.hasNextLine()) {
				s.append(scan.nextLine()).append("\n");
			}
			aboutdialog.setLicense(s.toString());
		} catch (FileNotFoundException e1) { }

        aboutdialog.connect(new AboutDialog.ActivateLink() {
			
			@Override
			public boolean onActivateLink(AboutDialog source, URI link) {
				try {
					Runtime.getRuntime().exec("chromium-browser "
							+ link.toString());
				} catch (IOException e) { }
				return true;
			}
		});
        
        return aboutdialog;
    }
}
