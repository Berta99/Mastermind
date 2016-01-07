package de.haju;

public class TippResult {
	private int containedColors;
	private int correctPositions;

	public TippResult(int containedColors, int correctPositions) {
		this.containedColors = containedColors;
		this.correctPositions = correctPositions;
	}

	/**
	 * @return anzahl der enthaltnen Farben
	 */
	public int getContainedColors() {
		return containedColors;
	}

	/**
	 * @return anzahl der Farben an der richtigen Position
	 */
	public int getCorrectPositions() {
		return correctPositions;
	}

}
