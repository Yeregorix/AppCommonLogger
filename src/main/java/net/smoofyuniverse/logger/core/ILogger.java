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

import java.util.function.Supplier;

/**
 * A logger.
 */
public interface ILogger {

	/**
	 * Gets the name.
	 *
	 * @return The name.
	 */
	String getName();

	/**
	 * Gets whether the level is active.
	 *
	 * @param level The level.
	 * @return Whether the level is active.
	 */
	boolean isActive(LogLevel level);

	default void log(LogLevel level, Supplier<String> textSupplier) {
		log(new LogMessage(this, level, null, textSupplier));
	}

	/**
	 * Logs the message.
	 *
	 * @param message The message.
	 */
	void log(LogMessage message);

	default void log(LogLevel level, String text) {
		log(new LogMessage(this, level, null, text));
	}

	default void log(LogLevel level, Throwable throwable) {
		log(new LogMessage(this, level, throwable, "An error occurred."));
	}

	default void log(LogLevel level, Supplier<String> textSupplier, Throwable throwable) {
		log(new LogMessage(this, level, throwable, textSupplier));
	}

	default void log(LogLevel level, String text, Throwable throwable) {
		log(new LogMessage(this, level, throwable, text));
	}

	default void trace(Supplier<String> supplier) {
		log(LogLevel.TRACE, supplier);
	}

	default void trace(String text) {
		log(LogLevel.TRACE, text);
	}

	default void trace(Supplier<String> supplier, Throwable throwable) {
		log(LogLevel.TRACE, supplier, throwable);
	}

	default void trace(Throwable throwable) {
		log(LogLevel.TRACE, throwable);
	}

	default void trace(String text, Throwable throwable) {
		log(LogLevel.TRACE, text, throwable);
	}

	default void debug(String text) {
		log(LogLevel.DEBUG, text);
	}

	default void debug(Supplier<String> supplier) {
		log(LogLevel.DEBUG, supplier);
	}

	default void debug(Throwable throwable) {
		log(LogLevel.DEBUG, throwable);
	}

	default void debug(String text, Throwable throwable) {
		log(LogLevel.DEBUG, text, throwable);
	}

	default void debug(Supplier<String> supplier, Throwable throwable) {
		log(LogLevel.DEBUG, supplier, throwable);
	}

	default void info(String text) {
		log(LogLevel.INFO, text);
	}

	default void info(Supplier<String> supplier) {
		log(LogLevel.INFO, supplier);
	}

	default void info(Throwable throwable) {
		log(LogLevel.INFO, throwable);
	}

	default void info(String text, Throwable throwable) {
		log(LogLevel.INFO, text, throwable);
	}

	default void info(Supplier<String> supplier, Throwable throwable) {
		log(LogLevel.INFO, supplier, throwable);
	}

	default void warn(String text) {
		log(LogLevel.WARN, text);
	}

	default void warn(Supplier<String> supplier) {
		log(LogLevel.WARN, supplier);
	}

	default void warn(Throwable throwable) {
		log(LogLevel.WARN, throwable);
	}

	default void warn(String text, Throwable throwable) {
		log(LogLevel.WARN, text, throwable);
	}

	default void warn(Supplier<String> supplier, Throwable throwable) {
		log(LogLevel.WARN, supplier, throwable);
	}

	default void error(String text) {
		log(LogLevel.ERROR, text);
	}

	default void error(Supplier<String> supplier) {
		log(LogLevel.ERROR, supplier);
	}

	default void error(Throwable throwable) {
		log(LogLevel.ERROR, throwable);
	}

	default void error(String text, Throwable throwable) {
		log(LogLevel.ERROR, text, throwable);
	}

	default void error(Supplier<String> supplier, Throwable throwable) {
		log(LogLevel.ERROR, supplier, throwable);
	}
}
