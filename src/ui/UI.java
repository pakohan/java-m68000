package ui;

import static org.gnome.gtk.WrapMode.WORD;
import static org.gnome.gtk.WrapMode.NONE;

import org.gnome.gdk.Event;
import org.gnome.gtk.AboutDialog;
import org.gnome.gtk.Button;
import org.gnome.gtk.DataColumnString;
import org.gnome.gtk.ErrorMessageDialog;
import org.gnome.gtk.FileChooserButton;
import org.gnome.gtk.FileChooserDialog;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.HBox;
import org.gnome.gtk.ListStore;
import org.gnome.gtk.MenuBar;
import org.gnome.gtk.ScrolledWindow;
import org.gnome.gtk.TextBuffer;
import org.gnome.gtk.TextIter;
import org.gnome.gtk.TextTag;
import org.gnome.gtk.TextView;
import org.gnome.gtk.TreeIter;
import org.gnome.gtk.TreeView;
import org.gnome.gtk.VBox;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;

import m68000.Processor;
import m68000.Program;


public final class UI {

	static Window window;
	static AboutDialog aboutdialog;
	static ErrorMessageDialog error;
	static VBox vbox;
	static HBox hbox;
	static FileChooserDialog dialog;
	static TextBuffer msgbuffer;
	static TextBuffer filebuffer;
	static TextView msgtextview;
	static TextView filetextview;
	static String str;
	static FileChooserButton button;
	static Button run;
	static Button close;
	static Button step;
	static MenuBar menuBar;
	static TextIter end;
	static TreeIter datatableiter;
	static TreeView dataRegister;
	static DataColumnString dataregistermemory;
	static ListStore liststore;

	static Program prog;
	static Processor core1;
	static HBox hbox1;
	static ScrolledWindow scrolledWindow;
	static HBox hbox2;
	static ScrolledWindow scrolledFileWindow;

	private UI() { }
	
	public static void main(final String[] args) {
        Gtk.init(args);

		createMainWindow();
		window.showAll();
		
		Gtk.main();
	}
	
	public static void setdatatable(final int n, final int x) {
		datatableiter = liststore.getIterFirst();
		for (int i = 0; i < n; i++) {
			datatableiter.iterNext();
		}
		liststore.setValue(datatableiter, dataregistermemory, new Integer(x).toString());
	}
	
	private static void createMainWindow() {
		window = new Window();
		window.setTitle("M68000");
		window.setDefaultSize(800, 800);
		window.connect(new Window.DeleteEvent() {
            public boolean onDeleteEvent(Widget source, Event event) {
                Gtk.mainQuit();
                return false;
            }
        });
		createVBox();
		window.add(vbox);
	}

	private static void createVBox() {
		vbox = new VBox(false, 2);
		createMenuBar();
		createHBox1();
		createMsgScrolledWindow();
		createHBox2();
		vbox.packStart(menuBar,        false, true, 0);
		vbox.packStart(hbox1,          true , true, 0);
		vbox.packStart(scrolledWindow, true , true, 0);
		vbox.packStart(hbox2,          false, true, 0);
	}
	

	private static void createHBox1() {
		hbox1 = new HBox(false, 2);
		createFileScrolledWindow();
		hbox1.packStart(scrolledFileWindow, true, true, 0);
		dataRegister = DataTable.createTreeView();
		hbox1.packStart(dataRegister, false, false, 0);
	}
	
	private static void createHBox2() {
		hbox2 = new HBox(false, 2);
		createButtons();
		hbox2.add(run);
		hbox2.add(step);
		hbox2.add(close);
	}
	
	private static void createFileScrolledWindow() {
		filebuffer = new TextBuffer();
		filetextview = new TextView(filebuffer);
		filetextview.setWrapMode(NONE);
		filetextview.setEditable(false);
		scrolledFileWindow = new ScrolledWindow();
		scrolledFileWindow.add(filetextview);
	}

	private static void createMsgScrolledWindow() {
		msgbuffer = new TextBuffer();
		msgtextview = new TextView(msgbuffer);
		msgtextview.setWrapMode(WORD);
		scrolledWindow = new ScrolledWindow();
		scrolledWindow.add(msgtextview);
	}
	
	public static void markLine(final int i) {
		TextTag black = new TextTag();
		black.setForeground("black");
		filebuffer.applyTag(black, filebuffer.getIterStart(), filebuffer.getIterEnd());
		TextIter pointerend = filebuffer.getIterStart();
	    pointerend.forwardLines(i);
	    TextIter pointerbegin = pointerend.copy();
	    pointerend.forwardLine();
	    TextTag tag = new TextTag();
	    tag.setForeground("red");
		filebuffer.applyTag(tag, pointerbegin, pointerend);
	}
	
	public static void printMessage(final String str) {
        end = msgbuffer.getIterEnd();
        msgbuffer.insert(end, str);
        msgbuffer.insert(end, "\n");
		msgtextview.scrollTo(end);
	}

	private static void createButtons() {
		//run button is for running the program from begin to end
		run = new Button("RUN");
		run.setSensitive(false);
		run.connect(new Button.Clicked() {
			
			@Override
			public void onClicked(Button source) {
				while (!core1.hasfinished()) {
					core1.run();
				}
				printMessage("Program beendet!");
				source.setSensitive(false);
				step.setSensitive(false);
			}
		});
		
		//step button is for running the program line for line
		step = new Button("STEP");
		step.setSensitive(false);
		step.connect(new Button.Clicked() {
			
			@Override
			public void onClicked(Button source) {
				if (!core1.hasfinished()) {
					core1.step();
				} else {
					printMessage("Program beendet!");
					source.setSensitive(false);
					run.setSensitive(false);
				}
			}
		});
		
		//close button is for closing the program
		close = new Button("CLOSE");
		close.connect(new Button.Clicked() {
			
			@Override
			public void onClicked(Button arg0) {
                Gtk.mainQuit();
			}
		});
	}

	private static void createMenuBar() {
		menuBar = TopMenuBar.createMenuBar();
	}
}
