package ui;

import java.io.FileNotFoundException;

import org.gnome.gdk.Pixbuf;
import org.gnome.gtk.AboutDialog;

public final class About {
    private About() { }
    private static String[] authors = {"Patrick Kohan", "Stefano DiMartino"};
    private static String[] commenters = {"Patrick Kohan"};

    public static AboutDialog createAboutDialog() {
        AboutDialog aboutdialog = new AboutDialog();
        aboutdialog.setCopyright("(c) 2011");
        aboutdialog.setProgramName("Java-M68000");
        aboutdialog.setComments("Ein einfacher Simulator für einen M68000");
        aboutdialog.setAuthors(authors);
        aboutdialog.setLicense("GNU GENERAL PUBLIC LICENSE\nVersion 3, 29 June 2007");
        aboutdialog.setVersion("2011.11.27 - r71");
        aboutdialog.setDocumenters(commenters);
        try {
			aboutdialog.setLogo(new Pixbuf("/usr/share/pixmaps/gnome-ccperiph.png"));
		} catch (FileNotFoundException e) { }
        return aboutdialog;
    }
}
