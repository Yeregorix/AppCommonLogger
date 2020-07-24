/*
 * Copyright (c) 2017-2020 Hugo Dupanloup (Yeregorix)
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

import java.time.LocalTime;
import java.util.function.Supplier;

public final class LogMessage {
	public final LocalTime time;
	public final LogLevel level;
	public final ILogger logger;
	public final Thread thread;

	private Supplier<String> supplier;
	private String text;

	public LogMessage(LogLevel level, ILogger logger, String text) {
		this(level, logger, Thread.currentThread(), text);
	}

	public LogMessage(LogLevel level, ILogger logger, Thread thread, String text) {
		this(LocalTime.now(), level, logger, thread, text);
	}

	public LogMessage(LocalTime time, LogLevel level, ILogger logger, Thread thread, String text) {
		this(time, level, logger, thread);
		if (text == null)
			throw new IllegalArgumentException("text");
		this.text = text;
	}

	private LogMessage(LocalTime time, LogLevel level, ILogger logger, Thread thread) {
		if (time == null)
			throw new IllegalArgumentException("time");
		if (level == null)
			throw new IllegalArgumentException("level");
		if (logger == null)
			throw new IllegalArgumentException("logger");
		if (thread == null)
			throw new IllegalArgumentException("thread");

		this.time = time;
		this.level = level;
		this.logger = logger;
		this.thread = thread;
	}

	public LogMessage(LogLevel level, ILogger logger, Supplier<String> supplier) {
		this(level, logger, Thread.currentThread(), supplier);
	}

	public LogMessage(LogLevel level, ILogger logger, Thread thread, Supplier<String> supplier) {
		this(LocalTime.now(), level, logger, thread, supplier);
	}

	public LogMessage(LocalTime time, LogLevel level, ILogger logger, Thread thread, Supplier<String> supplier) {
		this(time, level, logger, thread);
		if (supplier == null)
			throw new IllegalArgumentException("supplier");
		this.supplier = supplier;
	}

	public String getText() {
		if (this.text == null) {
			this.text = this.supplier.get();
			if (this.text == null)
				this.text = "";
		}
		return this.text;
	}
}
