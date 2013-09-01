import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Soccer League Table data processor supporting extensible ComputeFunction.
 * 
 * @author Henry
 * 
 */
public class SoccerLeagueTableProcessor {

	private static final SoccerLeagueTableProcessor instance = new SoccerLeagueTableProcessor();

	public static SoccerLeagueTableProcessor getInstance() {
		return instance;
	}

	private SoccerLeagueTableProcessor() {

	}

	/**
	 * <pre>
	 * A implementation of ComputeFunction for finding the team with the smallest difference 
	 * of For and Against scores.
	 * 
	 * If more than one team are found having the same difference, the first
	 * match would be returned.
	 * 
	 * The computation result can be retrieved through ComputeFunction.getResult()
	 * containing the team name. 
	 * An empty string would be returned if no matching data is found from the input file.
	 * </pre>
	 */
	public static final ComputeFunction smallestDiffFunc() {
		ComputeFunction func = new ComputeFunction() {
			private static final String CORE_DATA_REG_EXP = ".*(\\S+)\\s+(\\S+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\S+)\\s+(\\d+)\\s+(\\d+)";
			private final Pattern pattern = Pattern.compile(CORE_DATA_REG_EXP);
			
			private static final int AGAINST_SCORE_INDEX = 9;
			private static final int FOR_SCORE_INDEX = 7;
			private static final int TEAM_NAME_INDEX = 2;

			Integer lastSmallestDiff = null;
			String smallDiffTeam = "";

			@Override
			public void compute(String data) {
				Matcher matcher = pattern.matcher(data);
				if (matcher.matches()) {
					String teamName = matcher.group(TEAM_NAME_INDEX);
					int forScore = Integer.valueOf(matcher.group(FOR_SCORE_INDEX));
					int againstScore = Integer.valueOf(matcher.group(AGAINST_SCORE_INDEX));
					int diff = Math.abs(forScore - againstScore);
					
					if (lastSmallestDiff == null || diff < lastSmallestDiff.intValue()) {
						smallDiffTeam = teamName;
						lastSmallestDiff = diff;
					}
				}

			}

			@Override
			public String getResult() {
				return smallDiffTeam;
			}

		};

		return func;
	}

	/**
	 * <pre>
	 * Process the data using the specified ComputeFunction.
	 * 
	 * The result of it can be obtained through ComputeFunction.getResult();
	 * </pre>
	 * 
	 * @param file
	 *            The file containing the data to be processed.
	 * @param func
	 *            The ComputeFunction for use inside the data iteration.
	 * @throws IOException
	 */
	public void processData(File file, ComputeFunction func) throws IOException {
		try (BufferedReader bufReader = new BufferedReader(new FileReader(file))) {
			String line = null;

			while ((line = bufReader.readLine()) != null) {
				func.compute(line);
			}

		} catch (IOException ioe) {
			throw ioe;
		}

	}

}
