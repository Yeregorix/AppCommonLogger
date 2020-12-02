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

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A logger factory.
 */
public class LoggerFactory {
	private final Map<String, Logger> loggers = new ConcurrentHashMap<>(), unmodifiableLoggers = Collections.unmodifiableMap(this.loggers);
	private final LogAppender appender;
	private LogLevel level = LogLevel.TRACE;

	/**
	 * Creates a logger factory.
	 *
	 * @param appender The log appender.
	 */
	public LoggerFactory(LogAppender appender) {
		if (appender == null)
			throw new IllegalArgumentException("appender");
		this.appender = appender;
	}

	/**
	 * Gets the level.
	 *
	 * @return The level.
	 */
	public LogLevel getLevel() {
		return this.level;
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
	 * Gets whether the level is active.
	 *
	 * @param level The level.
	 * @return Whether the level is active.
	 */
	public boolean isActive(LogLevel level) {
		return level.ordinal() >= this.level.ordinal();
	}

	/**
	 * Gets the appender.
	 *
	 * @return The appender.
	 */
	public LogAppender getAppender() {
		return this.appender;
	}

	/**
	 * Gets the logger for the given name.
	 * Creates it if needed.
	 *
	 * @param name The name.
	 * @return The logger.
	 */
	public Logger provideLogger(String name) {
		if (name == null)
			throw new IllegalArgumentException();

		return this.loggers.computeIfAbsent(name, this::createLogger);
	}

	protected Logger createLogger(String name) {
		return new Logger(this, name);
	}

	/**
	 * Gets the logger for the given name if existing.
	 *
	 * @param name The name.
	 * @return The logger.
	 */
	public Optional<Logger> getLogger(String name) {
		if (name == null)
			throw new IllegalArgumentException();

		return Optional.ofNullable(this.loggers.get(name));
	}

	/**
	 * Gets all existing loggers.
	 *
	 * @return The loggers.
	 */
	public Map<String, Logger> getLoggers() {
		return this.unmodifiableLoggers;
	}
}
