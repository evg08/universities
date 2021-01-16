package org.levelup.thread.queue;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.UUID;

@RequiredArgsConstructor
public class Producer implements Runnable {
    private final Queue queue;
    @Override
    @SneakyThrows  //возможность не писать try catch для исключения
    public void run() {
        for (int i=0;i<20;i++){
            queue.add(()->{
                System.out.println(UUID.randomUUID().toString().substring(0,6));
            });
            Thread.sleep(100);
        }

    }
}
