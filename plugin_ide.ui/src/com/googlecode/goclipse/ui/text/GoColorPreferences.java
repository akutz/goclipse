/*******************************************************************************
 * Copyright (c) 2015 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package com.googlecode.goclipse.ui.text;

import org.eclipse.swt.graphics.RGB;

import melnorme.lang.ide.ui.text.coloring.ColoringItemPreference;

public interface GoColorPreferences {
	
	
    public static final ColoringItemPreference SYNTAX_COLORING__TEXT = 
    		new ColoringItemPreference("syntax_highlighting_text", true, new RGB(0, 0, 0), false, false, false);
	public static final ColoringItemPreference SYNTAX_COLORING__KEYWORD = 
			new ColoringItemPreference("syntax_highlighting_keyword", true, new RGB(127, 0, 85), true, false, false);
	public static final ColoringItemPreference SYNTAX_COLORING__KEYWORD_LITERAL = 
			new ColoringItemPreference("syntax_highlighting_value", true, new RGB(127, 0, 85), true, true, false);
	public static final ColoringItemPreference SYNTAX_COLORING__PRIMITIVE = 
			new ColoringItemPreference("syntax_highlighting_primitive", true, new RGB(127, 0, 85), false, true, false);
	public static final ColoringItemPreference SYNTAX_COLORING__BUILTIN_FUNCTION = 
			new ColoringItemPreference("syntax_highlighting_builtin_function", true, new RGB(0, 0, 0), true, false, false);
	public static final ColoringItemPreference SYNTAX_COLORING__OPERATOR = 
			new ColoringItemPreference("syntax_highlighting_operator", true, new RGB(0, 0, 0), false, false, false);
	public static final ColoringItemPreference SYNTAX_COLORING__STRUCTURAL_SYMBOLS = 
			new ColoringItemPreference("syntax_highlighting_syntax_chars", true, new RGB(0, 0, 0), false, false, false);
	
	public static final ColoringItemPreference SYNTAX_COLORING__CHARACTER = 
			new ColoringItemPreference("syntax_highlighting_character", true, new RGB(0, 75, 200), false, false, false);
	public static final ColoringItemPreference SYNTAX_COLORING__STRING = 
			new ColoringItemPreference("syntax_highlighting_string", true, new RGB(0, 75, 200), false, false, false);
	public static final ColoringItemPreference SYNTAX_COLORING__MULTILINE_STRING = 
			new ColoringItemPreference("syntax_highlighting_multiline_string", true, new RGB(230, 75, 0), false, false, false);

	public static final ColoringItemPreference SYNTAX_COLORING__COMMENT = 
			new ColoringItemPreference("syntax_highlighting_comment", true, new RGB(63, 127, 95), false, false, false);

}