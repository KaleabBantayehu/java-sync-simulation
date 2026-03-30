# TSL-Based Producer-Consumer Simulation

## 1. Project Overview
This project implements a solution to the classic **Producer-Consumer Problem** using a **Bounded Buffer**.  
The core objective is to demonstrate **Process Synchronization** and **Mutual Exclusion** using a simulated hardware-level **Test-and-Set Lock (TSL)**.

Instead of relying on high-level language constructs such as `synchronized` or Semaphores, this implementation utilizes **atomic operations** to mimic low-level CPU instructions.

---

## 2. Technical Architecture
The system is designed with a strict **Separation of Concerns (SoC)**, divided into five primary components:

- **TSLock**  
  A hardware-simulator class. It uses `java.util.concurrent.atomic.AtomicBoolean` to implement a non-blocking `compareAndSet (CAS)` operation, providing a true atomic "Test-and-Set" mechanism.

- **SharedBuffer**  
  The critical section. It manages a circular integer array, tracking in/out pointers and the element count. Access to these variables is protected by the TSLock.

- **Producer**  
  A worker thread that generates data. It employs a polling strategy; if the lock is busy or the buffer is full, it yields the CPU and retries, ensuring no deadlocks occur.

- **Consumer**  
  A worker thread that retrieves data. Like the producer, it uses a non-blocking approach to safely remove items.

- **Main**  
  The orchestrator that initializes the shared state and manages thread lifecycles.

---

## 3. Synchronization Logic
To prevent the **Deadlock Trap** inherent in busy-waiting systems, the implementation follows the **Acquire-Check-Release** pattern:

1. **Acquire** – Attempt to grab the TSL lock.  
2. **Check** – If the lock is acquired, check the buffer state (Full/Empty).  
3. **Execute/Skip** – If the state allows, perform the operation. If not, do nothing.  
4. **Release** – Always release the lock before yielding, allowing the other process a window to modify the buffer state.

---

## 4. How to Run

### Compilation
```bash
javac -d bin src/com/simulation/sync/*.java
```
### Execution
```bash
java -cp bin com.simulation.sync.Main
