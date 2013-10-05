package com.ijuru.imibare;

import com.ijuru.kwibuka.WordListWordifier;
import com.ijuru.kwibuka.Wordifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 */
public class Context {

	protected static Map<Locale, Wordifier> wordifiers = new HashMap<Locale, Wordifier>();

	/**
	 * Starts the web app
	 * @throws Exception
	 */
	public static void startApp() throws Exception {
		loadWordList(new Locale("en"));
	}

	/**
	 * Destroys the app
	 */
	public static void destroyApp() {
	}

	/**
	 * Gets all available wordifiers
	 * @return the wordifiers
	 */
	public static Collection<Wordifier> getAllWordifiers() {
		return wordifiers.values();
	}

	/**
	 * Gets the wordifier for the given locale
	 * @param locale the locale
	 * @return the wordifier
	 */
	public static Wordifier getWordifierByLocale(Locale locale) {
		return wordifiers.get(locale);
	}

	/**
	 * Loads a word list for the given locale
	 * @param locale the locale
	 * @throws Exception
	 */
	protected static void loadWordList(Locale locale) throws Exception {
		String wordListPath = "wordlists/" + locale.getLanguage() + ".lst";

		InputStream stream = Context.class.getClassLoader().getResourceAsStream(wordListPath);
		WordListWordifier wordifier = WordListWordifier.fromWordlist(locale, new InputStreamReader(stream));

		wordifiers.put(locale, wordifier);
	}
}