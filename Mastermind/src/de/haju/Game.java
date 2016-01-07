package de.haju;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Game {

	private static final int TIPP_COLORS = 4;

	private boolean running;

	public Set<Color> POSSIBLE_COLORS;

	private Tipp secret;

	private boolean gameSolved;

	private List<Tipp> tipps;
	private List<TippResult> tippResults;

	public Game() {

		POSSIBLE_COLORS = new HashSet<>();
		// Rot = 0;
		// Blau = 1;
		// Gelb = 2;
		// Orange = 3;
		// Grau = 4;
		// Schwarz = 5.

		POSSIBLE_COLORS.add(Color.RED);
		POSSIBLE_COLORS.add(Color.BLUE);
		POSSIBLE_COLORS.add(Color.YELLOW);
		POSSIBLE_COLORS.add(Color.ORANGE);
		POSSIBLE_COLORS.add(Color.GRAY);
		POSSIBLE_COLORS.add(Color.BLACK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.haju.IGame#start()
	 */
	public void start() {
		// checken, ob ein Spiel läuft!
		if (running == true) {
			return;
		}
		running = true;
		// Secret erzeugen!
		createSecret();
		tipps = new ArrayList<>();
		tippResults = new ArrayList<>();
	}

	private void createSecret() {

		Set<Color> remainingColors = new HashSet<>();
		Iterator<Color> posibleColorIterator = POSSIBLE_COLORS.iterator();
		while (posibleColorIterator.hasNext()) {
			remainingColors.add(posibleColorIterator.next());
		}

		Color[] secretColors = new Color[TIPP_COLORS];

		for (int i = 0; i < TIPP_COLORS; i++) {
			Color c = getRandomColor(remainingColors);
			secretColors[i] = c;
			remainingColors.remove(c);
		}
		secret = new Tipp(secretColors);
	}

	/**
	 * @param remainingColors
	 *            Menge mit Farben
	 * @return eine zufällige Farbe aus der gegebenen Menge
	 */
	private Color getRandomColor(Set<Color> remainingColors) {
		Color[] cols = new Color[remainingColors.size()];
		int i = 0;
		Iterator<Color> remainingColorIterator = remainingColors.iterator();
		while (remainingColorIterator.hasNext()) {
			cols[i++] = remainingColorIterator.next();
		}
		int index = (int) Math.round(Math.random() * (cols.length - 1));

		return cols[index];
	}

	public void stop() {
		running = false;
	}

	public void addTipp(Tipp tipp) {
		// ist Tipp ungültig dann beenden

		// gültigen Tipp merken
		tipps.add(tipp);

		// TippResult erzeugen
		TippResult result = evaluateTipp(secret, tipp);
		tippResults.add(result);

		// bemerken ob Spiel gewonnen ist
		gameSolved = true;
	}

	protected TippResult evaluateTipp(Tipp secret, Tipp tipp) {
		int correctPositions = 0;
		for (int i = 0; i < secret.getColors().length; i++) {
			if (secret.getColors()[i] == tipp.getColors()[i]) {
				correctPositions++;
			}
		}
		int containedColors = 0;
		Set<Color> secretColorsSet = new HashSet<>();
		for (Color c : secret.getColors()) {
			secretColorsSet.add(c);
		}
		for (Color c : tipp.getColors()) {
			if (secretColorsSet.contains(c)) {
				containedColors++;
			}
		}

		return new TippResult(containedColors, correctPositions);
	}

	public boolean getResult() {
		return gameSolved;
	}

	public int getTippCount() {
		return tipps.size();
	}

	public Tipp getTipp(int tippnumber) {
		return tipps.get(tippnumber);
	}

	public TippResult getTippResult(int tippnumber) {
		return tippResults.get(tippnumber);
	}
}
