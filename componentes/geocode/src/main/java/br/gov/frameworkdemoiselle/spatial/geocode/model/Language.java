package br.gov.frameworkdemoiselle.spatial.geocode.model;

import java.util.HashMap;
import java.util.Map;

public enum Language  {
    
    Arabic ("ar"),
    Bulgarian ("bg"),
    Catalan ("ca"),
    ChineseSimplified ("zh-CN"),
    ChineseTraditional ("zh-TW"),
    Croation ("hr"),
    Czech ("cs"),
    Danish ("da"),
    Dutch ("nl"),
    English ("en"),
    Estonian ("et"),
    Finnish ("fi"),
    French ("fr"),
    German ("de"),
    Greek ("el"),
    Hebrew ("iw"),
    Hungarian ("hu"),
    Icelandic ("is"),
    Indonesian ("id"),
    Italian ("it"),
    Japanese ("ja"),
    Korean ("ko"),
    Latvian ("lv"),
    Lithuanian ("lt"),
    Norwegian ("no"),
    Polish ("pl"),
    Portuguese ("pt"),
    Portuguese_Brazil ("pt_BR"),
    Romanian ("ro"),
    Russian ("ru"),
    Serbian ("sr"),
    Slovak ("sk"),
    Slovenian ("sl"),
    Spanish ("es"),
    Swedish ("sv"),
    Turkish ("tr");
    
	/** The Constant stringToEnum. */
	private static final Map<String, Language> stringToEnum = new HashMap<String, Language>();

	static { // Initialize map from constant name to enum constant
		for (Language op : values()) {
			stringToEnum.put(op.value(), op);
		}
	}

	/** The value. */
	private final String value;

	/**
	 * Instantiates a new image size.
	 * 
	 * @param value
	 *            the value
	 */
	Language(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	/**
	 * From value.
	 * 
	 * @param value
	 *            the value
	 * 
	 * @return the image size
	 */
	public static Language fromValue(String value) {
		return stringToEnum.get(value);
	}
}