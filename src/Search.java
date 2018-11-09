import java.text.DecimalFormat;

public class Search implements Comparable<Search> {

	private DecimalFormat FORMATTER; // TODO private static final?
	private final String location;
	private int totalMatches;
	private final int totalWords;
	private double rawScore;
	private String score; // TODO Remove

	/**
	 * Constructor for the Query class
	 * @param loc location that the query word is found in
	 * @param matches number matches in the location
	 * @param words total number of words in the location
	 * @param rs raw score of the location
	 * @param sc rounded score of the location
	 */
	public Search(String loc, int matches, int words) {
		FORMATTER = new DecimalFormat("0.000000"); 
		this.location = loc;
		this.totalMatches = matches;
		this.totalWords = words;
		
		// TODO this.rawScore = ...
	}

	/**
	 * Gets the location
	 * @return location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Gets the total matches of the location
	 * @return total number of matches in the location
	 */
	public int getMatches() {
		return totalMatches;
	}

	/**
	 * Gets the total words of the location
	 * @return total words in the location
	 */
	public int getWords() {
		return totalWords;
	}

	/**
	 * TODO
	 * @param matches
	 */
	public void calculate(int matches) {
		// TODO this.totalMatches += matches;
		this.totalMatches = matches;
		this.rawScore = Double.valueOf(this.totalMatches) / Double.valueOf(this.totalWords);
	}

	/**
	 * Gets the raw score of the results in the location
	 * @return raw score
	 */
	public double getRawScore() {
		return rawScore;
	}

	/**
	 * Gets the rounded score of the results in the location
	 * @return rounded score
	 */
	public String getScore() {
		this.score = FORMATTER.format(this.rawScore);
		return score;
		
		// TODO return FORMATTER.format(this.rawScore);
	}

	/**
	 * Compares the search results by their scores. If scores are the same, then compares by the total number of words
	 * in the file. If that's the same as well, sorts alphabetically 
	 */
	@Override
	public int compareTo(Search o) {
		if (this.getRawScore() > o.getRawScore()) {
			return -1;
		} else if (this.getRawScore() < o.getRawScore()) {
			return 1;
		} else {
			if (this.getWords() > o.getWords()) {
				return -1;
			} else if (this.getWords() < o.getWords()) {
				return 1;
			} else {
				return this.getLocation().compareToIgnoreCase(o.getLocation());
			}
		}
		
		/* TODO
		int result = Double.compare(this.rawScore, o.rawScore);
		
		if (result == 0) {
			compare by ints 
			
			if ints are 0
				compare by location
		}
		
		return result
		*/
	}

	/**
	 * Overridden toString method
	 */
	@Override
	public String toString() {
		return "Location: " + location + " Score: " + score + " Matches: " + totalMatches + " Words: " + totalWords;
	}
}