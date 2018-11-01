package grammar_analysis;

import java.util.ArrayList;

import lexical_analysis.LexicalAnalyzer;
import lexical_analysis.Message;
import lexical_analysis.ReadFile;

public class GrammarAnalyzer {

	private ArrayList<Message> result;
	private ArrayList<Error> errorList;
	private Message curMessage = null;
	private int index = 0;
	private int syn = -1;
	private String token = "";
	private int line = 1;
	private boolean isRight = true;
	private int size;
	private boolean canPrint = false;

	public GrammarAnalyzer(ArrayList<Message> result) {
		this.result = result;
		size = result.size();
		errorList =new ArrayList<>();
//		for (int i = 0; i < result.size(); i++) {
//			result.get(i).print();
//		}
	}

	/**
	 * ��ȡ��һ����
	 */
	private boolean catchNextWord() {
		if (index < size) {
//			System.out.print(token + " " + syn + " ");
			if (canPrint) {
				System.out.print(token + " ");
			}
			canPrint = true;
			curMessage = result.get(index++);
			syn = curMessage.getSyn();
			token = curMessage.getToken();
			if (line != curMessage.getLine()) {
				for (int i = line; i < curMessage.getLine(); i++) {
					System.out.println();
				}
				line = curMessage.getLine();
			}
			return true;
		}else {
			return false;
		}
	}

	/**
	 * ����
	 */
	public void analyse() {
		catchNextWord();
		if (syn != 1) {
			System.out.print("ȱbegin ");
			errorList.add(new Error("ȱbegin", curMessage.getLine()));
			isRight = false;
		}else {
			catchNextWord();
		}
		sentences();
//		System.out.println(syn);
		System.out.print(token + " ");
		if (syn != 6) {
			System.out.print("ȱend ");
			errorList.add(new Error("ȱend", curMessage.getLine()));
			isRight = false;
		}else {
			catchNextWord();
		}
		if (syn != 0) {
			System.out.print("ȱ#������ ");
			errorList.add(new Error("ȱ#������", curMessage.getLine()));
			isRight = false;
		}
		if (isRight) {
			System.out.println();
			System.out.println("success");
		}
	}

	/**
	 * ��䴮
	 */
	public void sentences() {
		statement();
//		System.out.print(" \"���ڵ�syn=" + syn + "\" ");
		while (syn == 26) {
			catchNextWord();
			statement();
		}
		if (syn != 6 && syn != 0 && index < size) {
			System.out.print("ȱ; ");
			errorList.add(new Error("ȱ;", curMessage.getLine()));
			isRight = false;
//			System.out.print(" \"���ڵ�syn=" + syn + "\" ");
			sentences();
		}
	}

	/**
	 * ���
	 */
	private void statement() {
		if (syn == 2) {
			catchNextWord();
			condition();
		}else if (syn == 4) {
			catchNextWord();
			loop();
		}else if (syn == 10) {
			catchNextWord();
			assignment();
		}else {
			System.out.print("δ����� ");
			errorList.add(new Error("δ�����", curMessage.getLine()));
			isRight = false;
			if (syn != 6) {
				catchNextWord();
				statement();
			}
		}
	}

	/**
	 * ��ֵ���
	 */
	private void assignment() {
//		if (syn != 10) {
//			System.out.print("ȱ�ٱ�ʶ�� ");
//			isRight = false;
//		}else {
//			catchNextWord();
//		}
		if (syn != 18) {
			System.out.print("ȱ��:= ");
			errorList.add(new Error("ȱ:=", curMessage.getLine()));
			isRight = false;
		}else {
			catchNextWord();
		}
		expression();
	}

	/**
	 *�������
	 */
	private void condition() {
		expression();
		if (syn < 20 || syn > 25) {
			System.out.print("ȱ�ٹ�ϵ���ӷ� ");
			errorList.add(new Error("ȱ��ϵ���ӷ�", curMessage.getLine()));
			isRight = false;
		}else {
			catchNextWord();
		}
		expression();
		if (syn != 3) {
			System.out.print("ȱ��then ");
			errorList.add(new Error("ȱthen", curMessage.getLine()));
			isRight = false;
		}else {
			catchNextWord();
		}
		statement();
	}

	/**
	 * ѭ�����
	 */
	private void loop() {
		expression();
		if (syn < 20 || syn > 25) {
			System.out.print("ȱ�ٹ�ϵ���ӷ� ");
			errorList.add(new Error("ȱ��ϵ���ӷ�", curMessage.getLine()));
			isRight = false;
		}else {
			catchNextWord();
		}
		expression();
		if (syn != 5) {
			System.out.print("ȱ��do ");
			errorList.add(new Error("ȱdo", curMessage.getLine()));
			isRight = false;
		}else {
			catchNextWord();
		}
		statement();
	}

	/**
	 * ���ʽ
	 */
	private void expression() {
		term();
		while (syn == 13 || syn == 14) {
			catchNextWord();
			term();
		}
	}

	/**
	 * ��
	 */
	private void term() {
		factor();
		while (syn == 15 || syn ==16) {
			catchNextWord();
			factor();
		}
	}

	/**
	 * ����
	 */
	private void factor() {
//		System.out.print(" ��factor��syn=" + syn +" ");
		if (syn == 10 || syn == 11) {
			catchNextWord();
		}else if (syn == 27) {
			catchNextWord();
			expression();
			if (syn != 28) {
				System.out.print("ȱ')' ");
				errorList.add(new Error("ȱ)", curMessage.getLine()));
				isRight = false;
			}else {
				catchNextWord();
			}
		}else if (syn == 3 || syn == 5 || syn >=20 && syn <= 25) {

		}else {
			System.out.print("���ʽ���� ");
			errorList.add(new Error("���ʽ����", curMessage.getLine()));
			isRight = false;
		}
	}
	
	public boolean getIsRight() {
		return isRight;
	}
	
	public ArrayList<Error> getErrors() {
		return errorList;
	}

}
