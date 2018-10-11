package com.boot.integration.conf.thread;

public class ThreadSync
{
    public static void main(String[] args)
    {
        //创建一个线程对象
        TicketThread tt = new TicketThread();

        //创建三个抢票对象
        Thread t1 = new Thread(tt, "t1");
        Thread t2 = new Thread(tt, "t2");
        Thread t3 = new Thread(tt, "t3");

        t1.start();
        t2.start();
        t3.start();
    }
}

class TicketThread extends Thread
{
    private int ticketNum = 5;

    public void run()
    {
        for (int i = 0; i < 3; i++)
        {
            //            synchronized (this)
            //            {
            //                if (ticketNum > 0)
            //                {
            //                    System.out.println("===================================");
            //                    System.out.println("当前票数：" + ticketNum);
            //                    System.out.println(Thread.currentThread().getName()+"-->已抢票");
            //                    ticketNum--;
            //                    System.out.println("当前剩票：" + ticketNum);
            //                    System.out.println("===================================");
            //                }
            //            }

            sale();
        }
    }

    public synchronized void sale()
    {
        if (ticketNum > 0)
        {
            System.out.println("===================================");
            System.out.println("当前票数：" + ticketNum);
            System.out.println(Thread.currentThread().getName() + "-->已抢票");
            ticketNum--;
            System.out.println("当前剩票：" + ticketNum);
            System.out.println("===================================");
        }
    }
}