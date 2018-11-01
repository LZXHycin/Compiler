package lexical_analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadFile {
	
	public static String path = "as.txt";
	
	public static StringBuilder getCode() {
		File file = new File(path);
		InputStreamReader reader;
		StringBuilder code = new StringBuilder("");
		try {
			reader = new InputStreamReader(new FileInputStream(file));
			BufferedReader buffer = new BufferedReader(reader);
			String line;
			code = new StringBuilder("");
			while((line = buffer.readLine())!=null) {
				code.append(line + '\n');
			}
			buffer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}
	
}
