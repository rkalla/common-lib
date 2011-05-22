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
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Default implementation of {@link IService} utilizing a single, fixed-size
 * {@link ThreadPoolExecutor}.
 * 
 * @author Riyad Kalla (software@thebuzzmedia.com)
 */
public class DefaultService implements IService {
	public static final int DEFAULT_THREAD_POOL_SIZE = 64;

	private boolean shutdown;
	private ExecutorService executor;

	public DefaultService() {
		this(DEFAULT_THREAD_POOL_SIZE);
	}

	public DefaultService(int threadPoolSize) throws IllegalArgumentException {
		if (threadPoolSize < 1)
			throw new IllegalArgumentException("threadPoolSize must be >= 1");

		shutdown = false;
		executor = Executors.newFixedThreadPool(threadPoolSize);
	}

	public boolean isShutdown() {
		return shutdown;
	}

	/**
	 * Implemented to do a graceful shutdown using
	 * {@link ThreadPoolExecutor#shutdown()} on the internal executor. All
	 * pending tasks will be given a chance to complete.
	 */
	public void shutdown() {
		// Shut down the executor service so all threads are destroyed.
		executor.shutdown();

		// Update state
		shutdown = true;
	}

	public ExecutorService getExecutorService() {
		return executor;
	}

	public <T> Future<T> submit(Callable<T> task) {
		return executor.submit(task);
	}
}