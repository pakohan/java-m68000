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

import static org.gnome.gtk.WrapMode.WORD;
import static org.gnome.gtk.WrapMode.NONE;

import org.gnome.gdk.Event;
import org.gnome.gdk.EventButton;
import org.gnome.gtk.Button;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.HBox;
import org.gnome.gtk.ScrolledWindow;
import org.gnome.gtk.TextBuffer;
import org.gnome.gtk.TextIter;
import org.gnome.gtk.TextTag;
import org.gnome.gtk.TextView;
import org.gnome.gtk.TreeView;
import org.gnome.gtk.VBox;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;
import org.gnome.gtk.WindowPosition;

import m68000.Processor;
import m68000.Processor.Marker;

public final class UI {
    static enum MemoryDisplay {
        BIN, OKT, DEZ, HEX
    }

    private static Window window;
    private static TextBuffer msgbuffer;
    private static TextBuffer filebuffer;
    private static TextView msgtextview;
    private static Button run;
    private static Button close;
    private static Button step;
    private static Button reload;
    private static Processor core1;
    private static MemoryDisplay md;
    private static StringBuilder buf = new StringBuilder();

    private static final int WIDTH = 800;
    private static final int HEIGHT = 700;

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
        // try {
        Gtk.init(args);
        Ressources.initRessources();
        createMainWindow();
        window.showAll();
        Gtk.main();
        /*
         * } catch (Exception x) { System.out.println(Gtk.eventsPending());
         * Writer result = new StringWriter(); PrintWriter printWriter = new
         * PrintWriter(result); x.printStackTrace(printWriter);
         * ErrorMessageDialog error = new ErrorMessageDialog(window, "Fehler",
         * result.toString()); error.connect(new Dialog.Response() {
         *
         * @Override public void onResponse(Dialog source, ResponseType
         * response) { Gtk.mainQuit(); } }); error.run(); error.hide();
         * Gtk.main(); Gtk.mainQuit(); }
         */
    }

    public static void setCore1(final Processor proc) {
        UI.core1 = proc;
    }

    private static void createMainWindow() {
        window = new Window();
        window.setTitle("M68000");
        window.setDefaultSize(WIDTH, HEIGHT);
        window.setIcon(Ressources.getIcon());
        window.connect(new Window.DeleteEvent() {
            public boolean onDeleteEvent(final Widget source,
                    final Event event) {
                Gtk.mainQuit();
                return false;
            }
        });
        window.setPosition(WindowPosition.CENTER);
        window.add(createVBox());
    }

    public static Window getWindow() {
        return window;
    }

    private static VBox createVBox() {
        VBox vbox = new VBox(false, 5);
        vbox.packStart(TopMenuBar.createMenuBar(), false, true, 0);
        vbox.packStart(createHBox1(), true, true, 0);
        vbox.packStart(createMsgScrolledWindow(), true, true, 0);
        vbox.packStart(createHBox2(), false, true, 0);
        return vbox;
    }

    private static HBox createHBox1() {
        HBox hbox1 = new HBox(false, 5);
        createFileScrolledWindow();
        hbox1.packStart(createFileScrolledWindow(), true, true, 0);
        TreeView dataRegister = DataTable.createTreeView();
        hbox1.packStart(dataRegister, false, false, 0);
        TreeView adressRegister = AdressTable.createTreeView();
        hbox1.packStart(adressRegister, false, false, 0);
        return hbox1;
    }

    private static HBox createHBox2() {
        HBox hbox2 = new HBox(false, 5);
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
        filetextview.setCursorVisible(false);

        filetextview.connect(new Widget.ButtonReleaseEvent() {
            @Override
            public boolean onButtonReleaseEvent(final Widget source,
                    final EventButton event) {
                int line = filebuffer.getInsert().getIter().getLine();
                if (core1 != null) {
                    if (core1.toggleBreakPoint(line)) {
                        markLine(line, Marker.BREAK);
                    } else {
                        markLine(line, Marker.UNMARK);
                    }
                }
                return false;
            }
        });

        ScrolledWindow scrolledFileWindow = new ScrolledWindow();
        scrolledFileWindow.add(filetextview);
        return scrolledFileWindow;
    }

    private static ScrolledWindow createMsgScrolledWindow() {
        msgbuffer = new TextBuffer();
        msgtextview = new TextView(msgbuffer);
        msgtextview.setWrapMode(WORD);
        msgtextview.setEditable(false);
        msgtextview.setCursorVisible(false);

        ScrolledWindow scrolledWindow = new ScrolledWindow();
        scrolledWindow.add(msgtextview);

        TextIter end = msgbuffer.getIterEnd();
        msgbuffer.insert(end, buf.toString());
        msgtextview.scrollTo(end);
        return scrolledWindow;
    }

    public static void markLine(final int i, final Marker type) {
        TextTag tt = new TextTag();
        switch (type) {
        case EXE:
            tt.setForeground("red");
            break;
        case UNMARK:
            tt.setForeground("black");
            break;
        case BREAK:
            tt.setForeground("blue");
            break;
        default:
            tt.setForeground("black");
        }
        TextIter pointerend = filebuffer.getIterStart();
        pointerend.forwardLines(i);
        TextIter pointerbegin = pointerend.copy();
        pointerend.forwardLine();
        filebuffer.applyTag(tt, pointerbegin, pointerend);
    }

    public static void printMessage(final String msg) {
        TextIter end = msgbuffer.getIterEnd();
        msgbuffer.insert(end, msg);
        msgbuffer.insert(end, "\n");
        msgtextview.scrollTo(end);
    }

    public static void setMd(final MemoryDisplay memory) {
        UI.md = memory;
    }

    private static void createButtons() {
        run = new Button("RUN");
        run.setSensitive(false);
        run.connect(new Button.Clicked() {

            @Override
            public void onClicked(final Button source) {
                    core1.run();
                    if (core1.hasfinished()) {
                        printMessage("Program beendet!");
                        source.setSensitive(false);
                        step.setSensitive(false);
                    }
            }
        });

        reload = new Button("RELOAD");
        reload.setSensitive(false);
        reload.connect(new Button.Clicked() {

            @Override
            public void onClicked(final Button source) {
                TopMenuBar.loadsource();
            }
        });

        step = new Button("STEP");
        step.setSensitive(false);
        step.connect(new Button.Clicked() {

            @Override
            public void onClicked(final Button source) {
                    core1.step();
                if (core1.hasfinished()) {
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

    public static void addErrorMessage(final String message) {
        buf.append(message).append("\n");
    }

    public static String getNumberAppeareance(final int x) {
        String number;
        StringBuilder strb = new StringBuilder();
        switch (md) {
        case BIN:
            number = Integer.toBinaryString(x);
            for (int i = number.length(); i < 8; i++) {
                strb.append("0");
            }
            strb.append(number);
            break;
        case OKT:
            number = Integer.toOctalString(x);
            for (int i = number.length(); i < 4; i++) {
                strb.append("0");
            }
            strb.append(number);
            break;
        case DEZ:
            number = Integer.toString(x);
            for (int i = number.length(); i < 3; i++) {
                strb.append("0");
            }
            strb.append(number);
            break;
        case HEX:
            number = Integer.toHexString(x);
            for (int i = number.length(); i < 2; i++) {
                strb.append("0");
            }
            strb.append(number);
            break;
        default :
            number = "0";
        }
        number = strb.toString();
        return number;
    }

    public static TextBuffer getFilebuffer() {
        return filebuffer;
    }
}
