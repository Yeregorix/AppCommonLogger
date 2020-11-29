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

package net.smoofyuniverse.logger.appender.string;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * A {@link StringAppender} writing to a file.
 */
public class FileAppender implements StringAppender {
	private BufferedWriter writer;

	public final StandardOpenOption[] options;
	public final Charset charset;
	public final Path file;

	public FileAppender(Path file, StandardOpenOption... options) {
		this(file, StandardCharsets.UTF_8, options);
	}

	public FileAppender(Path file, Charset charset, StandardOpenOption... options) {
		if (file == null)
			throw new IllegalArgumentException("file");
		if (charset == null)
			throw new IllegalArgumentException("charset");
		if (options == null)
			throw new IllegalArgumentException("options");

		this.file = file;
		this.charset = charset;
		this.options = options;
	}

	@Override
	public void accept(String message) {
		try {
			if (this.writer == null)
				this.writer = Files.newBufferedWriter(this.file, this.charset, this.options);

			this.writer.write(message);
			this.writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void close() {
		try {
			this.writer.close();
		} catch (IOException ignored) {
		}
		this.writer = null;
	}
}
