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
    private DataColumnString ramvalue1;
    private DataColumnString ramvalue2;

    public RamDisplay() {
        this.window = new Window();
        window.setTitle("RAM");
        window.setDefaultSize(300, 800);
        HBox hbox = new HBox(false, 0);
        ScrolledWindow scrolled = new ScrolledWindow();
        ramindex = new DataColumnString();
        ramvalue1 = new DataColumnString();
        ramvalue2 = new DataColumnString();
        DataColumn[] ramDataColumn = new DataColumn[] {
            ramindex,
            ramvalue1,
            ramvalue2
        };
        this.ramlist = new ListStore(ramDataColumn);
        TreeView ramtable = new TreeView(ramlist);
        TreeIter row;
        for (int i = 0; i <= m68000.RAM.MAX_BYTE; i +=2) {
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

    public final void setRamValue(final int n, final int x) {
        TreeIter iter = ramlist.getIterFirst();
        int m = 0;
        if ((n % 2) != 0) {
            m++;
        }
        for (int i = m; i < n; i += 2) {
            iter.iterNext();
        }
        StringBuilder strb = new StringBuilder();
        String number = Integer.toBinaryString(x);
        for (int i = number.length(); i < 8; i++) {
        	strb.append("0");
        }
        strb.append(number);
        number = strb.toString();
        if ((n % 2) == 0) {
            ramlist.setValue(iter,
                             this.ramvalue1,
                             number);
        } else {
            ramlist.setValue(iter,
                             this.ramvalue2,
                             number);
        }
    }
}