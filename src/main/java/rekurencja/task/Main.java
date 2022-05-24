package rekurencja.task;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {
    public static Map<String, Long> joinMaps(List<Map<String, Long>> maps) {
        return Optional.ofNullable(maps).orElse(Collections.emptyList()).stream()
                .filter(Objects::nonNull)
                .flatMap(o -> o.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingLong(Map.Entry::getValue)));
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        /*
        Stworz komunikacje Producent-Consumer miedzy watkami.
        grupa producentow:
        - jako argument dostaja folder w ktorym wyszukuja pliki (rekurencyjnie w dol) java i dostarczaja je do consumerow
        grupa konsumerow:
        - analizuja zawartosc pliku pod kątem wystąpień typow prymitywnych, tzn: ile jest intow, byteow, booleanow, double, floatow, shortow, longow
        i na ich podstawie zwiekszaja jakas mape wspolna dla wszystkich consumerow gdzie kluczem bedzie prymitow a wartoscia ilosc wystapien danego prymitowa.

        mozna uruchomic wiele producentow i wiele consumerow,
        consumerzy powinni zakonczyc swoje dzialanie w momencie gdy nie ma juz aktywnego producenta i nie zostalo nic do procesowania
        */

        BlockingQueue<String> queue = new LinkedBlockingDeque<String>();
        AtomicInteger atomicInteger = new AtomicInteger(4);
        Map<String, Long> resultMap = new HashMap<>();

        Producer p1 = new Producer("src/main/", queue, atomicInteger);
        Producer p2 = new Producer("src/main/", queue, atomicInteger);
        Producer p3 = new Producer("src/main/java/cars/task", queue, atomicInteger);
        Producer p4 = new Producer("src/main/java/", queue, atomicInteger);

        p1.start();
        p2.start();
        p3.start();
        p4.start();

        Consumer c1 = new Consumer(queue, atomicInteger);
        Consumer c2 = new Consumer(queue, atomicInteger);
        Consumer c3 = new Consumer(queue, atomicInteger);
        Consumer c4 = new Consumer(queue, atomicInteger);

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Future<Map<String, Long>> submit = executorService.submit(c1);
        Future<Map<String, Long>> submit1 = executorService.submit(c2);
        Future<Map<String, Long>> submit3 = executorService.submit(c3);
        Future<Map<String, Long>> submit2 = executorService.submit(c4);

        executorService.shutdown();
        Thread.sleep(10000);

        List<Map<String, Long>> maps = Arrays.asList(submit.get(), submit1.get(), submit2.get(), submit3.get());
        Map<String, Long> stringLongMap = joinMaps(maps);
        System.out.println(stringLongMap);
    }
}
