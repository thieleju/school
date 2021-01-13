
/********************************************************************************
 * Aufgabe: Eine DB mit der Tabelle "kunde" und den Feldern "id" und "name" zu
 * erstellen. (Im Prinzip ist es egal, welches DBMS man nimmt.)
 * Am besten macht ihr es mit mysql und Xampp. Mit einer mysql-Workbench die DB erstellen,
 * mit Xampp wird die Verbindung zur DB hergestellt.
 * Damit die Verbindung zur DB klappt, muesst ihr eine Java-Bibliothek (im Ordner) an das
 * Projekt haengen. Rechtsklick auf Libraries und ADD Jar/Folder klicken.
 * Erste Aufgabe ist also, dieses Programm zum laufen zu bekommen.
 * Das kann klappen, muss aber nicht. Notfalls muesst ihr andere Versionen der Bibliothek
 * bzw. der Programme aus dem Netz laden, oder ????
 * Hier solltet ihr erstmal selbst versuchen, eine Verbindung zur DB herzustellen, mithilfe
 * des Internets. 
 * Ihr koennt natuerlich auch andere Loesungen erstellen, also mit einem anderen DBMS
 * oder andere Programmierstile, solange es JAVA ist (und wir es verstehen).
 * Von uns bekommt ihr spaeter bei den Aufgaben nur den DB-Aufbau, welches DBMS ihr benutzt, ist egal.
 * Man kann dies auch mit Access machen.
 * Zweite Aufgabe ist, bei der Suche den eingegebenen Namen zu verwenden. Diesen muss man
 * in das SQL-Statement einbauen, auch dafuer gibt es mehrere Moeglichkeiten.
 * Dritte Aufgabe ist, den zweiten Menuepunkt zu programmieren.
 * 
************************************************************************************* */

import java.util.Scanner;

public class BspDB2021 {

    public static void main(String[] args) {

        int wahl;
        Scanner sc = new Scanner(System.in);
        DB db = new DB();

        do {

            // Menue
            System.out.println("1 - Kunden suchen");
            System.out.println("2 - neuen Kunden einfuegen");
            System.out.println("0 - Ende");

            wahl = sc.nextInt();

            if (wahl == 1) {
                db.suchen();
            }

        } while (wahl != 0);

        db.end();
    }
}