package lexical_analysis;

import java.util.ArrayList;

public class Splitter {

	private ArrayList<String> resultsList = new ArrayList<String>();
	private ArrayList<Integer> resultLine = new ArrayList<Integer>();
	private int status;
	private String text;
	private int line=1;

	public Splitter(String text){
		this.text = text;
		status = 0;
//		System.out.println(text);
	}

	public boolean handleText() {
		int length = text.length();
		String token = "";
		for (int i = 0; i < length; i++) {
			char ch = text.charAt(i);
//			System.out.println("µÚ"+line+"ÐÐ"+ch);
			if (ch=='\n') {
				line++;
			}
			if (!isLegal(ch)) {
				return false;
			}

			switch (status) {
			case 0:
				if (isLetter(ch)) {		//±êÊ¶·û
					status = 1;
					token += ch;
				}else if (isDigit(ch)) {	//Êý×Ö
					status = 2;
					token += ch;
				}else if (ch=='/') {	//
					status = 3;
					token += ch;
				}else if (ch==':') {
					status = 6;
					token += ch;
				}else if (ch=='<') {
					status = 7;
					token += ch;
				}else if (ch=='>') {
					status = 8;
					token += ch;
				}else if (isSingle(ch)) {
					resultsList.add(String.valueOf(ch));
					resultLine.add(line);
				}else {
				}
				break;

			case 1:
				if (isDigit(ch) || isLetter(ch)) {
					token += ch;
				}else {
					resultsList.add(token);
					i--;
					if (ch=='\n') {
						line--;
					}
					resultLine.add(line);
					token = "";
					status = 0;
				}
				break;

			case 2:
				if (isDigit(ch)) {
					token += ch;
				}else {
					resultsList.add(token);
					i--;
					if (ch=='\n') {
						line--;
					}
					resultLine.add(line);
					token = "";
					status = 0;
				}
				break;

			case 3:
				if (ch=='*') {		//×¢ÊÍ
					token += ch;
					status = 4;
				}else {
					resultsList.add(token);
					i--;
					if (ch=='\n') {
						line--;
					}
					resultLine.add(line);
					token = "";
					status = 0;
				}
				break;

			case 4:
				token += ch;
				if (ch=='*') {		//×¢ÊÍ
					status = 5;
				}
				break;

			case 5:
				token += ch;
				if (ch=='*') {
				}else if (ch=='/') {	//×¢ÊÍ
//					resultsList.add(token);
					token = "";
					status = 0;
				}else {
					status = 4;
				}
				break;

			case 6:
				if (ch=='=') {
					token += ch;
				}else {
					i--;
					if (ch=='\n') {
						line--;
					}
				}
				resultsList.add(token);
				resultLine.add(line);
				token = "";
				status = 0;
				break;

			case 7:
				if (ch=='>' || ch=='=') {
					token += ch;
				}else {
					i--;
					if (ch=='\n') {
						line--;
					}
				}
				resultsList.add(token);
				resultLine.add(line);
				token = "";
				status = 0;
				break;

			case 8:
				if (ch=='=') {
					token += ch;
				}else {
					i--;
					if (ch=='\n') {
						line--;
					}
				}
				resultsList.add(token);
				resultLine.add(line);
				token = "";
				status = 0;
				break;
			}
		}
		return true;
	}

	public ArrayList<String> getResult() {
		return resultsList;
	}

	public ArrayList<Integer> getLine() {
		return resultLine;
	}

	/**
	 * ÅÐ¶ÏÊÇ·ñÎª×Ö·û
	 * @param ch
	 * @return
	 */
	private boolean isLetter(char ch){
		if (ch>='A'&&ch<='Z' || ch>='a'&&ch<='z') {
			return true;
		}
		return false;
	}

	/**
	 * ÅÐ¶ÏÊÇ·ñÎªÊý×Ö
	 * @param ch
	 * @return
	 */
	private boolean isDigit(char ch) {
		if (ch>='0'&&ch<='9'){
			return true;
		}
		return false;
	}

	/**
	 * ÅÐ¶ÏÊÇ·ñÎªµ¥¸ö×Ö·û¹Ø¼ü×Ö
	 * @param ch
	 * @return
	 */
	private boolean isSingle(char ch) {
		if (ch=='+' || ch=='-' || ch=='*' || ch=='=' || ch==';' || ch=='(' || ch==')' || ch=='#'){
			return true;
		}
		return false;
	}

	/**
	 * ÅÐ¶ÏÊÇ·ñ·Ö¸ô·û
	 * @param ch
	 * @return
	 */
	private boolean isSplitCh(char ch) {
		if (ch==' ' || ch=='\n'){
			return true;
		}
		return false;
	}

	/**
	 * ÅÐ¶Ï×Ö·ûÊÇ·ñºÏ·¨
	 * @param ch
	 * @return
	 */
	private boolean isLegal(char ch) {
		if (isDigit(ch) || isLetter(ch) || isSingle(ch) || isSplitCh(ch) || ch=='/' || ch==':' || ch=='<' || ch=='>') {
			return true;
		}
		return false;
	}
}
