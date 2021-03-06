package oerich.nlputils.tokenize.sentenceboundarydetection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import oerich.nlputils.TestEnvironmentConstants;

import org.junit.Test;

/**
 * 
 * @author Philipp Förmer, philipp at cathaldar dot net
 * 
 */
public class GermanMaximumEntropySentenceBoundaryDetectorTest {

	private static final String MODEL_FILE_PATH = TestEnvironmentConstants.RESOURCE_PATH_NAME
			+ "oerich/nlputils/resource/sentenceModel.bin.gz";

	public GermanMaximumEntropySentenceBoundaryDetectorTest() {

	}

	@Test
	public void testConstructor() {
		try {
			GermanMaximumEntropySentenceBoundaryDetector detector = new GermanMaximumEntropySentenceBoundaryDetector(
					"");
			assertNotNull(detector);
			fail("shouldn't it?");
		} catch (IOException exception) {
			assertTrue(true);
		}
	}

	@Test
	public void testDetectSentenceBoundaries() throws IOException {
		String sentences = "Hallo Test, was testest du gerade? Ok. Du bist ein Test";
		GermanMaximumEntropySentenceBoundaryDetector detector = new GermanMaximumEntropySentenceBoundaryDetector(
				MODEL_FILE_PATH);
		String[] sentenceList = detector.tokenize(sentences);
		assertNotNull(sentenceList);

		StringBuffer buffer = new StringBuffer();

		for (String str : sentenceList) {
			buffer.append(str);
			// System.out.println("+" + str);
		}

		assertEquals(sentences, buffer.toString());
		assertNotNull(detector.tokenize(""));

		try {
			detector.tokenize(null);
			fail("Text must not be null.");
		} catch (IllegalArgumentException exception) {
			assertTrue(true);
		}

	}

}
