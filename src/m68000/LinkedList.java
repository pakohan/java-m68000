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
package m68000;

/**
 * The Class LinkedList.
 *
 * @param <T> the generic type
 */
public class LinkedList<T> {

    /** The item. */
    private T item;

    /** The size. */
    private int size = 1;
    /** The next. */
    private LinkedList<T> next;

    /** The prev. */
    private LinkedList<T> prev;

    /**
     * Instantiates a new linked list.
     *
     * @param element the element
     */
    public LinkedList(final T element) {
        this.item = element;
        this.next = this;
        this.prev = this;
    }

    /**
     * Instantiates a new linked list.
     *
     * @param pre the pre
     * @param nex the nex
     * @param element the element
     */
    public LinkedList(final LinkedList<T> pre,
                      final LinkedList<T> nex,
                      final T element) {
        this.item = element;
        this.next = nex;
        this.prev = pre;
        this.prev.setNext(this);
    }

    /**
     * Adds the.
     *
     * @param element the element
     */
    public final void add(final T element) {
        this.prev = new LinkedList<T>(this.prev, this, element);
        ++this.size;
    }

    /**
     * Gets the size.
     *
     * @return the size
     */
    public final int getSize() {
        return this.size;
    }

    /**
     * Gets the item.
     *
     * @return the item
     */
    public final T getItem() {
        return this.item;
    }

    /**
     * Sets the item.
     *
     * @param element the new item
     */
    public final void setItem(final T element) {
        this.item = element;
    }

    /**
     * Gets the next.
     *
     * @return the next
     */
    public final LinkedList<T> getNext() {
        return this.next;
    }

    /**
     * Sets the next.
     *
     * @param nex the new next
     */
    public final void setNext(final LinkedList<T> nex) {
        this.next = nex;
    }

    /**
     * Gets the prev.
     *
     * @return the prev
     */
    public final LinkedList<T> getPrev() {
        return this.prev;
    }

    /**
     * Sets the prev.
     *
     * @param pre the new prev
     */
    public final void setPrev(final LinkedList<T> pre) {
        this.prev = pre;
    }

    @Override
    public final String toString() {
        StringBuilder str = new StringBuilder();
        LinkedList<T> tmp = this;
        for (int i = 0; i <= this.size; i++) {
            str.append(this.item.toString()).append("\n");
            tmp = tmp.getNext();
        }
        return str.toString();
    }
}
