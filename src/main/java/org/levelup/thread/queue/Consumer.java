package org.levelup.thread.queue;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
@RequiredArgsConstructor
public class Consumer implements Runnable{
    private final Queue queue;
    @Override
    @SneakyThrows
    public void run() {
        while (true){
            Runnable task=queue.take();
            task.run();
            Thread.sleep(200);
        }

    }
}
