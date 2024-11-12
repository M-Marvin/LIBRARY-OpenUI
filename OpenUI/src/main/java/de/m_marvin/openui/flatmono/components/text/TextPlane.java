package de.m_marvin.openui.flatmono.components.text;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.m_marvin.gframe.fontrendering.FontRenderer;

public class TextPlane {
	
	public class TextLine implements CharSequence {
		
		public static class LineIndexOutOfBoundsException extends IndexOutOfBoundsException {
			private static final long serialVersionUID = -1902307886303065183L;
			
			public LineIndexOutOfBoundsException(int index, int len, int size) {
				super(String.format("Line index out of bounds [line length: %d]: index %d -> len %d", size, index, len));
			}
			
			public LineIndexOutOfBoundsException(int index, int size, boolean insert) {
				super(String.format("Line index out of bounds [line length: %d%s]: index %d", size, insert ? " + 1" : "", index));
			}
		}
		
		public TextLine() {
			this.line = new ArrayList<>();
		}
		
		public TextLine(CharSequence text) {
			this();
			for (int i = 0; i < text.length(); i++) {
				this.line.add(new LineCharacter(text.charAt(i), 0)); // TODO font for typed text
			}
		}
		
		public TextLine(TextLine line) {
			this(line, 0, line.length());
		}
		
		public TextLine(TextLine line, int off, int len) {
			this();
			for (int i = off; i < len; i++) {
				LineCharacter lc = line.line.get(i);
				this.line.add(new LineCharacter(lc));
			}
		}

		public class LineCharacter {
			
			public char character;
			public int fontId;
			
			public LineCharacter(char character, int fontId) {
				this.character = character;
				this.fontId = fontId;
			}
			
			public LineCharacter(LineCharacter character) {
				this(character.character, character.fontId);
			}
			
			public Font font() {
				return TextPlane.this.fonts.get(this.fontId);
			}
			
			public int getCharHeight() {
				return getCharacterHeight(font());
			}
			
			public int getCharWidth() {
				return getCharacterWidth(this.character, font());
			}
			
			@Override
			public String toString() {
				return String.format("%c[%d]", this.character, this.fontId);
			}
			
		}
		
		protected List<LineCharacter> line;
		protected int height = -1;
		protected int width = -1;
		
		public int getLineHeight() {
			if (this.height == -1) {
				for (LineCharacter c : line) 
					if (c.getCharHeight() > this.height) this.height = c.getCharHeight();
				if (this.height == -1) 
					this.height = getCharacterHeight(TextPlane.this.getDefaultFont());
			}
			return this.height;
		}

		public int getLineWidth() {
			if (this.width == -1) {
				for (LineCharacter c : line) 
					if (c.getCharHeight() > this.width) this.width = c.getCharWidth();
			}
			return this.width;
		}
		
		@Override
		public int length() {
			return this.line.size();
		}

		@Override
		public char charAt(int index) {
			return this.line.get(index).character;
		}

		@Override
		public CharSequence subSequence(int start, int end) {
			return new TextLine(this, start, end);
		}
		
		public LineCharacter lineCharAt(int index) {
			return this.line.get(index);
		}
		
		public void insert(CharSequence text, int index) {
			if (index > this.line.size()) 
				throw new LineIndexOutOfBoundsException(index, this.line.size(), true);
			if (text instanceof TextLine textSequence) {
				for (int i = 0; i < text.length(); i++) {
					this.line.add(index + i, new LineCharacter(textSequence.lineCharAt(i)));
				}
			} else {
				for (int i = 0; i < text.length(); i++) {
					this.line.add(index + i, new LineCharacter(text.charAt(i), 0)); // TODO font for typed text
				}
			}
			this.height = -1;
			this.width = -1;
		}
		
		public void remove(int index, int len) {
			if (index >= this.line.size() || index + len > this.line.size())
				throw new LineIndexOutOfBoundsException(index, len, this.line.size());
			for (int i = 0; i < len; i++) {
				this.line.remove(index);
			}
			this.height = -1;
			this.width = -1;
		}
		
		@Override
		public String toString() {
			return this.line.stream().map(c -> String.valueOf(c.character)).collect(Collectors.joining());
		}
		
	}
	
	public static class TextIndexOutOfBoundsException extends IndexOutOfBoundsException {
		private static final long serialVersionUID = -1902307886303065183L;
		
		public TextIndexOutOfBoundsException(int line, int lines, boolean insert) {
			super(String.format("Text index out of bounds [lines: %d%s]: line %d", lines, insert ? " + 1" : "", line));
		}
	}
	
	protected Map<Integer, Font> fonts;
	protected List<TextLine> lines;
	protected int charCount = -1;
	
	// TODO text limits
	protected int lineLimit = -1;
	protected int columnLimite = -1;
	protected int charCountLimit = -1;
	
	public TextPlane(Font defaultFont) {
		this.fonts = new HashMap<Integer, Font>();
		this.lines = new ArrayList<>();
		setDefaultFont(defaultFont);
		addLine(0);
	}
	
	public void setDefaultFont(Font font) {
		this.fonts.put(0, font);
	}
	
	public Font getDefaultFont() {
		return this.fonts.get(0);
	}
	
	public int getCharCount() {
		if (this.charCount == -1) {
			this.charCount = this.lines.size() - 1;
			for (TextLine l : this.lines) {
				this.charCount += l.length();
			}
		}
		return this.charCount;
	}
	
	public List<TextLine> getLines() {
		return lines;
	}
	
	public TextLine getLine(int line) {
		if (line >= this.lines.size())
			throw new TextIndexOutOfBoundsException(line, this.lines.size(), false);
		return this.lines.get(line);
	}
	
	public TextLine getOrCreateLine(int line) {
		if (line > this.lines.size())
			throw new TextIndexOutOfBoundsException(line, this.lines.size(), true);
		if (line < this.lines.size()) {
			return this.lines.get(line);
		} else {
			this.lines.add(new TextLine());
			this.charCount = -1;
			return this.lines.get(line);
		}
	}
	
	public void insert(CharSequence text, int line, int column) {
		TextLine textLine = getOrCreateLine(line);
		textLine.insert(text, column);
		this.charCount = -1;
	}
	
	public void insertMultiLine(CharSequence text, int line, int column) {
		List<CharSequence> lines = splitLines(text);
		
		// Insert at first line and store right side content after insert position
		TextLine sl = this.getLine(line);
		TextLine t = (TextLine) sl.subSequence(column, sl.length());
		if (column < sl.length()) sl.remove(column, sl.length() - column);
		insert(lines.get(0), line, column);
		
		// Insert remaining lines
		for (int i = 1; i < lines.size(); i++) {
			this.lines.add(line + i, new TextLine(lines.get(i)));
		}
		
		// Insert stored part at end of last line
		TextLine el = this.lines.get(lines.size() + line - 1);
		el.insert(t, el.length());
		
		this.charCount = -1;
	}
	
	public void remove(int line, int column, int len) {
		while (len > 0) {
			TextLine textLine = getLine(line);
			if (textLine.length() - column >= len) {
				// remove only from this line
				textLine.remove(column, len);
				len = 0;
			} else {
				// remove with line feed (combine remainder with next line)
				int ll = textLine.length() - column;
				if (column < textLine.length()) textLine.remove(column, ll);
				len -= ll + 1;
				if (line + 1 < this.lines.size()) {
					textLine.insert(getLine(line + 1), column);
					removeLine(line + 1);
				}
			}
		}
		this.charCount = -1;
	}
	
	public void removeLine(int index) {
		if (index >= this.lines.size())
			throw new TextIndexOutOfBoundsException(index, this.lines.size(), false);
		this.lines.remove(index);
		this.charCount = -1;
	}
	
	public void addLine(int index) {
		if (index > this.lines.size())
			throw new TextIndexOutOfBoundsException(index, this.lines.size(), true);
		this.lines.add(index, new TextLine());
		this.charCount = -1;
	}
	
	public TextLine copyText(int line, int column, int len) {
		TextLine result = new TextLine();
		while (len > 0) {
			TextLine textLine = getLine(line);
			if (textLine.length() - column >= len) {
				// copy only from this line
				result.insert(textLine.subSequence(column, column + len), result.length());
				len = 0;
			} else {
				// copy from this line and following lines
				result.insert(column > 0 ? textLine.subSequence(column, textLine.length()) : textLine, result.length());
				result.insert("\n", result.length());
				len -= textLine.length() - column + 1;
				line++;
				column = 0;
			}
		}
		return result;
	}
	
	public static List<CharSequence> splitLines(CharSequence text) {
		List<CharSequence> lines = new ArrayList<>();
		int start = 0;
		for (int i = 0; i < text.length(); i++) {
			char c1 = text.charAt(i);
			if (c1 == '\n' || c1 == '\r') {
				lines.add(text.subSequence(start, i));
				
				char c2 = i + 1 < text.length() ? text.charAt(i + 1) : 0;
				if (c1 == '\n' && c2 == '\r') {
					i++;
				}
				start = i + 1;
			}
		}
		if (start < text.length()) {
			lines.add(text.subSequence(start, text.length()));
		} else {
			lines.add(text.subSequence(0, 0));
		}
		return lines;
	}
	
	public static int getCharacterWidth(char character, Font font) {
		return FontRenderer.calculateStringWidth(String.valueOf(character), font);
	}
	
	public static int getCharacterHeight(Font font) {
		return FontRenderer.getFontHeight(font);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		this.lines.forEach(l -> sb.append(l.toString()).append("\n"));
		return sb.toString();
	}
	
}
