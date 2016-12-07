package com.example.botos.appointment.threadPool;

import com.example.botos.appointment.utils.Utils;

/**
 * Created by Botos on 12/7/2016.
 */
public class PriorityRunnable implements Runnable {

    private final Utils.Priority priority;

    public PriorityRunnable(Utils.Priority priority) {
        this.priority = priority;
    }

    @Override
    public void run() {
        // nothing to do here.
    }

    public Utils.Priority getPriority() {
        return priority;
    }

}
