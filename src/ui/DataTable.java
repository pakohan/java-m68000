package ui;

import org.gnome.gtk.CellRendererText;
import org.gnome.gtk.DataColumn;
import org.gnome.gtk.DataColumnString;
import org.gnome.gtk.ListStore;
import org.gnome.gtk.TreeIter;
import org.gnome.gtk.TreeView;
import org.gnome.gtk.TreeViewColumn;

public final class DataTable {
    private DataTable() { }

    public static TreeView createTreeView() {
        DataColumnString dataregisterindex = new DataColumnString();
        UI.dataregistermemory = new DataColumnString();
        DataColumn[] table = new DataColumn[] {
                dataregisterindex,
                UI.dataregistermemory
        };

        UI.dataregisterliststore = new ListStore(table);
        TreeIter row;
        for (int i = 0; i < m68000.Processor.REG_AMOUNT; i++) {
            row = UI.dataregisterliststore.appendRow();
            UI.dataregisterliststore.setValue(row,
                    dataregisterindex,
                    Integer.valueOf(i).toString());
        }
        rebuildDataTable();
        
        TreeView dataRegister = new TreeView(UI.dataregisterliststore);
        TreeViewColumn vertical;
        CellRendererText renderer;

        vertical = dataRegister.appendColumn();
        vertical.setTitle("Datenregister");
        renderer = new CellRendererText(vertical);
        renderer.setMarkup(dataregisterindex);

        vertical = dataRegister.appendColumn();
        vertical.setTitle("Datenwert");
        renderer = new CellRendererText(vertical);
        renderer.setMarkup(UI.dataregistermemory);

        return dataRegister;
    }
    
    public static void rebuildDataTable() {
        TreeIter iter = UI.dataregisterliststore.getIterFirst();
        for (int i = 0; i < m68000.Processor.REG_AMOUNT; i++) {
        	UI.dataregisterliststore.setValue(iter, UI.dataregistermemory, "0");
            iter.iterNext();
        }
    }
}
