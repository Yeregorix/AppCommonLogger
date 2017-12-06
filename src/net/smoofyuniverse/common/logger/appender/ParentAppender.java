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

package net.smoofyuniverse.common.logger.appender;

import net.smoofyuniverse.common.logger.core.LogMessage;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParentAppender implements LogAppender {
	private Collection<LogAppender> childs;

	public ParentAppender() {
		this(new CopyOnWriteArrayList<>());
	}

	public ParentAppender(Collection<LogAppender> childs) {
		this.childs = childs;
	}

	public ParentAppender(LogAppender... childs) {
		this(Arrays.asList(childs));
	}

	public Collection<LogAppender> getChilds() {
		return this.childs;
	}

	@Override
	public void append(LogMessage msg) {
		for (LogAppender a : this.childs)
			a.append(msg);
	}

	@Override
	public void appendRaw(String msg) {
		for (LogAppender a : this.childs)
			a.appendRaw(msg);
	}

	@Override
	public void close() {
		for (LogAppender a : this.childs)
			a.close();
	}
}
