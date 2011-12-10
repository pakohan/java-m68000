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
package gtk;

import org.gnome.gtk.CellRendererText;
import org.gnome.gtk.DataColumn;
import org.gnome.gtk.DataColumnString;
import org.gnome.gtk.ListStore;
import org.gnome.gtk.TreeIter;
import org.gnome.gtk.TreeView;
import org.gnome.gtk.TreeViewColumn;

public final class DataTable {
    private static ListStore dataregisterliststore;
    private static DataColumnString dataregistermemory;

    private DataTable() {
    }

    public static TreeView createTreeView() {
        DataColumnString dataregisterindex = new DataColumnString();
        DataTable.dataregistermemory = new DataColumnString();
        DataColumn[] table = new DataColumn[] {
                dataregisterindex,
                DataTable.dataregistermemory };

        DataTable.dataregisterliststore = new ListStore(table);
        TreeIter row;
        for (int i = 0; i < m68000.Processor.REG_AMOUNT; i++) {
            row = DataTable.dataregisterliststore.appendRow();
            DataTable.dataregisterliststore.setValue(row,
                    dataregisterindex,
                    Integer.valueOf(i).toString());
        }
        rebuildDataTable();

        TreeView dataRegister = new TreeView(DataTable.dataregisterliststore);
        TreeViewColumn vertical;
        CellRendererText renderer;

        vertical = dataRegister.appendColumn();
        vertical.setTitle("Datenregister");
        renderer = new CellRendererText(vertical);
        renderer.setMarkup(dataregisterindex);

        vertical = dataRegister.appendColumn();
        vertical.setTitle("Datenwert");
        renderer = new CellRendererText(vertical);
        renderer.setMarkup(DataTable.dataregistermemory);

        return dataRegister;
    }

    public static void rebuildDataTable() {
        TreeIter iter = DataTable.dataregisterliststore.getIterFirst();
        for (int i = 0; i < m68000.Processor.REG_AMOUNT; i++) {
            DataTable.dataregisterliststore.setValue(iter,
                    DataTable.dataregistermemory,
                    "0");
            iter.iterNext();
        }
    }

    public static void setdatatable(final int n, final int x) {
        TreeIter datatableiter = DataTable.dataregisterliststore.getIterFirst();
        for (int i = 0; i < n; i++) {
            datatableiter.iterNext();
        }
        String number = UI.getNumberAppeareance(x);
        DataTable.dataregisterliststore.setValue(datatableiter,
                DataTable.dataregistermemory,
                number);
    }
}
