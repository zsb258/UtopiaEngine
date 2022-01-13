package files;

import files.Fn.Dice;
import files.gameui.UI;
import files.Fn.Grid.ArtifactGrid;
import files.Fn.UserInput;

public class Artifacts {
	interface Artifact {
		void foundByPlayer();
		void activateArtifact();
		String getName();
	}

	private static class ArtifactClass implements Artifact {
		String name = "Artifact name";
		String effect;
		int inRegion;
		boolean found = false;
		boolean activated = false;
		boolean used;

		@Override
		public void foundByPlayer() {
			this.found = true;
		}

		@Override
		public void activateArtifact() {
			if (this.activated) {
				UI.print("The Artifact is already active.");
				return;
			}
			new ArtifactGrid().runActivation();
		}

		@Override
		public String getName() {
			return this.name;
		}

	}

	static class ArtifactZero extends ArtifactClass {
		ArtifactZero() {
			super();
			name = "Seal of Balance";
			effect = "Ignore all events in any one region (only once per game)";
			inRegion = Locations.RegionIndex.ZERO;

			used = false;
		}
	}

	static class ArtifactOne extends ArtifactClass {
		ArtifactOne() {
			super();
			name = "Hermetic Mirror";
			effect = "Subtract up to 10 from any Search in Halebeard Peak and the Fiery Maw";
			inRegion = Locations.RegionIndex.TWO;
		}
	}

	static class ArtifactTwo extends ArtifactClass {
		ArtifactTwo() {
			super();
			name = "Void Gate";
			effect = "Recover from unconsciousness in four days instead of six";
			inRegion = Locations.RegionIndex.THREE;
		}
	}

	static class ArtifactThree extends ArtifactClass {
		ArtifactThree() {
			super();
			name = "Golden Chassis";
			effect = "+1 to each die while in combat against a spirit encounter (S)";
			inRegion = Locations.RegionIndex.THREE;
		}
	}

	static class ArtifactFour extends ArtifactClass {
		ArtifactFour() {
			super();
			name = "Scrying Lens";
			effect = "Subtract up to 10 from any Search in Root-Strangled Marshes and Glassrock Canyon";
			inRegion = Locations.RegionIndex.FOUR;
		}
	}

	static class ArtifactFive extends ArtifactClass {
		ArtifactFive() {
			super();
			name = "Crystal Battery";
			effect = "Spend any three components to recharge one tool belt item";
			inRegion = Locations.RegionIndex.FIVE;
		}
	}

	void run() {


	}

	public static void main(String[] args) {
		new Artifacts().run();
	}
}
