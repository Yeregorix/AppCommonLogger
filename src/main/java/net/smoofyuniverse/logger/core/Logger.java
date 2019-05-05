/*
 * Copyright (c) 2017 Hugo Dupanloup (Yeregorix)
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

import net.smoofyuniverse.logger.appender.LogAppender;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

public final class Logger implements ILogger {
	private LoggerFactory factory;
	private LogAppender appender;
	private LogLevel level;
	private String name;

	protected Logger(LoggerFactory factory, String name) {
		if (factory == null)
			throw new IllegalArgumentException("factory");
		if (name == null)
			throw new IllegalArgumentException("name");

		this.factory = factory;
		this.appender = factory.getAppender();
		this.name = name;
	}

	public Logger(LogAppender appender, String name) {
		if (appender == null || name == null)
			throw new IllegalArgumentException();

		this.appender = appender;
		this.name = name;
	}

	public Optional<LogLevel> getLevel() {
		return Optional.ofNullable(this.level);
	}

	public void setLevel(LogLevel level) {
		this.level = level;
	}

	public Optional<LoggerFactory> getFactory() {
		return Optional.ofNullable(this.factory);
	}

	@Override
	public boolean isActive(LogLevel level) {
		if (this.level == null)
			return this.factory == null || this.factory.isActive(level);
		return level.ordinal() >= this.level.ordinal();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void log(LogMessage msg, Throwable throwable) {
		if (!isActive(msg.level))
			return;

		this.appender.append(msg);
		if (throwable != null) {
			StringWriter buffer = new StringWriter();
			throwable.printStackTrace(new PrintWriter(buffer));
			this.appender.appendRaw(buffer.toString());
		}
	}
}
