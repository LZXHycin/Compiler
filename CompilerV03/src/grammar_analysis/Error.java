package grammar_analysis;

public class Error {
	
	private String errorTip;
	private int line;
	
	public Error(String errorTip, int line) {
		this.errorTip = errorTip;
		this.line = line;
	}
	
	public String getErrorTip() {
		return errorTip;
	}
	
	public int getLine() {
		return line;
	}
	
	public void print() {
		System.out.println("第" + line + "行：错误信息——" + errorTip);
	}

}
