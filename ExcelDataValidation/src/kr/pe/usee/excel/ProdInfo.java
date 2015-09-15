package kr.pe.usee.excel;

public class ProdInfo {
	String[] prod;

	public ProdInfo() {
		prod = new String[2];
	}
	public ProdInfo(String p) {
		prod = p.split("@");
	}

	public String getCd() {
		return prod[0];
	}

	public void setCd(String cd) {
		prod[0] = cd;
	}

	public String getName() {
		return prod[1];
	}

	public void setName(String name) {
		prod[1] = name;
	}

}
