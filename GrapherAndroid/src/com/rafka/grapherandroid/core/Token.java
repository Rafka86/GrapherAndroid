package com.rafka.grapherandroid.core;

class Token {
	public TokenType Ident;
	public String Buf;
	public int Priority;
	public boolean LeftCat;

	public Token(TokenType ident, String buf) {
		this.Ident = ident;
		this.Buf = buf;
		this.Priority = GetPriority(buf);
		this.LeftCat = IsLeftCat(buf);
	}

	private static int GetPriority(String s) {
		switch (s) {
		case "+":
			return 2;
		case "-":
			return 2;
		case "*":
			return 1;
		case "/":
			return 1;
		case "%":
			return 1;
		case "^":
			return 0;
		case "_":
			return 1;
		default:
			return Integer.MAX_VALUE;
		}
	}

	private static boolean IsLeftCat(String s) {
		return !(s.equals("^") || s.equals("_"));
	}
}
