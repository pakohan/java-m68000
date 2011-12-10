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
    private DataColumnString[] datacolumn;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 800;

    public RamDisplay() {
        this.window = new Window();
        window.setTitle("RAM");
        window.setDefaultSize(WIDTH, HEIGHT);
        HBox hbox = new HBox(false, 0);
        ScrolledWindow scrolled = new ScrolledWindow();
        ramindex = new DataColumnString();
        datacolumn = new DataColumnString[16];
        for (int i = 0; i < 16; i++) {
            datacolumn[i] = new DataColumnString();
        }
        DataColumn[] ramDataColumn = new DataColumn[] {
                ramindex,
                datacolumn[0],
                datacolumn[1],
                datacolumn[2],
                datacolumn[3],
                datacolumn[4],
                datacolumn[5],
                datacolumn[6],
                datacolumn[7],
                datacolumn[8],
                datacolumn[9],
                datacolumn[10],
                datacolumn[11],
                datacolumn[12],
                datacolumn[13],
                datacolumn[14],
                datacolumn[15]};
        this.ramlist = new ListStore(ramDataColumn);
        TreeView ramtable = new TreeView(ramlist);
        TreeIter row;
        for (int i = 0; i < m68000.RAM.MAX_BYTE; i += 16) {
            row = ramlist.appendRow();
            ramlist.setValue(row, ramindex, Integer.toHexString(i));
        }
        rebuildTable();
        CellRendererText renderer;

        TreeViewColumn vertical;

        vertical = ramtable.appendColumn();
        vertical.setTitle("Index");
        renderer = new CellRendererText(vertical);
        renderer.setMarkup(ramindex);

        for (int i = 0; i < 16; i++) {
            vertical = ramtable.appendColumn();
            StringBuilder str = new StringBuilder();
            if (i < 10) {
                str.append("0");
            }
            str.append(i);
            vertical.setTitle(str.toString());
            renderer = new CellRendererText(vertical);
            renderer.setMarkup(datacolumn[i]);
        }

        scrolled.add(ramtable);
        hbox.packStart(scrolled, true, true, 0);
        window.add(hbox);
    }

    public final void rebuildTable() {
        TreeIter iter = ramlist.getIterFirst();
        for (int i = 0; i < m68000.RAM.MAX_BYTE; i += 16) {
            for (int j = 0; j < 16; j++) {
                ramlist.setValue(iter, this.datacolumn[j], "0");
            }
            iter.iterNext();
        }
    }

    public final void showAll() {
        window.showAll();
    }

    public final void setRamValue(final int n, final int x) {
        TreeIter iter = ramlist.getIterFirst();
        for (int i = 16; i <= n; i += 16) {
            iter.iterNext();
        }
        String number = UI.getNumberAppeareance(x);
        ramlist.setValue(iter, this.datacolumn[n % 16], number);
    }
}
