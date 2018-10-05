import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.TreeMap;
import java.util.TreeSet;

public class TreeJSONWriter {

	/**
	 * Writes several tab <code>\t</code> symbols using the provided
	 * {@link Writer}.
	 *
	 * @param times  the number of times to write the tab symbol
	 * @param writer the writer to use
	 * @throws IOException if the writer encounters any issues
	 */
	public static void indent(int times, Writer writer) throws IOException {
		for (int i = 0; i < times; i++) {
			writer.write('\t');
		}
	}

	/**
	 * Writes the element surrounded by quotes using the provided {@link Writer}.
	 *
	 * @param element the element to quote
	 * @param writer  the writer to use
	 * @throws IOException if the writer encounters any issues
	 */
	public static void quote(String element, Writer writer) throws IOException {
		writer.write('"');
		writer.write(element);
		writer.write('"');
	}

	/**
	 * Writes the set of elements formatted as a pretty JSON array of numbers to
	 * the specified file.
	 *
	 * @param elements the elements to convert to JSON
	 * @param path     the path to the file write to output
	 * @throws IOException if the writer encounters any issues
	 */
	public static void asArray(TreeSet<Integer> elements, Path path) {
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			asArray(elements, writer, 0);
		} catch (IOException | NullPointerException e) {
			System.out.println("There was an issue finding the direcotry or file: " + path);
		}
	}

	/**
	 * Writes the set of elements formatted as a pretty JSON array of numbers
	 * using the provided {@link Writer} and indentation level.
	 *
	 * @param elements the elements to convert to JSON
	 * @param writer   the writer to use
	 * @param level    the initial indentation level
	 * @throws IOException if the writer encounters any issues
	 *
	 * @see Writer#write(String)
	 * @see Writer#append(CharSequence)
	 *
	 * @see System#lineSeparator()
	 *
	 * @see #indent(int, Writer)
	 */
	public static void asArray(TreeSet<Integer> elements, Writer writer, int level) {
		try {
			writer.write('[' + System.lineSeparator());
			
			if (!elements.isEmpty()) {
				int size = elements.size();
				int count = 0;
				for (Integer elem : elements) {
					count++;
					if (count != size) {
						indent(level + 1, writer);
						writer.write(elem.toString() + ',' + System.lineSeparator());
					} else {
						indent(level + 1, writer);
						writer.write(elem.toString() + System.lineSeparator());
					}
				}
			}
			indent(level, writer);
			writer.write(']');
		} catch (IOException | NullPointerException e) {
			System.out.println("There was an issue finding the direcotry or file: " + writer);
		}
	}

	/**
	 * Writes the map of elements formatted as a pretty JSON object to
	 * the specified file.
	 *
	 * @param elements the elements to convert to JSON
	 * @param path     the path to the file write to output
	 * @throws IOException if the writer encounters any issues
	 *
	 */
	public static void asObject(TreeMap<String, TreeSet<Integer>> elements, Path path) {
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			asObject(elements, writer, 0);
		} catch (IOException | NullPointerException e) {
			System.out.println("There was an issue finding the direcotry or file: " + path);
		}
	}

	/**
	 * Writes the map of elements as a pretty JSON object using the provided
	 * {@link Writer} and indentation level.
	 *
	 * @param elements the elements to convert to JSON
	 * @param writer   the writer to use
	 * @param level    the initial indentation level
	 * @throws IOException if the writer encounters any issues
	 *
	 * @see Writer#write(String)
	 * @see Writer#append(CharSequence)
	 *
	 * @see System#lineSeparator()
	 *
	 * @see #indent(int, Writer)
	 * @see #quote(String, Writer)
	 */
	public static void asObject(TreeMap<String, TreeSet<Integer>> elements, Writer writer, int level) {
		try {
			writer.write("{" + System.lineSeparator());
			int size = elements.keySet().size();
			int count = 0;

			for (String key : elements.keySet()) {
				count++;
				if (count != size) {
					indent(level + 1, writer);
					quote(key, writer);
					writer.write(": ");
					asArray(elements.get(key), writer, level + 1);
					writer.write("," + System.lineSeparator());
				} else {
					indent(level + 1, writer);
					quote(key, writer);
					writer.write(": ");
					asArray(elements.get(key), writer, level + 1);
					writer.write(System.lineSeparator());
				}	
			}
			indent(level, writer);
			writer.write("}");
		} catch (IOException | NullPointerException e) {
			System.out.println("There was an issue finding the direcotry or file: " + writer);
		}
	}
	
	/**
	 * Writes the map of String keys and WordIndex values as a pretty JSON object using the provided
	 * {@link Writer} and indentation level.
	 *
	 * @param index the elements to convert to JSON
	 * @param writer   the writer to use
	 * @param level    the initial indentation level
	 * @throws IOException if the writer encounters any issues
	 *
	 * @see Writer#write(String)
	 * @see Writer#append(CharSequence)
	 *
	 * @see System#lineSeparator()
	 *
	 * @see #indent(int, Writer)
	 * @see #quote(String, Writer)
	 */
	public static void asInvertedIndex(InvertedIndex index, Path path) {
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {

			int level = 0;
			writer.write("{" + System.lineSeparator());
			int size = index.words();
			int count = 0;
			
			for (String key : index.wordsKeySet()) {
				count++;
				if (count != size) {
					indent(level + 1, writer);
					quote(key, writer);
					writer.write(": ");
					asObject(index.get(key), writer, level + 1);
					writer.write("," + System.lineSeparator());
				} else {
					indent(level + 1, writer);
					quote(key, writer);
					writer.write(": ");
					asObject(index.get(key), writer, level + 1);
					writer.write(System.lineSeparator());
				}
			}
			writer.write("}" + System.lineSeparator());
		} catch (IOException | NullPointerException e) {
			System.out.println("There was an issue finding the direcotry or file: " + path);
		}
	}
	
	public static void asLocations(InvertedIndex index, Path path) {
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)){
			
			int level = 0;
			writer.write("{" + System.lineSeparator());
			int size = index.totalLocations().size();
			int count = 0;
			
			for (String location : index.totalLocations().keySet()) {
				count++;
				if (count != size) {
					indent(level + 1, writer);
					quote(location, writer);
					writer.write(": " + index.totalLocations().get(location) + ", " + System.lineSeparator());
				} else {
					indent(level + 1, writer);
					quote(location, writer);
					writer.write(": " + index.totalLocations().get(location) + System.lineSeparator());
				}
			}
			writer.write("}");
		} catch (IOException | NullPointerException e) {
			System.out.println("There was an issue finding the direcotry or file: " + path);
		}
	}
	
	

	/* public static void asSearchResult(InvertedIndex elements, Path path, Set<String> queries, 
			Map<Double, List<String>> scores) {
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			asSearchResult(elements, queries, scores, writer, 0);
		} catch (IOException | NullPointerException e) {
			System.out.println("There was an issue finding the direcotry or file: " + path);
		}
	} */
	
	/* public static void asSearchResult(InvertedIndex elements, Map<Double, List<String>> scores,
			Writer writer, int level) {
		
		try {
			for (Double score : scores.keySet()) {
				int size = scores.get(score).size();
				int count = 0;
				for (String location : scores.get(score)) {
					count++;
					if (count != size) {
						indent(level, writer);
						writer.write("{" + System.lineSeparator());
						indent(level + 1, writer);
						quote("where", writer);
						writer.write(": ");
						quote(location, writer);
						writer.write("," + System.lineSeparator());
						indent(level + 1, writer);
						quote("count", writer);
						writer.write(": ");
						writer.write(elements.totalLocations().get(location) + "," + System.lineSeparator());
						indent(level + 1, writer);
						quote("score", writer);
						writer.write(": ");
						writer.write(String.valueOf(score) + System.lineSeparator());
						indent(level, writer);
						writer.write("}," + System.lineSeparator());
					} else {
						indent(level, writer);
						writer.write("{" + System.lineSeparator());
						indent(level + 1, writer);
						quote("where", writer);
						writer.write(": ");
						quote(location, writer);
						writer.write("," + System.lineSeparator());
						indent(level + 1, writer);
						quote("count", writer);
						writer.write(": ");
						writer.write(Search.get(location) + "," + System.lineSeparator());
						indent(level + 1, writer);
						quote("score", writer);
						writer.write(": ");
						writer.write(String.valueOf(score) + System.lineSeparator());
						indent(level, writer);
						writer.write("}" + System.lineSeparator());
					}
				}
			}
		} catch (IOException | NullPointerException e) {
			System.out.println("There was an issue finding the direcotry or file: " + writer);
		}
	} */
	
	
	
	/* public static void asSearchResult(InvertedIndex elements,Set<String> queries, 
			Map<Double, List<String>> scores, Writer writer, int level) {
		try {

			writer.write("[" + System.lineSeparator());
			indent(level + 1, writer);
			writer.write("{" + System.lineSeparator());
			indent(level + 2, writer);
			quote("queries", writer);
			writer.write(": ");
			
			
			String temp = "";
			int count = queries.size();
			for (String query : queries) {
				count++;
				if (count != queries.size()) {
					temp += query + " ";
				} else {
					temp += query;
				}
			}
			quote(temp, writer);
			
			
			writer.write("," + System.lineSeparator());
			indent(level + 2, writer);
			quote("results", writer);
			writer.write(": [" + System.lineSeparator());
			
			asSearchResult(elements, scores, writer, level + 3);
			
			indent(level + 2, writer);
			writer.write("]" + System.lineSeparator());
			indent(level + 1, writer);
			writer.write("}" + System.lineSeparator());
			indent(level, writer);
			writer.write("]");
		} catch (IOException | NullPointerException e) {
			System.out.println("There was an issue finding the direcotry or file: " + writer);
		}
	} */
}
