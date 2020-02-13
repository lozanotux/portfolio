package com.platzi.lambdas;

import java.util.Arrays;
import java.util.List;

@FunctionalInterface
interface OnOneListener {

	void onOne(String message);
	
}

public class LambdaExample {

	public static void main(String[] args) {
		OnOneListener oneListener = new OnOneListener() {
			@Override
			public void onOne(String message) {
				System.out.println("One: " + message);
			}
		};
		
		OnOneListener oneListener2 = (String message) -> {
			System.out.println("One Lambda: " + message);
		};
		
		oneListener.onOne("Sin lambda :(");
		oneListener2.onOne("Con Lambda :)");
		
		OnOneListener oneListener3 = message -> System.out.println("Tu mensaje: " + message);
		
		oneListener3.onOne("Short Lambda");
		
		
		List<String> words = Arrays.asList("hello", "world", null, "");
		words.stream()
			.filter(w -> w != null) // ["hello", "world", ""]
			.filter(w -> !w.isEmpty()) // ["hello", "world"]
			.forEach(System.out::println);
		
	}
	
}
