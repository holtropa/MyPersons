package nl.rabobank.personenbeheer;


import java.sql.*;
import oracle.jdbc.driver.OracleDriver;


public class WritePersoonToDbase {


    private static final String INSERT_PERSOON_SQL = "INSERT INTO XZA_MYPERSOON (ID) VALUES (?)";
    public static final String METHOD_TO_GET_NATIVE_CONNECTION = "getInnermostDelegate";


    public long storePersoon(final Persoon persoon) {
        final int id = 0;
        PreparedStatement insertStatement = null;
        Connection conn = null;

        try {
            conn = getConnection();
            insertStatement = conn.prepareStatement(INSERT_PERSOON_SQL);
            insertStatement.setInt(1, id);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
        }
        return id;
    }


    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "holtropa", "holtropia");
        } catch (Exception e) {
            return connection;
        }
        return connection;
    }



}
