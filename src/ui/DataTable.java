package ui;

import org.gnome.gtk.CellRendererText;
import org.gnome.gtk.DataColumn;
import org.gnome.gtk.DataColumnString;
import org.gnome.gtk.ListStore;
import org.gnome.gtk.TreeIter;
import org.gnome.gtk.TreeView;
import org.gnome.gtk.TreeViewColumn;

public class DataTable {
	private DataTable() { }

	public static TreeView createTreeView() {
		DataColumnString dataregisterindex = new DataColumnString();
		UI.dataregistermemory = new DataColumnString();
		DataColumn[] table = new DataColumn[] {
				dataregisterindex,
				UI.dataregistermemory
		};
		
		UI.liststore = new ListStore(table);
		TreeIter row;
		for (int i = 0; i < 8; i++) {
			row = UI.liststore.appendRow();
			UI.liststore.setValue(row, dataregisterindex, new Integer(i).toString());
			UI.liststore.setValue(row, UI.dataregistermemory, "0");
		}
		
		TreeView dataRegister = new TreeView(UI.liststore);
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
}
