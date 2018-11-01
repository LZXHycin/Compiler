package lexical_analysis;

public class Message {

	private String token;
	private int syn;
	private int line;

	public Message(String token, int syn, int line){
		this.syn = syn;
		this.token = token;
		this.line = line;
	}

	public void print(){
		if (this.syn==10) {
			System.out.println("(" + syn + ",'" + token + "')");
		}else {
			System.out.println("(" + syn + "," + token + ")");
		}
		System.out.println(line);
	}

	public int getSyn() {
		return syn;
	}

	public String getToken() {
		return token;
	}

	public int getLine() {
		return line;
	}
}
