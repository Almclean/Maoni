package com.maoni;

import java.util.Stack;

public class StackPlay {
	
	public static Stack<Integer> myStack = new Stack<Integer>();
	
	public static void main (String[] args) {
		myStack.push(5);
		myStack.push(4);
		myStack.push(3);
		myStack.pop();
		
		System.out.println(myStack.toString());
		Integer[] ints = myStack.toArray(new Integer[]{});
		System.out.println(Multiply(ints));
	}
	
	private static Integer Multiply(Integer...integers) {
		int accum = 1;
		for (int i : integers) {
			accum *= i;
		}
		return accum;
	}
}
