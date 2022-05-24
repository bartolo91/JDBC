package rekurencja.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Queue;

public class FileVisitor extends SimpleFileVisitor<Path> {

    public Queue<String> queue;

    public FileVisitor(Queue<String> queue) {
        this.queue = queue;
    }

    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        if (file.getFileName().toString().endsWith(".java")) {
            BufferedReader bufferedReader = Files.newBufferedReader(file);
            StringBuilder currentFile = new StringBuilder(bufferedReader.readLine());
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                currentFile.append(s);
            }
            queue.add(currentFile.toString());

        }
        return FileVisitResult.CONTINUE;
    }
}
