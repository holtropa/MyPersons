package nl.rabobank.personenbeheer;


import java.sql.*;
import oracle.jdbc.driver.OracleDriver;


public class WritePersoonToDbase {

      private static final String INSERT_PERSOON_SQL = "INSERT INTO XZA_MYPERSOON (ID, ACHTERNAAM, BSNNUMMER, EMAIL, GESLACHT, TELEFOONNUMMER, TUSSENVOEGSELS, VOORLETTERS, HUISNUMMER, HUISNUMMERTOEVOEGING, LAND, POSTCODE, STRAAT, WOONPLAATS, GEBOORTEPLAATS, GEMEENTE) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_PERSOON_SQL = "DELETE FROM XZA_MYPERSOON WHERE ACHTERNAAM = ?";
    private static final String SELECT_PERSOON_SQL = "SELECT * FROM XZA_MYPERSOON WHERE ACHTERNAAM = ?";


    public long storePersoon(final Persoon persoon) {
        final int id = 0;
        PreparedStatement insertStatement = null;
        Connection conn = null;
        try {
            conn = getConnection();
            insertStatement = conn.prepareStatement(INSERT_PERSOON_SQL);
            insertStatement.setInt(1, getMaxPersoonID(conn));
            insertStatement.setString(2, persoon.getAchterNaam());
            insertStatement.setString(3, "leeg");
            insertStatement.setString(4, "leeg");
            insertStatement.setString(5, "leeg");
            insertStatement.setString(6, "leeg");
            insertStatement.setString(7, "leeg");
            insertStatement.setString(8, "leeg");
            insertStatement.setInt(9, 0);
            insertStatement.setString(10, "leeg");
            insertStatement.setString(11, "leeg");
            insertStatement.setString(12, "leeg");
            insertStatement.setString(13, "leeg");
            insertStatement.setString(14, "leeg");
            insertStatement.setString(15, "leeg");
            insertStatement.setString(16, "leeg");
            insertStatement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
        }
        return id;
    }

    public int removePersoon(final Persoon persoon) {
        final int id = 0;
        PreparedStatement insertStatement = null;
        Connection conn = null;
        try {
            conn = getConnection();
            insertStatement = conn.prepareStatement(DELETE_PERSOON_SQL);
            insertStatement.setString(1, persoon.getAchterNaam());
            insertStatement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
        }
        return id;
    }

    public int selectPersoon(final Persoon persoon) {
        final int id = 0;
        PreparedStatement insertStatement = null;
        Connection conn = null;
        try {
            conn = getConnection();
            insertStatement = conn.prepareStatement(SELECT_PERSOON_SQL);
            insertStatement.setString(1, persoon.getAchterNaam());
            insertStatement.execute();
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

    public static int getMaxPersoonID(Connection connection){
        int id=0;
        String sql = "SELECT  NVL(MAX(ID),0)+1  FROM XZA_MYPERSOON ";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            if(statement!=null){
                try{
                    ResultSet results = statement.executeQuery();
                    if(results != null){
                        try{
                            if(results.next()){
                                id = results.getInt(1);
                            }
                        }
                        catch(Exception resultSetException) {resultSetException.printStackTrace();
                        }
                        results.close();
                    }
                }
                catch(Exception statmentExcption){statmentExcption.printStackTrace();
                }
                statement.close();
            }
        } catch (Exception generalException){generalException.printStackTrace();
        }
        return id;
    }

}
