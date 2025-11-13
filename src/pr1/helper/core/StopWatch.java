/*
 * Copyright (C) 2025 Benjamin Wagner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package pr1.helper.core;

/**
 * A simple and lightweight utility class for measuring elapsed time.
 * <p>
 * The {@code StopWatch} class provides nanosecond precision for timing operations
 * and can report elapsed time in both milliseconds and seconds. It uses
 * {@link System#nanoTime()} internally for high-resolution time measurement.
 * </p>
 *
 * @author Benjamin Wagner
 * @version 1.0
 * @since 2025
 */
public class StopWatch {

    /**
     * The timestamp when the stopwatch was started, in nanoseconds.
     */
    private long startTime;

    /**
     * The timestamp when the stopwatch was stopped, in nanoseconds.
     */
    private long endTime;

    /**
     * Indicates whether the stopwatch is currently running.
     */
    private boolean isRunning;

    /**
     * Starts the stopwatch.
     * <p>
     * Records the current time using {@link System#nanoTime()} and sets
     * the internal state to running. If the stopwatch is already running,
     * the start time is reset to the current moment.
     * </p>
     */
    public void start() {
        startTime = System.nanoTime();
        isRunning = true;
    }

    /**
     * Stops the stopwatch.
     * <p>
     * Records the current time as the end timestamp and marks the stopwatch
     * as stopped. The elapsed time can then be retrieved using
     * {@link #getElapsedMillis()} or {@link #getElapsedSeconds()}.
     * </p>
     */
    public void stop() {
        endTime = System.nanoTime();
        isRunning = false;
    }

    /**
     * Returns the elapsed time in seconds.
     * <p>
     * If the stopwatch is still running, the result is calculated relative
     * to the current time. Otherwise, it reflects the duration between
     * {@link #start()} and {@link #stop()}.
     * </p>
     *
     * @return elapsed time in seconds as a {@code double}.
     */
    public double getElapsedSeconds() {
        long elapsed = isRunning ? System.nanoTime() - startTime : endTime - startTime;
        return elapsed / 1_000_000_000.0;
    }

    /**
     * Returns the elapsed time in milliseconds.
     * <p>
     * If the stopwatch is still running, the result is calculated relative
     * to the current time. Otherwise, it reflects the duration between
     * {@link #start()} and {@link #stop()}.
     * </p>
     *
     * @return elapsed time in milliseconds as a {@code long}.
     */
    public long getElapsedMillis() {
        long elapsed = isRunning ? System.nanoTime() - startTime : endTime - startTime;
        return elapsed / 1_000_000;
    }
}
