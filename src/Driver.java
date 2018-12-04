import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Driver {
	/**
	 * Parses the command-line arguments to build and use an in-memory search
	 * engine from files or the web.
	 *
	 * @param args the command-line arguments to parse
	 * @return 0 if everything went well
	 */
	public static void main(String[] args) {
		ArgumentMap argMap = new ArgumentMap(args);
		InvertedIndex index;
		ThreadSafeInvertedIndex threadSafeIndex;
		QueryFileParser search;

		if (argMap.hasFlag("-threads")) {
			threadSafeIndex = new ThreadSafeInvertedIndex();
			search = new MultithreadedSearch(threadSafeIndex, argMap.getThreads("-threads", 5));
			index = threadSafeIndex;
		} else {
			index = new InvertedIndex();
			search = new QuerySearch(index);
			threadSafeIndex = null;
		}

		if (argMap.hasFlag("-path")) {
			try {
				Path path = argMap.getPath("-path");
				if (argMap.flagPath("-path")) {

					if (threadSafeIndex != null) {
						MultithreadedPathChecker.filesInPath(path, argMap.getThreads("-threads", 5), threadSafeIndex);
					} else {
						PathChecker.filesInPath(path, index);
					}
				} else {
					System.out.println("There is no path provided. A valid path is needed to build the index.");
				}
			} catch (IOException | NullPointerException e) {
				System.out.println("There was an issue finding the path. A valid path is needed to build the index.");
			}
		}

		if (argMap.hasFlag("-index")) {
			try {
				Path path = argMap.getPath("-index", Paths.get("index.json"));
				index.writeIndexJSON(path);
			} catch (IOException | NullPointerException e) {
					System.out.println("File not found, index cannot be printed in json format.");
			}
		}

		if (argMap.hasFlag("-search")) {
			try {
				Path path = argMap.getPath("-search");
				if (argMap.flagPath("-search")) {
					search.stemQueryFile(path, argMap.hasFlag("-exact"));
				}
			} catch (NullPointerException e) {
				System.out.println("Unable to open the query file or directory provided. A valid query file or "
						+ "directory is needed to search.");
			}
		}

		if (argMap.hasFlag("-results")) {
			try {
				Path path = argMap.getPath("-results", Paths.get("results.json"));
				search.writeJSON(path);
			} catch (NullPointerException e) {
				System.out.println("File not found, search results cannot be printed in json format.");
			}
		}

		if (argMap.hasFlag("-locations")) {
			try {
				Path path = argMap.getPath("-locations", Paths.get("locations.json"));
				index.writeLocJSON(path);
			} catch (IOException | NullPointerException e) {
					System.out.println("File not found, locations cannot be printed in json format.");
			}
		}
	}
}