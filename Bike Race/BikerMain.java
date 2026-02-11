// Assignment:
// Keep logic working as it is, just use Executor Service.

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

class Biker implements Callable<BikerResult> {
    private final String name;
    private final int totalDistance;
    private final CountDownLatch startSignal;
    private final Random random = new Random();
    Biker(String name, int totalDistance, CountDownLatch startSignal) {
        this.name = name;
        this.totalDistance = totalDistance;
        this.startSignal = startSignal;
    }

    public BikerResult call() throws Exception {
        startSignal.await();
        long startTime = System.currentTimeMillis();
        int distanceCovered = 0;
        while (distanceCovered < totalDistance) {
            int speed = random.nextInt(100) + 1;
            distanceCovered += speed;
            if (distanceCovered > totalDistance) {
                distanceCovered = totalDistance;
            }
            synchronized (Biker.class) {
                System.out.println("[" + name + "] = Covered: " + distanceCovered + "mtrs");
            }
            Thread.sleep(1000);
        }

        long endTime = System.currentTimeMillis();
        synchronized (Biker.class) {
            System.out.println("[" + name + "] FINISHED!");
        }
        return new BikerResult(name, startTime, endTime, endTime - startTime);
    }
}

// New class to store results so can sort using stream
record BikerResult(String name, long startTime, long endTime, long timeTaken) {} 

public class BikerMain {
    private static final int BIKERS = 2;
    private static final ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) throws Exception{
        try (Scanner sc = new Scanner(System.in)) {

            System.out.print("Enter race Distance in KM: ");
            int distanceKm = sc.nextInt();
            int totalDistanceMeters = distanceKm * 1000;
            System.out.print("Total Race Distance: "+totalDistanceMeters+"mtrs.\n\n");

            sc.nextLine();
            List<String> bikerNames = new ArrayList<>();
            for (int i = 1; i <= BIKERS; i++) {
                System.out.print("Enter name for Biker " + i + ": ");
                bikerNames.add(sc.nextLine());
            }

            ExecutorService executor = Executors.newFixedThreadPool(BIKERS);
            CountDownLatch startSignal = new CountDownLatch(1);

            List<Callable<BikerResult>> tasks = bikerNames.stream()
                .map(name -> new Biker(name, totalDistanceMeters, startSignal))
                .collect(Collectors.toList());

            lock.lock();
                System.out.println("\nAll bikers are ready...");
                System.out.println("Race starting in:");
                for (int i = 3; i >= 0; i--) {
                    if(i == 0)
                        System.out.println("Go!!\n");
                    else
                        System.out.println(i + "...");
                    Thread.sleep(1000);
                }
            lock.unlock();

            startSignal.countDown();

            System.out.println("==Race Status==");
            List<Future<BikerResult>> futures = executor.invokeAll(tasks);
            executor.shutdown();


            List<BikerResult> results = new ArrayList<>();
            for (Future<BikerResult> f : futures) {
                results.add(f.get());
            }
            results.sort(Comparator.comparingLong(BikerResult::timeTaken));

            lock.lock();
                System.out.println("\n===== RANKINGS =====");
                int rank = 1;
                for (BikerResult r : results) {
                    System.out.println(rank++ + " | Biker: " + r.name() + " | StartTime: "+ r.startTime() + " | EndTime: "+ r.endTime() +" | Time Taken: " + r.timeTaken() + "ms.");
                }
            lock.unlock();

        } catch (Exception e) {
            System.out.println("Error: "+e);
        }
    }
}