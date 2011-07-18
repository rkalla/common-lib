/**   
 * Copyright 2011 The Buzz Media, LLC
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thebuzzmedia.common.concurrent;

import java.io.IOException;
import java.util.concurrent.Callable;

import com.thebuzzmedia.common.concurrent.FailedTaskException.FailureType;

/**
 * Class used to implement an automatically retryable {@link Callable}.
 * <p/>
 * A task that fails and is deemed to be retryable (
 * {@link #canRetry(Exception, int, int)} returns <code>true</code>) is
 * automatically retried up-to <code>retryCount</code> number of times;
 * gradually increasing the wait-time between retries as determined by the
 * implementation of {@link #calculateRetryDelay(int, long, double)}.
 * <p/>
 * A task is considered successful if a call to {@link #callImpl(int)} doesn't
 * throw an exception.
 * <p/>
 * After the last retry attempt OR if {@link #canRetry(Exception, int, int)}
 * returns <code>false</code>, the task is considered to have failed and the
 * root exception is propagated to the caller in the form of a wrapping
 * {@link RuntimeException}.
 * <p/>
 * The {@link RuntimeException} message or {@link RuntimeException#getCause()}
 * return value will provide details about the failure type of the task.
 * 
 * @author Riyad Kalla (software@thebuzzmedia.com)
 * 
 * @param <V>
 *            The type of the object that {@link #callImpl(int)} implementation
 *            will return to {@link #call()} and {@link #call()} will return to
 *            the caller. In short, the result type of this task.
 * 
 * @since 2.0
 */
public abstract class AbstractRetryableTask<V> implements Callable<V> {
	public static final int DEFAULT_RETRY_COUNT = 5;
	public static final int DEFAULT_INITIAL_RETRY_DELAY = 100;
	public static final double DEFAULT_RETRY_DELAY_FACTOR = 5;

	private int retryCount;
	private long currentRetryDelay;
	private double retryDelayFactor;

	private boolean stopped;
	private boolean interrupted;

	/**
	 * Create a task that will be retried {@link #DEFAULT_RETRY_COUNT} number of
	 * times with an initial retry delay of {@link #DEFAULT_INITIAL_RETRY_DELAY}
	 * milliseconds that is increased by a factor of
	 * {@link #DEFAULT_RETRY_DELAY_FACTOR} every time the task fails.
	 */
	public AbstractRetryableTask() {
		this(DEFAULT_RETRY_COUNT, DEFAULT_INITIAL_RETRY_DELAY,
				DEFAULT_RETRY_DELAY_FACTOR);
	}

	/**
	 * Create a task that will be retried <code>retryCount</code> number of
	 * times with an initial retry delay of <code>initialRetryDelay</code> that
	 * is increased by a factor of <code>retryDelayFactor</code> every time the
	 * task fails.
	 * 
	 * @param retryCount
	 *            The number of times the task will be retried.
	 * @param initialRetryDelay
	 *            The initial retry delay in milliseconds that execution will be
	 *            paused when the task fails the first time. This value will be
	 *            grown by <code>retryDelayFactor</code> every subsequent time
	 *            the task fails.
	 * @param retryDelayFactor
	 *            The factor by which the retry delay is increased every
	 *            subsequent time the task fails. If a custom implementation of
	 *            {@link #calculateRetryDelay(int, long, double)} doesn't need
	 *            this value, it can be set to any positive value and ignored.
	 * 
	 * @throws IllegalArgumentException
	 *             if <code>retryCount</code> is &lt; 1, if
	 *             <code>initialRetryDelay</code> is &lt; 1 or if
	 *             <code>retryDelayFactor</code> is &lt;= 0.
	 */
	public AbstractRetryableTask(int retryCount, long initialRetryDelay,
			double retryDelayFactor) throws IllegalArgumentException {
		if (retryCount < 1)
			throw new IllegalArgumentException(
					"retryCount must be >= 1; specifying 1 or more times to attempt execution of the task. The task must be executed at least 1.");
		if (initialRetryDelay < 1)
			throw new IllegalArgumentException("initialRetryDelay must be > 0");
		if (retryDelayFactor <= 0)
			throw new IllegalArgumentException(
					"retryDelayFactor must be > 0 as it is used to calculate multiples of the retry delay each time the task fails.");

		this.retryCount = retryCount;
		this.currentRetryDelay = initialRetryDelay;
		this.retryDelayFactor = retryDelayFactor;

		this.stopped = false;
		this.interrupted = false;
	}

	public void stop() {
		stopped = true;
	}

	public boolean isStopped() {
		return stopped;
	}

	public boolean isInterrupted() {
		return interrupted;
	}

	/**
	 * Implemented to retry the logic implemented by {@link #callImpl(int)}
	 * automatically if it fails with an exception and we are not on our last
	 * retry attempt and {@link #canRetry(Exception, int, int)} returns
	 * <code>true</code>.
	 * <p/>
	 * This method is <code>final</code> to avoid corruption of the retry logic.
	 * Please implement all task logic in {@link #callImpl(int)} and
	 * should-retry logic in {@link #canRetry(Exception, int, int)}.
	 * 
	 * @throws RuntimeException
	 *             if the executing {@link Thread} is interrupted while sleeping
	 *             and waiting to retry a failed task.
	 * @throws FailedTaskException
	 *             if the task has been retried <code>retryCount</code> times
	 *             and failed every time or if
	 *             {@link #canRetry(Exception, int, int)} returns
	 *             <code>false</code> indicating that the task should not be
	 *             retried.
	 */
	public final V call() throws RuntimeException, FailedTaskException {
		V result = null;
		boolean success = false;

		/*
		 * i=0 is the first attempt at running the task, i=1 through
		 * i=retryCount are the retry attempts indicated by the values given by
		 * the caller.
		 */
		for (int i = 0; !stopped && !interrupted && !success && i <= retryCount; i++) {
			try {
				result = callImpl(i);

				/*
				 * In case this is an operation that returns no value, we assume
				 * an operation was successful IF it completes without an
				 * exception.
				 */
				success = true;
			} catch (Exception e) {
				// Ensure we have retry attempts left and we can retry
				if (i <= retryCount && canRetry(e, i, retryCount)) {
					try {
						// Calculate how long to sleep based on our last sleep.
						currentRetryDelay = calculateRetryDelay(i,
								currentRetryDelay, retryDelayFactor);

						// Sleep the current thread for that amount.
						Thread.sleep(currentRetryDelay);
					} catch (InterruptedException ex) {
						/*
						 * Calling ExecutorService.shutdownNow() interrupts all
						 * pending threads, if we currently have a number of
						 * sleeping threads waiting to be retried, being
						 * interrupted is a perfectly valid use case if the app
						 * is shutting down or being restarted, so respond to
						 * this gracefully instead of throwing an exception.
						 */
						interrupted = true;
					}

					// Try again
					continue;
				}

				String message;
				FailureType type;

				/*
				 * We either retried the task every time or canRetry returned
				 * false.
				 */
				if (i >= retryCount) {
					message = "Failed to execute task ["
							+ this.getClass().getName() + "] after retrying "
							+ retryCount + " times.";
					type = FailureType.MAX_RETRY;
				} else {
					message = "Failed to execute task ["
							+ this.getClass().getName()
							+ "] after canRetry(...) returned false.";
					type = FailureType.CAN_RETRY;
				}

				// Throw the exception up to the caller to do something with it.
				throw new FailedTaskException(type, message, e);
			}
		}

		return result;
	}

	/**
	 * Used to calculate a new retry delay based on what the last retry delay
	 * was and the current retry count (if applicable). This default
	 * implementation simply multiplies the <code>lastRetryDelay</code> by the
	 * <code>retryDelayFactor</code>.
	 * <p/>
	 * Implementors can customize this implementation to use whatever
	 * delay-increase logic they wish either using the passed in arguments or
	 * ignoring them completely.
	 * 
	 * @param currentRetryCount
	 *            The current retry attempt being executed by {@link #call()}.
	 * @param lastRetryDelay
	 *            The last delay that the executing thread was slept for.
	 * @param retryDelayFactor
	 *            The retry delay factor specified when this task was created.
	 * 
	 * @return an updated retry delay value based on whatever the last delay
	 *         value was and the current retry attempt this is (if applicable).
	 */
	protected long calculateRetryDelay(int currentRetryCount,
			long lastRetryDelay, double retryDelayFactor) {
		return (long) ((double) lastRetryDelay * retryDelayFactor);
	}

	/**
	 * Used to determine if, based on the given {@link Exception}, the task can
	 * be safely retried.
	 * <p/>
	 * Some {@link Exception}s indicate a temporary problem in which case the
	 * task should be retried while others can indicate a fundamental failure
	 * (e.g. {@link IOException}) and should not be retried.
	 * 
	 * @param e
	 *            The exception that a call to {@link #callImpl(int)} just
	 *            generated.
	 * @param currentRetryCount
	 *            The current retry attempt. This value will always be &lt; (
	 *            <code>retryCount - 1</code>) as this method is never queried
	 *            if the last attempted retry fails; the task is considered
	 *            failed at that point.
	 * @param maxRetryCount
	 *            The total number of times this task will be retried. This is
	 *            the <code>retryCount</code> variable passed to the constructor
	 *            when this task was created.
	 * 
	 * @return <code>true</code> if the task can be retried otherwise returns
	 *         <code>false</code> to indicate the task failed which causes
	 *         {@link #call()} to wrap the exception in a
	 *         {@link RuntimeException} and throw it up to the caller.
	 */
	protected abstract boolean canRetry(Exception e, int currentRetryCount,
			int maxRetryCount);

	/**
	 * The actual task logic.
	 * <p/>
	 * This will be invoked automatically by {@link #call()} every time this
	 * method throws an exception (indicating a failure). If the call succeeds
	 * then the return value from this method is passed to {@link #call()} and
	 * returned to the caller.
	 * <p/>
	 * Implementations of this method are considered "successful" if no
	 * {@link Exception} is thrown. It is not necessary for an implementation to
	 * return a non-<code>null</code> value to be considered successful.
	 * 
	 * @param retryAttempt
	 *            The current retry attempt for running this method. This value
	 *            starts at 0 (indicating the first run) and ends at value
	 *            <code>retryCount</code>.
	 * 
	 * @return the result of the operation or <code>null</code> if this
	 *         operation returns no value.
	 * 
	 * @throws Exception
	 *             if any {@link Exception} is thrown by the implementation
	 *             indicating that the task has failed and can be potentially
	 *             retried (depending on what
	 *             {@link #canRetry(Exception, int, int)} returns).
	 */
	protected abstract V callImpl(int retryAttempt) throws Exception;
}