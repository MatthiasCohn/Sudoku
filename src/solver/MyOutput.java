package solver;

import javax.swing.*;

/**
 * Diese Klasse realisiert die Ausgabefunktionalitäten
 * 
 * @author Cohn, Matthias (77210-565998)
 * @version 1.0 (04.01.2017)
 */
public class MyOutput extends MainSub {

	/**
	 * Ausgabe einer Fehlermeldung
	 * 
	 * @param sEMsg
	 *            Information / Text der Fehlermeldung
	 */
	public static void showErrMessage(String sEMsg) {
		JOptionPane.showMessageDialog(null, sEMsg, "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Ausgabe einer Information
	 * 
	 * @param sMsg
	 *            Information / Text
	 */
	public static void showMessage(String sMsg) {
		JOptionPane.showMessageDialog(null, sMsg, "Ergebnis", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * <H1>Wurde nicht umgesetzt!</H1> Main der Ausgabe / Export: Lösung wird in
	 * die ursrpüngliche Datei geschrieben oder ausgegeben ..
	 */
	public static void writeSolution() {

	}

	/**
	 * Bildet das aktuelle Sudoku-Array in Textform ab
	 * 
	 * @param myArr
	 *            Entspricht dem aktuellen Sudoku-Array
	 * @return Lesbare String-Version des Sodoku-Array's
	 */
	public static String writeArrToString(int myArr[][]) {
		String sArrToString = "";
		for (int iRow = 0; iRow < myArr.length; iRow++) {
			sArrToString = sArrToString + "[ ";
			for (int iCol = 0; iCol < myArr[iRow].length; iCol++) {
				String sNext = String.valueOf(myArr[iRow][iCol]);
				sArrToString = sArrToString + "[" + sNext + "]";
			}
			sArrToString = sArrToString + " ]\n";
		}
		return sArrToString;
	}

	/**
	 * Bildet das gelöste Sudoku-Array in Textform mit Rahmen ab
	 * 
	 * @param bGUI
	 *            Wahrheitswert, ob Ausgabe für Konsole oder JOptionPane
	 * @return sArrToString Sudoku als Formatierter String (in Tabellenform)
	 */
	public static String writeArrToSudoku(boolean bGUI) {
		String sArrToString = "";
		sArrToString = sBuildLine(sArrToString, bGUI, true);
		for (int iRow = 0; iRow < sudoku.length; iRow++) {
			sArrToString = sArrToString + "|| ";
			for (int iCol = 0; iCol < sudoku[iRow].length; iCol++) {
				String sNext = String.valueOf(sudoku[iRow][iCol]);
				sArrToString = sArrToString + sNext;
				if ((iCol + 1) % 3 == 0) {
					sArrToString = sArrToString + "  ||  ";
				} else {
					sArrToString = sArrToString + "  |  ";
				}
			}
			sArrToString = sArrToString + "\n";
			if ((iRow + 1) % 3 == 0) {
				sArrToString = sBuildLine(sArrToString, bGUI, true);
			} else {
				sArrToString = sBuildLine(sArrToString, bGUI, false);
			}
		}
		// System.out.println(sArrToString);
		return sArrToString;
	}

	/**
	 * Methode sorgt für eine bessere Lesbarkeit des Sudokus
	 * 
	 * @param sInput
	 *            Aktueller / Letzter Textstring
	 * @param bGUI
	 *            Wahrheitswert, ob Ausgabe für Konsole oder JOptionPane
	 * @param bDouble
	 *            Wahrheitswert, ob doppellinie (horizontial) erzeugt werden
	 *            sollen
	 * @return Eine Textzeile / Reihe des Sudokus
	 */
	private static String sBuildLine(String sInput, boolean bGUI, boolean bDouble) {
		sInput = sInput + "||";
		int iFaktor = 0;
		int iKorr = 0;
		if (bGUI == true) {
			if (bDouble == true) {
				iFaktor = 3;
				iKorr = 1;
			} else {
				iFaktor = 5;
				iKorr = 5;
			}
		} else {
			iFaktor = 6;
			iKorr = 0;
		}

		for (int iCol = 0; iCol < sudoku[0].length * iFaktor + iKorr; iCol++) {
			if (bDouble == true) {
				sInput = sInput + "=";
			} else {
				sInput = sInput + "-";
			}
		}
		sInput = sInput + "||\n";
		return sInput;
	}

	/**
	 * In einem korrekt gelöstem Sodoku beträgt die Summe <br>
	 * der Einträge = 405. Dies wird in dieser Methode überprüft.
	 * 
	 * @return Wahrheitswert, ob Summe der Einträge = 405
	 */
	public static boolean bCheckSum() {
		int iSum = 0;
		for (int iRow = 0; iRow < 9; iRow++) {
			for (int iCol = 0; iCol < 9; iCol++) {
				iSum += sudoku[iRow][iCol];
			}
		}
		if (iSum == 405) {
			return true;
		} else {
			return false;
		}
	}
}
