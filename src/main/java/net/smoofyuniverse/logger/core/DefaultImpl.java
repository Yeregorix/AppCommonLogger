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

import net.smoofyuniverse.logger.appender.log.FormattedAppender;
import net.smoofyuniverse.logger.appender.log.LogAppender;
import net.smoofyuniverse.logger.appender.string.PrintStreamAppender;

import java.time.LocalTime;

/**
 * The default implementation.
 */
public class DefaultImpl {
	/**
	 * The default appender of a new logger factory.
	 */
	public static final LogAppender FORMATTED_SYSTEM_APPENDER = new FormattedAppender(PrintStreamAppender.system());

	/**
	 * The default logger factory.
	 */
	public static final LoggerFactory FACTORY = new LoggerFactory();

	/**
	 * Formats the log message to a string.
	 *
	 * @param msg The log message.
	 * @return The formatted string.
	 */
	public static String formatLog(LogMessage msg) {
		return formatTime(msg.time) + " [" + msg.logger.getName() + "] " + msg.level.name() + " - " + msg.getText() + System.lineSeparator();
	}

	/**
	 * Formats the time to a string.
	 *
	 * @param time The time.
	 * @return The formatted string.
	 */
	public static String formatTime(LocalTime time) {
		return String.format("%02d:%02d:%02d", time.getHour(), time.getMinute(), time.getSecond());
	}
}
