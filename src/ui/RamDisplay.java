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

import org.gnome.gtk.CellRendererText;
import org.gnome.gtk.DataColumn;
import org.gnome.gtk.DataColumnString;
import org.gnome.gtk.HBox;
import org.gnome.gtk.ListStore;
import org.gnome.gtk.ScrolledWindow;
import org.gnome.gtk.TreeIter;
import org.gnome.gtk.TreeView;
import org.gnome.gtk.TreeViewColumn;
import org.gnome.gtk.Window;

public class RamDisplay {

    private Window window;
    private ListStore ramlist;
    private DataColumnString ramindex;
    private DataColumnString ramvalue1;
    private DataColumnString ramvalue2;

    private static final int WIDTH = 300;
    private static final int HEIGHT = 800;

    public RamDisplay() {
        this.window = new Window();
        window.setTitle("RAM");
        window.setDefaultSize(WIDTH, HEIGHT);
        HBox hbox = new HBox(false, 0);
        ScrolledWindow scrolled = new ScrolledWindow();
        ramindex = new DataColumnString();
        ramvalue1 = new DataColumnString();
        ramvalue2 = new DataColumnString();
        DataColumn[] ramDataColumn = new DataColumn[] {
                ramindex,
                ramvalue1,
                ramvalue2 };
        this.ramlist = new ListStore(ramDataColumn);
        TreeView ramtable = new TreeView(ramlist);
        TreeIter row;
        for (int i = 0; i <= m68000.RAM.MAX_BYTE; i += 2) {
            row = ramlist.appendRow();
            ramlist.setValue(row, ramindex, Integer.toString(i));
        }
        rebuildTable();
        CellRendererText renderer;

        TreeViewColumn vertical;

        vertical = ramtable.appendColumn();
        vertical.setTitle("Index");
        renderer = new CellRendererText(vertical);
        renderer.setMarkup(ramindex);

        vertical = ramtable.appendColumn();
        vertical.setTitle("Wert ger");
        renderer = new CellRendererText(vertical);
        renderer.setMarkup(ramvalue1);

        vertical = ramtable.appendColumn();
        vertical.setTitle("Wert unger");
        renderer = new CellRendererText(vertical);
        renderer.setMarkup(ramvalue2);

        scrolled.add(ramtable);
        hbox.packStart(scrolled, true, true, 0);
        window.add(hbox);
    }

    public final void rebuildTable() {
        TreeIter iter = ramlist.getIterFirst();
        for (int i = 0; i <= m68000.RAM.MAX_BYTE; i += 2) {
            ramlist.setValue(iter, this.ramvalue1, "0");
            ramlist.setValue(iter, this.ramvalue2, "0");
            iter.iterNext();
        }
    }

    public final void showAll() {
        window.showAll();
    }

    public final void setRamValue(final int n, final int x) {
        TreeIter iter = ramlist.getIterFirst();
        int m = 0;
        if ((n % 2) != 0) {
            m++;
        }
        for (int i = m; i < n; i += 2) {
            iter.iterNext();
        }
        String number = UI.getNumberAppeareance(x);
        if ((n % 2) == 0) {
            ramlist.setValue(iter, this.ramvalue1, number);
        } else {
            ramlist.setValue(iter, this.ramvalue2, number);
        }
    }
}
