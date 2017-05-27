package solver;

import java.io.*;
import javax.swing.JFileChooser;
import java.util.*;

/**
 * Diese Klasse realisiert die Eingabefunktionalität.<br>
 * Sie ermöglicht das auswählen einer CSV-Datei über einen
 * Datei-öffnen-Dialog.<br>
 * Datei wird eingelesen und auf zulässige Zeichen und Längen geprüft.<br>
 * Leere Felder werden mit '0' dargestellt.
 * 
 * @author Cohn, Matthias (77210-565998)
 * @version 1.0 04.01.17
 */
public class MyLoad extends MainSub {
	private static int sudokuBuffer[][] = new int[9][9];

	/**
	 * Diese Methode realisiert das Anzeigen eines Datei-öffnen-Dialogs. <br>
	 * Mit RegEx-Wird der Pfad zur ausgewählten Datei in String umgewandelt.
	 * 
	 * @see <a href=
	 *      "https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html">
	 *      https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html</a>
	 * 
	 * @see <a href=
	 *      "http://stackoverflow.com/questions/13696461/replace-special-character-with-an-escape-preceded-special-character-in-java">
	 *      http://stackoverflow.com/questions/13696461/replace-special-character-with-an-escape-preceded-special-character-in-java</a>
	 * @return String (RegEx-korrigiert) zur ausgwählten Datei
	 */
	private static String selFile() {
		// Hinweise von:
		// https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
		JFileChooser fcFile = new JFileChooser();
		int returnVal = fcFile.showOpenDialog(null);
		fcFile.setDialogType(JFileChooser.OPEN_DIALOG);
		fcFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File fFile = fcFile.getSelectedFile();
			String sFile = fFile.getPath().toString();
			// This is where a real application would open the file.
			sFile = sFile.replaceAll("\\\\", "\\\\\\\\");
			// http://stackoverflow.com/questions/13696461/replace-special-character-with-an-escape-preceded-special-character-in-java
			System.out.println("Opening: " + sFile);
			return sFile;
		} else {
			bStatusOK = false;
			iErrCode = 1;
			return null;
		}
	}

	/**
	 * Die ausgewählte CSV-Datei wird Zeilenweise eingelesen (fReadIn). <br>
	 * Jede Zeile wird dann in eine ArrayList eingelesen (LineToArrayList).<br>
	 * Dort erfolgt eine Kontrolle über die korrekte Länge der jeweiligen
	 * Zeilen; <br>
	 * Ebenso erfolgt dort die Kontrolle der korrekten Zahlen (1-9; 0 wird <br>
	 * leeren Zellen zugewiesen). <br>
	 * Jede ArrayList wird dann zu einer spalte in einem Puffer-Array
	 * (ArrLToArrBuffer). <br>
	 * Dieser Puffer wird verwendet um die korrekte Anzahl an Spalten überprüfen
	 * zu können.
	 * 
	 * @param sFilePath
	 *            String (RegEx-korrigiert) zur ausgwählten Datei
	 *            {@link} http://www.computer-masters.de/java-datei-zeilenweise-einlesen-bufferedreader.php
	 */
	private static void fReadIn(String sFilePath) {
		// Quelle:
		// http://www.computer-masters.de/java-datei-zeilenweise-einlesen-bufferedreader.php
		FileReader frFile = null;
		try {
			frFile = new FileReader(sFilePath); // String mit Pfad
			BufferedReader brPuffer = new BufferedReader(frFile);
			String sLine = "";
			try {
				int i = 0;
				while (null != (sLine = brPuffer.readLine()) && bStatusOK == true) {
					LineToArrayList(sLine, i);
					i++;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				bStatusOK = false;
				iErrCode = 2;
				e.printStackTrace();
			} finally {
				if (null != brPuffer) {
					try {
						brPuffer.close();
					} catch (IOException e) {
						bStatusOK = false;
						iErrCode = 3;
						e.printStackTrace();
					}
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			bStatusOK = false;
			iErrCode = 4;
			e.printStackTrace();
		}
	}

	private static void LineToArrayList(String sLine, int iRow) {
		ArrayList<Integer> alBuffer = new ArrayList<Integer>();
		System.out.println(sLine);
		String sLastChar = "";
		int iColCount = 0;
		for (int i = 0; i < sLine.length(); i++) {
			String sActChar = sLine.substring(i, i + 1);
			if (sActChar.equals(";")) {
				iColCount++;
				if (sLastChar == "" || sLastChar.equals(";")) {
					alBuffer.add(0);
				} else {
					sLastChar = sActChar;
					continue;
				}
			} else {
				try {
					int n = Integer.valueOf(sActChar);
					alBuffer.add(n);
					if (n < 1 && n > 9) {
						bStatusOK = false;
						iErrCode = 6;
						break;
					}
				} catch (Exception e) {
					bStatusOK = false;
					iErrCode = 5;
					break;
				}
			}
			sLastChar = sActChar;

		}
		// Fehlerkorrektur, wenn letzte Spalte keine Zahl enthält -> ";" fehlt
		// in der CSV
		if (iColCount == 8 && alBuffer.size() == 8) {
			alBuffer.add(0);
		}
		System.out.println(alBuffer.toString());

		if (alBuffer.size() == 9) {
			ArrLToArrBuffer(alBuffer, iRow);
		} else {
			iErrCode = 7;
			bStatusOK = false;
		}

	}

	private static void ArrLToArrBuffer(ArrayList<Integer> alBuffer, int iRow) {
		for (int iCol = 0; iCol < alBuffer.size(); iCol++) {
			if (iRow >= 10) {
				bStatusOK = false;
				iErrCode = 8;
				break;
			}
			sudokuBuffer[iRow][iCol] = alBuffer.get(iCol);
		}
	}

	/**
	 * Main des Inputmoduls
	 */
	public static void loadSudoku() {

		try {
			String sFilePath = selFile();
			if (sFilePath == null || sFilePath == "") {
				throw new Exception();
			}
			fReadIn(sFilePath);
		} catch (Exception e) {
			bStatusOK = false;
			iErrCode = 1;
		}

		if (bStatusOK == true) {
			sudoku = sudokuBuffer;
		}
		// System.exit(0);
	}

}
