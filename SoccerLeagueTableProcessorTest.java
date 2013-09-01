import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test of SoccerLeagueTableProcessor and its function.
 * 
 * @author Henry
 *
 */
public class SoccerLeagueTableProcessorTest {

	private final String DATA_FILE_FIXTURE = "football.dat";
	private SoccerLeagueTableProcessor processor;

	@Before
	public void setUp() throws Exception {
		processor = SoccerLeagueTableProcessor.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		processor = null;
	}

	
	@Test
	public void testSmallestDiffFunction() {
		ComputeFunction func = SoccerLeagueTableProcessor.smallestDiffFunc();
		func.compute("    1. Arsenal         38    26   9   3    79  -  36    87");
		Assert.assertEquals("Arsenal", func.getResult());
		
		func.compute("    2. Liverpool       38    24   8   6    67  -  30    80");
		Assert.assertEquals("Liverpool", func.getResult());
		
		func.compute("   13. Fulham          38    10  14  14    36  -  44    44");
		Assert.assertEquals("Fulham", func.getResult());
		
		func.compute("   19. Derby           38     8   6  24    33  -  63    30");
		Assert.assertEquals("Fulham", func.getResult());   
		
	}
	
	@Test
	public void testSmallestDiffFunctionWithNoMatch() {
		ComputeFunction func = SoccerLeagueTableProcessor.smallestDiffFunc();
		func.compute("afadsfa");
		Assert.assertEquals("", func.getResult());
		   
		func.compute("   19. Derby           38     8   6  24    33  -  63");
		Assert.assertEquals("", func.getResult());
		
		func.compute("   Derby           38     8   6  24    33  -  63    30");
		Assert.assertEquals("", func.getResult());
	}
	
	@Test
	public void testFindSmallestDiffTeam() throws URISyntaxException, IOException {		
		File file = new File(getClass().getResource(DATA_FILE_FIXTURE).toURI());
		ComputeFunction smallestDiffFunc = SoccerLeagueTableProcessor.smallestDiffFunc();
		processor.processData(file, smallestDiffFunc);
		
		String expected = "Aston_Villa";
		Assert.assertEquals(expected, smallestDiffFunc.getResult());
	}
	

}
