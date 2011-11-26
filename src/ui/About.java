package ui;

import org.gnome.gtk.AboutDialog;

public class About {
	private About() { }
	private static String[] authors = {"Patrick Kohan", "Stefano DiMartino"};

	public static AboutDialog createAboutDialog() {
		AboutDialog aboutdialog = new AboutDialog();
		aboutdialog.setCopyright("GPLv3");
		aboutdialog.setProgramName("Java-M68000");
		aboutdialog.setComments("Ein einfacher Simulator für einen M68000");
		aboutdialog.setAuthors(authors);
		return aboutdialog;
	}
}
