package nl.rabobank.personenbeheer;


import org.dbunit.operation.DatabaseOperation;

import java.sql.*;

public class H2MemoryDatabase {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) throws Exception {
        try {
            insertWithStatement();
            insertWithPreparedStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void insertWithPreparedStatement() throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement createPreparedStatement = null;
        PreparedStatement insertPreparedStatement = null;
        PreparedStatement selectPreparedStatement = null;

        String CreateQuery = "CREATE TABLE XZA_PERSOON(id int primary key, name varchar(255))";

     //   String CreateQuery = "CREATE TABLE XZA_PERSOON(ID int primary key, ACHTERNAAM varchar(255), BSNNUMMER varchar(255), EMAIL varchar(255), GESLACHT varchar(255), TELEFOON=NUMMER varchar(255), TUSSENVOEGSELS varchar(255)"
      //      + ", VOORLETTERS varchar(255), HUISNUMMER varchar(255), HUISNUMMERTOEVOEGING varchar(255), LAND varchar(255), POSTCODE varchar(255), STRAAT varchar(255)"
     //      + ", WOONPLAATS varchar(255), GEBOORTEPLAATS varchar(255), GEMEENTE varchar(255)";
        String InsertQuery = "INSERT INTO XZA_PERSOON" + "(id, name) values" + "(?,?)";
        String SelectQuery = "select * from XZA_PERSOON";

        try {
            connection.setAutoCommit(false);

            createPreparedStatement = connection.prepareStatement(CreateQuery);
            createPreparedStatement.executeUpdate();
            createPreparedStatement.close();

            insertPreparedStatement = connection.prepareStatement(InsertQuery);
            insertPreparedStatement.setInt(1, 1);
            insertPreparedStatement.setString(2, "Jose");
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();

            selectPreparedStatement = connection.prepareStatement(SelectQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();
            System.out.println("H2 In-Memory Database inserted through PreparedStatement");
            while (rs.next()) {
                System.out.println("Id " + rs.getInt("id") + " Name " + rs.getString("name"));
            }
            selectPreparedStatement.close();

            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    private static void insertWithStatement() throws SQLException {
        Connection connection = getDBConnection();
        Statement stmt = null;
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            stmt.execute("CREATE TABLE XZA_PERSOON(id int primary key, name varchar(255))");
            stmt.execute("INSERT INTO XZA_PERSOON(id, name) VALUES(1, 'Anju')");
            stmt.execute("INSERT INTO XZA_PERSOON(id, name) VALUES(2, 'Sonia')");
            stmt.execute("INSERT INTO XZA_PERSOON(id, name) VALUES(3, 'Asha')");

            ResultSet rs = stmt.executeQuery("select * from XZA_PERSOON");
            System.out.println("H2 In-Memory Database inserted through Statement");
            while (rs.next()) {
                System.out.println("Id " + rs.getInt("id") + " Name " + rs.getString("name"));
            }

            stmt.execute("DROP TABLE XZA_PERSOON");
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

  //  private static Connection getDBConnection() {\
    public static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
}
