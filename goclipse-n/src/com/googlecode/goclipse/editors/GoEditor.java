package com.googlecode.goclipse.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.source.DefaultCharacterPairMatcher;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;

import com.googlecode.goclipse.Activator;

public class GoEditor extends TextEditor {
	
	public final static String EDITOR_MATCHING_BRACKETS = "matchingBrackets";
	public final static String EDITOR_MATCHING_BRACKETS_COLOR= "matchingBracketsColor";

	private ColorManager colorManager;
	private IPropertyChangeListener changeListener;
	private DefaultCharacterPairMatcher matcher;
	
	public GoEditor() {
		super();
		setSourceViewerConfiguration(new Configuration());
		
		setKeyBindingScopes(new String[] {"com.googlecode.goclipse.editor"});
		
		changeListener = new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) { 
//				SysUtils.debug("Preference Change Event");
//				if (event.getProperty().equals(PreferenceConstants.FIELD_USE_HIGHLIGHTING)) {
//					setSourceViewerConfiguration(new Configuration(colorManager));
//					setDocumentProvider(new DocumentProvider());
//					initializeEditor();
//				}
			}
		};
		
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(changeListener);
	}
	
	private IDocumentProvider createDocumentProvider(IEditorInput input) {
        if(input instanceof IFileEditorInput){
        	return new DocumentProvider();
        } else {
            return new TextDocumentProvider();
        }
	}

	@Override
	protected final void doSetInput(IEditorInput input) throws CoreException {
	        setDocumentProvider(createDocumentProvider(input));
	        super.doSetInput(input);
	}
	 
	@Override
	protected void configureSourceViewerDecorationSupport (SourceViewerDecorationSupport support) {
		super.configureSourceViewerDecorationSupport(support);		
	 
		char[] matchChars = {'(', ')', '[', ']', '{', '}'}; //which brackets to match		
		matcher = new DefaultCharacterPairMatcher(matchChars ,
				IDocumentExtension3.DEFAULT_PARTITIONING);
		support.setCharacterPairMatcher(matcher);
		support.setMatchingCharacterPainterPreferenceKeys(EDITOR_MATCHING_BRACKETS,EDITOR_MATCHING_BRACKETS_COLOR);
		
	 
		//Enable bracket highlighting in the preference store
		IPreferenceStore store = getPreferenceStore();
		store.setDefault(EDITOR_MATCHING_BRACKETS, true);
		store.setDefault(EDITOR_MATCHING_BRACKETS_COLOR, "128,128,128");
	}

	public DefaultCharacterPairMatcher getPairMatcher() {
		return matcher;
	}

	public void dispose() {
		if (colorManager != null) {
			colorManager.dispose();
		}
		super.dispose();
	}
}