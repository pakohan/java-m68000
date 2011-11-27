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

    public Window window;
    private ListStore ramlist;
    private DataColumnString ramindex;
    private DataColumnString ramvalue;

    public RamDisplay() {
        this.window = new Window();
        window.setTitle("RAM");
        window.setDefaultSize(300, 800);
        HBox hbox = new HBox(false, 0);
        ScrolledWindow scrolled = new ScrolledWindow();
        ramindex = new DataColumnString();
        ramvalue = new DataColumnString();
        DataColumn[] ramDataColumn = new DataColumn[] {
                ramindex,
                ramvalue
        };
        this.ramlist = new ListStore(ramDataColumn);
        TreeView ramtable = new TreeView(ramlist);
        TreeIter row;
        for (int i = 0; i < m68000.RAM.MAX_BYTE; i++) {
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
        vertical.setTitle("Wert");
        renderer = new CellRendererText(vertical);
        renderer.setMarkup(ramvalue);

        scrolled.add(ramtable);
        hbox.packStart(scrolled, true, true, 0);
        window.add(hbox);
    }

    public final void rebuildTable() {
        TreeIter iter = ramlist.getIterFirst();
        for (int i = 0; i < m68000.RAM.MAX_BYTE; i++) {
            ramlist.setValue(iter, this.ramvalue, "0");
            iter.iterNext();
        }
    }

    public final void setRamValue(final int n, final int x) {
        TreeIter iter = ramlist.getIterFirst();
        for (int i = 0; i < n; i++) {
            iter.iterNext();
        }
        ramlist.setValue(iter,
                this.ramvalue,
                Integer.valueOf(x).toString());
    }
}
