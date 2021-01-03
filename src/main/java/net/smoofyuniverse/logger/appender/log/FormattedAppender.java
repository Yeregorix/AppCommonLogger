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

package net.smoofyuniverse.logger.appender.log;

import net.smoofyuniverse.logger.appender.string.StringAppender;
import net.smoofyuniverse.logger.core.DefaultImpl;
import net.smoofyuniverse.logger.core.LogMessage;

import java.util.function.Function;

public class FormattedAppender implements LogAppender {
	public final StringAppender stringAppender;
	public final Function<LogMessage, String> formatter;

	public FormattedAppender(StringAppender stringAppender) {
		this(stringAppender, DefaultImpl::formatLog);
	}

	public FormattedAppender(StringAppender stringAppender, Function<LogMessage, String> formatter) {
		if (stringAppender == null)
			throw new IllegalArgumentException("stringAppender");
		if (formatter == null)
			throw new IllegalArgumentException("formatter");

		this.stringAppender = stringAppender;
		this.formatter = formatter;
	}

	@Override
	public void accept(LogMessage message) {
		acceptString(this.formatter.apply(message));
		acceptString(message.getStackTrace());
	}

	private void acceptString(String message) {
		if (!message.isEmpty())
			this.stringAppender.accept(message);
	}

	@Override
	public void close() {
		this.stringAppender.close();
	}
}
