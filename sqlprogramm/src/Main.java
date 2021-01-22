
/**
 * SQL Programm für Aufgabe 1
 * 
 * Disclaimer: Spaltennamen müssen wahrscheinlich angepasst werden
 * 
 * https://github.com/thieleju/school/tree/main/sqlprogramm
 */

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // create DB object
        DB db = new DB("192.168.2.221", "3306", "school", "test", "t3st@D3MO");

        Scanner sc = new Scanner(System.in);
        int auswahl = 0;
        do {
            System.out.println("=> Wähle zwischen den Punkten");
            System.out.println("1 - Neuen Kunden erstellen");
            System.out.println("2 - Neuen Artikel erstellen");
            System.out.println("3 - Suche Kunde von bis");
            System.out.println("4 - Zeige alle Kunden ohne Rechnung");
            System.out.println("5 - Erhöhe nettoPreis eines Artikels um X ");
            System.out.println("0 - Programm beenden");

            auswahl = sc.nextInt();

            switch (auswahl) {
                case 0:
                    return;
                case 1:
                    db.createNewKunde();
                    break;
                case 2:
                    db.createNewArtikel();
                    break;
                case 3:
                    db.printKundeVonBis(getInt("3) Geben Sie die von Kundennummer ein:"),
                            getInt("3) Geben Sie die bis Kundennummer ein:"));
                    break;
                case 4:
                    db.printSelectQuery(
                            "Select * from kunde as k left join rechnung as r on r.kundenNr = k.idkunde where r.kundenNr IS NULL;");
                    break;
                case 5:
                    db.multiplyArtikelBy(getInt("5) Geben Sie die Artikel ID ein:"));
                    break;
                default:
                    System.out.println("Ungültige Eingabe, Programm wird beendet");
            }

        } while (auswahl != 0);

        sc.close();
        db.close();
    }

    private static int getInt(String message) {
        int von;
        System.out.println(message);
        Scanner sc = new Scanner(System.in);
        von = sc.nextInt();
        return von;
    }

}