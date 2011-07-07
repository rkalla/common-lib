package com.thebuzzmedia.common.concurrent;

/**
 * Exception thrown by an {@link AbstractRetryableTask} when it fails to execute
 * either caused by the maximum number of retries being attempted or the
 * {@link AbstractRetryableTask#canRetry(Exception, int, int)} returning
 * <code>false</code>.
 * 
 * @author Riyad Kalla (software@thebuzzmedia.com)
 */
public class FailedTaskException extends RuntimeException {
	private static final long serialVersionUID = 5871849147423444823L;

	public FailedTaskException(String message, Exception cause) {
		super(message, cause);
	}
}