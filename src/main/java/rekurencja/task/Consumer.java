package rekurencja.task;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public record Consumer(BlockingQueue<String> producerQueue,
                       AtomicInteger counter) implements Callable<Map<String, Long>> {

    @Override
    public Map<String, Long> call() {
        Map<String, Long> rtn = new HashMap<>();
        while (true) {
            String poll = producerQueue.poll();
            if (poll == null && counter.get() == 0) {
                break;
            }
            if (poll == null) {
                continue;
            }
            updateMapWithOccurrences(rtn, poll);
        }

        return rtn;
    }

    public static long getOccurrences(String source, String variableType) {
        return Arrays.stream(source.split(" "))
                .filter(c -> c.equals(variableType))
                .count();
    }

    public void updateMapWithOccurrences(Map<String, Long> map, String clazzContent) {
        List<String> stringsTypes = Arrays.asList("int", "double", "boolean", "char", "long", "byte", "short", "float");
        for (String primitiveType : stringsTypes) {
            if (map.get(primitiveType) == null) {
                map.put(primitiveType, getOccurrences(clazzContent, primitiveType));
            } else {
                Long totalOccurrences = map.get(primitiveType) + getOccurrences(clazzContent, primitiveType);
                map.put(primitiveType, totalOccurrences);
            }
        }
    }
}
