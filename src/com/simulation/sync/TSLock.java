package com.simulation.sync;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TSLock: Simulates a hardware-level Test-and-Set Lock.
 * Uses a busy-waiting (spinning) strategy to achieve mutual exclusion.
 */
public class TSLock {
    // false = unlocked, true = locked
    private final AtomicBoolean state = new AtomicBoolean(false);

    /**
     * Attempts to acquire the lock. 
     * Returns true if successful, false if the lock was already held.
     */
    public boolean tryLock() {
        // Atomic 'Test and Set' logic
        return state.compareAndSet(false, true);
    }

    /**
     * Releases the lock.
     */
    public void unlock() {
        state.set(false);
    }
}