package com.simulation.sync;

import java.util.Random;

/**
 * Consumer: Attempts to remove items from the SharedBuffer.
 * Uses a non-blocking polling strategy with Thread.yield() if the buffer is empty or locked.
 */
public class Consumer implements Runnable {
    private final SharedBuffer buffer;
    private final Random random = new Random();

    public Consumer(SharedBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            int item;

            // Polling loop: Keep trying until an item is retrieved
            while ((item = buffer.remove()) == -1) {
                // Lock was busy or buffer was empty; yield to let Producer work
                Thread.yield();
            }

            // Process the item (we just log it here, but could be complex logic)
            // Simulation of processing time
            try {
                Thread.sleep(random.nextInt(400) + 100); // 100ms to 500ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}