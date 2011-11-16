package m68000;

/**
 * The Class LinkedList.
 *
 * @param <T> the generic type
 */
public class LinkedList<T> {

    /** The item. */
    private T item;

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
     * @param item the item
     */
    public final void add(final T item) {
        this.prev = new LinkedList<T>(this.prev, this, item);
    }

    /**
     * Gets the item.
     *
     * @return the item
     */
    public final T getItem() {
        return item;
    }

    /**
     * Sets the item.
     *
     * @param item the new item
     */
    public final void setItem(final T item) {
        this.item = item;
    }

    /**
     * Gets the next.
     *
     * @return the next
     */
    public final LinkedList<T> getNext() {
        return next;
    }

    /**
     * Sets the next.
     *
     * @param next the new next
     */
    public final void setNext(final LinkedList<T> next) {
        this.next = next;
    }

    /**
     * Gets the prev.
     *
     * @return the prev
     */
    public final LinkedList<T> getPrev() {
        return prev;
    }

    /**
     * Sets the prev.
     *
     * @param prev the new prev
     */
    public final void setPrev(final LinkedList<T> prev) {
        this.prev = prev;
    }
}