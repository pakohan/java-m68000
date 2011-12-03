package ui;

import org.gnome.gdk.EventButton;
import org.gnome.gtk.Frame;
import org.gnome.gtk.RadioButton;
import org.gnome.gtk.RadioGroup;
import org.gnome.gtk.VBox;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;

import ui.UI.MemoryDisplay;

public final class SettingsWindow {
	public Window window;
	public SettingsWindow() {
		this.window = new Window();
		
        window.setTitle("RAM");
        window.setDefaultSize(300, 300);
        
        RadioGroup group = new RadioGroup();        
        RadioButton bin = new RadioButton(group, "binär");  
        bin.connect(new Widget.ButtonPressEvent() {
			
			@Override
			public boolean onButtonPressEvent(Widget source, EventButton event) {
				UI.md = MemoryDisplay.BIN;
				return false;
			}
		});
        
        RadioButton okt = new RadioButton(group, "oktal");
        okt.connect(new Widget.ButtonPressEvent() {
			
			@Override
			public boolean onButtonPressEvent(Widget source, EventButton event) {
				UI.md = MemoryDisplay.OKT;
				return false;
			}
		});
        
        RadioButton dez = new RadioButton(group, "dezimal");
        dez.connect(new Widget.ButtonPressEvent() {
			
			@Override
			public boolean onButtonPressEvent(Widget source, EventButton event) {
				UI.md = MemoryDisplay.DEZ;
				return false;
			}
		});
        
        RadioButton hex = new RadioButton(group, "hex");
        hex.connect(new Widget.ButtonPressEvent() {
			
			@Override
			public boolean onButtonPressEvent(Widget source, EventButton event) {
				UI.md = MemoryDisplay.HEX;
				return false;
			}
		});
        

        VBox vbox1 = new VBox(false, 0);
        vbox1.add(bin);
        vbox1.add(okt);
        vbox1.add(dez);
        vbox1.add(hex);
        
        Frame speicherart = new Frame("Speicherdarstellung");
        speicherart.add(vbox1);
        
        VBox vbox2 = new VBox(false, 0);
        
        vbox2.packStart(speicherart, false, true, 0);
        
        this.window.add(vbox2);
	}
}
