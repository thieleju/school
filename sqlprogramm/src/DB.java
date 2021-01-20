
// https://github.com/thieleju/school/tree/main/sqlprogramm

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class DB {
    String hostname;
    String port;
    String dbname;
    String user;
    String password;
    Connection con = null;

    DB(String hostname, String port, String dbname, String user, String password) {

        this.hostname = hostname;
        this.port = port;
        this.dbname = dbname;
        this.user = user;
        this.password = password;

        try {
            System.out.println("-> Driver loaded");
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            System.err.println("Unable to load driver!");
            e.printStackTrace();
        }

        try {
            String url = "jdbc:mysql://" + hostname + ":" + port + "/" + dbname;
            System.out.println("->  Connected to " + url);

            con = DriverManager.getConnection(url, user, password);

        } catch (SQLException sqle) {
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("SQLState: " + sqle.getSQLState());
            System.out.println("VendorError: " + sqle.getErrorCode());
            sqle.printStackTrace();
        }

    }

    void createNewKunde() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Geben Sie Ihren Vornamen ein: ");
        String vorname = sc.nextLine();
        System.out.println("Geben Sie Ihren Nachname ein: ");
        String nachname = sc.nextLine();
        System.out.println("Geben Sie Ihre Straße ein: ");
        String strasse = sc.nextLine();
        System.out.println("Geben Sie Ihre PLZ ein: ");
        String plz = sc.nextLine();
        System.out.println("Geben Sie Ihren Ort ein: ");
        String ort = sc.nextLine();
        sc.close();

        try {
            String sqlQuery = "insert into kunde (vorname, nachname, strasse, plz, ort) values (?,?,?,?,?);";
            PreparedStatement ps = con.prepareStatement(sqlQuery);

            ps.setString(1, vorname);
            ps.setString(2, nachname);
            ps.setString(3, strasse);
            ps.setString(4, plz);
            ps.setString(5, ort);

            ps.execute();

            System.out.println("-> Kunde " + vorname + " " + nachname + " erfolgreich angelegt!");
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Failed to create new kunde");
        }
    }

    void createNewArtikel() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Geben Sie die Bezeichnung ein: ");
        String bezeichnung = sc.nextLine();
        System.out.println("Geben Sie den NettoPreis ein: ");
        Double nettoPreis = sc.nextDouble();
        System.out.println("Geben Sie die MwSt ein: ");
        Double mwst = sc.nextDouble();
        sc.close();

        try {
            String sqlQuery = "insert into artikel (bezeichnung, nettoPreis, mwSt) values (?,?,?);";
            PreparedStatement ps = con.prepareStatement(sqlQuery);

            ps.setString(1, bezeichnung);
            ps.setDouble(2, nettoPreis);
            ps.setDouble(3, mwst);

            ps.execute();

            System.out.println("-> Artikel " + bezeichnung + " erfolgreich angelegt!");
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Failed to create new artikel");
        }
    }

    void printKundeVonBis(int von, int bis) {
        printSelectQuery("select * from kunde where idkunde between " + von + " and " + bis + ";");
    }

    void printSelectQuery(String sqlQuery) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            printTable(rs);

            stmt.close();

        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Failed to search for kunden");
        }
    }

    void printTable(ResultSet rs) {
        try {
            int columnsNumber = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1)
                        System.out.print(" | ");
                    String columnValue = rs.getString(i);
                    System.out.print(rs.getMetaData().getColumnName(i) + ": " + columnValue);
                }
                System.out.println("");
            }
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Failed to search for kunden");
        }
    }

    void suchen() {

        String name;
        int id;

        try {
            Statement stmt = con.createStatement();
            ResultSet rs;

            stmt = con.createStatement();

            Scanner sc = new Scanner(System.in);
            System.out.print("Name: ");
            String eingabe = sc.next();

            String sqlQuery = "SELECT * FROM kunde;";

            // Die Anfrage wird an die DB gesendet und das Resultset wird zurueckgegeben
            // und kann dann mit der Variable rs weiterverarbeitet werden.
            // Die Klasse Statement besitzt noch andere Methoden, die ggf. benutzt werden
            // muessen
            rs = stmt.executeQuery(sqlQuery);

            // Dies ist normalerweise unnoetig, man erhaelt so die Anzahl der Spalten
            int spalten = rs.getMetaData().getColumnCount();
            System.out.println("Anzahl Spalten: " + spalten);

            // mit rs.next() wird immer der naechste Datensatz geholt, bis es keine mehr
            // gibt.
            // Auch die Klasse Resultset hat andere Methoden, die recht nuetzlich sind.
            while (rs.next()) {
                // mit get... erhaelt man den Wert der jeweiligen Spalte (1,2,3,....)
                id = rs.getInt(1);
                name = rs.getString(2);

                System.out.println("Id: " + id + ",  Name: " + name);
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Fehler: " + e);
        }

    }

    public void close() {
        try {
            System.out.println("-> Datenbank-Verbindung beendet");
            con.close();
        } catch (SQLException e) {
            System.out.println("Fehler: " + e);
        }
    }
}