import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/** 
 * Data structure to store file paths and the word positions.
 */
public class InvertedIndex {

	/** 
	 * Stores a mapping of files to the positions the words were found in the file.
	 */
	private final TreeMap<String, TreeMap<String, TreeSet<Integer>>> index;

	/**
	 *  Initializes the index.
	 */
	public InvertedIndex() {
		index = new TreeMap<>();
	}
	
	/** 
	 * Gets the TreeMap of keys paths and values positions associated with the word
	 * 
	 * @param word word inside of the file
	 * @return TreeMap containing path and positions of word 
	 */
	public TreeMap<String, TreeSet<Integer>> get(String word) {
		TreeMap<String, TreeSet<Integer>> temp = new TreeMap<>();
		if (index.containsKey(word)) {
			temp = index.get(word);
		}
		return temp;
	}
	
	/** 
	 * Gets the TreeSet of positions associated with the path
	 * 
	 * @param word word inside of the file
	 * @param location path of the file
	 * @return TreeSet containing positions associated with the path
	 */
	public TreeSet<Integer> get(String word, String location) {
		TreeSet<Integer> temp = new TreeSet<>();
		if (index.containsKey(word)) {
			temp = index.get(word).get(location);
		}
		return temp;
	}
	
	/** 
	 * Adds the key word to with value TreeMap of key path and value positions
	 * 
	 * @param word word inside of the file
	 * @param location path of the file
	 * @param position position the word appears in the file
	 */
	public void addWord(String word, String location, int position) {
		TreeMap<String, TreeSet<Integer>> value = new TreeMap<>();
		value.put(location, new TreeSet<Integer>());
		value.get(location).add(position);
		index.put(word, value);
	}
	
	/**
	 * Adds a key path and value position to the already existing word in the TreeMap
	 * 
	 * @param word word inside of the file
	 * @param location path of the file
	 * @param position position the word appears in the file
	 */
	public void addPath(String word, String location, int position) {
		TreeSet<Integer> value = new TreeSet<>();
		value.add(position);
		index.get(word).put(location, value);
	}
	
	/**
	 * Adds the position in which the word appears in the path
	 * 
	 * @param word word inside of the file
	 * @param location path of the file
	 * @param position position the word appears in the file
	 */
	public void addPosition(String word, String location, int position) {
		index.get(word).get(location).add(position);
	}

	/*
	public void addAll(String[] words, String location)
	public void addAll(String[] words, String location, int position)
	*/
	
	/**
	 * Shows all the words in the map
	 *
	 * @return Returns a set view of all the paths
	 */
	public Set<String> wordsKeySet() {
		return index.keySet();
	}
	
	/**
	 * Shows all the paths associated with the word in the map
	 *
	 * @param word word inside of the file
	 * @return Returns a set view of all the paths
	 */
	public Set<String> locationKeySet(String word) {
		if (index.containsKey(word)) {
			return index.get(word).keySet();
		} else {
			return null;
		}
	}
	
	/**
	 * Shows all the positions associated with a path in the map
	 *
	 * @param word word inside of the file
	 * @param location path of the file
	 * @return Returns a set view of all the positions associated with the path
	 */
	public TreeSet<Integer> positionsSet(String word, String location) {
		TreeSet<Integer> temp = new TreeSet<>();
		if (index.containsKey(word)) {
			temp = index.get(word).get(location);
		}
		return temp;
	}

	/** 
	 * Number of words in the the map
	 * 
	 * @return integer size of the number of words in the map
	 */
	public int words() {
		return index.size();
	}
	
	
	// TODO If the word doesn't exist, should return 0, but here there is a NullPointerException instead
	// TODO Make similar fixes to avoid null pointers for all remaining methods
	/** 
	 * Number of paths associated with the word in the the map
	 * 
	 * @param word word inside of the file
	 * @return integer size of the number of paths associated with word in the map
	 * 
	 */
	public int locations(String word) {
		if (index.containsKey(word)) {
			return index.get(word).size();
		} else {
			return 0;
		}
	}

	/** 
	 * Number of positions associated with the path in the the map
	 * 
	 * @param word word inside of the file
	 * @param location path of the file
	 * @return integer size of the number of paths associated with word in the map
	 * 
	 */
	public int positions(String word, String location) {
		if (index.containsKey(word)) {
			return index.get(word).get(location).size();
		} else {
			return 0;
		}
	}
	
	public Map<String, Integer> totalLocations() {
		Map<String, Integer> locationsMap = new TreeMap<>();
		for (String word : wordsKeySet()) {
			for (String path : locationKeySet(word)) {
				if (!locationsMap.containsKey(path)) {
					locationsMap.put(path, positions(word, path));
				} else {
					int value = locationsMap.get(path) + positions(word, path);
					locationsMap.replace(path, value);
				}
			}
		}
		return locationsMap;
	}

	/** Checks to see if the map contains the word
	 * 
	 * @param word word inside the file
	 * @return true if map contains the word
	 * 
	 */
	public boolean containsWord(String word) {
		return index.containsKey(word);
	}
	
	public Set<String> wordStartsWith(String w) {
		Set<String> words = new TreeSet<>();
		for (String word : index.keySet()) {
			if (word.startsWith(w)) {
				words.add(word) ;
			} 
		}
		return words;
	}
	
	/** Checks to see if the word contains the path
	 * 
	 * @param word word inside the file
	 * @param location path of the file
	 * @return true if word contains the path
	 * 
	 */
	public boolean containsLocation(String word, String location) {
		if (index.containsKey(word)) {
			return index.get(word).containsKey(location);
		} else {
			return false;
		}
	}

	public void clear() {
		index.clear();
	}
	
	/** 
	 * Prints in the map
	 */
	@Override
	public String toString() {
		return this.index.toString();
	}
}