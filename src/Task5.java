// Знакомство с ReentrantLock

import java.util.concurrent.locks.ReentrantLock;

public class Task5 {
    public static void main(String[] args) {
        CommonResource1 commonResource= new CommonResource1();
        ReentrantLock locker = new ReentrantLock(); // создаем заглушку
        for (int i = 1; i < 6; i++) {
            Thread t = new Thread(new CountThread1(commonResource, locker));
            t.setName("Thread "+ i);
            t.start();
        }
    }
}

class CommonResource1 {
    int x = 0;
}

class CountThread1 implements Runnable {
    CommonResource1 res;
    ReentrantLock locker;

    CountThread1(CommonResource1 res, ReentrantLock lock) {
        this.res = res;
        this.locker = lock;
    }

    public void run() {
        locker.lock(); // устанавливаем блокировку
        try {
            res.x = 1;
            for (int i = 1; i < 5; i++) {
                System.out.printf("%s %d \n", Thread.currentThread().getName(), res.x);
                res.x++;
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            locker.unlock(); // снимаем блокировку
        }
    }
}
