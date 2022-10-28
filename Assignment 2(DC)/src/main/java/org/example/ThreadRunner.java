package org.example;

import java.io.IOException;

/**
 * Processor of HTTP request.
 */

public class ThreadRunner
        extends Thread
{
    private final ThreadSafeQueue queue;

    public ThreadRunner(ThreadSafeQueue queue) {
        this.queue = queue;
    }

    public void StartProcessor(T t) throws IOException {
        Processor proc = new Processor(t.socket, t.request);
        proc.process();
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Wait for new element.
                T elem = (T) queue.pop();

                // Stop consuming if null is received.
                if (elem == null) {
                    return;
                }

                // Process element.
                StartProcessor(elem);
            }
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}