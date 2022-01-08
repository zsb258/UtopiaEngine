package files;

public class Artifacts {
	protected static class Artifact {
		boolean found = false;
		boolean activated = false;
	}

	protected static class SealOfBalance extends Artifact {
		String name = "Seal of Balance";
		String effect = "Ignore all events in any one region (only once per game)";
		int inRegion = Locations.Wilderness.HALEBEARD_PEAK;
		boolean used = false;
	}
}
