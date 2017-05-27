package solver;

/**
 * Hauptklasse der Anwendung Sudoku-Solver <br>
 * Stellt die Attribute: <br>
 * <ul>
 * <li>boolean bStatusOK = true;</li>
 * <li>int iErrCode = 0;</li>
 * <li>int sudoku[][];</li>
 * </ul>
 * für die Unterklassen:
 * <ul>
 * <li>MyLoad (Eingabe)</li>
 * <li>MyWorker (Verarbeitung)</li>
 * <li>MyOutput</li>
 * </ul>
 * zur Verfügung.<br> 
 * Die Hauptklasse steuert den Ablauf der Unterklassen und <br>
 * ermöglicht die Fehlerbehandlung.
 * 
 * @author Cohn, Matthias (77210-565998)
 * @version 1.0
 */
public class MainSub {
	static boolean bStatusOK = true;
	static int iErrCode = 0;
	static int sudoku[][];

	/**
	 * Steuerung des Programmablaufs zu den Unterklassen
	 * 
	 * @param args
	 *            Es werden keine Parameter erwartet
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyLoad.loadSudoku();
		checkStatus(100);

		MyWorker.doSolve();
		checkStatus(200);

		MyOutput.writeSolution();
		checkStatus(300);

		System.out.println("Programmstatus: " + bStatusOK);
		System.out.println("Erkannte Fehler: " + iErrCode);

		showResAndKill();
	}

	/**
	 * Überprüft den aktuelle Verarbeitungsstatus Eingabe: 100 Verarbeitung: 200
	 * Ausgabe: 300
	 * 
	 * @param iModule
	 *            Welche Unterklasse wurde zuletzt behandelt
	 */
	private static void checkStatus(int iModule) {
		if (bStatusOK == false) {
			iErrCode = iErrCode + iModule;
			showResAndKill();
		}
	}

	/**
	 * Zeigt das Ergebnis / Fehlermeldung und beendet das Programm
	 */
	private static void showResAndKill() {
		if (bStatusOK == false) {
			MyOutput.showErrMessage(setErrStr());
		} else {
			System.out.println(MyOutput.writeArrToSudoku(false));
			if (MyOutput.bCheckSum() == true) {
				MyOutput.showMessage("Sodoku gelöst: \n" + MyOutput.writeArrToSudoku(true));
			} else {
				MyOutput.showMessage("Sodoku konnte nicht gelöst werden.");
			}
		}
		System.exit(0);
	}

	/**
	 * Alle dokumentierten Fehlercodes werden in einer ArrayList festgehalten.
	 * gem. gesetztem iErrCode wird entsprechender Fehlertext ausgegeben;
	 * iErrCode entspricht dabei dem Index der ArrayList
	 * 
	 * @return zugehörigen FehlerString des aktuellen iErrCodes
	 */
	private static String setErrStr() {
		String ErrStr = "";

		String alErrStr[] = new String[400];
		// ArrayList<String> alErrStr =new ArrayList<String>();
		alErrStr[0] = "Keine Fehler aufgetreten.";
		/**
		 * Fehlercodes 1 - 99 Hauptprogramm
		 * 
		 */

		/**
		 * Fehlercodes 100-199: Eingabemodul
		 */
		alErrStr[100] = "Unbekannter Fehler bei der Eingabe";
		alErrStr[101] = "Abbruch durch Benutzer: Dateiauswahl";
		alErrStr[102] = "Abbruch beim laden des Sodokus: \nLaden in den Puffer";
		alErrStr[103] = "Abbruch beim laden des Sodokus: \nSchließen des Puffers";
		alErrStr[104] = "Abbruch beim laden des Sodokus: \nDatei nicht gefunden";
		alErrStr[105] = "Abbruch beim laden des Sodokus: \nungültige Zeichen im Sodoku";
		alErrStr[106] = "Abbruch beim laden des Sodokus: \nungültige Zahl im Sodoku - nur 1-9 erlaubt";
		alErrStr[107] = "Abbruch beim laden des Sodokus: \nungültige Anzahl der Ziffern in einer Reihe";
		alErrStr[108] = "Abbruch beim laden des Sodokus: \nungültige Anzahl der Reihen";

		/**
		 * Fehlercodes 200-299: Verarbeitung
		 */

		/**
		 * Fehlercodes 300-399: Ausgabe
		 */

		try {
			ErrStr = alErrStr[iErrCode];
		} catch (Exception e) {
			ErrStr = "Ein unbekannter Fehler ist aufgetreten.";
		}
		return ErrStr;
	}
}
