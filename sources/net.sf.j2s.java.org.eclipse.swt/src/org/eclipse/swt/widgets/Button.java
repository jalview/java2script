/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     ognize.com - initial Java2Script implementation
 *******************************************************************************/
package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.RunnableCompatibility;
import org.eclipse.swt.internal.browser.OS;
import org.eclipse.swt.internal.xhtml.CSSStyle;
import org.eclipse.swt.internal.xhtml.Element;
import org.eclipse.swt.internal.xhtml.HTMLEvent;
import org.eclipse.swt.internal.xhtml.document;


/**
 * Instances of this class represent a selectable user interface object that
 * issues notification when pressed and released. 
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>ARROW, CHECK, PUSH, RADIO, TOGGLE, FLAT</dd>
 * <dd>UP, DOWN, LEFT, RIGHT, CENTER</dd>
 * <dt><b>Events:</b></dt>
 * <dd>Selection</dd>
 * </dl>
 * <p>
 * Note: Only one of the styles ARROW, CHECK, PUSH, RADIO, and TOGGLE 
 * may be specified.
 * </p><p>
 * Note: Only one of the styles LEFT, RIGHT, and CENTER may be specified.
 * </p><p>
 * Note: Only one of the styles UP, DOWN, LEFT, and RIGHT may be specified
 * when the ARROW style is specified.
 * </p><p>
 * IMPORTANT: This class is intended to be subclassed <em>only</em>
 * within the SWT implementation.
 * </p>
 */

public class Button extends Control {
	String text = "";
	boolean textSizeCached = false;
	int textWidthCached, textHeightCached;
	private String lastColor;
	private boolean hasImage;
	Image image, image2;
	ImageList imageList;
	boolean ignoreMouse;
	/*
	static final int ButtonProc;
	static final TCHAR ButtonClass = new TCHAR (0,"BUTTON", true);
	static final char [] SCROLLBAR = new char [] {'S', 'C', 'R', 'O', 'L', 'L', 'B', 'A', 'R', 0};
	*/
	static final int CHECK_WIDTH = 13, CHECK_HEIGHT = 13;
	static final int ICON_WIDTH = 128, ICON_HEIGHT = 128;
	/*
	static {
		int hBitmap = OS.LoadBitmap (0, OS.OBM_CHECKBOXES);
		if (hBitmap == 0) {
			CHECK_WIDTH = OS.GetSystemMetrics (OS.IsWinCE ? OS.SM_CXSMICON : OS.SM_CXVSCROLL);
			CHECK_HEIGHT = OS.GetSystemMetrics (OS.IsWinCE ? OS.SM_CYSMICON : OS.SM_CYVSCROLL);
		} else {
			BITMAP bitmap = new BITMAP ();
			OS.GetObject (hBitmap, BITMAP.sizeof, bitmap);
			OS.DeleteObject (hBitmap);
			CHECK_WIDTH = bitmap.bmWidth / 4;
			CHECK_HEIGHT =  bitmap.bmHeight / 3;
		}
		WNDCLASS lpWndClass = new WNDCLASS ();
		OS.GetClassInfo (0, ButtonClass, lpWndClass);
		ButtonProc = lpWndClass.lpfnWndProc;
	}
	*/
	Element btnText;
	Element btnHandle; 

	/**
	 * Constructs a new instance of this class given its parent
	 * and a style value describing its behavior and appearance.
	 * <p>
	 * The style value is either one of the style constants defined in
	 * class <code>SWT</code> which is applicable to instances of this
	 * class, or must be built by <em>bitwise OR</em>'ing together 
	 * (that is, using the <code>int</code> "|" operator) two or more
	 * of those <code>SWT</code> style constants. The class description
	 * lists the style constants that are applicable to the class.
	 * Style bits are also inherited from superclasses.
	 * </p>
	 *
	 * @param parent a composite control which will be the parent of the new instance (cannot be null)
	 * @param style the style of control to construct
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the parent</li>
	 *    <li>ERROR_INVALID_SUBCLASS - if this class is not an allowed subclass</li>
	 * </ul>
	 *
	 * @see SWT#ARROW
	 * @see SWT#CHECK
	 * @see SWT#PUSH
	 * @see SWT#RADIO
	 * @see SWT#TOGGLE
	 * @see SWT#FLAT
	 * @see SWT#LEFT
	 * @see SWT#RIGHT
	 * @see SWT#CENTER
	 * @see Widget#checkSubclass
	 * @see Widget#getStyle
	 * 
	 * @j2sIgnore
	 */
	public Button (Composite parent, int style) {
		super (parent, checkStyle (style));
	}
	
void _setImage (Image image) {
	/*
	if (OS.COMCTL32_MAJOR >= 6 && OS.IsAppThemed ()) {
		OS.SendMessage (handle, OS.BCM_SETIMAGELIST, 0, 0);
		if (imageList != null) imageList.dispose ();
		imageList = null;
		if (image != null) {
			imageList = new ImageList (style & SWT.RIGHT_TO_LEFT);
			imageList.add (image);
			BUTTON_IMAGELIST buttonImageList = new BUTTON_IMAGELIST ();
			buttonImageList.himl = imageList.getHandle ();
			if ((style & SWT.LEFT) != 0) buttonImageList.uAlign = OS.BUTTON_IMAGELIST_ALIGN_LEFT;
			if ((style & SWT.CENTER) != 0) buttonImageList.uAlign = OS.BUTTON_IMAGELIST_ALIGN_CENTER;
			if ((style & SWT.RIGHT) != 0) buttonImageList.uAlign = OS.BUTTON_IMAGELIST_ALIGN_RIGHT;
			TCHAR buffer = new TCHAR (getCodePage (), "", true);
			OS.SetWindowText (handle, buffer);
			OS.SendMessage (handle, OS.BCM_SETIMAGELIST, 0, buttonImageList);
		} else {
			TCHAR buffer = new TCHAR (getCodePage (), text, true);
			OS.SetWindowText (handle, buffer);
			OS.SendMessage (handle, OS.BCM_SETIMAGELIST, 0, 0);
		}
	} else {
		if (image2 != null) image2.dispose ();
		image2 = null;
		int hImage = 0, imageBits = 0, fImageType = 0;
		if (image != null) {
			switch (image.type) {
				case SWT.BITMAP: {
					Rectangle rect = image.getBounds ();
					ImageData data = image.getImageData ();
					switch (data.getTransparencyType ()) {
						case SWT.TRANSPARENCY_PIXEL: 
							if (rect.width <= ICON_WIDTH && rect.height <= ICON_HEIGHT) {
								image2 = new Image (display, data, data.getTransparencyMask ());
								hImage = image2.handle;
								imageBits = OS.BS_ICON;
								fImageType = OS.IMAGE_ICON;
								break;
							}
							//FALL THROUGH
						case SWT.TRANSPARENCY_ALPHA:
							image2 = new Image (display, rect.width, rect.height);
							GC gc = new GC (image2);
							gc.setBackground (getBackground ());
							gc.fillRectangle (rect);
							gc.drawImage (image, 0, 0);
							gc.dispose ();
							hImage = image2.handle;
							imageBits = OS.BS_BITMAP;
							fImageType = OS.IMAGE_BITMAP;
							break;
						case SWT.TRANSPARENCY_NONE:
							hImage = image.handle;
							imageBits = OS.BS_BITMAP;
							fImageType = OS.IMAGE_BITMAP;
							break;
					}
					break;
				}
				case SWT.ICON: {
					hImage = image.handle;
					imageBits = OS.BS_ICON;
					fImageType = OS.IMAGE_ICON;
					break;
				}
			}
			
			/*
			* Feature in Windows.  The button control mirrors its image when the
			* flag WS_EX_LAYOUTRTL is set. This behaviour is not desirable in SWT.
			* The fix is to set a mirrored version of real image in the button.
			*-/
			if ((style & SWT.RIGHT_TO_LEFT) != 0) {
				if (!OS.IsWinCE && OS.WIN32_VERSION >= OS.VERSION (4, 10)) {
					Rectangle rect = image.getBounds ();
					int hDC = OS.GetDC (handle);
					int dstHdc = OS.CreateCompatibleDC (hDC);
					int hBitmap = OS.CreateCompatibleBitmap (hDC, rect.width, rect.height);
					int oldBitmap = OS.SelectObject (dstHdc, hBitmap);
					OS.SetLayout (dstHdc, OS.LAYOUT_RTL);
					if (fImageType == OS.IMAGE_BITMAP) {
						int srcHdc = OS.CreateCompatibleDC (hDC);
						int oldSrcBitmap = OS.SelectObject (srcHdc, hImage);
						OS.SetLayout (dstHdc, 0);
						OS.BitBlt (dstHdc, 0, 0, rect.width, rect.height, srcHdc, 0, 0, OS.SRCCOPY);
						OS.SelectObject (srcHdc, oldSrcBitmap);
						OS.DeleteDC (srcHdc);
					} else {
						int newBrush = OS.CreateSolidBrush (getBackgroundPixel ());
						int oldBrush = OS.SelectObject (dstHdc, newBrush);
						OS.PatBlt (dstHdc, 0, 0, rect.width, rect.height, OS.PATCOPY);
						OS.DrawIconEx (dstHdc, 0, 0, hImage, 0, 0, 0, 0, OS.DI_NORMAL);
						OS.SelectObject (dstHdc, oldBrush);
						OS.DeleteObject (newBrush);
					}
					OS.SelectObject (dstHdc, oldBitmap);
					OS.DeleteDC (dstHdc);
					OS.ReleaseDC (handle, hDC);
					if (image2 != null) image2.dispose ();
					image2 = Image.win32_new (display, SWT.BITMAP, hBitmap);
					imageBits = OS.BS_BITMAP;
					fImageType = OS.IMAGE_BITMAP;
					hImage = hBitmap;
				}
			}
		}
		int newBits = OS.GetWindowLong (handle, OS.GWL_STYLE);
		int oldBits = newBits;
		newBits &= ~(OS.BS_BITMAP | OS.BS_ICON);
		newBits |= imageBits;
		if (newBits != oldBits) OS.SetWindowLong (handle, OS.GWL_STYLE, newBits);
		OS.SendMessage (handle, OS.BM_SETIMAGE, fImageType, hImage);
	}
	*/
}

/**
 * Adds the listener to the collection of listeners who will
 * be notified when the control is selected, by sending
 * it one of the messages defined in the <code>SelectionListener</code>
 * interface.
 * <p>
 * <code>widgetSelected</code> is called when the control is selected.
 * <code>widgetDefaultSelected</code> is not called.
 * </p>
 *
 * @param listener the listener which should be notified
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @see SelectionListener
 * @see #removeSelectionListener
 * @see SelectionEvent
 */
public void addSelectionListener (SelectionListener listener) {
checkWidget ();
	if (listener == null) error (SWT.ERROR_NULL_ARGUMENT);
	TypedListener typedListener = new TypedListener (listener);
	addListener (SWT.Selection,typedListener);
	addListener (SWT.DefaultSelection,typedListener);
}

/*
int callWindowProc (int hwnd, int msg, int wParam, int lParam) {
	if (handle == 0) return 0;
	return OS.CallWindowProc (ButtonProc, hwnd, msg, wParam, lParam);
}
*/

static int checkStyle (int style) {
	style = checkBits (style, SWT.PUSH, SWT.ARROW, SWT.CHECK, SWT.RADIO, SWT.TOGGLE, 0);
	if ((style & (SWT.PUSH | SWT.TOGGLE)) != 0) {
		return checkBits (style, SWT.CENTER, SWT.LEFT, SWT.RIGHT, 0, 0, 0);
	}
	if ((style & (SWT.CHECK | SWT.RADIO)) != 0) {
		return checkBits (style, SWT.LEFT, SWT.RIGHT, SWT.CENTER, 0, 0, 0);
	}
	if ((style & SWT.ARROW) != 0) {
		style |= SWT.NO_FOCUS;
		return checkBits (style, SWT.UP, SWT.DOWN, SWT.LEFT, SWT.RIGHT, 0, 0);
	}
	return style;
}

void click () {
	/*
	* Feature in Windows.  BM_CLICK sends a fake WM_LBUTTONDOWN and
	* WM_LBUTTONUP in order to click the button.  This causes the
	* application to get unexpected mouse events.  The fix is to
	* ignore mouse events when they are caused by BM_CLICK.
	*/
	ignoreMouse = true;
//	OS.SendMessage (handle, OS.BM_CLICK, 0, 0);
	ignoreMouse = false;
}

public Point computeSize (int wHint, int hHint, boolean changed) {
	checkWidget ();
	int border = getBorderWidth ();
	int width = 0, height = 0;
	if ((style & SWT.ARROW) != 0) {
		/*
		if ((style & (SWT.UP | SWT.DOWN)) != 0) {
			width += OS.GetSystemMetrics (OS.SM_CXVSCROLL);
			height += OS.GetSystemMetrics (OS.SM_CYVSCROLL);
		} else {
			width += OS.GetSystemMetrics (OS.SM_CXHSCROLL);
			height += OS.GetSystemMetrics (OS.SM_CYHSCROLL);
		}
		*/
		if ((style & (SWT.UP | SWT.DOWN)) != 0) {
			width += 16;
			height += 16;
		} else {
			width += 16;
			height += 16;
		}
		if (wHint != SWT.DEFAULT) width = wHint;
		if (hHint != SWT.DEFAULT) height = hHint;
		width += border * 2; height += border * 2;
		return new Point (width, height);
	}
	int extra = 0;
//	boolean hasImage;
//	hasImage = image != null && (text == null || text.length() == 0);
//	String bg = "" + btnText.style.backgroundImage;
//	hasImage = bg != null && bg.length() != 0;
	/*
	if (OS.COMCTL32_MAJOR >= 6 && OS.IsAppThemed ()) {
		BUTTON_IMAGELIST buttonImageList = new BUTTON_IMAGELIST();
		OS.SendMessage (handle, OS.BCM_GETIMAGELIST, 0, buttonImageList);
		hasImage = buttonImageList.himl != 0;
	} else {
		int bits = OS.GetWindowLong (handle, OS.GWL_STYLE);
		hasImage = (bits & (OS.BS_BITMAP | OS.BS_ICON)) != 0;		
	}
	*/
	if (!hasImage) {
		/*
		int oldFont = 0;
		int hDC = OS.GetDC (handle);
		int newFont = OS.SendMessage (handle, OS.WM_GETFONT, 0, 0);
		if (newFont != 0) oldFont = OS.SelectObject (hDC, newFont);
		TEXTMETRIC lptm = OS.IsUnicode ? (TEXTMETRIC) new TEXTMETRICW () : new TEXTMETRICA ();
		OS.GetTextMetrics (hDC, lptm);
		int length = text.length ();
		if (length == 0) {
			height += lptm.tmHeight;
		} else {
			extra = Math.max (8, lptm.tmAveCharWidth);
			TCHAR buffer = new TCHAR (getCodePage (), text, true);
			RECT rect = new RECT ();
			int flags = OS.DT_CALCRECT | OS.DT_SINGLELINE;
			OS.DrawText (hDC, buffer, -1, rect, flags);
			width += rect.right - rect.left;
			height += rect.bottom - rect.top;
		}
		if (newFont != 0) OS.SelectObject (hDC, oldFont);
		OS.ReleaseDC (handle, hDC);
		*/
		if (text == null || text.length() == 0) {
			height += OS.getStringStyledHeight(".", "button-default", null);
		} else {
			if (!textSizeCached || changed) {
				String string = text.replaceAll("[\r\n]+", "");
				Point cssSize = OS.getStringStyledSize(string, "button-default", handle.style.cssText);
				textSizeCached = true;
				textWidthCached = cssSize.x;
				textHeightCached = cssSize.y;
			}
			
			width = textWidthCached;
			height = textHeightCached;
			if ((style & (SWT.RADIO | SWT.CHECK)) != 0) {
				width -= 5;
			}
			extra = Math.max (8, height);
		}
	} else {
		if (image != null) {
			if (image.width == 0 && image.height == 0) {
				if (image.url != null && image.url.length() != 0) {
					org.eclipse.swt.internal.xhtml.Image img = new org.eclipse.swt.internal.xhtml.Image ();
					img.src = this.image.url;
					this.image.width = img.width;
					this.image.height = img.height;
					width += img.width;
					height = Math.max(img.height, height);
					// TODO: The above method to find out width & height is unsafe!
					// TODO: Maybe the image may fail to be loaded!
				} else {
					// Default to 16x16 for common
					width += 16;
					height = Math.max(16, height);
				}
			} else {
				width += image.width;
				height = Math.max(image.height, height);
			}
			extra = 8;
		}
		/*
		if (image != null) {
			Rectangle rect = image.getBounds ();
			width = rect.width;
			height = rect.height;
			extra = 8;
		}
		*/
	}
	if ((style & (SWT.CHECK | SWT.RADIO)) != 0) {
		width += CHECK_WIDTH + extra;
		height = Math.max (height, CHECK_HEIGHT + 3);
	}
	if ((style & (SWT.PUSH | SWT.TOGGLE)) != 0) {
		width += 12;  height += 10;
	}
	if (wHint != SWT.DEFAULT) width = wHint;
	if (hHint != SWT.DEFAULT) height = hHint;
	width += border * 2; height += border * 2;
	return new Point (width, height);
}

/*
int defaultBackground () {
	if ((style & (SWT.PUSH | SWT.TOGGLE)) != 0) {
		return OS.GetSysColor (OS.COLOR_BTNFACE);
	}
	return super.defaultBackground ();
}

int defaultForeground () {
	return OS.GetSysColor (OS.COLOR_BTNTEXT);
}
*/
	
void enableWidget (boolean enabled) {
//	super.enableWidget (enabled);
	/*
	* Bug in Windows.  When a Button control is right-to-left and
	* is disabled, the first pixel of the text is clipped.  The fix
	* is to append a space to the text.
	*/
	/*
	if ((style & SWT.RIGHT_TO_LEFT) != 0) {
		if (OS.COMCTL32_MAJOR < 6 || !OS.IsAppThemed ()) {
			int bits = OS.GetWindowLong (handle, OS.GWL_STYLE);
			boolean hasImage = (bits & (OS.BS_BITMAP | OS.BS_ICON)) != 0;
			if (!hasImage) {
					String string = enabled ? text : text + " ";
					TCHAR buffer = new TCHAR (getCodePage (), string, true);
					OS.SetWindowText (handle, buffer);
			}
		}
	}
	*/
	btnHandle.disabled = !enabled;
	String cssName = handle.className;
	if (cssName == null) cssName = "";
	String key = "button-disabled";
	int idx = cssName.indexOf(key);
	if (!enabled) {
		if (idx == -1) {
			handle.className += " " + key; 
		}
	} else {
		if (idx != -1) {
			handle.className = cssName.substring(0, idx) + cssName.substring(idx + key.length()); 
		}
	}
}

/**
 * Returns a value which describes the position of the
 * text or image in the receiver. The value will be one of
 * <code>LEFT</code>, <code>RIGHT</code> or <code>CENTER</code>
 * unless the receiver is an <code>ARROW</code> button, in 
 * which case, the alignment will indicate the direction of
 * the arrow (one of <code>LEFT</code>, <code>RIGHT</code>, 
 * <code>UP</code> or <code>DOWN</code>).
 *
 * @return the alignment 
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public int getAlignment () {
	checkWidget ();
	if ((style & SWT.ARROW) != 0) {
		if ((style & SWT.UP) != 0) return SWT.UP;
		if ((style & SWT.DOWN) != 0) return SWT.DOWN;
		if ((style & SWT.LEFT) != 0) return SWT.LEFT;
		if ((style & SWT.RIGHT) != 0) return SWT.RIGHT;
		return SWT.UP;
	}
	if ((style & SWT.LEFT) != 0) return SWT.LEFT;
	if ((style & SWT.CENTER) != 0) return SWT.CENTER;
	if ((style & SWT.RIGHT) != 0) return SWT.RIGHT;
	return SWT.LEFT;
}

public int getBorderWidth() {
	if ((style & SWT.BORDER) != 0) {
		return 2;
	}
	return 0;
}

boolean getDefault () {
	if ((style & SWT.PUSH) == 0) return false;
//	int bits = OS.GetWindowLong (handle, OS.GWL_STYLE);
//	return (bits & OS.BS_DEFPUSHBUTTON) != 0;
	return false; // TODO
}

/**
 * Returns the receiver's image if it has one, or null
 * if it does not.
 *
 * @return the receiver's image
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public Image getImage () {
	checkWidget ();
	return image;
}

String getNameText () {
	return getText ();
}

/**
 * Returns <code>true</code> if the receiver is selected,
 * and false otherwise.
 * <p>
 * When the receiver is of type <code>CHECK</code> or <code>RADIO</code>,
 * it is selected when it is checked. When it is of type <code>TOGGLE</code>,
 * it is selected when it is pushed in. If the receiver is of any other type,
 * this method returns false.
 *
 * @return the selection state
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public boolean getSelection () {
	checkWidget ();
	if ((style & (SWT.CHECK | SWT.RADIO | SWT.TOGGLE)) == 0) return false;
//	int state = OS.SendMessage (handle, OS.BM_GETCHECK, 0, 0);
//	return (state & OS.BST_CHECKED) != 0;
	if ((style & SWT.TOGGLE) != 0) {
		//System.out.println(btnHandle.className);
		return (btnHandle.className != null && btnHandle.className.indexOf("button-selected") != -1);
	} else if ((style & (SWT.RADIO | SWT.CHECK)) != 0) {
		return btnHandle.checked;
	}
	return false;
}

/**
 * Returns the receiver's text, which will be an empty
 * string if it has never been set or if the receiver is
 * an <code>ARROW</code> button.
 *
 * @return the receiver's text
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public String getText () {
	checkWidget ();
	if ((style & SWT.ARROW) != 0) return "";
	return text;
}

/* (non-Javadoc)
 * @see org.eclipse.swt.widgets.Control#isEnabled()
 */
public boolean isEnabled() {
	return !btnHandle.disabled;
}

boolean isTabItem () {
	//TEMPORARY CODE
	//if ((style & SWT.PUSH) != 0) return true;
	return super.isTabItem ();
}

boolean mnemonicHit (char ch) {
	if (!setFocus ()) return false;
	/*
	* Feature in Windows.  When a radio button gets focus, 
	* it selects the button in WM_SETFOCUS.  Therefore, it
	* is not necessary to click the button or send events
	* because this has already happened in WM_SETFOCUS.
	*/
	if ((style & SWT.RADIO) == 0) click ();
	return true;
}

boolean mnemonicMatch (char key) {
	char mnemonic = findMnemonic (getText ());
	if (mnemonic == '\0') return false;
	return Character.toUpperCase (key) == Character.toUpperCase (mnemonic);
}

/* (non-Javadoc)
 * @see org.eclipse.swt.widgets.Control#releaseHandle()
 */
protected void releaseHandle() {
	if (btnText != null) {
		OS.destroyHandle(btnText);
		btnText = null;
	}
	if (btnHandle != null) {
		OS.destroyHandle(btnHandle);
		btnHandle = null;
	}
	super.releaseHandle();
}
void releaseWidget () {
	super.releaseWidget ();
	if (imageList != null) imageList.dispose ();
	imageList = null;
	if (image2 != null) image2.dispose ();
	image2 = null;
	text = null;
	image = null;
}

/**
 * Removes the listener from the collection of listeners who will
 * be notified when the control is selected.
 *
 * @param listener the listener which should no longer be notified
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @see SelectionListener
 * @see #addSelectionListener
 */
public void removeSelectionListener (SelectionListener listener) {
	checkWidget ();
	if (listener == null) error (SWT.ERROR_NULL_ARGUMENT);
	if (eventTable == null) return;
	eventTable.unhook (SWT.Selection, listener);
	eventTable.unhook (SWT.DefaultSelection,listener);	
}

void selectRadio () {
	/*
	* This code is intentionally commented.  When two groups
	* of radio buttons with the same parent are separated by
	* another control, the correct behavior should be that
	* the two groups act independently.  This is consistent
	* with radio tool and menu items.  The commented code
	* implements this behavior.
	*/
//	int index = 0;
//	Control [] children = parent._getChildren ();
//	while (index < children.length && children [index] != this) index++;
//	int i = index - 1;
//	while (i >= 0 && children [i].setRadioSelection (false)) --i;
//	int j = index + 1;
//	while (j < children.length && children [j].setRadioSelection (false)) j++;
//	setSelection (true);
	Control [] children = parent._getChildren ();
	for (int i=0; i<children.length; i++) {
		Control child = children [i];
		if (this != child) child.setRadioSelection (false);
	}
	setSelection (true);
}

/**
 * Controls how text, images and arrows will be displayed
 * in the receiver. The argument should be one of
 * <code>LEFT</code>, <code>RIGHT</code> or <code>CENTER</code>
 * unless the receiver is an <code>ARROW</code> button, in 
 * which case, the argument indicates the direction of
 * the arrow (one of <code>LEFT</code>, <code>RIGHT</code>, 
 * <code>UP</code> or <code>DOWN</code>).
 *
 * @param alignment the new alignment 
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setAlignment (int alignment) {
	checkWidget ();
	if ((style & SWT.ARROW) != 0) {
		if ((style & (SWT.UP | SWT.DOWN | SWT.LEFT | SWT.RIGHT)) == 0) return; 
		style &= ~(SWT.UP | SWT.DOWN | SWT.LEFT | SWT.RIGHT);
		style |= alignment & (SWT.UP | SWT.DOWN | SWT.LEFT | SWT.RIGHT);
		updateArrowStyle();
		int cx = width, cy = height;
		if ((style & SWT.BORDER) != 0) {
			cx -= 4;
			cy -= 4;
		}
		updateArrowSize(cx, cy);
		return;
	}
	if ((alignment & (SWT.LEFT | SWT.RIGHT | SWT.CENTER)) == 0) return;
	style &= ~(SWT.LEFT | SWT.RIGHT | SWT.CENTER);
	style |= alignment & (SWT.LEFT | SWT.RIGHT | SWT.CENTER);
	if ((style & (SWT.PUSH | SWT.TOGGLE)) != 0) {
		CSSStyle handleStyle = null;
		if ((style & (SWT.RADIO | SWT.CHECK)) != 0) {
			handleStyle = btnText.style;
		} else {
			handleStyle = btnHandle.style;
		}
		if ((style & SWT.LEFT) != 0) {
			btnText.style.textAlign = "left";
			handleStyle.backgroundPosition = "left center";
		} if ((style & SWT.CENTER) != 0) {
			btnText.style.textAlign = "center";
			handleStyle.backgroundPosition = "center center";
		} else if ((style & SWT.RIGHT) != 0) {
			btnText.style.textAlign = "right";
			handleStyle.backgroundPosition = "right center";
		}
	}
	/*
	int bits = OS.GetWindowLong (handle, OS.GWL_STYLE);
	bits &= ~(OS.BS_LEFT | OS.BS_CENTER | OS.BS_RIGHT);
	if ((style & SWT.LEFT) != 0) bits |= OS.BS_LEFT;
	if ((style & SWT.CENTER) != 0) bits |= OS.BS_CENTER;
	if ((style & SWT.RIGHT) != 0) bits |= OS.BS_RIGHT;
	OS.SetWindowLong (handle, OS.GWL_STYLE, bits);
	if (OS.COMCTL32_MAJOR >= 6 && OS.IsAppThemed ()) {
		if (imageList != null) {
			BUTTON_IMAGELIST buttonImageList = new BUTTON_IMAGELIST ();
			buttonImageList.himl = imageList.getHandle ();
			if ((style & SWT.LEFT) != 0) buttonImageList.uAlign = OS.BUTTON_IMAGELIST_ALIGN_LEFT;
			if ((style & SWT.CENTER) != 0) buttonImageList.uAlign = OS.BUTTON_IMAGELIST_ALIGN_CENTER;
			if ((style & SWT.RIGHT) != 0) buttonImageList.uAlign = OS.BUTTON_IMAGELIST_ALIGN_RIGHT;
			OS.SendMessage (handle, OS.BCM_SETIMAGELIST, 0, buttonImageList);
		}
	}
	OS.InvalidateRect (handle, null, true);
	*/
}

void setDefault (boolean value) {
	if ((style & SWT.PUSH) == 0) return;
	/*
	int hwndShell = menuShell ().handle;
	int bits = OS.GetWindowLong (handle, OS.GWL_STYLE);
	if (value) {
		bits |= OS.BS_DEFPUSHBUTTON;
		OS.SendMessage (hwndShell, OS.DM_SETDEFID, handle, 0);
	} else {
		bits &= ~OS.BS_DEFPUSHBUTTON;
		OS.SendMessage (hwndShell, OS.DM_SETDEFID, 0, 0);
	}
	OS.SendMessage (handle, OS.BM_SETSTYLE, bits, 1);
	*/
	if (value) {
		try {
			handle.focus();
		} catch (Error e) {
			
		}
	}
}

boolean setFixedFocus () {
	/*
	* Feature in Windows.  When a radio button gets focus, 
	* it selects the button in WM_SETFOCUS.  The fix is to
	* not assign focus to an unselected radio button.
	*/
	if ((style & SWT.RADIO) != 0 && !getSelection ()) return false;
	return super.setFixedFocus ();
}

/**
 * Sets the receiver's image to the argument, which may be
 * <code>null</code> indicating that no image should be displayed.
 *
 * @param image the image to display on the receiver (may be <code>null</code>)
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_INVALID_ARGUMENT - if the image has been disposed</li>
 * </ul> 
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setImage (Image image) {
	checkWidget ();
	if ((style & SWT.ARROW) != 0) return ;
	if (image != null && image.isDisposed()) error(SWT.ERROR_INVALID_ARGUMENT);
//	_setImage (this.image = image);
//	if (image != null && image.isDisposed()) error(SWT.ERROR_INVALID_ARGUMENT);
//	this.image = image;
//	if (image != null && handle != null) {
//		handle.style.backgroundImage = "url('" + image.url + "')";
//	}
	this.image = image;
	hasImage = true;
	if (this.image.handle == null && this.image.url != null && this.image.url.length() != 0) {
		OS.clearChildren(btnText);
		btnText.style.display = "";
		btnText.style.paddingTop = "";
		btnHandle.parentNode.style.bottom = ""; 
		btnHandle.parentNode.style.top = "";
		btnHandle.style.top = "";
		btnText.parentNode.style.position = "";
		btnText.parentNode.style.top = "";
		
		CSSStyle handleStyle = null;
		if ((style & (SWT.RADIO | SWT.CHECK)) != 0) {
			handleStyle = btnText.style;
			org.eclipse.swt.internal.xhtml.Image img = new org.eclipse.swt.internal.xhtml.Image ();
			img.src = this.image.url;
			this.image.width = img.width;
			this.image.height = img.height;
//			handleStyle.fontSize = this.image.height + "px";
			handleStyle.display = "block";
			handleStyle.marginLeft = (CHECK_WIDTH + 3) + "px"; 
			handleStyle.paddingTop = this.image.height + "px"; 
//			handleStyle.lineHeight = this.image.width + "px"; 
//			btnText.appendChild(document.createTextNode(" "));
		} else {
			handleStyle = btnHandle.style;
		}
		if (image.url.toLowerCase().endsWith(".png") && handleStyle.filter != null) {
//				Element imgBackground = document.createElement("DIV");
//				imgBackground.style.position = "absolute";
//				imgBackground.style.width = "100%";
//				imgBackground.style.height = "100%";
//				imgBackground.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src=\"" + this.image.url + "\", sizingMethod=\"image\")";
//				handle.appendChild(imgBackground);
			handleStyle.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src=\"" + this.image.url + "\", sizingMethod=\"image\")";
		} else {
			handleStyle.backgroundRepeat = "no-repeat";
			String bgXPos = "center";
			if ((style & (SWT.RADIO | SWT.CHECK)) != 0) {
				if ((style & SWT.RIGHT) != 0) {
					bgXPos = "right";
				} else if ((style & SWT.CENTER) != 0) {
					bgXPos = "center";
				} else {
					bgXPos = "left";
				}
			}
			handleStyle.backgroundPosition = bgXPos + " center";
			
			handleStyle.backgroundImage = "url(\"" + this.image.url + "\")";
		}
		//btnText.appendChild(document.createTextNode("hello"));
//	} else if (handle.childNodes.length == 0) {
//		for (int i = 0; i < image.handle.childNodes.length; i++) {
//			handle.appendChild(image.handle.childNodes[i]);
//		}
//	} else {
//		Element txt = handle.childNodes[0];
//		for (int i = 0; i < image.handle.childNodes.length; i++) {
//			handle.insertBefore(image.handle.childNodes[i], txt);
//		}
	}
	
}

boolean setRadioFocus () {
	if ((style & SWT.RADIO) == 0 || !getSelection ()) return false;
	return setFocus ();
}

boolean setRadioSelection (boolean value) {
	if ((style & SWT.RADIO) == 0) return false;
	if (getSelection () != value) {
		setSelection (value);
		postEvent (SWT.Selection);
	}
	return true;
}

boolean setSavedFocus () {
	/*
	* Feature in Windows.  When a radio button gets focus, 
	* it selects the button in WM_SETFOCUS.  If the previous
	* saved focus widget was a radio button, allowing the shell
	* to automatically restore the focus to the previous radio
	* button will unexpectedly check that button.  The fix is to
	* not assign focus to an unselected radio button.
	*/
	if ((style & SWT.RADIO) != 0 && !getSelection ()) return false;
	return super.setSavedFocus ();
}

	
/**
 * Sets the selection state of the receiver, if it is of type <code>CHECK</code>, 
 * <code>RADIO</code>, or <code>TOGGLE</code>.
 *
 * <p>
 * When the receiver is of type <code>CHECK</code> or <code>RADIO</code>,
 * it is selected when it is checked. When it is of type <code>TOGGLE</code>,
 * it is selected when it is pushed in.
 *
 * @param selected the new selection state
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setSelection (boolean selected) {
	checkWidget ();
	if ((style & (SWT.CHECK | SWT.RADIO | SWT.TOGGLE)) == 0) return;
	if ((style & SWT.TOGGLE) != 0) {
		String cssName = btnHandle.className;
		if (cssName == null) cssName = "";
		String key = "button-selected";
		int idx = cssName.indexOf(key);
		if (selected) {
			if (idx == -1) {
				btnHandle.className += " " + key; 
			}
		} else {
			if (idx != -1) {
				btnHandle.className = cssName.substring(0, idx) + cssName.substring(idx + key.length()); 
			}
		}
	} else if ((style & (SWT.RADIO | SWT.CHECK)) != 0) {
		btnHandle.checked = selected;
	} 
//	int flags = selected ? OS.BST_CHECKED : OS.BST_UNCHECKED;
	
	/*
	* Feature in Windows. When BM_SETCHECK is used
	* to set the checked state of a radio or check
	* button, it sets the WM_TABSTOP style.  This
	* is undocumented and unwanted.  The fix is
	* to save and restore the window style bits.
	*/
//	int bits = OS.GetWindowLong (handle, OS.GWL_STYLE);
//	OS.SendMessage (handle, OS.BM_SETCHECK, flags, 0);
//	OS.SetWindowLong (handle, OS.GWL_STYLE, bits);     
}

/**
 * Sets the receiver's text.
 * <p>
 * This method sets the button label.  The label may include
 * the mnemonic character but must not contain line delimiters.
 * </p>
 * <p>
 * Mnemonics are indicated by an '&amp' that causes the next
 * character to be the mnemonic.  When the user presses a
 * key sequence that matches the mnemonic, a selection
 * event occurs. On most platforms, the mnemonic appears
 * underlined but may be emphasised in a platform specific
 * manner.  The mnemonic indicator character '&amp' can be
 * escaped by doubling it in the string, causing a single
 *'&amp' to be displayed.
 * </p>
 * 
 * @param string the new text
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the text is null</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setText (String string) {
	checkWidget ();
	if (string == null) error (SWT.ERROR_NULL_ARGUMENT);
	if ((style & SWT.ARROW) != 0) return;
	/*
	if (OS.COMCTL32_MAJOR >= 6 && OS.IsAppThemed ()) {
		OS.SendMessage (handle, OS.BCM_SETIMAGELIST, 0, 0);
		if (imageList != null) imageList.dispose ();
		imageList = null;
	} else {
		int newBits = OS.GetWindowLong (handle, OS.GWL_STYLE);
		int oldBits = newBits;
		newBits &= ~(OS.BS_BITMAP | OS.BS_ICON);
		if (newBits != oldBits) {
			OS.SetWindowLong (handle, OS.GWL_STYLE, newBits);
		}
	}
	*/
	if (string != text) {
		textSizeCached = false;
	}
	OS.clearChildren(btnText);
//	boolean hasImage;
//	hasImage = image != null && (text == null || text.length() == 0);
//	String bg = "" + btnText.style.backgroundImage;
//	hasImage = bg != null && bg.length() != 0;
	if (hasImage) {
		btnText.style.backgroundImage = "";
		if (OS.isIE && image.url != null && image.url.toLowerCase().endsWith(".png")
				&& btnText.style.filter != null) {
			btnText.style.filter = "";
		}
	}
	text = string;
	hasImage = false;
	//string = string.replaceAll("&&", "&");
	string = string.replaceAll("(&(&))|([\r\n]+)", "$2");
	int idx = string.indexOf('&');
	if (idx == -1) {
		btnText.appendChild(document.createTextNode(string));
	} else {
		// Only one &
		btnText.appendChild(document.createTextNode(string.substring(0, idx)));
		Element underline = document.createElement("SPAN");
		underline.appendChild(document.createTextNode(string.substring(idx + 1, idx + 2)));
		underline.className = "button-text-mnemonics";
		btnText.appendChild(underline);
		btnText.appendChild(document.createTextNode(string.substring(idx + 2)));
	}
	/*
	* Bug in Windows.  When a Button control is right-to-left and
	* is disabled, the first pixel of the text is clipped.  The fix
	* is to append a space to the text.
	*-/
	if ((style & SWT.RIGHT_TO_LEFT) != 0) {
		if (OS.COMCTL32_MAJOR < 6 || !OS.IsAppThemed ()) {
			string = OS.IsWindowEnabled (handle) ? text : text + " ";
		}
	}
	TCHAR buffer = new TCHAR (getCodePage (), string, true);
	OS.SetWindowText (handle, buffer);
	*/
}

/* (non-Javadoc)
 * @see org.eclipse.swt.widgets.Widget#SetWindowPos(org.eclipse.swt.internal.xhtml.Element, org.eclipse.swt.internal.xhtml.Element, int, int, int, int, int)
 */
boolean SetWindowPos(Object hWnd, Object hWndInsertAfter, int X, int Y, int cx, int cy, int uFlags) {
	if ((style & SWT.BORDER) != 0) {
		cx -= 4;
		cy -= 4;
	}
	if ((style & SWT.ARROW) != 0) {
		updateArrowSize(cx, cy);
	}
	if ((style & (SWT.RADIO | SWT.CHECK)) != 0) {
//		boolean hasImage;
//		hasImage = image != null && (text == null || text.length() == 0);
//		String bg = "" + btnText.style.backgroundImage;
//		hasImage = bg != null && bg.length() != 0;

		int h = 0;
		if (!hasImage) {
			if (textSizeCached) {
				btnText.style.display = "block";
				if (textHeightCached < CHECK_HEIGHT) {
					btnText.style.paddingTop = ((CHECK_HEIGHT - textHeightCached) / 2) + "px";
					btnHandle.parentNode.style.bottom = "0"; 
					btnHandle.parentNode.style.top = "0";
					btnHandle.style.top = "0";
				} else {
					btnText.style.paddingTop = "0";
					//btnHandle.parentNode.style.bottom = "auto"; 
				}
			}
			h = textHeightCached;
		} else {
//			btnText.style.display = "block";
//			if (image.height < CHECK_HEIGHT) {
//				//btnText.style.paddingTop = ((image.height - textHeightCached) / 2) + "px";
//				btnHandle.parentNode.style.bottom = "auto"; 
//				btnHandle.parentNode.style.top = "auto";
//				btnHandle.style.top = "auto";
//			}
			h = image.height;
		}
		h = Math.max(CHECK_HEIGHT + 3, h);
		if (h < cy) {
			btnText.parentNode.style.position = "relative";
			btnText.parentNode.style.top = ((cy - h) / 2) + "px";
		}
	}
	Element el = (Element) hWnd;
	// TODO: What about hWndInsertAfter and uFlags
	el.style.left = X + "px";
	el.style.top = Y + "px";
	el.style.width = (cx > 0 ? cx : 0) + "px";
	el.style.height = (cy > 0 ? cy : 0) + "px";
	return true;
//	return super.SetWindowPos(null, null, X, Y, cx, cy, uFlags);
}

private void updateArrowSize(int cx, int cy) {
	int xx = Math.min(cx, cy) / 3;
	final CSSStyle s = btnText.style;
	s.borderWidth = (xx > 0 ? xx : 0) + "px";
	if ((style & SWT.LEFT) != 0) {
		s.borderLeftWidth = "0";
	} else if ((style & SWT.RIGHT) != 0) {
		s.borderRightWidth = "0";
	} else if ((style & SWT.UP) != 0) {
		s.borderTopWidth = "0";
	} else if ((style & SWT.DOWN) != 0) {
		if (xx > 1) {
			s.borderWidth = (xx - 1) + "px";
		}
		s.borderBottomWidth = "0";
	} else {
		s.borderTopWidth = "0";
	} 
	int x = cy / 6;
	xx = cy / 3;
	s.position = "relative";
	if ((style & (SWT.RIGHT | SWT.LEFT)) != 0) {
		s.top = (x - 3) + "px";
		if ((style & SWT.RIGHT) != 0) {
			s.left = "1px";
		}
	} else {
		if ((style & SWT.UP) != 0) {
			s.top = (xx - 3)+ "px";
		} else if ((style & SWT.DOWN) != 0) {
			s.top = (xx - 2)+ "px";
		}
	}
	/**
	 * TODO: Get rid of these nasty position things!
	 */
	if (OS.isMozilla && !OS.isFirefox) {
		if ((style & SWT.UP) != 0) {
			s.left = "-2px";
		} else if ((style & SWT.DOWN) != 0) {
			s.left = "-1px";
		}
	}
	if (OS.isFirefox) {
		if ((style & (SWT.RIGHT | SWT.LEFT)) != 0) {
			s.top = "-2px";
			if ((style & SWT.RIGHT) != 0) {
				s.left = "1px";
			}
		} else {
			if ((style & SWT.UP) != 0) {
				s.left = "-2px";
				s.top = "-1px";
			} else if ((style & SWT.DOWN) != 0) {
				s.left = "-1px";
				s.top = "-1px";
			}
		}
	}
}

public void setCursor(Cursor cursor) {
	if (handle != null) {
		handle.style.cursor = cursor.handle;
	}
}

/*
int widgetStyle () {
	int bits = super.widgetStyle ();
	if ((style & SWT.FLAT) != 0) bits |= OS.BS_FLAT;
	if ((style & SWT.ARROW) != 0) return bits | OS.BS_OWNERDRAW;
	if ((style & SWT.LEFT) != 0) bits |= OS.BS_LEFT;
	if ((style & SWT.CENTER) != 0) bits |= OS.BS_CENTER;
	if ((style & SWT.RIGHT) != 0) bits |= OS.BS_RIGHT;
	if ((style & SWT.PUSH) != 0) return bits | OS.BS_PUSHBUTTON | OS.WS_TABSTOP;
	if ((style & SWT.CHECK) != 0) return bits | OS.BS_CHECKBOX | OS.WS_TABSTOP;
	if ((style & SWT.RADIO) != 0) return bits | OS.BS_RADIOBUTTON;
	if ((style & SWT.TOGGLE) != 0) return bits | OS.BS_PUSHLIKE | OS.BS_CHECKBOX | OS.WS_TABSTOP;
	return bits | OS.BS_PUSHBUTTON | OS.WS_TABSTOP;
}
*/

/* (non-Javadoc)
 * @see org.eclipse.swt.widgets.Control#createHandle()
 */
void createHandle() {
//	super.createHandle();
	handle = document.createElement ("DIV");
	String cssName = "button-default";
	if ((style & SWT.BORDER) != 0) {
		cssName += " button-border";
	}
	if ((style & SWT.FLAT) != 0) {
		cssName += " button-flat";
	}
	handle.className = cssName;
	if (parent != null) {
		Element parentHandle = parent.containerHandle();
		if (parentHandle!= null) {
			parentHandle.appendChild(handle);
		}
	}
	
	if ((style & (SWT.RADIO | SWT.CHECK)) != 0) {
		Element btnEl = document.createElement("DIV");
		handle.appendChild(btnEl);
		Element btnWrapperEl = document.createElement("DIV");
		btnWrapperEl.className = "button-input-wrapper";
		btnEl.appendChild(btnWrapperEl);
		btnHandle = document.createElement("INPUT");
		if ((style & SWT.CHECK) != 0) {
			btnEl.className = "button-check";
			btnHandle.type = "checkbox";
		} else {
			btnEl.className = "button-radio";
			btnHandle.type = "radio";
		}
		btnWrapperEl.appendChild(btnHandle);
		btnText = document.createElement("DIV");
		btnText.className = "button-text";
		btnEl.appendChild(btnText);
	} else {
		btnHandle = document.createElement ("BUTTON");
		handle.appendChild(btnHandle);
		btnText = document.createElement("DIV");
		btnHandle.appendChild(btnText);
		if ((style & SWT.TOGGLE) != 0) {
			btnHandle.className = "button-toggle";
		} else if ((style & SWT.ARROW) != 0) {
			btnHandle.className = "button-arrow";
			updateArrowStyle(); 
		} else {
			btnHandle.className = "button-push";
		}
	}
	btnHandle.onmouseover = new RunnableCompatibility() {
		public void run() {
			String cssName = " button-hover";
			int idx = btnHandle.className.indexOf(cssName);
			if(idx == -1){
				btnHandle.className = btnHandle.className + cssName;
			}
		}
	};
	btnHandle.onmouseout = new RunnableCompatibility() {
		public void run() {
			String cssName = " button-hover";
			int idx = btnHandle.className.indexOf(cssName);
			if(idx != -1){
				btnHandle.className = btnHandle.className.substring(0, idx) + btnHandle.className.substring(cssName.length() + idx);
			}
		}
	};
	//bindHandle();
	hookSelection();
}

private void updateArrowStyle() {
	if ((style & SWT.LEFT) != 0) {
		btnText.className = "button-arrow-left";
	} else if ((style & SWT.RIGHT) != 0) {
		btnText.className = "button-arrow-right";
	} else if ((style & SWT.UP) != 0) {
		btnText.className = "button-arrow-up";
	} else if ((style & SWT.DOWN) != 0) {
		btnText.className = "button-arrow-down";
	} else {
		btnText.className = "button-arrow-up";
	}
}

void hookSelection() {
	RunnableCompatibility eventHandler = new RunnableCompatibility() {
		public void run() {
			if (!isEnabled()) {
				toReturn(false);
				return ;
			}
			if ((style & (SWT.CHECK | SWT.TOGGLE)) != 0) {
				HTMLEvent e = (HTMLEvent) getEvent();
				if ((style & SWT.CHECK) != 0) {
					if (e.srcElement != btnHandle) {
						setSelection (!getSelection ());
					}
				} else {
					setSelection (!getSelection ());
				}
			} else {
				if ((style & SWT.RADIO) != 0) {
					if ((parent.getStyle () & SWT.NO_RADIO_GROUP) != 0) {
						setSelection (!getSelection ());
					} else {
						selectRadio ();
					}
				}
			}
			postEvent (SWT.Selection);
			/*
			if ((style & SWT.TOGGLE) != 0) {
				setSelection(!getSelection());
			}
			Event e = new Event();
			e.display = display;
			e.type = SWT.Selection;
			e.item = Button.this;
			e.text = getText();
			e.widget = Button.this;
			sendEvent(e);
			*/
		}
	};
	handle.onclick = handle.ondblclick = eventHandler;
	if ((style & (SWT.RADIO | SWT.CHECK)) != 0) {
		btnText.onclick = eventHandler;
	}
}

/*
String windowClass() {
	return "BUTTON";
}

int windowProc () {
	return ButtonProc;
}

LRESULT WM_GETDLGCODE (int wParam, int lParam) {
	LRESULT result = super.WM_GETDLGCODE (wParam, lParam);
	if (result != null) return result;
	if ((style & SWT.ARROW) != 0) {
		return new LRESULT (OS.DLGC_STATIC);
	}
	return result;
}

LRESULT WM_KILLFOCUS (int wParam, int lParam) {
	LRESULT result = super.WM_KILLFOCUS (wParam, lParam);
	if ((style & SWT.PUSH) != 0 && getDefault ()) {
		menuShell ().setDefaultButton (null, false);
	}
	return result;
}

LRESULT WM_LBUTTONDOWN (int wParam, int lParam) {
	if (ignoreMouse) return null;
	return super.WM_LBUTTONDOWN (wParam, lParam);
}

LRESULT WM_LBUTTONUP (int wParam, int lParam) {
	if (ignoreMouse) return null;
	return super.WM_LBUTTONUP (wParam, lParam);
}

LRESULT WM_SETFOCUS (int wParam, int lParam) {
	/*
	* Feature in Windows. When Windows sets focus to
	* a radio button, it sets the WM_TABSTOP style.
	* This is undocumented and unwanted.  The fix is
	* to save and restore the window style bits.
	*-/
	int bits = 0;
	if ((style & SWT.RADIO) != 0) {
		bits = OS.GetWindowLong (handle, OS.GWL_STYLE);
	}
	LRESULT result = super.WM_SETFOCUS (wParam, lParam);
	if ((style & SWT.RADIO) != 0) {
		OS.SetWindowLong (handle, OS.GWL_STYLE, bits);
	}
	if ((style & SWT.PUSH) != 0) {
		menuShell ().setDefaultButton (this, false);
	}
	return result;
}

LRESULT WM_SYSCOLORCHANGE (int wParam, int lParam) {
	LRESULT result = super.WM_SYSCOLORCHANGE (wParam, lParam);
	if (result != null) return result;
	if (image2 != null) _setImage (image);
	return result;
}

LRESULT wmColorChild (int wParam, int lParam) {
	LRESULT result = super.wmColorChild (wParam, lParam);
	if (OS.COMCTL32_MAJOR >= 6 && OS.IsAppThemed ()) {
		Control control = findThemeControl ();
		if (control != null) {
			OS.SetBkMode (wParam, OS.TRANSPARENT);
			RECT rect = new RECT ();
			OS.GetClientRect (control.handle, rect);
			OS.MapWindowPoints (control.handle, handle, rect, 2);
			control.drawThemeBackground (wParam, rect);
			return new LRESULT (OS.GetStockObject (OS.NULL_BRUSH));
		}
	}
	return result;
}

LRESULT wmCommandChild (int wParam, int lParam) {
	int code = wParam >> 16;
	switch (code) {
		case OS.BN_CLICKED:
		case OS.BN_DOUBLECLICKED:
			if ((style & (SWT.CHECK | SWT.TOGGLE)) != 0) {
				setSelection (!getSelection ());
			} else {
				if ((style & SWT.RADIO) != 0) {
					if ((parent.getStyle () & SWT.NO_RADIO_GROUP) != 0) {
						setSelection (!getSelection ());
					} else {
						selectRadio ();
					}
				}
			}
			postEvent (SWT.Selection);
	}
	return super.wmCommandChild (wParam, lParam);
}

LRESULT wmDrawChild (int wParam, int lParam) {
	if ((style & SWT.ARROW) == 0) return super.wmDrawChild (wParam, lParam);
	DRAWITEMSTRUCT struct = new DRAWITEMSTRUCT ();
	OS.MoveMemory (struct, lParam, DRAWITEMSTRUCT.sizeof);
	RECT rect = new RECT ();
	OS.SetRect (rect, struct.left, struct.top, struct.right, struct.bottom);
	if (OS.COMCTL32_MAJOR >= 6 && OS.IsAppThemed ()) {
		int hTheme = OS.OpenThemeData (handle, SCROLLBAR);
		int iStateId = OS.ABS_LEFTNORMAL;
		switch (style & (SWT.UP | SWT.DOWN | SWT.LEFT | SWT.RIGHT)) {
			case SWT.UP: iStateId = OS.ABS_UPNORMAL; break;
			case SWT.DOWN: iStateId = OS.ABS_DOWNNORMAL; break;
			case SWT.LEFT: iStateId = OS.ABS_LEFTNORMAL; break;
			case SWT.RIGHT: iStateId = OS.ABS_RIGHTNORMAL; break;
		}
		/*
		* NOTE: The normal, hot, pressed and disabled state is
		* computed relying on the fact that the increment between
		* the direction states is invariant (always separated by 4).
		*-/
		if (!getEnabled ()) iStateId += OS.ABS_UPDISABLED - OS.ABS_UPNORMAL;
		if ((struct.itemState & OS.ODS_SELECTED) != 0) iStateId += OS.ABS_UPPRESSED - OS.ABS_UPNORMAL;
		OS.DrawThemeBackground (hTheme, struct.hDC, OS.SBP_ARROWBTN, iStateId, rect, null);
		OS.CloseThemeData (hTheme);
	} else {
		int uState = OS.DFCS_SCROLLLEFT;
		switch (style & (SWT.UP | SWT.DOWN | SWT.LEFT | SWT.RIGHT)) {
			case SWT.UP: uState = OS.DFCS_SCROLLUP; break;
			case SWT.DOWN: uState = OS.DFCS_SCROLLDOWN; break;
			case SWT.LEFT: uState = OS.DFCS_SCROLLLEFT; break;
			case SWT.RIGHT: uState = OS.DFCS_SCROLLRIGHT; break;
		}
		if (!getEnabled ()) uState |= OS.DFCS_INACTIVE;
		if ((style & SWT.FLAT) == SWT.FLAT) uState |= OS.DFCS_FLAT;
		if ((struct.itemState & OS.ODS_SELECTED) != 0) uState |= OS.DFCS_PUSHED;
		OS.DrawFrameControl (struct.hDC, rect, OS.DFC_SCROLL, uState);
	}
	return null;
}
*/
}
