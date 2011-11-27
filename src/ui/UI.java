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

public final class UI {

    private static Window window;
    private static VBox vbox;
    private static TextBuffer msgbuffer;
    private static TextBuffer filebuffer;
    private static TextView msgtextview;
    private static TextView filetextview;
    private static Button run;
    private static Button close;
    private static Button step;
    private static MenuBar menuBar;
    private static TextIter end;
    private static TreeIter datatableiter;
    private static TreeIter adresstableiter;
    private static TreeView dataRegister;
    private static TreeView adressRegister;
    public static DataColumnString dataregistermemory;
    public static DataColumnString adressregistermemory;
    public static ListStore dataregisterliststore;
    public static ListStore adressregisterliststore;
    private static Processor core1;
    private static HBox hbox1;
    private static ScrolledWindow scrolledWindow;
    private static HBox hbox2;
    private static ScrolledWindow scrolledFileWindow;
    public static RamDisplay ramdisplay;

    private UI() { }

    public static void clearFileBuffer() {
        filebuffer.delete(filebuffer.getIterStart(), filebuffer.getIterEnd());
    }

    public static void setSensitive(final boolean issensitive) {
        run.setSensitive(issensitive);
        step.setSensitive(issensitive);
    }

    public static void main(final String[] args) {
        //try {
            Gtk.init(args);
            createMainWindow();
            ramdisplay = new RamDisplay();
            window.showAll();
            Gtk.main();
        /*} catch (Exception x) {
            Writer result = new StringWriter();
            PrintWriter printWriter = new PrintWriter(result);
            x.printStackTrace(printWriter);
            ErrorMessageDialog error = new ErrorMessageDialog(window,
            "Fehler",
            result.toString());
            error.run();
            error.hide();
            Gtk.main();
        }*/
    }

    public static void setCore1(final Processor proc) {
        UI.core1 = proc;
    }

    public static void setdatatable(final int n, final int x) {
        datatableiter = dataregisterliststore.getIterFirst();
        for (int i = 0; i < n; i++) {
            datatableiter.iterNext();
        }
        dataregisterliststore.setValue(datatableiter,
                dataregistermemory,
                Integer.valueOf(x).toString());
    }

    public static void setadresstable(final int n, final int x) {
        adresstableiter = adressregisterliststore.getIterFirst();
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
        try {
			window.setIcon(new Pixbuf("/usr/share/pixmaps/gnome-ccperiph.png"));
		} catch (FileNotFoundException e) { }
        window.connect(new Window.DeleteEvent() {
            public boolean onDeleteEvent(final Widget source,
                    final Event event) {
                Gtk.mainQuit();
                return false;
            }
        });
        createVBox();
        window.add(vbox);
    }

    public static Window getWindow() {
        return window;
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
        adressRegister = AdressTable.createTreeView();
        hbox1.packStart(adressRegister, false, false, 0);
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
        end = msgbuffer.getIterEnd();
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

        //close button is for closing the program
        close = new Button("CLOSE");
        close.connect(new Button.Clicked() {

            @Override
            public void onClicked(final Button arg0) {
                Gtk.mainQuit();
            }
        });
    }

    private static void createMenuBar() {
        menuBar = TopMenuBar.createMenuBar();
    }

    public static TextBuffer getFilebuffer() {
        return filebuffer;
    }
}
