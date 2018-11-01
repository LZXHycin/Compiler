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
	 * 获取下一个词
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
	 * 程序
	 */
	public void analyse() {
		catchNextWord();
		if (syn != 1) {
			System.out.print("缺begin ");
			errorList.add(new Error("缺begin", curMessage.getLine()));
			isRight = false;
		}else {
			catchNextWord();
		}
		sentences();
//		System.out.println(syn);
		System.out.print(token + " ");
		if (syn != 6) {
			System.out.print("缺end ");
			errorList.add(new Error("缺end", curMessage.getLine()));
			isRight = false;
		}else {
			catchNextWord();
		}
		if (syn != 0) {
			System.out.print("缺#结束符 ");
			errorList.add(new Error("缺#结束符", curMessage.getLine()));
			isRight = false;
		}
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
//		System.out.print(" \"现在的syn=" + syn + "\" ");
		while (syn == 26) {
			catchNextWord();
			statement();
		}
		if (syn != 6 && syn != 0 && index < size) {
			System.out.print("缺; ");
			errorList.add(new Error("缺;", curMessage.getLine()));
			isRight = false;
//			System.out.print(" \"现在的syn=" + syn + "\" ");
			sentences();
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
			System.out.print("未明语句 ");
			errorList.add(new Error("未明语句", curMessage.getLine()));
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
			errorList.add(new Error("缺:=", curMessage.getLine()));
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
			errorList.add(new Error("缺关系连接符", curMessage.getLine()));
			isRight = false;
		}else {
			catchNextWord();
		}
		expression();
		if (syn != 3) {
			System.out.print("缺少then ");
			errorList.add(new Error("缺then", curMessage.getLine()));
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
			errorList.add(new Error("缺关系连接符", curMessage.getLine()));
			isRight = false;
		}else {
			catchNextWord();
		}
		expression();
		if (syn != 5) {
			System.out.print("缺少do ");
			errorList.add(new Error("缺do", curMessage.getLine()));
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
				errorList.add(new Error("缺)", curMessage.getLine()));
				isRight = false;
			}else {
				catchNextWord();
			}
		}else if (syn == 3 || syn == 5 || syn >=20 && syn <= 25) {

		}else {
			System.out.print("表达式错误 ");
			errorList.add(new Error("表达式错误", curMessage.getLine()));
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
