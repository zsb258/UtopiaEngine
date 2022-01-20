package files;

import files.gameui.UI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Player {
	final private static int DOOMSDAY = 15;
	final private static Locations.Location HOME = new Locations.Workshop();

	private Locations.Location location = HOME;
	private int hp = PlayerConstants.MAX_HP;
	private int currDay = 0;

	private List<Artifacts.Artifact> artifacts = new ArrayList<>();
	private List<Treasures.Treasure> treasures = new ArrayList<>();
	private int[] stores = new int[PlayerConstants.TYPES_OF_MATERIALS];
	private int[] toolBelt = new int[PlayerConstants.TYPES_OF_TOOLS];
	private HashMap<Integer, Boolean> effectsInPlace = new HashMap<>();
	private int[] wastebasket = new int[10];
	private int wasteFilled = 0;

	static class PlayerConstants {
		private PlayerConstants(){}
		public final static int MAX_HP = 6;

		public final static int TYPES_OF_MATERIALS = 6;
		static class Materials {
			private Materials(){}
			public final static int CAPACITY = 4;
			public final static int ZERO = 0;
			public final static int ONE = 1;
			public final static int TWO = 2;
			public final static int THREE = 3;
			public final static int FOUR = 4;
			public final static int FIVE = 5;

			static String toName(int i) {
				//noinspection EnhancedSwitchMigration
				switch (i) {
					case ZERO: return "Silver";
					case ONE: return "Quartz";
					case TWO: return "Silica";
					case THREE: return "Gum";
					case FOUR: return "Wax";
					case FIVE: return "Lead";
					default: return "";
				}
			}
		}

		public final static int TYPES_OF_TOOLS = 3;
	}

	@Override
	public String toString() {
		//noinspection StringBufferReplaceableByString
		StringBuilder s = new StringBuilder();
		s.append("Player info:").append(System.lineSeparator());
		s.append("Location: ").append(location.getName()).append(System.lineSeparator());
		s.append("HP: ").append(hp).append(System.lineSeparator());
		s.append("Current Day: ").append(currDay).append(System.lineSeparator());
		s.append("Artifacts: ").append(artifacts).append(System.lineSeparator());
		s.append("Treasures: ").append(treasures).append(System.lineSeparator());
		s.append("Components: ").append(Arrays.toString(stores)).append(System.lineSeparator());
		return s.toString();
	}

	Player() {
		Arrays.fill(stores, 0);
	}

	boolean setLocation(Locations.Location location) {
		if (this.location != location) {
			this.location = location;
			UI.print(String.format("You are now in %s", this.location.getName()));
			return true;
		}
		UI.print(String.format("You are already in %s", this.location.getName()));
		return false;
	}

	Locations.Location getLocation() {
		return location;
	}

	void goHome() {
		this.setLocation(HOME);
	}

	int getHp() {
		return this.hp;
	}

	void receiveDamage(int i, String source) {
		this.hp -= i;
		UI.print(String.format("You lost %d HP from %s.", i, source));
		UI.print(String.format("You are now at %d HP.", this.getHp()));
		if (this.hp == 0) {
			// player faints
			// teleports back to workshop
			// rest for 6 days to recover to full hp
			this.fullRest();
		}
		else if (this.hp < 0) {
			// player died
			// to implement for final activation
			this.hp = -10; // placeholder assignment
		}
	}

	boolean isConscious() {
		return this.hp > 0;
	}

	int getComponentCount(int type) {
		return this.stores[type];
	}

	void obtainOneComponent(int type) {
		if (this.stores[type] < PlayerConstants.Materials.CAPACITY) {
			this.stores[type] += 1;
			return;
		}
		UI.print("You already have the maximum quantity for this component.");
	}

	void spendOneComponent(int type) {
		if (this.stores[type] > 0) {
			this.stores[type] -= 1;
			return;
		}
		UI.print("You do not have sufficient quantity of this component.");
	}

	void getTreasure(Treasures.Treasure treasure) {
		this.treasures.add(treasure);
	}

	void getArtifact(Artifacts.Artifact artifact) {
		artifact.foundByPlayer();
		this.artifacts.add(artifact);
	}

	void aDayHasPast() {
		currDay++;
		if (currDay == DOOMSDAY) {
			UI.printGameFailed();
		}
	}

	void daysHavePast(int i) {
		for (int count = 0; count < i; count++) {
			aDayHasPast();
		}
		UI.print(String.format("%d day(s) has past.%nDays left to Doomsday: %d%n",
				i, DOOMSDAY - currDay));
	}

	void rest(int i) {
		daysHavePast(i);
		hp += i;
		if (hp > PlayerConstants.MAX_HP) {
			hp = PlayerConstants.MAX_HP;
		}
	}

	void fullRest() {
		this.goHome();
		this.rest(PlayerConstants.MAX_HP);
		UI.print("You rest for six days to recover from the combat");
	}

	void addToWaste(int val) {
		if (wasteFilled <= 9) {
			wastebasket[wasteFilled] = val;
			wasteFilled++;
			UI.print(String.format("You have discarded %d.", val));
			printWaste();
		}
	}

	void printWaste() {
		UI.immediatePrint("Wastebasket:");
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < wastebasket.length; i++) {
			if (i == 5) {
				s.append(UI.SEP);
			}
			int val = wastebasket[i];
			s.append("[").append(val != 0 ? val : " ").append("]");
		}
		UI.immediatePrint(s.toString());
	}

	public static void main (String[] args) {

	}
}