package ui;

import static org.gnome.gtk.WrapMode.WORD;
import static org.gnome.gtk.WrapMode.NONE;

import java.io.FileNotFoundException;

import org.gnome.gdk.Event;
import org.gnome.gdk.Pixbuf;
import org.gnome.gtk.Button;
import org.gnome.gtk.DataColumnString;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.HBox;
import org.gnome.gtk.ListStore;
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

public final class UI {

    private static Window window;
    private static TextBuffer msgbuffer;
    private static TextBuffer filebuffer;
    private static TextView msgtextview;
    private static Button run;
    private static Button close;
    private static Button step;
    private static Button reload;
    public static DataColumnString dataregistermemory;
    public static DataColumnString adressregistermemory;
    public static ListStore dataregisterliststore;
    public static ListStore adressregisterliststore;
    private static Processor core1;
    public static RamDisplay ramdisplay;
    public static Pixbuf icon;

    private UI() { }

    public static void clearFileBuffer() {
        filebuffer.delete(filebuffer.getIterStart(), filebuffer.getIterEnd());
    }

    public static void setSensitive(final boolean issensitive) {
        run.setSensitive(issensitive);
        step.setSensitive(issensitive);
        reload.setSensitive(true);
    }

    public static void main(final String[] args) {
        //try {
        Gtk.init(args);
		try {
			icon = new Pixbuf("/home/mogli/Dokumente/Programmierung/M68000/res/gnome-ccperiph.png");
		} catch (FileNotFoundException e) { }
        createMainWindow();
        ramdisplay = new RamDisplay();
        window.showAll();
        Gtk.main();
        /*} catch (Exception x) {
        	System.out.println(Gtk.eventsPending());
            Writer result = new StringWriter();
            PrintWriter printWriter = new PrintWriter(result);
            x.printStackTrace(printWriter);
            ErrorMessageDialog error = new ErrorMessageDialog(window,
            		"Fehler",
            		result.toString());
            error.connect(new Dialog.Response() {

        		@Override
        		public void onResponse(Dialog source, ResponseType response) {
        			Gtk.mainQuit();
        		}
        	});
            error.run();
            error.hide();
            Gtk.main();
            Gtk.mainQuit();
        }*/
    }

    public static void setCore1(final Processor proc) {
        UI.core1 = proc;
    }

    public static void setdatatable(final int n, final int x) {
        TreeIter datatableiter = dataregisterliststore.getIterFirst();
        for (int i = 0; i < n; i++) {
            datatableiter.iterNext();
        }
        dataregisterliststore.setValue(datatableiter,
                                       dataregistermemory,
                                       Integer.valueOf(x).toString());
    }

    public static void setadresstable(final int n, final int x) {
        TreeIter adresstableiter = adressregisterliststore.getIterFirst();
        for (int i = 0; i < n; i++) {
            adresstableiter.iterNext();
        }
        adressregisterliststore.setValue(adresstableiter,
                                         dataregistermemory,
                                         Integer.valueOf(x).toString());
    }

    private static void createMainWindow() {
        window = new Window();
        window.setTitle("M68000");
        window.setDefaultSize(800, 700);
        window.setIcon(icon);
        window.connect(new Window.DeleteEvent() {
            public boolean onDeleteEvent(final Widget source,
            final Event event) {
                Gtk.mainQuit();
                return false;
            }
        });
        window.add(createVBox());
    }

    public static Window getWindow() {
        return window;
    }

    private static VBox createVBox() {
        VBox vbox = new VBox(false, 2);
        createHBox2();
        vbox.packStart(TopMenuBar.createMenuBar(),        false, true, 0);
        vbox.packStart(createHBox1(),          true , true, 0);
        vbox.packStart(createMsgScrolledWindow(), true , true, 0);
        vbox.packStart(createHBox2(),          false, true, 0);
        return vbox;
    }

    private static HBox createHBox1() {
        HBox hbox1 = new HBox(false, 2);
        createFileScrolledWindow();
        hbox1.packStart(createFileScrolledWindow(), true, true, 0);
        TreeView dataRegister = DataTable.createTreeView();
        hbox1.packStart(dataRegister, false, false, 0);
        TreeView adressRegister = AdressTable.createTreeView();
        hbox1.packStart(adressRegister, false, false, 0);
        return hbox1;
    }

    private static HBox createHBox2() {
        HBox hbox2 = new HBox(false, 2);
        createButtons();
        hbox2.add(run);
        hbox2.add(step);
        hbox2.add(reload);
        hbox2.add(close);
        return hbox2;
    }

    private static ScrolledWindow createFileScrolledWindow() {
        filebuffer = new TextBuffer();
        TextView filetextview = new TextView(filebuffer);
        filetextview.setWrapMode(NONE);
        filetextview.setEditable(false);
        ScrolledWindow scrolledFileWindow = new ScrolledWindow();
        scrolledFileWindow.add(filetextview);
        return scrolledFileWindow;
    }

    private static ScrolledWindow createMsgScrolledWindow() {
        msgbuffer = new TextBuffer();
        msgtextview = new TextView(msgbuffer);
        msgtextview.setWrapMode(WORD);
        ScrolledWindow scrolledWindow = new ScrolledWindow();
        scrolledWindow.add(msgtextview);
        return scrolledWindow;
    }

    public static void markLine(final int i) {
        TextTag black = new TextTag();
        black.setForeground("black");
        filebuffer.applyTag(black,
                            filebuffer.getIterStart(),
                            filebuffer.getIterEnd());
        TextIter pointerend = filebuffer.getIterStart();
        pointerend.forwardLines(i);
        TextIter pointerbegin = pointerend.copy();
        pointerend.forwardLine();
        TextTag tag = new TextTag();
        tag.setForeground("red");
        filebuffer.applyTag(tag, pointerbegin, pointerend);
    }

    public static void printMessage(final String msg) {
        TextIter end = msgbuffer.getIterEnd();
        msgbuffer.insert(end, msg);
        msgbuffer.insert(end, "\n");
        msgtextview.scrollTo(end);
    }

    private static void createButtons() {
        //run button is for running the program from begin to end
        run = new Button("RUN");
        run.setSensitive(false);
        run.connect(new Button.Clicked() {

            @Override
            public void onClicked(final Button source) {
                while (!core1.hasfinished()) {
                	TextTag black = new TextTag();
                    black.setForeground("black");
                    filebuffer.applyTag(black,
                                        filebuffer.getIterStart(),
                                        filebuffer.getIterEnd());
                    core1.run();
                }
                printMessage("Program beendet!");
                source.setSensitive(false);
                step.setSensitive(false);
            }
        });

        reload = new Button("RELOAD");
        reload.setSensitive(false);
        reload.connect(new Button.Clicked() {
			
			@Override
			public void onClicked(Button source) {
				TopMenuBar.loadsource();
			}
		});
        
        //step button is for running the program line for line
        step = new Button("STEP");
        step.setSensitive(false);
        step.connect(new Button.Clicked() {

            @Override
            public void onClicked(final Button source) {
                if (!core1.hasfinished()) {
                    core1.step();
                } else {
                    printMessage("Program beendet!");
                    source.setSensitive(false);
                    run.setSensitive(false);
                }
            }
        });

        close = new Button("CLOSE");
        close.connect(new Button.Clicked() {

            @Override
            public void onClicked(final Button arg0) {
                Gtk.mainQuit();
            }
        });
    }

    public static TextBuffer getFilebuffer() {
        return filebuffer;
    }
}
