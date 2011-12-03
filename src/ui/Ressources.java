package ui;

import java.io.FileNotFoundException;

import org.gnome.gdk.Pixbuf;

public final class Ressources {
    private static Pixbuf icon;

    private Ressources() { }

    public static void initRessources() {
        try {
            icon = new Pixbuf(
          "/home/mogli/Dokumente/Programmierung/M68000/res/gnome-ccperiph.png");
        } catch (FileNotFoundException e) {
            UI.addErrorMessage("Fehler: Icon nicht gefunden");
        }
    }

    public static Pixbuf getIcon() {
        return icon;
    }
}
