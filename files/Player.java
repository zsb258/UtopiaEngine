package files;

import java.util.ArrayList;
import java.util.List;

public class Player {
	int location;
	int hp = PlayerConstants.MAX_HP;
	int currDay = 0;

	List<Artifacts.Artifact> artifacts = new ArrayList<>();
	List<Treasures.Treasure> treasures = new ArrayList<>();
	int[] stores = new int[PlayerConstants.TYPES_OF_MATERIALS];
	int[] toolBelt = new int[PlayerConstants.TYPES_OF_TOOLS];

	protected static class PlayerConstants {
		private PlayerConstants(){}
		public final static int MAX_HP = 6;

		public final static int TYPES_OF_MATERIALS = 6;
		protected static class Materials {
			private Materials(){}
			public final static int SILVER = 0;
			public final static int QUARTZ = 1;
			public final static int SILICA = 2;
			public final static int GUM = 3;
			public final static int WAX = 4;
			public final static int LEAD = 5;

			static String toName(int i) {
				return switch (i) {
					case SILVER -> "Silver";
					case QUARTZ -> "Quartz";
					case SILICA -> "Silica";
					case GUM -> "Gum";
					case WAX -> "Wax";
					case LEAD -> "Lead";
					default -> "";
				};
			}
		}

		public final static int TYPES_OF_TOOLS = 3;
	}

	Player() {
//		Arrays.fill(stores, 0);
//		artifacts.add(new Artifacts.SealOfBalance());
//		treasures.add(new Treasures.IcePlate());
	}

	void search(int location) {

	}

	void encounterMonster(int region, int monsterLevel) {

	}

	public static void main (String[] args) {

	}
}