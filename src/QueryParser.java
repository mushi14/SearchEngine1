import java.util.TreeSet;

public class QueryParser {
	
	static TreeSet<String> queries = new TreeSet<String>();

//	public static TreeSet<String> parse(InvertedIndex index, Path path) throws IOException {
//		PathChecker.readQueryFiles(index, PathChecker.filesInPath(path), queries);
//		return queries;
//	}
	
	public static TreeSet<String> getQueries() {
		return queries;
	}	
}
