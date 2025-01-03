package de.m_marvin.openui.flatmono.components;

import java.awt.Color;
import java.awt.Font;
import java.util.Optional;
import java.util.function.Consumer;

import de.m_marvin.gframe.buffers.defimpl.SimpleBufferSource;
import de.m_marvin.gframe.fontrendering.FontRenderer;
import de.m_marvin.gframe.inputbinding.KeyCodes;
import de.m_marvin.gframe.inputbinding.UserInput.FunctionalKey;
import de.m_marvin.gframe.resources.defimpl.ResourceLocation;
import de.m_marvin.gframe.translation.PoseStack;
import de.m_marvin.openui.core.TextRenderer;
import de.m_marvin.openui.core.UIRenderMode;
import de.m_marvin.openui.core.components.Component;
import de.m_marvin.openui.flatmono.UtilRenderer;
import de.m_marvin.openui.flatmono.components.text.TextPlane;
import de.m_marvin.openui.flatmono.components.text.TextPlane.TextLine;
import de.m_marvin.openui.flatmono.components.text.TextPlane.TextLine.LineCharacter;
import de.m_marvin.univec.impl.Vec2i;

/* WORK IN PROGRESS */
public class AdvancedTextFieldComponent extends Component<ResourceLocation> {
	
	public static final int FRAME_WIDTH = 1;
	public static final int TEXT_BORDER_GAP = 2;
	public static final int TEXT_AUTO_SCROLL_AREA = 20;
	
	protected Color color;
	protected Color textColor;
	protected TextPlane text = new TextPlane(DEFAULT_FONT);
	
	protected int maxLength = 64;
	protected Consumer<String> changeCallback = s -> {};
	
	protected int scrollLine = 0;
	protected int scrollColumn = 0;
	protected int cursorLine = 0;
	protected int cursorColumn = 0;
	
	protected boolean textOverride = false;
	protected boolean cursorState = false;
//	protected int cursorPosition = 0;
	protected int textOffset = 0;
	protected int selectionStart = -1;
	protected int selectionEnd = -1;
	
	public AdvancedTextFieldComponent(Color textColor, Color color) {
		this.color = color;
		this.textColor = textColor;
		
		setMargin(5, 5, 5, 5);
		setSize(new Vec2i(120, FontRenderer.getFontHeight(getFont()) + 2));
		fixSize();
	}
	
	public AdvancedTextFieldComponent(Color textColor) {
		this(textColor, Color.BLACK);
	}
	
	public AdvancedTextFieldComponent() {
		this(Color.WHITE);
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		assert color != null : "Argument can not be null!";
		this.color = color;
		this.redraw();
	}
	
	public Color getTextColor() {
		return textColor;
	}
	
	public void setTextColor(Color textColor) {
		assert textColor != null : "Argument can not be null!";
		this.textColor = textColor;
		this.redraw();
	}
	
	public void setChangeCallback(Consumer<String> changeCallback) {
		this.changeCallback = changeCallback;
	}
	
	public Consumer<String> getChangeCallback() {
		return changeCallback;
	}
	
	public Font getFont() {
		return this.text.getDefaultFont();
	}
	
	public void setFont(Font font) {
		assert font != null : "Argument can not be null!";
		this.text.setDefaultFont(font);
		this.redraw();
	}
	
	public void insertText(int line, int column, CharSequence text, boolean multiLine) {
		if (multiLine) {
			this.text.insertMultiLine(text, line, column);
		} else {
			this.text.insert(text, line, column);
		}
	}
	
	public void removeText(int line, int column, int len) {
		this.text.remove(line, column, len);
	}
	
	public void moveCursor(int movement) {
		this.cursorColumn += movement;
		if (this.cursorColumn < 0) {
			while (this.cursorColumn < 0) {
				if (this.cursorLine == 0) {
					this.cursorColumn = 0;
					break;
				}
				this.cursorLine--;
				this.cursorColumn += this.text.getLine(this.cursorLine).length() + 1;
			}
		} else {
			while (this.cursorColumn > this.text.getLine(this.cursorLine).length()) {
				this.cursorColumn -= this.text.getLine(this.cursorLine).length() + 1;
				if (this.cursorLine == this.text.getLines().size() - 1) {
					this.cursorColumn = this.text.getLine(this.cursorLine).length();
					break;
				}
				this.cursorLine++;
			}
		}
	}
	
	public String getText() {
		return null;
	}
	
	public void setText(String text) {
		
	}
	
	public void setCursorPosition(int charIndex) {
		
	}
	
	public void moveCursorNear(int xPos) {
//		this.cursorPosition = this.textOffset + FontRenderer.limitStringWidth(this.text.substring(this.textOffset), this.font, Math.min(this.size.x - TEXT_BORDER_GAP * 2, xPos)).length();
	}
	
	public void clearSelection() {
		this.selectionStart = this.selectionEnd = -1;
	}
	
	public void setSelection(int from, int to) {
		this.selectionStart = from;
		this.selectionEnd = to;
		this.redraw();
	}
	
	public boolean hasSelection() {
		return this.selectionStart != this.selectionEnd;
	}
	
//	public String getSelection() {
//		if (!hasSelection()) return "";
//		int startIndex = Math.min(this.selectionStart, this.selectionEnd);
//		int endIndex = Math.max(this.selectionStart, this.selectionEnd);
//		return this.text.substring(startIndex, endIndex);
//	}
	
	@Override
	public void setup() {
		super.setup();
		this.getContainer().getUserInput().addTextInputListener(this::textInput);
		this.getContainer().getUserInput().addKeyboardListener(this::keyTyped);
	}
	
	@Override
	public void cleanup() {
		super.cleanup();
		this.getContainer().getUserInput().removeTextInputListener(this::textInput);
		this.getContainer().getUserInput().removeKeyboardListener(this::keyTyped);
	}
	
	@Override
	protected void onClicked(int button, boolean pressed, boolean repeated) {
		
		if (pressed && button == KeyCodes.MOUSE_BUTTON_1) {
			
//			moveCursorNear((int) this.getContainer().getCursorPosition().x - this.getAbsoluteOffset().x);
//			this.selectionStart = this.selectionEnd = this.cursorPosition;
//			this.redraw();
			
		}
		
	}
	
	@Override
	protected void onCursorMoveOver(Vec2i position, boolean leaved) {
		
		if (this.selectionStart != -1 && this.selectionEnd != -1 && this.getContainer().getUserInput().isMouseButtonPressed(KeyCodes.MOUSE_BUTTON_1)) {
			
//			int cursorPos = (int) this.getContainer().getCursorPosition().x - this.getAbsoluteOffset().x;
//			if (cursorPos > this.size.x - TEXT_BORDER_GAP * 2 - TEXT_AUTO_SCROLL_AREA) {
//				setCursorPosition(this.cursorPosition + 1);
//			} else if (cursorPos < TEXT_AUTO_SCROLL_AREA) {
//				setCursorPosition(this.cursorPosition - 1);
//			}
//			moveCursorNear(cursorPos);
//			this.selectionEnd = this.cursorPosition;
//			this.redraw();
			
		}
		
	}
	
	protected void keyTyped(int keycode, int scancode, boolean pressed, boolean released) {
		if (!this.isVisible()) return;
		
		if (this.isFocused() && pressed) {
			
			boolean ctrlDown = this.getContainer().getUserInput().isKeyPressed(KeyCodes.KEY_LEFT_CONTROL) || this.getContainer().getUserInput().isKeyPressed(KeyCodes.KEY_RIGHT_CONTROL);
			
			if (ctrlDown) {
				if (keycode == KeyCodes.KEY_C && hasSelection()) {
//					StringSelection data = new StringSelection(getSelection());
//					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(data, data);
				} else if (keycode == KeyCodes.KEY_V) {
//					try {
//						Transferable data = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
//						if (data.isDataFlavorSupported(DataFlavor.stringFlavor)) {
//							String pasteText = data.getTransferData(DataFlavor.stringFlavor).toString();
//							pasteText = pasteText.replaceAll("[\n\r\t]", "");
//							
//							if (hasSelection()) {
//								int pasted = textInput(this.selectionStart, this.selectionEnd, pasteText);
//								setCursorPosition(Math.min(this.selectionStart, this.selectionEnd) + pasted);
//							} else {
//								int pasted = textInput(this.cursorPosition, this.cursorPosition, pasteText);
//								setCursorPosition(this.cursorPosition + pasted);
//							}
//							clearSelection();
//						}
//					} catch (UnsupportedFlavorException | IOException e) {
//						clearSelection();
//					}
				} else if (keycode == KeyCodes.KEY_X && hasSelection()) {
//					StringSelection data = new StringSelection(getSelection());
//					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(data, data);
//					
//					deleteText(this.selectionStart, this.selectionEnd - 1);
//					setCursorPosition(Math.min(this.selectionStart, this.selectionEnd));
//					clearSelection();
				}
				this.redraw();
			}
			
		}
		
	}
	
	protected void textInput(int codepoint, Optional<FunctionalKey> functionalKey) {
		if (!this.isVisible()) return;
		
		if (this.isFocused()) {
			
			if (functionalKey.isPresent()) {

				boolean shiftDown = this.getContainer().getUserInput().isKeyPressed(KeyCodes.KEY_LEFT_SHIFT) || this.getContainer().getUserInput().isKeyPressed(KeyCodes.KEY_RIGHT_SHIFT);
				
				switch (functionalKey.get()) {
				
				// Insertion modes
				case ENTER:
					insertText(this.cursorLine, this.cursorColumn, String.valueOf("\n"), true);
					moveCursor(1);
					break;
				case INSERT:
					this.textOverride = !this.textOverride;
					break;
				
				// Move cursor
				case POS1:
					this.cursorColumn = 0;
					break;
				case END:
					this.cursorColumn = this.text.getLine(this.cursorLine).length();
					break;
				case KEY_LEFT: 
					moveCursor(-1);
					break;
				case KEY_RIGHT:
					moveCursor(+1);
					break;
				
				// Delete characters
				case BACKSPACE: 
					if (this.cursorColumn > 0) {
						removeText(this.cursorLine, this.cursorColumn - 1, 1);
						moveCursor(-1);
					} else if (this.cursorLine > 0) {
						removeText(this.cursorLine - 1, this.text.getLine(this.cursorLine - 1).length(), 1);
						moveCursor(-1);
					}
					break;
				case DEL: 
					if (this.text.getLine(this.cursorLine).length() > this.cursorColumn || this.cursorLine < this.text.getLines().size() - 1)
						removeText(this.cursorLine, this.cursorColumn, 1);
					break;
					
				default: break;
				}
				
			} else {
				
				// Type text
				insertText(this.cursorLine, this.cursorColumn, String.valueOf((char) codepoint), false);
				moveCursor(1);
				
				if (this.textOverride && this.text.getLine(this.cursorLine).length() > this.cursorColumn) {
					removeText(this.cursorLine, this.cursorColumn, 1);
				}
				
			}
			
			this.cursorState = true;
			this.redraw();
			
		}
		
	}
	
	@Override
	public void onChangeFocus() {
		if (this.isFocused()) {
			this.cursorState = false;
			this.scheduledTick(1);
		} else {
			this.textOverride = false;
			this.cursorState = false;
			this.redraw();
		}
	}
	
	@Override
	public void scheduledTick(int arg) {
		if (arg == 1 && this.isFocused()) {
			this.cursorState = !this.cursorState;
			this.scheduleTick(400, 1);
			this.redraw();
		}
	}
	
	@Override
	public void drawBackground(SimpleBufferSource<ResourceLocation, UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {
		
		int tx = TEXT_BORDER_GAP;
		int ty = TEXT_BORDER_GAP;
		
		for (int i1 = this.scrollLine; i1 < this.text.getLines().size(); i1++) {
			TextLine line = this.text.getLines().get(i1);
			int x = tx;
			
			for (int i2 = this.scrollColumn; i2 < line.length(); i2++) {
				LineCharacter character = line.lineCharAt(i2);
				
				TextRenderer.drawText(x, ty, String.valueOf(character.character), character.font(), this.textColor, this.container.getActiveTextureLoader(), bufferSource, matrixStack);
				
				x += character.getCharWidth();
			}
			
			ty += line.getLineHeight();
		}
		
	}
	
	@Override
	public void drawForeground(SimpleBufferSource<ResourceLocation, UIRenderMode<ResourceLocation>> bufferSource, PoseStack matrixStack) {
		
		UtilRenderer.drawFrame(this.size.x, this.size.y, FRAME_WIDTH, this.color, bufferSource, matrixStack);
		
		if (this.isFocused() && this.cursorState) {
			
			
			
//			
//			// Draw Cursor
//			String offsetText = this.text.substring(this.textOffset).substring(0, this.cursorPosition - this.textOffset);
//			int cursorPos = FontRenderer.calculateStringWidth(offsetText, this.font);
//			
//			int cursorWidth = this.textOverride && this.cursorPosition < this.text.length() ? FontRenderer.calculateStringWidth(String.valueOf(this.text.charAt(cursorPosition)), this.font) : TEXT_BORDER_GAP;
//			int cursorHeight = FontRenderer.getFontHeight(this.font) - TEXT_BORDER_GAP;
//			
//			UtilRenderer.drawRectangle(cursorPos + TEXT_BORDER_GAP, TEXT_BORDER_GAP, cursorWidth, cursorHeight, this.textColor, bufferSource, matrixStack);
//			
//			if (this.selectionStart != this.selectionEnd) {
//				
//				// Draw selection
//				int startIndex = Math.min(this.selectionStart, this.selectionEnd);
//				int endIndex = Math.max(this.selectionStart , this.selectionEnd);
//				
//				int startPos = startIndex < this.textOffset ? 0 : FontRenderer.calculateStringWidth(this.text.substring(this.textOffset, startIndex), this.font);
//				int endPos = endIndex < this.textOffset ? 0 : FontRenderer.calculateStringWidth(this.text.substring(this.textOffset, endIndex), this.font);
//				
//				if (startPos > this.size.x - 2) startPos = this.size.x - TEXT_BORDER_GAP;
//				if (endPos > this.size.x - 2) endPos = this.size.x - TEXT_BORDER_GAP;
//				
//				UtilRenderer.drawRectangle(startPos + TEXT_BORDER_GAP, TEXT_BORDER_GAP, endPos - startPos, cursorHeight, this.textColor, bufferSource, matrixStack);
//				
//			}
			
		}
		
	}
	
}
