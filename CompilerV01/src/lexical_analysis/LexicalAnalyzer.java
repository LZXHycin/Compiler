package lexical_analysis;

import java.util.ArrayList;

public class LexicalAnalyzer {

	private ArrayList<Message> result = new ArrayList<Message>();

	public LexicalAnalyzer(String text){
		Splitter splitter = new Splitter(text);
		splitter.handleText();
		ArrayList<String> strings = splitter.getResult();
		ArrayList<Integer> line = splitter.getLine();
		for (int i = 0; i < strings.size(); i++) {
			String string = strings.get(i);
			result.add(new Message(string, matchValue(string),line.get(i)));

		}
	}

	public ArrayList<Message> getResult() {
		return result;
	}

	private int matchValue(String string) {
		if (string.matches("\\d+")) {
			return 11;
		}
		switch (string) {
		case "begin":
			return 1;
		case "if":
			return 2;
		case "then":
			return 3;
		case "while":
			return 4;
		case "do":
			return 5;
		case "end":
			return 6;
		case "+":
			return 13;
		case "-":
			return 14;
		case "*":
			return 15;
		case "/":
			return 16;
		case ":":
			return 17;
		case ":=":
			return 18;
		case "<":
			return 20;
		case "<>":
			return 21;
		case "<=":
			return 22;
		case ">":
			return 23;
		case ">=":
			return 24;
		case "=":
			return 25;
		case ";":
			return 26;
		case "(":
			return 27;
		case ")":
			return 28;
		case "#":
			return 0;
		default:
			//×Ö·û´®
			return 10;
		}
	}

	public void print() {
		for (int i = 0; i < result.size(); i++) {
			result.get(i).print();
		}
	}

}
