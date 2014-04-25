package net.famzangl.minecraft.minebot.ai.path;

import net.famzangl.minecraft.minebot.ai.AIHelper;

public class MineBySettingsPathFinder extends MinePathfinder {

	protected class FileSettingsProvider implements ISettingsProvider {
		private String name_prefix;
		private float defaultValue;
		private float min;
		private float max;

		public FileSettingsProvider(String name_prefix, float defaultValue,
				float min, float max) {
			this.name_prefix = name_prefix;
			this.defaultValue = defaultValue;
			this.min = min;
			this.max = max;
		}

		@Override
		public float getFloat(String name) {
			float val = settings.getFloat(name_prefix + name, defaultValue,
					min, max);
			System.out.println(name_prefix + name + " -> " + val);
			return val;
		}
	}

	public MineBySettingsPathFinder(AIHelper helper) {
		super(helper);
		maxDistancePoints = 0;
		maxDistanceFactor = MIN_FACTOR;
		for (String s : settings.getKeys()) {
			if (s.matches("mine_points_.*")) {
				float p = settings.getFloat(s, 1, 0, MAX_POINTS);
				maxDistancePoints = Math.max(p, maxDistancePoints);
			} else if (s.matches("mine_factor_.*")) {
				float p = settings.getFloat(s, 1, 0, MAX_FACTOR);
				if (p > 0) {
					maxDistanceFactor = Math.max(p, maxDistanceFactor);
				}
			}
		}
	}

	@Override
	protected ISettingsProvider getFactorProvider() {
		return new FileSettingsProvider("mine_factor_", 0, 0, MAX_FACTOR);
	}

	@Override
	protected ISettingsProvider getPointsProvider() {
		return new FileSettingsProvider("mine_points_", 1, 0, MAX_POINTS);
	}

}