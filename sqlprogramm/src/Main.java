// https://github.com/thieleju/school/tree/main/sqlprogramm

public class Main {

    public static void main(String[] args) {

        // create DB object
        DB db = new DB("192.168.2.221", "3306", "school", "test", "t3st@D3MO");

        db.createNewKunde();

        db.createNewArtikel();

        db.printKundeVonBis(3, 4);

        db.printSelectQuery(
                "Select * from kunde as k left join rechnung as r on r.kundenNr = k.idkunde where r.kundenNr IS NULL;");

        db.close();
    }
}