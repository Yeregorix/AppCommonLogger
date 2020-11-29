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

import net.smoofyuniverse.logger.appender.log.LogAppender;

import java.util.Optional;

/**
 * Default implementation of a {@link Logger}.
 */
public final class Logger implements ILogger {
	private final LoggerFactory factory;
	private final LogAppender appender;
	private final String name;
	private LogLevel level;

	protected Logger(LoggerFactory factory, String name) {
		if (factory == null)
			throw new IllegalArgumentException("factory");
		if (name == null)
			throw new IllegalArgumentException("name");

		this.factory = factory;
		this.appender = factory.getAppender();
		this.name = name;
	}

	/**
	 * Creates a standalone logger.
	 *
	 * @param appender The log appender.
	 * @param name     The name.
	 */
	public Logger(LogAppender appender, String name) {
		if (appender == null || name == null)
			throw new IllegalArgumentException();

		this.factory = null;
		this.appender = appender;
		this.name = name;
	}

	/**
	 * Gets the level.
	 *
	 * @return The level.
	 */
	public Optional<LogLevel> getLevel() {
		return Optional.ofNullable(this.level);
	}

	/**
	 * Sets the level.
	 *
	 * @param level The level.
	 */
	public void setLevel(LogLevel level) {
		this.level = level;
	}

	/**
	 * Gets the factory this logger is from.
	 *
	 * @return The factory.
	 */
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
	public void log(LogMessage message) {
		if (isActive(message.level))
			this.appender.accept(message);
	}
}
