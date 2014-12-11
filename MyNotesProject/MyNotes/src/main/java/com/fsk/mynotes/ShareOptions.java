package com.fsk.mynotes;


public enum ShareOptions {

	EMAIL("E-Mail"),
	SMS ("SMS"),
	NFC ("NFC");
	
	private String mText;
	
	ShareOptions(String pText) {
		mText = pText;
	}
	
	public String getText() {
		return mText;
	}
	
	public static String[] getTextArray() {
		String[] returnValue = new String[ShareOptions.values().length];
		for (int i=0; i<ShareOptions.values().length;++i) {
			returnValue[i] = ShareOptions.values()[i].mText;
		}
		
		return returnValue;
	}
	
	public static ShareOptions getOption(int ordinal) {
		ShareOptions returnValue = null;
		for (ShareOptions option:ShareOptions.values()) {
			if (ordinal == option.ordinal()) {
				returnValue = option;
				break;
			}
		}
		return returnValue;
	}
}
