/*
 * Copyright (c) 2017-2021 Hugo Dupanloup (Yeregorix)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.smoofyuniverse.logger.core;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalTime;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * A data structure representing a log message.
 */
public final class LogMessage {
	/**
	 * The logger.
	 */
	public final ILogger logger;

	/**
	 * The log level.
	 */
	public final LogLevel level;

	/**
	 * The time.
	 */
	public final LocalTime time;

	/**
	 * The thread.
	 */
	public final Thread thread;

	/**
	 * The throwable.
	 */
	public final Throwable throwable;
	private String stackTrace;

	private Supplier<String> textSupplier;
	private String text;

	public LogMessage(ILogger logger, LogLevel level, Throwable throwable, String text) {
		this(logger, level, LocalTime.now(), Thread.currentThread(), throwable, text);
	}

	public LogMessage(ILogger logger, LogLevel level, LocalTime time, Thread thread, Throwable throwable, String text) {
		this(logger, level, time, thread, throwable);
		if (text == null)
			throw new IllegalArgumentException("text");
		this.text = text;
	}

	public LogMessage(ILogger logger, LogLevel level, LocalTime time, Thread thread, Throwable throwable, String text, String stackTrace) {
		this(logger, level, time, thread, throwable, text);
		this.stackTrace = stackTrace;
	}

	private LogMessage(ILogger logger, LogLevel level, LocalTime time, Thread thread, Throwable throwable) {
		if (logger == null)
			throw new IllegalArgumentException("logger");
		if (level == null)
			throw new IllegalArgumentException("level");
		if (time == null)
			throw new IllegalArgumentException("time");
		if (thread == null)
			throw new IllegalArgumentException("thread");

		this.time = time;
		this.level = level;
		this.logger = logger;
		this.thread = thread;
		this.throwable = throwable;
	}

	public LogMessage(ILogger logger, LogLevel level, Throwable throwable, Supplier<String> textSupplier) {
		this(logger, level, LocalTime.now(), Thread.currentThread(), throwable, textSupplier);
	}

	public LogMessage(ILogger logger, LogLevel level, LocalTime time, Thread thread, Throwable throwable, Supplier<String> textSupplier) {
		this(logger, level, time, thread, throwable);
		if (textSupplier == null)
			throw new IllegalArgumentException("textSupplier");
		this.textSupplier = textSupplier;
	}

	public LogMessage(ILogger logger, LogLevel level, LocalTime time, Thread thread, Throwable throwable, Supplier<String> textSupplier, String stackTrace) {
		this(logger, level, time, thread, throwable, textSupplier);
		this.stackTrace = stackTrace;
	}

	/**
	 * Gets the text.
	 * This method may lazy-initialize the text from a supplier.
	 *
	 * @return The text.
	 */
	public String getText() {
		if (this.text == null) {
			this.text = this.textSupplier.get();
			if (this.text == null)
				this.text = "";
		}
		return this.text;
	}

	/**
	 * Transforms text and stack trace.
	 *
	 * @param transformer The transformer.
	 * @return The new log message.
	 */
	public LogMessage transform(UnaryOperator<String> transformer) {
		return new LogMessage(this.logger, this.level, this.time, this.thread, this.throwable,
				transformer.apply(getText()), transformer.apply(getStackTrace()));
	}

	/**
	 * Gets the stack trace.
	 * This method may lazy-initialize the stack trace from the throwable.
	 * Stack trace may be set even when the throwable is null.
	 *
	 * @return The stack trace.
	 */
	public String getStackTrace() {
		if (this.stackTrace == null) {
			if (this.throwable == null) {
				this.stackTrace = "";
			} else {
				StringWriter buffer = new StringWriter();
				this.throwable.printStackTrace(new PrintWriter(buffer));
				this.stackTrace = buffer.toString();
			}
		}
		return this.stackTrace;
	}
}
