
// https://github.com/thieleju/school/tree/main/sqlprogramm

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class DB {

    Connection con = null;
    Scanner sc = new Scanner(System.in);

    DB(String hostname, String port, String dbname, String user, String password) {

        try {
            System.out.println("-> Driver loaded");
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            System.err.println("Unable to load driver!");
            e.printStackTrace();
        }

        try {
            String url = "jdbc:mysql://" + hostname + ":" + port + "/" + dbname;
            System.out.println("-> Connected to " + url);

            con = DriverManager.getConnection(url, user, password);

        } catch (SQLException sqle) {
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("SQLState: " + sqle.getSQLState());
            System.out.println("VendorError: " + sqle.getErrorCode());
            sqle.printStackTrace();
        }

    }

    void createNewKunde() {
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
        System.out.println("Geben Sie die Bezeichnung ein: ");
        String bezeichnung = sc.nextLine();
        System.out.println("Geben Sie den NettoPreis ein: ");
        Double nettoPreis = sc.nextDouble();
        System.out.println("Geben Sie die MwSt ein: ");
        Double mwst = sc.nextDouble();

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

    void multiplyArtikelBy(int id) {
        try {
            // read artikel nettopreis
            String sqlQuery = "select * from artikel where idartikel = " + id + ";";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            rs.next();
            // necessary because rs.getString() returns with , instead of .
            String valueWithComma = rs.getString("nettoPreis").replace(",", ".");
            double nettoPreis = Double.parseDouble(valueWithComma);

            // logs
            System.out.println("Query: " + sqlQuery);
            System.out.println("Aktueller Nettopreis: " + nettoPreis);

            // read multiplyer
            System.out.println("Geben sie die x Prozent ein, mit denen der Nettopreis erhöht werden soll");
            double multiplyer = sc.nextDouble();

            // set new value
            sqlQuery = "update artikel set nettoPreis = ?";
            double newValue = (1 + multiplyer / 100) * nettoPreis;
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ps.setDouble(1, newValue);
            ps.execute();

            System.out.println(
                    "Successfully changed nettoPreis of artikel " + id + " from " + nettoPreis + " to " + newValue);

        } catch (SQLException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void printSelectQuery(String sqlQuery) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            System.out.println("Query: " + sqlQuery);

            // print table
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

            stmt.close();

        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Failed to search for kunden");
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