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

package net.smoofyuniverse.logger.appender;

import net.smoofyuniverse.logger.core.LogMessage;
import net.smoofyuniverse.logger.filter.LogFilter;

public class FilteredAppender implements LogAppender {
	public final LogAppender delegate;
	public final LogFilter filter;

	public FilteredAppender(LogAppender delegate, LogFilter filter) {
		if (delegate == null)
			throw new IllegalArgumentException("delegate");
		if (filter == null)
			throw new IllegalArgumentException("filter");

		this.delegate = delegate;
		this.filter = filter;
	}

	@Override
	public void append(LogMessage msg) {
		if (this.filter.score(msg) >= 0)
			this.delegate.append(msg);
	}

	@Override
	public void appendRaw(String msg) {
		if (this.filter.scoreRaw(msg) >= 0)
			this.delegate.appendRaw(msg);
	}
}
