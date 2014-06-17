package main;

import ui.UI;

public class Main {
	public static void main(String[] args) {
		UI ui;
		ui = new ui.PopupUI();
		Control control = new Control(ui);
		control.run();
	}
}
