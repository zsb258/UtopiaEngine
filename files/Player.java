package files;

import gameui.UI;

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

	protected static class PlayerConstants {
		private PlayerConstants(){}
		public final static int MAX_HP = 6;

		public final static int TYPES_OF_MATERIALS = 6;
		protected static class Materials {
			private Materials(){}
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

	void receiveDamage(int i) {
		this.hp -= i;
		if (this.hp == 0) {
			// player faints
			// teleports back to workshop
			// rest for 6 days to recover to full hp
			this.rest(PlayerConstants.MAX_HP);
			this.goHome();
		}
		else if (this.hp < 0) {
			// player died
			this.hp = -10; // placeholder assignment
		}
	}

	boolean isConscious() {
		return this.hp > 0;
	}

	void getOneComponent(int i) {
		this.stores[i] += 1;
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

	public static void main (String[] args) {

	}
}