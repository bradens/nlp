package test.java.oerich.nlputils.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import oerich.nlputils.text.DefaultStopWordFilter;
import oerich.nlputils.text.StopWordFilterFactory;
import oerich.nlputils.tokenize.DefaultWordTokenizer;

import org.junit.Test;


public class StopWordFilterFactoryTest {

	@Test
	public void testCreateInstance() {
		DefaultStopWordFilter filter = (DefaultStopWordFilter) StopWordFilterFactory
				.createStopWordFilter("./stopwords.txt");
		DefaultWordTokenizer tokenizer = (DefaultWordTokenizer) StopWordFilterFactory
				.createTokenizer("./stopsign2.txt");

		assertNotNull(filter);
		assertNotNull(tokenizer);
		assertNotNull(filter.getStopWords());
		assertNotNull(tokenizer.getStopSigns());
		assertTrue(0 < filter.getStopWords().length);
		assertTrue(0 < tokenizer.getStopSigns().length);
		String[] filterStopWords = filter
				.filterStopWords(tokenizer
						.tokenize("Dieser Satz (obwohl mit Stopwords) wird gefiltert, Zeichen entfertn und so weiter..."));
		// System.out.println(Arrays.toString(filterStopWords));
		assertEquals(7, filterStopWords.length);
	}

}