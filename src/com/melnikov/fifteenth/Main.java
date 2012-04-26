package com.melnikov.fifteenth;

public class Main {

	public static void main(String[] args){
		Model model = new Model();
		View view = new View(model);
		view.show();
	}
}
