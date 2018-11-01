package main;

import java.util.ArrayList;

import grammar_analysis.Error;
import grammar_analysis.GrammarAnalyzer;
import lexical_analysis.LexicalAnalyzer;
import lexical_analysis.Message;
import lexical_analysis.ReadFile;

public class Main {

	public static void main(String[] args) {

		//词法分析：获取二元组
		LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(ReadFile.getCode().toString());
		ArrayList<Message> result = lexicalAnalyzer.getResult();

		//语法分析：根据二元组进行分析
		GrammarAnalyzer grammarAnalyzer = new GrammarAnalyzer(result);
		grammarAnalyzer.analyse();
		System.out.println("\n---------------------分隔符--------------------");
		ArrayList<Error> errorList = grammarAnalyzer.getErrors();
		for (int i=0; i<errorList.size(); ++i) {
			errorList.get(i).print();
		}
	}

}
