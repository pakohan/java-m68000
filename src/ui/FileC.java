package ui;

import org.gnome.gtk.FileChooserAction;
import org.gnome.gtk.FileChooserDialog;
import org.gnome.gtk.FileFilter;

public class FileC {
	public static FileChooserDialog createFileChooserDialog() {
		FileChooserDialog dialog = new FileChooserDialog("Open File", UI.window, FileChooserAction.OPEN);
		FileFilter filter = new FileFilter("Assembler");
		filter.addPattern("*.s");
		filter.addPattern("*.S");
		FileFilter filter2 = new FileFilter("Alle Dateien");
		filter2.addPattern("*");
		dialog.addFilter(filter);
		dialog.addFilter(filter2);
		return dialog;
	}
}
