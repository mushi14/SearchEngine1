import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * TODO Fill in your own comments!
 */
public class Driver {

	/**
	 * Parses the command-line arguments to build and use an in-memory search
	 * engine from files or the web.
	 *
	 * @param args the command-line arguments to parse
	 * @return 0 if everything went well
	 */
	public static void main(String[] args) {
		// TODO Fill in		
		var index = new HashMap<String, HashMap<String, HashSet<Integer>>>();
		
		FileRead file_read = new FileRead();

		Path p = file_read.getPath("/Users/mushahidhassan/Desktop/CS 212/ProjectTests/project-tests/text/simple/position.teXt");
		
		file_read.read(p, index);
		
//		System.out.println(index);
		
		
//		System.out.println(Arrays.toString(args));
	}

}
