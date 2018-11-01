package main;

import java.util.ArrayList;

import grammar_analysis.Error;
import grammar_analysis.GrammarAnalyzer;
import lexical_analysis.LexicalAnalyzer;
import lexical_analysis.Message;
import lexical_analysis.ReadFile;

public class Main {

	public static void main(String[] args) {

		//�ʷ���������ȡ��Ԫ��
		LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(ReadFile.getCode().toString());
		ArrayList<Message> result = lexicalAnalyzer.getResult();

		//�﷨���������ݶ�Ԫ����з���
		GrammarAnalyzer grammarAnalyzer = new GrammarAnalyzer(result);
		grammarAnalyzer.analyse();
		System.out.println("\n---------------------�ָ���--------------------");
		ArrayList<Error> errorList = grammarAnalyzer.getErrors();
		for (int i=0; i<errorList.size(); ++i) {
			errorList.get(i).print();
		}
	}

}
