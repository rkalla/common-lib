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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Interface used to define a simple, {@link ExecutorService}-backed service
 * used to process {@link Callable} tasks in parallel; returning an appropriate
 * {@link Future} to the caller that will eventually contain the result of the
 * operation (or <code>null</code> if there is no result).
 * <p/>
 * Services are meant as a natural compliment to {@link AbstractRetryableTask}s
 * to provide an easy-to-use foundation for executing retryable tasks in a
 * system.
 * 
 * @author Riyad Kalla (software@thebuzzmedia.com)
 */
public interface IService {
	/**
	 * Used to determine if this service has been shutdown already.
	 * 
	 * @return <code>true</code> if the service has been shutdown, otherwise
	 *         returns <code>false</code>.
	 */
	public boolean isShutdown();

	/**
	 * Used to execute clean-up code necessary to "shutdown" this service.
	 * Implementation details will depend on what resources a {@link IService}
	 * holds on to and what needs to be cleaned up before exiting.
	 */
	public void shutdown();

	/**
	 * Used to retrieve the {@link ExecutorService} instance backing this
	 * service.
	 * <p/>
	 * For shutting down a service an any backing resources like this one, do
	 * not try and shutdown the {@link ExecutorService} directly, but instead
	 * call {@link #shutdown()} to clean up any resources this service uses.
	 * 
	 * @return the {@link ExecutorService} instance backing this service.
	 */
	public ExecutorService getExecutorService();

	/**
	 * Used to execute the given {@link Callable} asynchronously using the
	 * backing {@link #getExecutorService()} and returning a {@link Future}
	 * representing the eventual result.
	 * <p/>
	 * Calling {@link Future#get()} is the easiest way to block on the task
	 * until it returns a value or throws an exception indicating it failed.
	 * 
	 * @param <T>
	 *            The type of the result returned by the {@link Future} when the
	 *            result is ready.
	 * @param task
	 *            The task that will be executed asynchronously.
	 * 
	 * @return a {@link Future} representing the eventual result. Calling
	 *         {@link Future#get()} is the easiest way for the calling thread to
	 *         block on the asynchronous call until the result is either ready
	 *         (and returned) or the task throws an exception indicating it
	 *         failed.
	 */
	public <T> Future<T> submit(Callable<T> task);
}