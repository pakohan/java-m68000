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
 * The Class LinkedList cares about a linked list. The pointer previous element
 * of the first list element points to the last element in the list, so you
 * can call the add()-method of the head element to add an element at the end
 * of the list.
 *
 * @param <T> the type of the element stored in the list. It is recommended to
 * use an object to store more than one single element in the list.
 */
public class LinkedList<T> {

    /**
     * The item stores the data.
     */
    private T item;

    /**
     * The number of elements stored in this list. Only in the head the right
     * number will be stored!!!
     */
    private int size = 1;

    /**
     * The pointer to the next element.
     */
    private LinkedList<T> next;

    /**
     * The pointer to the previous element.
     */
    private LinkedList<T> prev;

    /**
     * Instantiates a new linked list. The given argument is the head of the
     * list.
     *
     * @param element the head
     */
    public LinkedList(final T element) {
        this.item = element;
        this.next = this;
        this.prev = this;
    }

    /**
     * Instantiates a new linked list. The first given argument points to the
     * previous element, the second to the next element. The third will store
     * the data.
     *
     * @param pre the pre
     * @param nex the nex
     * @param element the element
     */
    private LinkedList(final LinkedList<T> pre,
                      final LinkedList<T> nex,
                      final T element) {
        this.item = element;
        this.next = nex;
        this.prev = pre;
        this.prev.setNext(this);
    }

    /**
     * Adds a new element to the end of the list.
     *
     * @param element the element that will be added
     */
    public final void add(final T element) {
        this.prev = new LinkedList<T>(this.prev, this, element);
        ++this.size;
    }

    /**
     * Gets the size of the list. Only makes sense if called by the head
     * element object.
     *
     * @return the size
     */
    public final int getSize() {
        return this.size;
    }

    /**
     * Returns the item stored in the list.
     *
     * @return the item
     */
    public final T getItem() {
        return this.item;
    }

    /**
     * Returns the next element.
     *
     * @return the next element
     */
    public final LinkedList<T> getNext() {
        return this.next;
    }

    /**
     * Sets the next element.
     *
     * @param nex the next element
     */
    private void setNext(final LinkedList<T> nex) {
        this.next = nex;
    }

    /**
     * Returns the previous element.
     *
     * @return the previous element
     */
    public final LinkedList<T> getPrev() {
        return this.prev;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
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
