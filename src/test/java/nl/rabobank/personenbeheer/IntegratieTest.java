package nl.rabobank.personenbeheer;

import org.dbunit.*;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class IntegratieTest extends DBTestCase {

//    public IntegratieTest(String name)
//    {
//        super(name);
//    }

    public IntegratieTest(String name)
    {
        super( name );
//        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.hsqldb.jdbcDriver" );
//        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:hsqldb:sample" );
//        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "sa" );
//        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.h2.Driver" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "" );
        H2MemoryDatabase mydbase = new H2MemoryDatabase();
      //  H2MemoryDatabase.main();
       // H2MemoryDatabase.insertWithPreparedStatement();
       // mydbase.main(["",""]);
        // System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "" );
//        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.hsqldb.jdbcDriver" );
//        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "database.connection.url" );
//        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "database.connection.user" );
//        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "database.connection.password" );
//        // System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "" );
    }

    protected IDataSet getDataSet() throws Exception
    {

        File file = new File(getClass().getClassLoader().getResource("export.xml").getFile());
        return new FlatXmlDataSetBuilder().build(new FileInputStream(file));

    }

    /**
     * Override method to set custom properties/features
     */
    protected void setUpDatabaseConfig(DatabaseConfig config) {
        config.setProperty(DatabaseConfig.PROPERTY_BATCH_SIZE, new Integer(97));
        config.setFeature(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, true);
    }

//    @Before
//    public void setUp() throws Exception
//    {
//
//        // initialize your database connection here
//        IDatabaseConnection connection = null;
//        // ...
//
//        // initialize your dataset here
//        IDataSet dataSet = null;
//        // ...
//
//        try
//        {
//            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
//        }
//        finally
//        {
//            connection.close();
//        }
//    }

//    @Override
//    protected DatabaseOperation getSetUpOperation() throws Exception
//    {
//        dbConnection = DriverManager.getConnection("jdbc:hsqldb:mem:tests", "sa", "");
//        createTables();
//        return DatabaseOperation.REFRESH;
//    }
//
//    private void createTables() throws Exception {
//
//        PreparedStatement statement = dbConnection
//                .prepareStatement("CREATE TABLE digidauthsessions ( "
//                        + "id INTEGER IDENTITY NOT NULL PRIMARY KEY,"
//                        + "request_token VARCHAR(50) NOT NULL,"
//                        + "response_token VARCHAR(50) NOT NULL,"
//                        + "relatienr VARCHAR(50) NOT NULL,"
//                        + "digidnameid VARCHAR(1000) NOT NULL" + ")");
//        statement.execute();
//        statement.close();
//    }

    private PrepAndExpectedTestCase tc; // injected or instantiated, already configured

    @Test
    public void testExample() throws Exception
    {
        final String[] prepDataFiles = {}; // define prep files
        final String[] expectedDataFiles = {}; // define expected files
        final VerifyTableDefinition[] tables = {}; // define tables to verify
        final PrepAndExpectedTestCaseSteps testSteps = () -> {
            // execute test steps

            return null; // or an object for use outside the Steps
        };

        tc.runTest(tables, prepDataFiles, expectedDataFiles, testSteps);
    }

    @Test
    public void write_a_person_to_dbase() {
        WritePersoonToDbase writePersoonToDbase = new WritePersoonToDbase();
        Persoon persoon = new Persoon("Piet", LocalDate.of(1965, 12, 12));
        PersoonInvoer.personen.add(persoon);
        int itId=persoon.getId();
        writePersoonToDbase.storePersoon(persoon);
        int zoekId = writePersoonToDbase.selectPersoon(persoon);
        assertEquals(zoekId,PersoonInvoer.personen.get(0).getId());
        writePersoonToDbase.removePersoon(persoon);
    }
}
