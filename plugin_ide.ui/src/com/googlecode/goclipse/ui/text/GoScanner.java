package com.googlecode.goclipse.ui.text;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;

import com.googlecode.goclipse.ui.GoUIPreferenceConstants;

import _org.eclipse.cdt.ui.text.ITokenStoreFactory;
import melnorme.lang.ide.core.text.DefaultPredicateRule;
import melnorme.lang.ide.ui.text.AbstractLangScanner;
import melnorme.utilbox.collections.ArrayList2;

public class GoScanner extends AbstractLangScanner {
	
	private static String tokenPrefProperties[] = new String[] {
		GoUIPreferenceConstants.SYNTAX_COLORING__TEXT.key,
		GoUIPreferenceConstants.SYNTAX_COLORING__KEYWORD.key,
		GoUIPreferenceConstants.SYNTAX_COLORING__KEYWORD_LITERAL.key,
		GoUIPreferenceConstants.SYNTAX_COLORING__PRIMITIVE.key,
		GoUIPreferenceConstants.SYNTAX_COLORING__BUILTIN_FUNCTION.key,
		GoUIPreferenceConstants.SYNTAX_COLORING__OPERATOR.key,
		GoUIPreferenceConstants.SYNTAX_COLORING__STRUCTURAL_SYMBOLS.key
	};
	
	public GoScanner(ITokenStoreFactory tokenStoreFactory) {
		super(tokenStoreFactory.createTokenStore(tokenPrefProperties));
	}
	
	@Override
	protected void initRules(ArrayList2<IRule> rules) {
		
		// Add generic whitespace rule.
		rules.add(new WhitespaceRule(new LangWhitespaceDetector()));
		
		
		final IToken tkDefault = getToken(GoUIPreferenceConstants.SYNTAX_COLORING__TEXT.key);
		
		WordRule wordRule = new WordRule(new JavaWordDetector(), tkDefault);
		
		final IToken keyword         = getToken(GoUIPreferenceConstants.SYNTAX_COLORING__KEYWORD.key);
		final IToken value           = getToken(GoUIPreferenceConstants.SYNTAX_COLORING__KEYWORD_LITERAL.key);
		final IToken primitive       = getToken(GoUIPreferenceConstants.SYNTAX_COLORING__PRIMITIVE.key);
		final IToken builtinFunction = getToken(GoUIPreferenceConstants.SYNTAX_COLORING__BUILTIN_FUNCTION.key);
		final IToken textToken       = getToken(GoUIPreferenceConstants.SYNTAX_COLORING__TEXT.key);
		setDefaultReturnToken(textToken);
		
		// add tokens for each reserved word
		wordRule.addWord("break",       keyword);
		wordRule.addWord("default",     keyword);
		wordRule.addWord("func",        keyword);
		wordRule.addWord("interface",   keyword);
		wordRule.addWord("select",      keyword);
		wordRule.addWord("case",        keyword);
		wordRule.addWord("defer",       keyword);
		wordRule.addWord("go",          keyword);
		wordRule.addWord("map",         keyword);
		wordRule.addWord("struct",      keyword);
		wordRule.addWord("chan",        keyword);
		wordRule.addWord("else",        keyword);
		wordRule.addWord("goto",        keyword);
		wordRule.addWord("package",     keyword);
		wordRule.addWord("switch",      keyword);
		wordRule.addWord("const",       keyword);
		wordRule.addWord("fallthrough", keyword);
		wordRule.addWord("if",          keyword);
		wordRule.addWord("range",       keyword);
		wordRule.addWord("type",        keyword);
		wordRule.addWord("continue",    keyword);
		wordRule.addWord("for",         keyword);
		wordRule.addWord("import",      keyword);
		wordRule.addWord("return",      keyword);
		wordRule.addWord("var",         keyword);
		
		wordRule.addWord("append",  builtinFunction);
		wordRule.addWord("cap",     builtinFunction);
		wordRule.addWord("close",   builtinFunction);
		wordRule.addWord("complex", builtinFunction);
		wordRule.addWord("copy",    builtinFunction);
		wordRule.addWord("delete",  builtinFunction);
		wordRule.addWord("imag",    builtinFunction);
		wordRule.addWord("len",     builtinFunction);
		wordRule.addWord("make",    builtinFunction);
		wordRule.addWord("new",     builtinFunction);
		wordRule.addWord("panic",   builtinFunction);
		wordRule.addWord("print",   builtinFunction);
		wordRule.addWord("println", builtinFunction);
		wordRule.addWord("real",    builtinFunction);
		wordRule.addWord("recover", builtinFunction);
		
		wordRule.addWord("nil",   value);
		wordRule.addWord("true",  value);
		wordRule.addWord("false", value);
		wordRule.addWord("iota",  value);
		
		wordRule.addWord("uint8", primitive);
		wordRule.addWord("uint16", primitive);
		wordRule.addWord("uint32", primitive);
		wordRule.addWord("uint64", primitive);
		wordRule.addWord("int8", primitive);
		wordRule.addWord("int16", primitive);
		wordRule.addWord("int32", primitive);
		wordRule.addWord("int64", primitive);
		wordRule.addWord("float32", primitive);
		wordRule.addWord("float64", primitive);
		wordRule.addWord("complex64",  primitive);
		wordRule.addWord("complex128", primitive);
		wordRule.addWord("rune",       primitive);
		wordRule.addWord("byte",       primitive);
		wordRule.addWord("uint",       primitive);
		wordRule.addWord("int",        primitive);
		wordRule.addWord("uintptr",    primitive);
		
		wordRule.addWord("string", primitive);
		wordRule.addWord("bool",   primitive);
		wordRule.addWord("error",  primitive);
		
		rules.add(wordRule);
		
		rules.add(new GoOperatorRule(getToken(GoUIPreferenceConstants.SYNTAX_COLORING__OPERATOR.key)));
		rules.add(new GoControlCharactersRule(getToken(GoUIPreferenceConstants.SYNTAX_COLORING__STRUCTURAL_SYMBOLS.key)));
	}
	
	public static class GoOperatorRule extends DefaultPredicateRule {
		
		public GoOperatorRule(IToken token) {
			super(token);
		}
		
		@Override
		public IToken evaluate(ICharacterScanner scanner) {
			int read = scanner.read();
			
			if(read == ICharacterScanner.EOF) {
				return Token.UNDEFINED;
			}
			
			switch (read) {
			case '+': return currentOr('=', '+', scanner);
			case '-': return currentOr('=', '-', scanner);
			case '*': return currentOr('=', scanner);
			case '/': return currentOr('=', scanner);
			case '^': return currentOr('=', scanner);
			case '!': return currentOr('=', scanner);
			case '=': return currentOr('=', scanner);
			case '%': return currentOr('=', scanner);
			case '|': return currentOr('=', '|', scanner);
			case '&': 
				if(consume('^', scanner)) {
					return currentOr('=', scanner);
				}
				return currentOr('=', '&', scanner);
			
			case '<': 
				if(consume('<', scanner)) {
					return currentOr('=', scanner);
				}
				return currentOr('=', '-', scanner);
			case '>': 
				if(consume('>', scanner)) {
					return currentOr('=', scanner); // ">>" , ">>="
				}
				return currentOr('=', scanner);
				
			case ':':
				if(consume('=', scanner)) {
					return getSuccessToken(); // ":="
				}
				
				// fall-through
			default:
				scanner.unread(); return Token.UNDEFINED;
			}
			
		}
		
	}
	
	public static class GoControlCharactersRule extends DefaultPredicateRule {
		
		public GoControlCharactersRule(IToken token) {
			super(token);
		}
		
		@Override
		public IToken evaluate(ICharacterScanner scanner) {
			int read = scanner.read();
			
			if(read == ICharacterScanner.EOF) {
				return Token.UNDEFINED;
			}
			
			switch (read) {
			case ':':
			case ';':
			case '.':
			case '(':
			case ')':
			case '[':
			case ']':
			case '{':
			case '}':
				return getSuccessToken();
			default:
				scanner.unread(); return Token.UNDEFINED;
			}
			
		}
		
	}
	
}