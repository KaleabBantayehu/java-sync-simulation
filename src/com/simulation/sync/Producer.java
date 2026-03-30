package com.simulation.sync;

import java.util.Random;

/**
 * Producer: Generates random integers and attempts to insert them into the SharedBuffer.
 * Uses a non-blocking polling strategy with Thread.yield() on failure.
 */
public class Producer implements Runnable {
    private final SharedBuffer buffer;
    private final Random random = new Random();

    public Producer(SharedBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            int item = random.nextInt(100); // Generate a random item (0-99)
            boolean success = false;

            // Polling loop: Keep trying until the item is accepted
            while (!success) {
                success = buffer.insert(item);
                
                if (!success) {
                    // Lock was busy or buffer was full; yield to let Consumer work
                    Thread.yield();
                }
            }

            // Simulate work/production time after successful insertion
            try {
                Thread.sleep(random.nextInt(400) + 100); // 100ms to 500ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}