package com.rafka.grapherandroid.core;

import java.util.ArrayDeque;
import java.util.Locale;
import java.util.Queue;
import java.util.Stack;

class Converter {
	private static Stack<Token> mem = new Stack<Token>();

	public Converter() {
	}

	private static boolean isNumber(char c) {
		return '0' <= c && c <= '9';
	}

	private static boolean isAlphabet(char c) {
		return ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z');
	}

	private static boolean isOperator(char c) {
		switch (c) {
		case '+':
			return true;
		case '-':
			return true;
		case '*':
			return true;
		case '/':
			return true;
		case '^':
			return true;
		case '%':
			return true;
		default:
			return false;
		}
	}

	private static boolean isBracket(char c) {
		return c == '(' || c == ')';
	}

	private static boolean isFunction(String s) {
		switch (s) {
		case "SIN":
			return true;
		case "COS":
			return true;
		case "TAN":
			return true;
		case "EXP":
			return true;
		case "LOG":
			return true;
		default:
			return false;
		}
	}

	private static boolean isConst(String s) {
		switch (s) {
		case "PI":
			return true;
		case "E":
			return true;
		case "EPS":
			return true;
		default:
			return false;
		}
	}

	static Queue<Token> Tokenize(String s) {
		Queue<Token> expr = new ArrayDeque<Token>();
		int startIndex = 0;
		String buf;

		expr.clear();

		for (int i = 0; i < s.length(); i++) {
			if (isNumber(s.charAt(i))) {
				startIndex = i;
				while (i < s.length() && (isNumber(s.charAt(i)) || s.charAt(i) == '.'))
					i++;
				expr.add(new Token(TokenType.NUM, s.substring(startIndex, i--)));
			} else if (isAlphabet(s.charAt(i))) {
				startIndex = i;
				while (i < s.length() && (isNumber(s.charAt(i)) || isAlphabet(s.charAt(i))))
					i++;
				buf = s.substring(startIndex, i--);
				if (isFunction(buf.toUpperCase(Locale.ENGLISH)))
					expr.add(new Token(TokenType.FNC, buf));
				else if (isConst(buf.toUpperCase(Locale.ENGLISH)))
					expr.add(new Token(TokenType.CST, buf));
				else
					expr.add(new Token(TokenType.VAR, buf));
			} else if (isOperator(s.charAt(i))) {
				if ((i == 0 || s.charAt(i - 1) == '(') && s.charAt(i) == '-') {
					expr.add(new Token(TokenType.OPR, "_"));
				} else {
					expr.add(new Token(TokenType.OPR, s.substring(i, i + 1)));
				}
			} else if (isBracket(s.charAt(i))) {
				expr.add(new Token(TokenType.BRC, s.substring(i, i + 1)));
			}
		}

		return expr;
	}

	static void ToRPN(Queue<Token> expr) {
		Token t;
		int n = expr.size();

		mem.clear();

		for (int i = 0; i < n; i++) {
			t = expr.poll();
			switch (t.Ident) {
			case NUM:
			case CST:
			case VAR:
				expr.add(t);
				break;
			case FNC:
				mem.push(t);
				break;
			case BRC:
				if (t.Buf.equals("(")) {
					mem.push(t);
				}
				if (t.Buf.equals(")")) {
					while (!mem.peek().Buf.equals("("))
						expr.add(mem.pop());
					t = mem.pop();
					if (mem.size() != 0 && mem.peek().Ident == TokenType.FNC)
						expr.add(mem.pop());
				}
				break;
			case OPR:
				if (mem.size() == 0) {
					mem.push(t);
					break;
				}
				while (mem.size() != 0 && mem.peek().Ident == TokenType.OPR) {
					if ((t.LeftCat && t.Priority >= mem.peek().Priority) || t.Priority > mem.peek().Priority)
						expr.add(mem.pop());
					else
						break;
				}
				mem.push(t);
				break;
			}
		}
		while (mem.size() != 0)
			expr.add(mem.pop());
	}
}
