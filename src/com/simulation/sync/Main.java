package com.simulation.sync;

/**
 * Main: The Orchestrator for the Producer-Consumer TSL Simulation.
 * Initializes the shared resource and launches concurrent threads.
 */
public class Main {
    public static void main(String[] args) {
        // 1. Define a fixed buffer size (Bounded Buffer)
        int bufferSize = 5;

        // 2. Instantiate the shared resource (The Critical Section)
        SharedBuffer sharedBuffer = new SharedBuffer(bufferSize);

        // 3. Instantiate the workers (The Runnable Agents)
        Producer producer = new Producer(sharedBuffer);
        Consumer consumer = new Consumer(sharedBuffer);

        // 4. Wrap workers in Thread objects
        Thread threadProducer = new Thread(producer);
        Thread threadConsumer = new Thread(consumer);

        // 5. Set descriptive names for easier debugging/logging
        threadProducer.setName("Producer-1");
        threadConsumer.setName("Consumer-1");

        // 6. Launch the simulation
        System.out.println("==============================================");
        System.out.println("Starting TSL-Based Producer-Consumer Simulation");
        System.out.println("Buffer Size: " + bufferSize);
        System.out.println("==============================================");

        threadProducer.start();
        threadConsumer.start();
    }
}