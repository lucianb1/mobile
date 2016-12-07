package com.example.botos.appointment.threadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import android.os.Process;

/**
 * Created by Botos on 12/7/2016.
 */
public class DefaultExecutorSupplier {

    /*
    * Number of cores to decide the number of threads
    */
    public static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    /*
    * thread pool executor for background tasks
    */
    private ThreadPoolExecutor mSeverRequests;
    /*
    * thread pool executor for light weight background tasks
    */
    private ThreadPoolExecutor mImageRequests;
    /*
    * thread pool executor for main thread tasks
    */
    private Executor mMainThreadExecutor;
    /*
    * an instance of DefaultExecutorSupplier
    */
    private static DefaultExecutorSupplier sInstance;

    private PriorityThreadPoolExecutor mForPriorityBackgroundTasks;

    /*
    * returns the instance of DefaultExecutorSupplier
    */
    public static DefaultExecutorSupplier getInstance() {
        if (sInstance == null) {
            synchronized (DefaultExecutorSupplier.class) {
                sInstance = new DefaultExecutorSupplier();
            }
        }
        return sInstance;
    }
    /*
    * constructor for  DefaultExecutorSupplier
    */
    private DefaultExecutorSupplier() {

        // setting the thread factory
        ThreadFactory backgroundPriorityThreadFactory = new
                PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND);

        // setting the thread pool executor for mSeverRequests;
        mSeverRequests = new ThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                backgroundPriorityThreadFactory
        );

        // setting the thread pool executor for mImageRequests;
        mImageRequests = new ThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                backgroundPriorityThreadFactory
        );

        mForPriorityBackgroundTasks = new PriorityThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L,
                TimeUnit.SECONDS,
                backgroundPriorityThreadFactory
        );

        // setting the thread pool executor for mMainThreadExecutor;
        mMainThreadExecutor = new MainThreadExecutor();
    }

    /*
    * returns the thread pool executor for background task
    */
    public ThreadPoolExecutor getServerRequestsThreadPool() {
        return mSeverRequests;
    }

    /*
    * returns the thread pool executor for light weight background task
    */
    public ThreadPoolExecutor getImageRequestsThreadPool() {
        return mImageRequests;
    }

    /*
    * returns the thread pool executor for main thread task
    */
    public Executor forMainThreadTasks() {
        return mMainThreadExecutor;
    }

    public PriorityThreadPoolExecutor getForPriorityBackgroundTasks() {
        return mForPriorityBackgroundTasks;
    }

}
