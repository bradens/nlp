package oerich.nlputils.dictionary;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import oerich.nlputils.io.IOUtil;

import org.apache.commons.lang.Validate;
import org.apache.lucene.analysis.hunspell.HunspellDictionary;
import org.apache.lucene.util.Version;

/**
 * Adapter to {@link org.apache.lucene.analysis.hunspell.HunspellDictionary}.
 * 
 * @author Philipp Förmer
 * @see org.apache.lucene.analysis.hunspell.HunspellDictionary
 * @see http://en.wikipedia.org/wiki/Hunspell
 * @see http://wiki.services.openoffice.org/wiki/Dictionaries
 */
public class LuceneHunspellDictionaryAdapter implements Dictionary {

	private final HunspellDictionary adaptedDictionary;

	/**
	 * @param dictionaryResourcePath
	 *            non null resource path of the hunspell dictionary file
	 * @param affixResourcePath
	 *            non null resource path of the hunspell affix file
	 * @throws IOException
	 *             in case of an io error reading from dictionary or affix, or
	 *             the resources could not be located
	 * @throws ParseException
	 *             if the dictionary or affix could not be parsed
	 */
	public LuceneHunspellDictionaryAdapter(final String dictionaryResourcePath,
			final String affixResourcePath) throws IOException, ParseException {
		this(IOUtil.getResourceAsStream(dictionaryResourcePath), IOUtil
				.getResourceAsStream(affixResourcePath));
	}

	/**
	 * @param dictionaryResourcePath
	 *            non null resource path of the hunspell dictionary file
	 * @param affixResourcePath
	 *            non null resource path of the hunspell affix file
	 * @param caseSensitive
	 *            whether the dictionary should be case sensitive or not
	 * @throws IOException
	 *             in case of an io error reading from dictionary or affix, or
	 *             the resources could not be located
	 * @throws ParseException
	 *             if the dictionary or affix could not be parsed
	 */
	public LuceneHunspellDictionaryAdapter(final String dictionaryResourcePath,
			final String affixResourcePath, final boolean caseSensitive)
			throws IOException, ParseException {
		this(IOUtil.getResourceAsStream(dictionaryResourcePath), IOUtil
				.getResourceAsStream(affixResourcePath), caseSensitive);
	}

	/**
	 * 
	 * @param dictionary
	 *            non null input stream of the hunspell dictionary file
	 * @param affix
	 *            non null input stream of the hunspell affix file
	 * @throws IOException
	 *             in case of an io error reading from dictionary or affix
	 * @throws ParseException
	 *             if the dictionary or affix could not be parsed
	 */
	public LuceneHunspellDictionaryAdapter(final InputStream dictionary,
			final InputStream affix) throws IOException, ParseException {
		this(dictionary, affix, false);
	}

	/**
	 * 
	 * @param dictionary
	 *            non null input stream of the hunspell dictionary file
	 * @param affix
	 *            non null input stream of the hunspell affix file
	 * @param caseSensitive
	 *            whether the dictionary should be case sensitive or not
	 * @throws IOException
	 *             in case of an io error reading from dictionary or affix
	 * @throws ParseException
	 *             if the dictionary or affix could not be parsed
	 */
	public LuceneHunspellDictionaryAdapter(final InputStream dictionary,
			final InputStream affix, final boolean caseSensitive)
			throws IOException, ParseException {
		Validate.notNull(dictionary);
		Validate.notNull(affix);
		adaptedDictionary = createHunspellDictionary(dictionary, affix,
				caseSensitive);
	}

	protected HunspellDictionary createHunspellDictionary(
			final InputStream dictionary, final InputStream affix,
			final boolean caseSensitive) throws IOException, ParseException {
		return new HunspellDictionary(
				IOUtil.decorateAsBufferedInputStream(affix),
				IOUtil.decorateAsBufferedInputStream(dictionary),
				Version.LUCENE_36, !caseSensitive);
	}

	@Override
	public boolean isCorrectSpelled(final String word) {
		return word != null
				&& adaptedDictionary.lookupWord(word.toCharArray(), 0,
						word.length()) != null;
	}

}
