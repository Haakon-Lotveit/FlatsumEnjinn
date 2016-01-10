package test.no.haakon.flatsum.auxilliary.resource;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import no.haakon.flatsum.auxilliary.resource.ResourceConfigurationParser;

public class TestImageResourceConfigurationParser {
	static final String firstString = "første-navn-delt-med-et-mellomrom";
	static final String secondString = "andre-navn-delt-med-en-tabulator";
	static final String thirdString = "tredje-navn-delt-med-flere-mellomrom";
	static final String fourthString = "fjerde-navn-delt-med-flere-tabulatorer";
	static final String fifthString = "femte-navn-delt-med-både-mellomrom-og-tabulator";
	static final String fileName = "some-image.png";
	File testFile;
	ResourceConfigurationParser cand;
	@Before
	public void setUp() throws IOException {
		testFile = File.createTempFile("testing" + UUID.randomUUID() , "irc");
		try(PrintStream out = new PrintStream(testFile)) {
			out.println(firstString  + " "            + fileName);
			out.println(secondString + "\t"           + fileName);
			out.println(thirdString  + "    "         + fileName);
			out.println(fourthString + "\t\t"         + fileName);
			out.println(fifthString  +  "\t\t   \t  " + fileName);
		}
		cand = new ResourceConfigurationParser(testFile);
	}
	
	@After
	public void tearDown() {
		testFile.delete();
		cand = null;
	}
	
	@Test
	public void testParse() throws Throwable {
		Map<String,String> parsed = new HashMap<>();
		cand.parse().forEach(x -> parsed.put(x[0], x[1]));
		
		String actual = "";
		
		actual = parsed.get(firstString);
		assertEquals("Didn't split correctly when single space was used", fileName, actual);
		actual = parsed.get(secondString);
		assertEquals("Didn't split correctly when single tabulator was used", fileName, actual);
		actual = parsed.get(thirdString);
		assertEquals("Didn't split correctly when multiple spaces were used", fileName, actual);
		actual = parsed.get(fourthString);
		assertEquals("Didn't split correctly when multiple tabs were used", fileName, actual);
		actual = parsed.get(fifthString);		
		assertEquals("Didn't split correctly when multiple spaces and tabs were used", fileName, actual);
	}

}
