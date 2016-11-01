package com.rafka.grapherandroid.core;

import java.util.HashMap;
import java.util.Locale;
import java.util.Queue;
import java.util.Stack;

public class Expression {
	private Queue<Token> expr;
	private int l;

	public Expression(String expression) {
		expr = Converter.Tokenize(expression.replace(" ", ""));
		Converter.ToRPN(expr);
		l = expr.size();
	}

	public float Eval(HashMap<String, Float> values) {
		Stack<Float> ans = new Stack<Float>();
		Token t;
		float p;

		for (int i = 0; i < l; i++) {
			t = expr.poll();
			switch (t.Ident) {
			case BRC:
				break;
			case CST:
				switch (t.Buf.toUpperCase(Locale.ENGLISH)) {
				case "PI":
					ans.push((float)Math.PI);
					break;
				case "E":
					ans.push((float)Math.E);
					break;
				case "EPS":
					ans.push(Float.MIN_NORMAL);
					break;
				}
				break;
			case FNC:
				switch (t.Buf) {
				case "sin":
				case "SIN":
					ans.push((float)Math.sin(ans.pop()));
					break;
				case "cos":
				case "COS":
					ans.push((float)Math.cos(ans.pop()));
					break;
				case "tan":
				case "TAN":
					ans.push((float)Math.tan(ans.pop()));
					break;
				case "Sin":
					ans.push((float)Math.asin(ans.pop()));
					break;
				case "Cos":
					ans.push((float)Math.acos(ans.pop()));
					break;
				case "Tan":
					ans.push((float)Math.atan(ans.pop()));
					break;
				case "exp":
				case "EXP":
				case "Exp":
					ans.push((float)Math.exp(ans.pop()));
					break;
				case "log":
				case "LOG":
				case "Log":
					ans.push((float)Math.log(ans.pop()));
					break;
				}
				break;
			case NUM:
				ans.push(Float.valueOf(t.Buf));
				break;
			case OPR:
				p = ans.pop();
				switch (t.Buf) {
				case "+":
					ans.push(ans.pop() + p);
					break;
				case "-":
					ans.push(ans.pop() - p);
					break;
				case "*":
					ans.push(ans.pop() * p);
					break;
				case "/":
					ans.push(ans.pop() / p);
					break;
				case "%":
					ans.push(ans.pop() % p);
					break;
				case "^":
					ans.push((float)Math.pow(ans.pop(), p));
					break;
				case "_":
					ans.push(-p);
					break;
				}
				break;
			case VAR:
				if (values != null)
					if (values.containsKey(t.Buf))
						ans.push(values.get(t.Buf));
					else
						ans.push(Float.NaN);
				break;
			default:
				break;
			}
			expr.add(t);
			if (ans.peek().isNaN())
				for (i += 1; i < l; i++)
					expr.add(expr.poll());
		}
		return ans.pop();
	}

	public float Eval() {
		return Eval(null);
	}

	public void PrintExpression() {
		Token t;
		for (int i = 0; i < l; i++) {
			t = expr.poll();
			System.out.print(t.Buf + " ");
			expr.add(t);
		}
		System.out.println();
	}

	public String getExprString() {
		StringBuilder sb = new StringBuilder();
		Token t;
		sb.delete(0, sb.length());

		for (int i = 0; i < l; i++) {
			t = expr.poll();
			sb.append(t.Buf);
			sb.append(' ');
			expr.add(t);
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public void Remake(String newExpression) {
		expr = Converter.Tokenize(newExpression.replace(" ", ""));
		Converter.ToRPN(expr);
		l = expr.size();
	}

	public boolean isEmpty() {
		return expr.isEmpty();
	}
}
