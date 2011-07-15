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

/**
 * Exception thrown by an {@link AbstractRetryableTask} when it fails to execute
 * either caused by the maximum number of retries being attempted or the
 * {@link AbstractRetryableTask#canRetry(Exception, int, int)} returning
 * <code>false</code>.
 * 
 * @author Riyad Kalla (software@thebuzzmedia.com)
 * @since 2.3
 */
public class FailedTaskException extends RuntimeException {
	private static final long serialVersionUID = 5871849147423444823L;

	public enum FailureType {
		MAX_RETRY, CAN_RETRY;
	}

	private FailureType type;

	public FailedTaskException(FailureType type, String message, Exception cause) {
		super(message, cause);
		this.type = type;
	}

	public FailureType getFailureType() {
		return type;
	}
}