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
	 * 获取下一个词
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
	 * 程序
	 */
	public void analyse() {
		catchNextWord();
		if (syn != 1) {
			System.out.print("缺begin ");
			isRight = false;
		}else {
			catchNextWord();
		}
		sentences();
//		System.out.println(syn);
		if (syn != 6) {
			System.out.print("缺end ");
			isRight = false;
		}else {
			catchNextWord();
		}
		if (syn != 0) {
			System.out.print("缺#结束符 ");
			isRight = false;
		}
		System.out.print(token + " ");
		if (isRight) {
			System.out.println();
			System.out.println("success");
		}
	}

	/**
	 * 语句串
	 */
	public void sentences() {
		statement();
		while (syn == 26) {
			catchNextWord();
			statement();
		}
	}

	/**
	 * 语句
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
			System.out.print("未明语句 " + syn + " ");
			isRight = false;
			if (syn != 6) {
				catchNextWord();
				statement();
			}
		}
	}

	/**
	 * 赋值语句
	 */
	private void assignment() {
//		if (syn != 10) {
//			System.out.print("缺少标识符 ");
//			isRight = false;
//		}else {
//			catchNextWord();
//		}
		if (syn != 18) {
			System.out.print("缺少:= ");
			isRight = false;
		}else {
			catchNextWord();
		}
		expression();
	}

	/**
	 *条件语句
	 */
	private void condition() {
		expression();
		if (syn < 20 || syn > 25) {
			System.out.print("缺少关系连接符 ");
			isRight = false;
		}else {
			catchNextWord();
		}
		expression();
		if (syn != 3) {
			System.out.print("缺少then ");
			isRight = false;
		}else {
			catchNextWord();
		}
		statement();
	}

	/**
	 * 循环语句
	 */
	private void loop() {
		expression();
		if (syn < 20 || syn > 25) {
			System.out.print("缺少关系连接符 ");
			isRight = false;
		}else {
			catchNextWord();
		}
		expression();
		if (syn != 5) {
			System.out.print("缺少do ");
			isRight = false;
		}else {
			catchNextWord();
		}
		statement();
	}

	/**
	 * 表达式
	 */
	private void expression() {
		term();
		while (syn == 13 || syn == 14) {
			catchNextWord();
			term();
		}
	}

	/**
	 * 项
	 */
	private void term() {
		factor();
		while (syn == 15 || syn ==16) {
			catchNextWord();
			factor();
		}
	}

	/**
	 * 因子
	 */
	private void factor() {
//		System.out.print(" 在factor中syn=" + syn +" ");
		if (syn == 10 || syn == 11) {
			catchNextWord();
		}else if (syn == 27) {
			catchNextWord();
			expression();
			if (syn != 28) {
				System.out.print("缺')' ");
				isRight = false;
			}else {
				catchNextWord();
			}
		}else if (syn == 3 || syn == 5 || syn >=20 && syn <= 25) {

		}else {
			System.out.print("表达式错误 ");
			isRight = false;
		}
	}

//	public void analyse() {
//		catchNextWord();
//		lrparser();
//	}
//
//	private void lrparser() {
//		// begin开头
//		if (syn == 1) {
//			catchNextWord();
//			//从这begin出发
//			yucu();
//			//返回以end结束
//			if (syn == 6) {
//				catchNextWord();
//				if (syn == 0 && isRight) {
//					System.out.println("success ");
//				}
//			} else {
//				if (isRight) {
//					System.out.println("缺'end' ");
//					isRight = false;
//				}
//			}
//		} else {
//			System.out.println("缺'begin' ");
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
//		if (syn == 10) {		//赋值语句
//			catchNextWord();
//			if (syn == 18) {
//				catchNextWord();
//				expression();
//			}else {
//				System.out.println("赋值号错误 ");
//				isRight = false;
//			}
//		}else if (syn == 2) {		//if..then..语句
//			catchNextWord();
//			expression();
//			if (syn>=20 && syn<=25) {
//				catchNextWord();
//				expression();
//				if (syn==3) {
//					catchNextWord();
//					statement();
//				}else {
//					System.out.println("缺少then ");
//					isRight = false;
//				}
//			}else {
//				System.out.println("缺少关系连接符 ");
//				isRight = false;
//			}
//		}else if (syn == 4) {		//while..do..语句
//			catchNextWord();
//			expression();
//			if (syn >= 20 && syn <= 25) {
//				catchNextWord();
//				expression();
//				if (syn == 5) {
//					catchNextWord();
//					statement();
//				}else {
//					System.out.println("缺少do ");
//					isRight = false;
//				}
//			}else {
//				System.out.println("缺少关系连接符 ");
//				isRight = false;
//			}
//		}else {
//			System.out.println("语句错误 ");
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
//				System.out.println("')'错误 ");
//				isRight = false;
//			}
//		}else {
//			System.out.println("表达式错误 ");
//			isRight = false;
//		}
//	}

}
