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

import org.gnome.gtk.FileChooserAction;
import org.gnome.gtk.FileChooserDialog;
import org.gnome.gtk.FileFilter;

public final class FileC {
    private FileC() {
    }

    public static FileChooserDialog createFileChooserDialog() {
        FileChooserDialog dialog = new FileChooserDialog("Open File",
                UI.getWindow(), FileChooserAction.OPEN);
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
