package com.simulation.sync;

/**
 * SharedBuffer: The Critical Section managing the bounded buffer.
 * Uses TSLock to prevent race conditions on 'count', 'in', and 'out'.
 */
public class SharedBuffer {
    private final int[] buffer;
    private final int size;
    private int in = 0;
    private int out = 0;
    private int count = 0;

    private final TSLock lock = new TSLock();

    public SharedBuffer(int size) {
        this.size = size;
        this.buffer = new int[size];
    }

    /**
     * Tries to insert an item. 
     * Returns true on success, false if buffer was full or lock was busy.
     */
    public boolean insert(int item) {
        if (lock.tryLock()) {
            try {
                if (count < size) {
                    buffer[in] = item;
                    in = (in + 1) % size;
                    count++;
                    System.out.println("[PRODUCED] Item: " + item + " | Buffer Count: " + count);
                    return true;
                }
            } finally {
                lock.unlock();
            }
        }
        return false; // Could not insert (either locked or full)
    }

    /**
     * Tries to remove an item.
     * Returns the item value if successful, or -1 if failed.
     */
    public int remove() {
        int item = -1;
        if (lock.tryLock()) {
            try {
                if (count > 0) {
                    item = buffer[out];
                    out = (out + 1) % size;
                    count--;
                    System.out.println("[CONSUMED] Item: " + item + " | Buffer Count: " + count);
                }
            } finally {
                lock.unlock();
            }
        }
        return item;
    }
}