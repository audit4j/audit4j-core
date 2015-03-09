/*
 * Copyright (c) 2014-2015 Janith Bandara, This source is a part of
 * Audit4j - An open source auditing framework.
 * http://audit4j.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.audit4j.core.util;

import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

import org.audit4j.core.util.annotation.NotThreadSafe;

/**
 * Simple stop watch, allowing for timing of a number of tasks, exposing total
 * running time and running time for each named task.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.3.0
 */
@NotThreadSafe("Not thread safe")
public class StopWatch {

    /**
     * Identifier of this stop watch. Handy when we have output from multiple
     * stop watches and need to distinguish between them in log or console
     * output.
     */
    private final String id;

    /** The keep task list. */
    private boolean keepTaskList = true;

    /** The task list. */
    private final List<TaskInfo> taskList = new LinkedList<TaskInfo>();

    /** Start time of the current task. */
    
    private long startTime;
    
    /** The start time millis. */
    private long startTimeMillis;

    /** Is the stop watch currently running?. */
    private boolean running;

    /** Name of the current task. */
    private String currentTaskName;

    /** The last task info. */
    private TaskInfo lastTaskInfo;

    /** The task count. */
    private int taskCount;

    /** The total time. */
    private long totalTime;
    
    /** Total running time. */
    private long totalTimeMillis;
    
    /**
     * Construct a new stop watch. Does not start any task.
     */
    public StopWatch() {
        this.id = "";
    }

    /**
     * Construct a new stop watch with the given id. Does not start any task.
     * 
     * @param id
     *            identifier for this stop watch. Handy when we have output from
     *            multiple stop watches and need to distinguish between them.
     */
    public StopWatch(String id) {
        this.id = id;
    }

    /**
     * Determine whether the TaskInfo array is built over time. Set this to
     * "false" when using a StopWatch for millions of intervals, or the task
     * info structure will consume excessive memory. Default is "true".
     * 
     * @param keepTaskList
     *            the new keep task list
     */
    public void setKeepTaskList(boolean keepTaskList) {
        this.keepTaskList = keepTaskList;
    }

    /**
     * Start an unnamed task. The results are undefined if {@link #stop()} or
     * timing methods are called without invoking this method.
     * 
     * @throws IllegalStateException
     *             the illegal state exception
     * @see #stop()
     */
    public void start() throws IllegalStateException {
        start("");
    }

    /**
     * Start a named task. The results are undefined if {@link #stop()} or
     * timing methods are called without invoking this method.
     * 
     * @param taskName
     *            the name of the task to start
     * @throws IllegalStateException
     *             the illegal state exception
     * @see #stop()
     */
    public void start(String taskName) throws IllegalStateException {
        if (this.running) {
            throw new IllegalStateException("Can't start StopWatch: it's already running");
        }
        this.startTime = System.nanoTime();
        this.startTimeMillis = System.currentTimeMillis();
        this.running = true;
        this.currentTaskName = taskName;
    }

    /**
     * Reset.
     *
     * @throws IllegalStateException the illegal state exception
     */
    public void reset() throws IllegalStateException {
        this.startTime = 0;
        this.startTimeMillis = 0;
        this.running = false;
        this.currentTaskName = null;
    }

    /**
     * Stop the current task. The results are undefined if timing methods are
     * called without invoking at least one pair {@link #start()} /
     * 
     * @throws IllegalStateException
     *             the illegal state exception {@link #stop()} methods.
     * @see #start()
     */
    public void stop() throws IllegalStateException {
        if (!this.running) {
            throw new IllegalStateException("Can't stop StopWatch: it's not running");
        }
        long lastTime = System.nanoTime() - this.startTime;
        this.totalTime += lastTime;
        
        long lastTimeMillis = System.currentTimeMillis() - this.startTimeMillis;
        this.totalTimeMillis += lastTimeMillis;

        this.lastTaskInfo = new TaskInfo(this.currentTaskName, lastTimeMillis, lastTime);
        if (this.keepTaskList) {
            this.taskList.add(lastTaskInfo);
        }
        ++this.taskCount;
        this.running = false;
        this.currentTaskName = null;
    }

    /**
     * Return whether the stop watch is currently running.
     * 
     * @return true, if is running
     */
    public boolean isRunning() {
        return this.running;
    }

    /**
     * Return the time taken by the last task.
     * 
     * @return the last task time millis
     * @throws IllegalStateException
     *             the illegal state exception
     */
    public long getLastTaskTime() throws IllegalStateException {
        if (this.lastTaskInfo == null) {
            throw new IllegalStateException("No tasks run: can't get last task interval");
        }
        return this.lastTaskInfo.getTime();
    }
    
    /**
     * Return the time taken by the last task.
     * 
     * @return the last task time millis
     * @throws IllegalStateException
     *             the illegal state exception
     */
    public long getLastTaskTimeMillis() throws IllegalStateException {
        if (this.lastTaskInfo == null) {
            throw new IllegalStateException("No tasks run: can't get last task interval");
        }
        return this.lastTaskInfo.getTimeMillis();
    }

    /**
     * Return the name of the last task.
     * 
     * @return the last task name
     * @throws IllegalStateException
     *             the illegal state exception
     */
    public String getLastTaskName() throws IllegalStateException {
        if (this.lastTaskInfo == null) {
            throw new IllegalStateException("No tasks run: can't get last task name");
        }
        return this.lastTaskInfo.getTaskName();
    }

    /**
     * Return the last task as a TaskInfo object.
     * 
     * @return the last task info
     * @throws IllegalStateException
     *             the illegal state exception
     */
    public TaskInfo getLastTaskInfo() throws IllegalStateException {
        if (this.lastTaskInfo == null) {
            throw new IllegalStateException("No tasks run: can't get last task info");
        }
        return this.lastTaskInfo;
    }

    public long getTotalTime() {
        return totalTime;
    }

    /**
     * Return the total time in milliseconds for all tasks.
     * 
     * @return the total time millis
     */
    public long getTotalTimeMillis() {
        return this.totalTimeMillis;
    }

    /**
     * Return the total time in seconds for all tasks.
     * 
     * @return the total time seconds
     */
    public double getTotalTimeSeconds() {
        return this.totalTimeMillis / 1000.0;
    }

    /**
     * Return the number of tasks timed.
     * 
     * @return the task count
     */
    public int getTaskCount() {
        return this.taskCount;
    }

    /**
     * Return an array of the data for tasks performed.
     * 
     * @return the task info
     */
    public TaskInfo[] getTaskInfo() {
        if (!this.keepTaskList) {
            throw new UnsupportedOperationException("Task info is not being kept!");
        }
        return this.taskList.toArray(new TaskInfo[this.taskList.size()]);
    }

    /**
     * Return a short description of the total running time.
     * 
     * @return the string
     */
    public String shortSummary() {
        return "StopWatch '" + this.id + "': running time (millis) = " + getTotalTimeMillis();
    }

    /**
     * Return a string with a table describing all tasks performed. For custom
     * reporting, call getTaskInfo() and use the task info directly.
     * 
     * @return the string
     */
    public String prettyPrint() {
        StringBuilder sb = new StringBuilder(shortSummary());
        sb.append('\n');
        if (!this.keepTaskList) {
            sb.append("No task info kept");
        } else {
            sb.append("-----------------------------------------\n");
            sb.append("ms % Task name\n");
            sb.append("-----------------------------------------\n");
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMinimumIntegerDigits(5);
            nf.setGroupingUsed(false);
            NumberFormat pf = NumberFormat.getPercentInstance();
            pf.setMinimumIntegerDigits(3);
            pf.setGroupingUsed(false);
            for (TaskInfo task : getTaskInfo()) {
                sb.append(nf.format(task.getTimeMillis())).append(" ");
                sb.append(pf.format(task.getTimeSeconds() / getTotalTimeSeconds())).append(" ");
                sb.append(task.getTaskName()).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Return an informative string describing all tasks performed For custom
     * reporting, call {@code getTaskInfo()} and use the task info directly.
     * 
     * @return the string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(shortSummary());
        if (this.keepTaskList) {
            for (TaskInfo task : getTaskInfo()) {
                sb.append("; [").append(task.getTaskName()).append("] took ").append(task.getTimeMillis());
                long percent = Math.round((100.0 * task.getTimeSeconds()) / getTotalTimeSeconds());
                sb.append(" = ").append(percent).append("%");
            }
        } else {
            sb.append("; no task info kept");
        }
        return sb.toString();
    }

    /**
     * Inner class to hold data about one task executed within the stop watch.
     * 
     * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
     */
    public static final class TaskInfo {

        /** The task name. */
        private final String taskName;

        /** The time millis. */
        private final long timeMillis;
        
        /** The time. */
        private final long time;

        /**
         * Instantiates a new task info.
         *
         * @param taskName the task name
         * @param timeMillis the time millis
         * @param time the time
         */
        TaskInfo(String taskName, long timeMillis, long time) {
            this.taskName = taskName;
            this.timeMillis = timeMillis;
            this.time = time;
        }

        /**
         * Return the name of this task.
         * 
         * @return the task name
         */
        public String getTaskName() {
            return this.taskName;
        }

        /**
         * Gets the time.
         *
         * @return the time
         */
        public long getTime() {
            return time;
        }

        /**
         * Return the time in milliseconds this task took.
         * 
         * @return the time millis
         */
        public long getTimeMillis() {
            return this.timeMillis;
        }

        /**
         * Return the time in seconds this task took.
         * 
         * @return the time seconds
         */
        public double getTimeSeconds() {
            return this.timeMillis / 1000.0;
        }
    }
}