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

package net.smoofyuniverse.logger.filter;

import net.smoofyuniverse.logger.core.LogMessage;

import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/**
 * A log filter.
 * <p>
 * Computes an integer value.
 * If the value is positive, the message is allowed.
 * If the value is strictly negative, the message is denied.
 */
public interface LogFilter extends ToIntFunction<LogMessage>, Predicate<LogMessage> {

	/**
	 * Gets whether this log message is allowed.
	 *
	 * @param message The log message.
	 * @return Whether this log message is allowed.
	 */
	@Override
	default boolean test(LogMessage message) {
		return applyAsInt(message) >= 0;
	}

	/**
	 * Gets score for this log message.
	 *
	 * @param message The log message.
	 * @return The score.
	 */
	@Override
	int applyAsInt(LogMessage message);
}
