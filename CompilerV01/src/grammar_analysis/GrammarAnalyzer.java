package grammar_analysis;

import java.util.ArrayList;

import lexical_analysis.LexicalAnalyzer;
import lexical_analysis.Message;
import lexical_analysis.ReadFile;

public class GrammarAnalyzer {

	private ArrayList<Message> result;
	private Message curMessage = null;
	private int index = 0;
	private int syn = -1;
	private String token = "";
	private int line = 1;
	private boolean isRight = true;
	private int size;

	public GrammarAnalyzer(ArrayList<Message> result) {
		this.result = result;
		size = result.size();
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
			System.out.print(token + " ");
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
			isRight = false;
		}else {
			catchNextWord();
		}
		sentences();
//		System.out.println(syn);
		if (syn != 6) {
			System.out.print("ȱend ");
			isRight = false;
		}else {
			catchNextWord();
		}
		if (syn != 0) {
			System.out.print("ȱ#������ ");
			isRight = false;
		}
		System.out.print(token + " ");
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
		while (syn == 26) {
			catchNextWord();
			statement();
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
			System.out.print("δ����� " + syn + " ");
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
			isRight = false;
		}else {
			catchNextWord();
		}
		expression();
		if (syn != 3) {
			System.out.print("ȱ��then ");
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
			isRight = false;
		}else {
			catchNextWord();
		}
		expression();
		if (syn != 5) {
			System.out.print("ȱ��do ");
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
				isRight = false;
			}else {
				catchNextWord();
			}
		}else if (syn == 3 || syn == 5 || syn >=20 && syn <= 25) {

		}else {
			System.out.print("���ʽ���� ");
			isRight = false;
		}
	}

//	public void analyse() {
//		catchNextWord();
//		lrparser();
//	}
//
//	private void lrparser() {
//		// begin��ͷ
//		if (syn == 1) {
//			catchNextWord();
//			//����begin����
//			yucu();
//			//������end����
//			if (syn == 6) {
//				catchNextWord();
//				if (syn == 0 && isRight) {
//					System.out.println("success ");
//				}
//			} else {
//				if (isRight) {
//					System.out.println("ȱ'end' ");
//					isRight = false;
//				}
//			}
//		} else {
//			System.out.println("ȱ'begin' ");
//			isRight = false;
//		}
//	}
//
//	private void yucu() {
//		statement();
//		while (syn == 26) {
//			catchNextWord();
//			statement();
//		}
//	}
//
//	private void statement() {
//		if (syn == 10) {		//��ֵ���
//			catchNextWord();
//			if (syn == 18) {
//				catchNextWord();
//				expression();
//			}else {
//				System.out.println("��ֵ�Ŵ��� ");
//				isRight = false;
//			}
//		}else if (syn == 2) {		//if..then..���
//			catchNextWord();
//			expression();
//			if (syn>=20 && syn<=25) {
//				catchNextWord();
//				expression();
//				if (syn==3) {
//					catchNextWord();
//					statement();
//				}else {
//					System.out.println("ȱ��then ");
//					isRight = false;
//				}
//			}else {
//				System.out.println("ȱ�ٹ�ϵ���ӷ� ");
//				isRight = false;
//			}
//		}else if (syn == 4) {		//while..do..���
//			catchNextWord();
//			expression();
//			if (syn >= 20 && syn <= 25) {
//				catchNextWord();
//				expression();
//				if (syn == 5) {
//					catchNextWord();
//					statement();
//				}else {
//					System.out.println("ȱ��do ");
//					isRight = false;
//				}
//			}else {
//				System.out.println("ȱ�ٹ�ϵ���ӷ� ");
//				isRight = false;
//			}
//		}else {
//			System.out.println("������ ");
//			isRight = false;
//		}
//	}
//
//
//	private void expression() {
//		term();
//		while (syn == 13 || syn == 14){
//			catchNextWord();
//			term();
//		}
//	}
//
//	private void term() {
//		factor();
//		while (syn == 15 || syn == 16) {
//			catchNextWord();
//			factor();
//		}
//	}
//
//	private void factor() {
//		if (syn == 10 || syn == 11) {
//			catchNextWord();
//		}else if (syn == 27) {
//			catchNextWord();
//			expression();
//			if (syn == 28) {
//				catchNextWord();
//			}else {
//				System.out.println("')'���� ");
//				isRight = false;
//			}
//		}else {
//			System.out.println("���ʽ���� ");
//			isRight = false;
//		}
//	}

}
