package demo.timer;


import demo.util.DateUtil;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

    public static void main(String[] args) {
        TimerTask myTimeTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("执行任务myTimeTask: " + DateUtil.now());

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer();
        System.out.println("启动时间：" + DateUtil.now());
        //在时间等于或超过time的时候执行且只执行一次task，这个time指的是时刻。
        //timer.schedule(myTimeTask, new Date());
        //在delay时间之后，执行且只执行一次task。这个delay表示的是延迟时间，比如说2秒后执行
        //timer.schedule(myTimeTask, 1000);
        //在时间等于或超过time的时候首次执行task，之后每隔period毫秒重复执行一次task 。
        //timer.schedule(myTimeTask, new Date(), 1000);
        //在delay时间之后，开始首次执行task，之后每隔period毫秒重复执行一次task
        //timer.schedule(myTimeTask, 2000, 1000);

        TimerTask myTimeTask2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("执行任务myTimeTask2: " + DateUtil.now());
            }
        };
        timer.schedule(myTimeTask, 1000);
        timer.schedule(myTimeTask2, 2000);

    }
}
//结论：task2实际上是4后才执行，正因为Timer内部是一个线程，而任务1所需的时间超过了两个任务间的间隔导致。
