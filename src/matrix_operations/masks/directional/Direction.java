package matrix_operations.masks.directional;

public enum Direction {
	E(0), SE(1), S(2), SW(3);

	private final int rotationTimes;

	private Direction(int rotationTimes) {
		this.rotationTimes = rotationTimes;
	}

	public int getRotationTimes() {
		return rotationTimes;
	}
}
