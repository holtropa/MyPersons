package nl.rabobank.personenbeheer;

import org.dbunit.*;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
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


    public IntegratieTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.h2.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");
    }

    protected IDataSet getDataSet() throws Exception {

        File file = new File(getClass().getClassLoader().getResource("export.xml").getFile());
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
        IDatabaseConnection con = new DatabaseConnection(mydbase.getDBConnection());

        // initialize your dataset here
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        IDataSet datasets = getDataSet();


        DatabaseOperation.REFRESH.execute(con, new CompositeDataSet(datasets)); // Import


        try {
            DatabaseOperation.CLEAN_INSERT.execute(con, datasets);
        } finally {
            con.close();
        }
    }

    private PrepAndExpectedTestCase tc; // injected or instantiated, already configured

    @Test
    public void testExample() throws Exception {
        final String[] prepDataFiles = {}; // define prep files
        final String[] expectedDataFiles = {}; // define expected files
        final VerifyTableDefinition[] tables = {}; // define tables to verify
        final PrepAndExpectedTestCaseSteps testSteps = () -> {
            // execute test steps

            return null; // or an object for use outside the Steps
        };

        tc.runTest(tables, prepDataFiles, expectedDataFiles, testSteps);
    }
}
