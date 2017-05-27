package solver;

import java.util.ArrayList;

/**
 * @author Cohn, Matthias (77210-565998)
 * @version 1.0 - 14.12.2016
 * 
 */
public class MyWorker extends MainSub {

	/**
	 * Main-Funktion des verarbeitenden Moduls
	 */
	public static void doSolve() {

		int iNextField[] = new int[2];
		iNextField = nextField(0, 0); // Erstes leeres Feld wird ermittelt

		if (iNextField[0] < 9) {
			bSearch(iNextField[0], iNextField[1]);
		}
	}

	/**
	 * 
	 * @param iRow
	 *            Aktuelle Reihennummer im Array (0-8)
	 * @param iCol
	 *            Aktuelle Spaltennummer im Array (0-8)
	 * @return Wahrheitswert, ob eine gültige Zahlenkombination gefunden wurde
	 */
	private static boolean bSearch(int iRow, int iCol) {
		boolean bFound = true;
		ArrayList<Integer> alPossible = new ArrayList<Integer>();
		try {
			alPossible = getPossibleSet(loadField(iRow, iCol), loadRow(iRow), loadCol(iCol));
		} catch (Exception e) {
			bFound = false;
		}
		if (alPossible.size() >= 1) {
			sudoku[iRow][iCol] = 10; // Für in Arbeit um nachfolgende Anweisung
										// nicht in die for-schleife
			// packen zu müssen
			int iNextField[] = nextField(iRow, iCol);
			for (int iTry : alPossible) {
				sudoku[iRow][iCol] = iTry;
				System.out.println(MyOutput.writeArrToString(sudoku));
				if (iNextField != null) {
					bFound = bSearch(iNextField[0], iNextField[1]);
				} else {
					bFound = true;
				}
				if (bFound == true) {
					break;
				}
			}
		} else {
			/**
			 * Wenn die Liste der Möglichkeiten =0 entspricht, gibt es mit der
			 * akutellen Zahlenkombination keine mögliche Lösung. Es muss (min.
			 * 1 Feld zurückgesprungen werden und eine neue Kombination versucht
			 * werden.
			 */
			bFound = false;
		}
		if (bFound == false) {
			sudoku[iRow][iCol] = 0;
		}
		return bFound;
	}

	/**
	 * Sucht das nächste 'freie' Feld im Sudoku (mit 0 belegt)
	 * 
	 * @param iRow
	 *            Aktuelle Reihe
	 * @param iCol
	 *            Aktuelle Spalte
	 * @return IntegerArray [Reihe, Spalte] des nächsten Felds; <br>
	 *         null wenn kein weiteres Feld vorhanden
	 */
	private static int[] nextField(int iRow, int iCol) {
		int nextField[] = new int[2];
		boolean bFound = false;

		for (int i = iRow; i < 9; i++) {
			for (int j = iCol; j < 9; j++) {
				if (sudoku[i][j] == 0) {
					nextField[0] = i; // nächster iRow-Wert - X-Achse
					nextField[1] = j; // nächster iCol-Wert - Y-Achse
					bFound = true;
				}
				if (bFound == true) {
					break;
				}
			}
			if (bFound == true) {
				break;
			}
			iCol = 0;
		}
		if (bFound == true) {
			return nextField;
		} else {
			return null;
		}
	}

	/**
	 * Generiert eine Liste von Möglichkeiten, welche Ziffern in dem aktuellen
	 * <br>
	 * Feld vorkommen können.<br>
	 * Dafür werden die Ziffern (1-9) ermittelt, welche nicht in der aktuellen
	 * <br>
	 * Reihe, Spalte und Feld vorkommen.
	 * 
	 * @param alField
	 *            Liste mit vorhandenen Ziffern im aktuellen 3x3 Feld
	 * @param alRow
	 *            Liste mit vorhandenen Ziffern in der aktuellen Reihe
	 * @param alCol
	 *            Liste mit vorhandenen Ziffern in der aktuellen Spalte
	 * @return eine Integer-ArrayList mit allen möglichen Ziffern
	 */
	private static ArrayList<Integer> getPossibleSet(ArrayList<Integer> alField, ArrayList<Integer> alRow,
			ArrayList<Integer> alCol) {
		ArrayList<Integer> alPossible = new ArrayList<Integer>();
		for (int i = 1; i <= 9; i++) {
			if (alField.contains(i) == false && alRow.contains(i) == false && alCol.contains(i) == false) {
				alPossible.add(i);
			}
		}
		return alPossible;
	}

	/**
	 * Bestimmt alle vorhandenen Ziffern in der aktuellen Reihe
	 * 
	 * @param iRow
	 *            aktuelle Reihe
	 * @return eine Integer-ArrayList mit allen vorhandenen Ziffern
	 */
	private static ArrayList<Integer> loadRow(int iRow) {
		ArrayList<Integer> alRow = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++) {
			if (sudoku[iRow][i] > 0) {
				alRow.add(sudoku[iRow][i]);
			}
		}
		return alRow;
	}

	/**
	 * Bestimmt alle vorhandenen Ziffern in der aktuellen Spalte
	 * 
	 * @param iCol
	 *            aktuelle Zeile
	 * @return eine Integer-ArrayList mit allen vorhandenen Ziffern
	 */
	private static ArrayList<Integer> loadCol(int iCol) {
		ArrayList<Integer> alCol = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++) {
			if (sudoku[i][iCol] > 0) {
				alCol.add(sudoku[i][iCol]);
			}
		}
		return alCol;
	}

	/**
	 * Bestimmt alle vorhandenen Ziffern in einem 3x3Feld.<br>
	 * Dazu wird in einem ersten Schritt das obere linke (erste) Feld<br>
	 * des aktuellen 3x3 Felds ermittelt. Alle vorhandenen Ziffern werden in
	 * eine ArrayList geschrieben
	 * 
	 * @param iRow
	 *            aktuelle Reihe
	 * @param iCol
	 *            aktuelle Zeile
	 * @return eine Integer-ArrayList mit allen vorhandenen Ziffern
	 */
	private static ArrayList<Integer> loadField(int iRow, int iCol) {
		ArrayList<Integer> alField = new ArrayList<Integer>();

		// linkes oberes Feld des aktuellen 9er-Felds ermitteln:
		int iFRow = (int) (Math.floor(iRow / 3) * 3);
		int iFCol = (int) (Math.floor(iCol / 3) * 3);

		for (int i = iFRow; i < 3 + iFRow; i++) {
			for (int j = iFCol; j < 3 + iFCol; j++) {
				if (sudoku[i][j] > 0) {
					alField.add(sudoku[i][j]);
				}
			}
		}
		return alField;
	}
}
