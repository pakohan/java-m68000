package ui;

import org.gnome.gtk.CellRendererText;
import org.gnome.gtk.DataColumn;
import org.gnome.gtk.DataColumnString;
import org.gnome.gtk.ListStore;
import org.gnome.gtk.TreeIter;
import org.gnome.gtk.TreeView;
import org.gnome.gtk.TreeViewColumn;

public final class AdressTable {
    private AdressTable() { }

    public static TreeView createTreeView() {
        DataColumnString adressregisterindex = new DataColumnString();
        UI.adressregistermemory = new DataColumnString();
        DataColumn[] table = new DataColumn[] {
                adressregisterindex,
                UI.adressregistermemory
        };

        UI.adressregisterliststore = new ListStore(table);
        TreeIter row;
        for (int i = 0; i < m68000.Processor.REG_AMOUNT; i++) {
            row = UI.adressregisterliststore.appendRow();
            UI.adressregisterliststore.setValue(row,
                    adressregisterindex,
                    Integer.valueOf(i).toString());
        }
        rebuildAddressTable();
        
        TreeView dataRegister = new TreeView(UI.adressregisterliststore);
        TreeViewColumn vertical;
        CellRendererText renderer;

        vertical = dataRegister.appendColumn();
        vertical.setTitle("Adressregister");
        renderer = new CellRendererText(vertical);
        renderer.setMarkup(adressregisterindex);

        vertical = dataRegister.appendColumn();
        vertical.setTitle("Datenwert");
        renderer = new CellRendererText(vertical);
        renderer.setMarkup(UI.adressregistermemory);

        return dataRegister;
    }
    
    public static void rebuildAddressTable() {
        TreeIter iter = UI.adressregisterliststore.getIterFirst();
        for (int i = 0; i < m68000.Processor.REG_AMOUNT; i++) {
        	UI.adressregisterliststore.setValue(iter, UI.adressregistermemory, "0");
            iter.iterNext();
        }
    }
}
