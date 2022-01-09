package files;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Monsters {
	protected interface Monster {
		void setLocation(Locations.Location location);
		void combat(Player player);
		void checkDroppedItem(Player player);
	}

	private static class MonsterClass implements Monster {
		int inRegion = 0;
		Locations.Location locatedAt;
		int level = 6;
		String name = "Monster";
		boolean isSpirit = false;
		int[] monsterAttackRange = new int[]{1, 1};
		int[] playerAttackRange = new int[]{1, 6};
		boolean hasComponent = true;
		boolean hasTreasure = false;

		@Override
		public void setLocation(Locations.Location location) {
			this.locatedAt = location;
		}

		@Override
		public void combat(Player player) {
			Random dice = new Random();
			boolean combatEnded = false;
			while (!combatEnded && player.isConscious()) {
				int[] rolls = new int[]{dice.nextInt(6) + 1, dice.nextInt(6) + 1};
				for (int roll : rolls) {
					if (roll >= monsterAttackRange[0] && roll <= monsterAttackRange[1]) {
						player.receiveDamage(1);
					}
					if (roll >= playerAttackRange[0] && roll <= playerAttackRange[1]) {
						combatEnded = true;
					}
				}
			}
			checkDroppedItem(player);
		}

		@Override
		public void checkDroppedItem(Player player) {
			int roll = new Random().nextInt(6) + 1;
			if (roll <= this.level) {
				// player loots dropped item
				if (this.hasComponent) {
					player.getOneComponent(inRegion);
				}
				else if (this.hasTreasure) {
					locatedAt.foundTreasure(player);
				}
			}
		}
	}

	protected static class RegionZeroMonsters extends MonsterClass {
		private static class MonsterZeroOne extends MonsterClass {
			MonsterZeroOne() {
				this.inRegion = Locations.RegionIndex.ZERO;
				this.level = 1;
				this.name = "Ice Bear";
				this.monsterAttackRange = new int[]{1, 1};
				this.playerAttackRange = new int[]{5, 6};
			}
		}

		private static class MonsterZeroTwo extends MonsterClass {
			MonsterZeroTwo() {
				this.inRegion = Locations.RegionIndex.ZERO;
				this.level = 2;
				this.name = "Roving Bandits";
				this.monsterAttackRange = new int[]{1, 1};
				this.playerAttackRange = new int[]{6, 6};
			}
		}

		private static class MonsterZeroThree extends MonsterClass {
			MonsterZeroThree() {
				this.inRegion = Locations.RegionIndex.ZERO;
				this.level = 3;
				this.name = "Blood Wolves";
				this.monsterAttackRange = new int[]{1, 2};
				this.playerAttackRange = new int[]{6, 6};
			}
		}

		private static class MonsterZeroFour extends MonsterClass {
			MonsterZeroFour() {
				this.inRegion = Locations.RegionIndex.ZERO;
				this.level = 4;
				this.name = "Horse Eater Hawk";
				this.monsterAttackRange = new int[]{1, 3};
				this.playerAttackRange = new int[]{6, 6};
			}
		}

		private static class MonsterZeroFive extends MonsterClass {
			Treasures.Treasure treasure;
			MonsterZeroFive() {
				this.inRegion = Locations.RegionIndex.ZERO;
				this.level = 2;
				this.name = "The Hollow Giant (S)";
				this.monsterAttackRange = new int[]{1, 4};
				this.playerAttackRange = new int[]{6, 6};
				this.isSpirit = true;
				this.hasTreasure = true;
				this.treasure = new Treasures.TreasureZero();
			}
		}

		protected static List<Monster> getMonsters() {
			List<Monster> monsters = new ArrayList<>();
			monsters.add(new MonsterZeroOne());
			monsters.add(new MonsterZeroTwo());
			monsters.add(new MonsterZeroThree());
			monsters.add(new MonsterZeroFour());
			monsters.add(new MonsterZeroFive());
			return monsters;
		}
	}


}
