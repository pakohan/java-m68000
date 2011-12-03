package ui;

import org.gnome.gdk.Pixbuf;

public final class Ressources {
    private static Pixbuf icon;

    private Ressources() { }

    public static void initRessources() {
        /*try {
            icon = new Pixbuf(
          "/home/mogli/Dokumente/Programmierung/M68000/res/gnome-ccperiph.png");
        } catch (FileNotFoundException e) {
            UI.printMessage("Fehler: Icon nicht gefunden");
        }*/
    }

    public static Pixbuf getIcon() {
        return icon;
    }
}