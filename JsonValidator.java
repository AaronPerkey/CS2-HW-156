package unl.cse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A basic JSON validator. This validator only checks that the JSON file is
 * well-balanced: that all opening brackets have a corresponding closing bracket
 * and that they follow legal nesting rules.
 * 
 */
public class JsonValidator {

	public static boolean isValidJSON(String jsonString) {
		Stack<Character> s = new Stack<>();
		for(char v : jsonString.toCharArray()) {
			
			if(v == '{' || v == '(' || v == '[') {
				s.push(v);
			} else if(v == '}') {
				if(s.isEmpty() || s.pop() != '{') {
					return false;
				}
			} else if(v == ')') {
				if(s.isEmpty() || s.pop() != '(') {
					return false;
				}
			} else if(v == ']') {
				if(s.isEmpty() || s.pop() != '[') {
					return false;
				}
			}
		}
		return s.isEmpty();
	}

	public static String getFileContents(String fileName) {
		StringBuilder sb = new StringBuilder();
		Scanner s = null;
		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		while (s.hasNextLine()) {
			sb.append(s.nextLine());
		}
		s.close();
		return sb.toString();
	}

	public static void main(String[] args) {
		String jsonFileName = "data/data001.json";
		String jsonString = getFileContents(jsonFileName);
		System.out.println(isValidJSON(jsonString) ? "Valid" : "Invalid");
	}
}
