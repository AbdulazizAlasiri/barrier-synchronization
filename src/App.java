import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class App {
    private CyclicBarrier cyclicBarrier;
    private int NUM_THREADS; // number of threads that are going to execute

    class MyThread implements Runnable {

        @Override
        public void run() {
            String thisThreadName = Thread.currentThread().getName();

            try {
                // Do some work
                Thread.sleep(1000);
                System.out.println(thisThreadName + " waiting for other threads to reach barrier.");
                cyclicBarrier.await(); // barrier point();

                // Do some work after all threads reaches the barrier point and get released
                System.out.println(thisThreadName + " Is has been released from the barrier point.");

            } catch (InterruptedException e) {
                // ...
            } catch (BrokenBarrierException e) {
                // ...
            }
        }

    }

    // this thread will run when the all threads reached the barrier point.
    class AggregatorThread implements Runnable {

        @Override
        public void run() {

            // do some work
            System.out.println("All threads are in the barrier point");

        }
    }

    public void runDemo(int numThreads) {
        NUM_THREADS = numThreads;

        cyclicBarrier = new CyclicBarrier(NUM_THREADS, new AggregatorThread()); // init(N);

        System.out.println("Creating " + NUM_THREADS + "  threads. ");

        for (int i = 0; i < NUM_THREADS; i++) {
            Thread worker = new Thread(new MyThread());
            worker.setName("Thread " + i);
            worker.start();
        }
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.runDemo(5);
    }
}
