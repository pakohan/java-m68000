package ui;

import static org.gnome.gtk.WrapMode.WORD;

import org.gnome.gdk.Event;
import org.gnome.gtk.Alignment;
import org.gnome.gtk.Button;
import org.gnome.gtk.FileChooserAction;
import org.gnome.gtk.FileChooserButton;
import org.gnome.gtk.FileFilter;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.HBox;
import org.gnome.gtk.ScrolledWindow;
import org.gnome.gtk.TextBuffer;
import org.gnome.gtk.TextIter;
import org.gnome.gtk.TextView;
import org.gnome.gtk.VBox;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;

public final class MainWindow {
	String str;
	TextBuffer buffer;
	TextView textview;
	FileChooserButton button;
	Button action;
	Window window;
	
    public MainWindow() {        
        //create window and set properties
		window = new Window();
		window.setTitle("M68000");
		window.setDefaultSize(800, 800);
		window.connect(new Window.DeleteEvent() {
            public boolean onDeleteEvent(Widget source, Event event) {
                Gtk.mainQuit();
                return false;
            }
        });
		
		VBox vbox = new VBox(false, 1);
		window.add(vbox);
				
		//create Textfiled and add to vbox
		Alignment textfield = createTextField();
		vbox.packStart(textfield, true, true, 2);
		
		//create FileChooserButton and add to vbox
		HBox hbox = new HBox(false, 1);
		this.action = new Button("RUN");
		hbox.packStart(action, true, true, 2);
		FileChooserButton button = createFileChooserButton();
		hbox.packStart(button, false, true, 2);
		
		vbox.packStart(hbox, false, true, 2);
		//show window with contents
		window.showAll();
		
		//must be called, too...
    }
    
    public final Window getWindow() {
		return window;
	}

	public final Button getAction() {
		return action;
	}

	public void appendMessage(String msg) {
        final TextIter end;
        /*
         * Get a TextIter pointing at the end of the existing text.
         */
        end = this.buffer.getIterEnd();
        /*
         * Start with a paragraph separator and a timestamp. We colour the
         * timestamp a lighter colour so as to not distract from the text.
         */
        this.buffer.insert(end, "\n");
        this.buffer.insert(end, msg);
        /*
         * Loop over what we're going to add, replacing text smileys with
         * graphical ones. As for the text we write, if the user sent it we'll
         * make it blue but if incoming we'll leave it black.
         */
		textview.scrollTo(end);
    }
    
    private final Alignment createTextField() {
		//create TextBuffer, add to TextView
		this.buffer = new TextBuffer();
		textview = new TextView(buffer);
		textview.setWrapMode(WORD);

		//create ScrolledWindow and add textview to it, then add this
		//ScrolledWindow to the hbox
		ScrolledWindow scrolled = new ScrolledWindow();
		scrolled.add(textview);
		Alignment alignment = new Alignment();
		alignment.add(scrolled);
		alignment.setAlignment(0, 0, 1.0f, 1.0f);
		return alignment;
	}
    
    

    private final FileChooserButton createFileChooserButton() {
    	this.button = new FileChooserButton(
    			System.getProperty("user.home"), FileChooserAction.OPEN);
		FileFilter filter = new FileFilter("Assembler");
		filter.addPattern("*.s");
		filter.addPattern("*.S");
		FileFilter filter2 = new FileFilter("Alle Dateien");
		filter2.addPattern("*");
		button.addFilter(filter);
		button.addFilter(filter2);
		return button;
    }

	public FileChooserButton getFileChooserButton() {
		return this.button;
	}
}
