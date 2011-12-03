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
package ui;

import org.gnome.gdk.EventButton;
import org.gnome.gtk.Button;
import org.gnome.gtk.Frame;
import org.gnome.gtk.HBox;
import org.gnome.gtk.RadioButton;
import org.gnome.gtk.RadioGroup;
import org.gnome.gtk.VBox;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;

import ui.UI.MemoryDisplay;

public final class SettingsWindow {
    protected Window window;

    public SettingsWindow() {
        this.window = new Window();

        window.setTitle("RAM");

        RadioGroup group = new RadioGroup();
        RadioButton bin = new RadioButton(group, "binär");
        bin.connect(new Widget.ButtonPressEvent() {

            @Override
            public boolean onButtonPressEvent(final Widget source,
                    final EventButton event) {
                UI.mdtmp = MemoryDisplay.BIN;
                return false;
            }
        });

        RadioButton okt = new RadioButton(group, "oktal");
        okt.connect(new Widget.ButtonPressEvent() {

            @Override
            public boolean onButtonPressEvent(final Widget source,
                    final EventButton event) {
                UI.mdtmp = MemoryDisplay.OKT;
                return false;
            }
        });

        RadioButton dez = new RadioButton(group, "dezimal (kaputt)");
        dez.setSensitive(false);
        dez.connect(new Widget.ButtonPressEvent() {

            @Override
            public boolean onButtonPressEvent(final Widget source,
                    final EventButton event) {
                UI.mdtmp = MemoryDisplay.DEZ;
                return false;
            }
        });

        RadioButton hex = new RadioButton(group, "hex");
        hex.connect(new Widget.ButtonPressEvent() {

            @Override
            public boolean onButtonPressEvent(final Widget source,
                    final EventButton event) {
                UI.mdtmp = MemoryDisplay.HEX;
                return false;
            }
        });

        VBox vbox1 = new VBox(false, 5);
        vbox1.add(bin);
        vbox1.add(okt);
        vbox1.add(dez);
        vbox1.add(hex);

        Frame speicherart = new Frame("Speicherdarstellung");
        speicherart.add(vbox1);

        VBox vbox2 = new VBox(false, 0);

        vbox2.packStart(speicherart, false, true, 10);

        Button close = new Button("CLOSE");
        close.connect(new Button.Clicked() {

            @Override
            public void onClicked(final Button arg0) {
                window.hide();
            }
        });

        vbox2.packStart(close, false, true, 10);

        HBox hbox = new HBox(false, 0);

        hbox.packStart(vbox2, false, true, 10);

        this.window.add(hbox);
    }
}
