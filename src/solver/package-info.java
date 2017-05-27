/**
 * Package besteht aus (min.) 4 Modulen:
 * <ul>
 * <li>MainSub.java (Hauptprogramm)</li>
 * <li>MyLoad.java (Eingabemodul)</li>
 * <li>MyWorker.java (Verarbeitungsmodul)</li>
 * <li>MyOutput.java (Ausgabemodul)</li>
 * </ul>
 * Alle (EVA-)Module greifen auf 3 global definierte Variablen des
 * Hauptprogramms:
 * <ul>
 * <li>int sudoku [9][9]: das Sodoku als 2D-Array
 * <li>boolean bStatusOK: Wahrheitswert, ob Fehler während der Verarbeitung
 * aufgetreten ist.
 * <li>int iErrCode: Fehlercode, der aufgetretene Fehler genauer spezifiziert;
 * <br>
 * Fehlerdokumentation iErrCode: Text erfolgt direkt im Hauptprogramm
 * </ul>
 * Die (EVA-)Module erweitern (extends) das Hauptprogramm. Somit erfolgt der<br>
 * Zugriff auf die 3 global definierten Variablen direkt.
 */
package solver;