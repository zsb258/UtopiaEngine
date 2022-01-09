package gameui;

public class UI {
	public static void infoPrint(String msg) {
		System.out.println(msg);
	}

	public static void gameFailedPrint() {
		infoPrint("Doomsday has come.");
		infoPrint("You failed tp build the Utopia Engine.");
		infoPrint("THE END");
	}
}
