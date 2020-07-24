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

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.ParsePosition;
import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.TreeMap;

public final class DatedRollingFileAppender implements LogAppender {
	public static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final Clock DEFAULT_CLOCK = Clock.systemDefaultZone();

	public final Path directory;
	public final DateTimeFormatter formatter;
	public final Clock clock;
	public final String prefix, suffix;
	public final int maxFiles;

	private LocalDate currentDate;
	private BufferedWriter writer;
	private Path file;

	public DatedRollingFileAppender(Path directory, DateTimeFormatter formatter, Clock clock, String prefix, String suffix, int maxFiles) {
		if (directory == null)
			throw new IllegalArgumentException("directory");
		if (formatter == null)
			throw new IllegalArgumentException("formatter");
		if (clock == null)
			throw new IllegalArgumentException("clock");
		if (prefix == null)
			throw new IllegalArgumentException("prefix");
		if (suffix == null)
			throw new IllegalArgumentException("suffix");

		this.directory = directory;
		this.formatter = formatter;
		this.clock = clock;
		this.suffix = suffix;
		this.prefix = prefix;
		this.maxFiles = maxFiles < 0 ? 0 : maxFiles;

		try {
			Files.createDirectories(directory);
		} catch (IOException ignored) {
		}
	}

	@Override
	public void appendRaw(String msg) {
		try {
			if (update())
				cleanup();
			getWriter().write(msg);
			this.writer.flush();
		} catch (Exception e) {
			throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
		}
	}

	public boolean update() {
		LocalDate today = LocalDate.now(this.clock);
		if (!today.equals(this.currentDate)) {
			this.file = this.directory.resolve(this.prefix + this.formatter.format(today) + this.suffix);
			this.currentDate = today;
			close();
			return true;
		}
		return false;
	}

	public void cleanup() throws Exception {
		if (this.maxFiles == 0)
			return;

		TreeMap<LocalDate, Path> files = new TreeMap<>();

		try (DirectoryStream<Path> st = Files.newDirectoryStream(this.directory)) {
			for (Path p : st) {
				String fn = p.getFileName().toString();
				if (fn.startsWith(this.prefix) && fn.endsWith(this.suffix)) {
					try {
						files.put(LocalDate.from(this.formatter.parse(fn, new ParsePosition(this.prefix.length()))), p);
					} catch (DateTimeParseException ignored) {
					}
				}
			}
		}

		if (this.file != null)
			files.put(this.currentDate, this.file);

		int toDelete = files.size() - this.maxFiles;
		if (toDelete <= 0)
			return;

		for (Path p : files.values()) {
			Files.delete(p);
			toDelete--;
			if (toDelete == 0)
				break;
		}
	}

	public BufferedWriter getWriter() throws IOException {
		if (this.writer == null)
			this.writer = Files.newBufferedWriter(getFile(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		return this.writer;
	}

	public Path getFile() {
		if (this.file == null)
			throw new IllegalStateException();
		return this.file;
	}

	@Override
	public void close() {
		if (this.writer == null)
			return;
		try {
			this.writer.close();
		} catch (IOException ignored) {
		}
		this.writer = null;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Path directory;
		private DateTimeFormatter formatter = DEFAULT_FORMATTER;
		private Clock clock = DEFAULT_CLOCK;
		private String prefix = "", suffix = ".log";
		private int maxFiles = 0;

		private Builder() {}

		public Builder directory(Path value) {
			this.directory = value;
			return this;
		}

		public Builder formatter(DateTimeFormatter value) {
			this.formatter = value;
			return this;
		}

		public Builder clock(Clock value) {
			this.clock = value;
			return this;
		}

		public Builder prefix(String value) {
			this.prefix = value;
			return this;
		}

		public Builder suffix(String value) {
			this.suffix = value;
			return this;
		}

		public Builder maxFiles(int value) {
			this.maxFiles = value;
			return this;
		}

		public DatedRollingFileAppender build() {
			return new DatedRollingFileAppender(this.directory, this.formatter, this.clock, this.prefix, this.suffix, this.maxFiles);
		}
	}
}
