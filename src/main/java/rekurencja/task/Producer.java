package rekurencja.task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer extends Thread {

    private final String folderPath;
    private final BlockingQueue<String> producerQueue;
    private final AtomicInteger counter;

    public Producer(String folderPath, BlockingQueue<String> producerQueue, AtomicInteger counter) {
        this.folderPath = folderPath;
        this.producerQueue = producerQueue;
        this.counter = counter;
    }

    @Override
    public void run() {
        try {
            retrieveJavaFilesToString(folderPath);
            counter.getAndDecrement();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void retrieveJavaFilesToString(String folderPath) throws IOException {
        Files.walkFileTree(Paths.get(folderPath), new FileVisitor(producerQueue));
    }
}
