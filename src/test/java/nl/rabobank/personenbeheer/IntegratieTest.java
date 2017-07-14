package nl.rabobank.personenbeheer;

import org.dbunit.*;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDate;

import static nl.rabobank.personenbeheer.H2MemoryDatabase.getDBConnection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class IntegratieTest extends DBTestCase {


    public IntegratieTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.h2.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");
    }

    protected IDataSet getDataSet() throws Exception {

        File file = new File(getClass().getClassLoader().getResource("export2.xml").getFile());
        return new FlatXmlDataSetBuilder().build(new FileInputStream(file));

    }

    protected void setUpDatabaseConfig(DatabaseConfig config) {
        config.setProperty(DatabaseConfig.PROPERTY_BATCH_SIZE, new Integer(97));
        config.setFeature(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, true);
    }

    @Before
    public void setUp() throws Exception {

        // initialize your database connection here
        H2MemoryDatabase mydbase = new H2MemoryDatabase();
        IDatabaseConnection con = new DatabaseConnection(getDBConnection());

        DatabaseConfig dbConfig = con.getConfig();

                                                            // added this line to get rid of the warning
                                                            dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new OracleDataTypeFactory());

        // initialize your dataset here
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        IDataSet datasets = getDataSet();

        this.createTable();
 //       this.insertTable();
  //      this.selectTable();

  //      DatabaseOperation.REFRESH.execute(con, new CompositeDataSet(datasets)); // Import


        try {
            DatabaseOperation.CLEAN_INSERT.execute(con, datasets);
            this.selectTable();
        } finally {
            con.close();
        }
    }

    private PrepAndExpectedTestCase tc; // injected or instantiated, already configured

    @Test
    public void testExample() throws Exception {
        final String[] prepDataFiles = {"export2.xml"}; // define prep files
        final String[] expectedDataFiles = {"1","Anne"}; // define expected files
        final VerifyTableDefinition[] tables = {}; // define tables to verify
        final PrepAndExpectedTestCaseSteps testSteps = () -> {
            // execute test steps
            insertTable("1", "Anne");
            insertTable("2", "Piet");
            insertTable("3", "Henk");
            insertTable("4", "Maarten");

            return null; // or an object for use outside the Steps
        };

        tc.runTest(tables, prepDataFiles, expectedDataFiles, testSteps);
    }

    public void createTable() throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement createPreparedStatement = null;

//        String CreateQuery = "CREATE TABLE XZA_PERSOON(id number primary key, " +
        String CreateQuery = "CREATE TABLE XZA_PERSOON(id number primary key, " +
                "ACHTERNAAM varchar(255))";
//                "BSNNUMMER varchar(255)," +
//                "EMAIL varchar(255)," +
//                "GESLACHT varchar(255)," +
//                "TELEFOONNUMMER varchar(255)," +
//                "TUSSENVOEGSELS varchar(255)," +
//                "VOORLETTERS varchar(255)," +
//                "HUISNUMMER number," +
//                "HUISNUMMERTOEVOEGING varchar(255)," +
//                "LAND varchar(255)," +
//                "POSTCODE varchar(255)," +
//                "STRAAT varchar(255)," +
//                "WOONPLAATS varchar(255)," +
//                "GEBOORTEPLAATS varchar(255)," +
//                "GEMEENTE  varchar(255))";

        try {
            connection.setAutoCommit(false);

            createPreparedStatement = connection.prepareStatement(CreateQuery);
            createPreparedStatement.executeUpdate();
            createPreparedStatement.close();

        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public void selectTable() throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement createPreparedStatement = null;
//        Persoon persoon = new Persoon();
        String CreateQuery = "SELECT * FROM XZA_PERSOON";

        try {
            connection.setAutoCommit(false);
//            ResultSet result = createPreparedStatement.executeQuery();

            createPreparedStatement = connection.prepareStatement(CreateQuery);
            ResultSet result = createPreparedStatement.executeQuery();
            while (result.next()) {
                Persoon persoon = new Persoon();
                persoon.setId(result.getInt(1));
                persoon.setAchterNaam(result.getString(2));
                System.out.println("persoon is: " + persoon.toString());
 //               mypersonen.add(persoon);
            }
            createPreparedStatement.close();

        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }


    public void insertTable(String id, String achternaam) throws SQLException {
//        String insert_sql = "INSERT INTO XZA_MYPERSOON (ID, ACHTERNAAM, BSNNUMMER, EMAIL, GESLACHT, TELEFOONNUMMER, TUSSENVOEGSELS, VOORLETTERS, HUISNUMMER, HUISNUMMERTOEVOEGING, LAND, POSTCODE, STRAAT, WOONPLAATS, GEBOORTEPLAATS, GEMEENTE) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String insert_sql = "INSERT INTO XZA_PERSOON (ID, ACHTERNAAM) " +
                "VALUES (?, ?)";

//        Connection connection = null;
        Connection connection = getDBConnection();
        Persoon persoon = new Persoon();
        try {
            PreparedStatement statement = connection.prepareStatement(insert_sql);
            if (statement != null) {
//                persoon.setId(getMaxPersoonID(connection));
//                statement.setInt(1, persoon.getId());
                statement.setInt(1, 1);
                statement.setString(2, "Simon");
                try {
                    int count = statement.executeUpdate();
                    boolean ok = count == 1;
                    //         if(!ok) id=0;
                } catch (Exception statmentExcption) {
                    statmentExcption.printStackTrace();
                    statmentExcption.printStackTrace(); //return 0 ;
                }
                statement.close();
            }
        } catch (Exception generalException) {
            generalException.printStackTrace();
            generalException.printStackTrace(); //return 0;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) { /* ignored */}
            }
        }
 //       return persoon;
    }

}
