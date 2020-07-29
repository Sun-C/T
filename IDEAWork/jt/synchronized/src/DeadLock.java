public class DeadLock implements Runnable{
    public int i =1;
    private static Object o1 = new Object(), o2 = new Object();
    @Override
    public void run() {
        if (i == 1){
            synchronized (o1){
                // Thread.sleep(500);
                synchronized (o2) {
                    System.out.println("1");
                }
            }
        }
        if (i == 0){
            synchronized (o2){
                // Thread.sleep(500);
                synchronized (o1) {
                    System.out.println("0");
                }
            }
        }
    }

    public static void main(String... args) {
        DeadLock dl1 = new DeadLock();
        DeadLock dl2 = new DeadLock();
        dl1.i=1;
        dl2.i=0;
        new Thread(dl1).start();
        new Thread(dl2).start();
    }
}
