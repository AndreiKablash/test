package config;

import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBUnitConfig extends DBTestCase {
    private Properties property;
    protected IDatabaseTester tester;
    protected IDataSet beforeData;

    @Before
    public void setUp() throws Exception {
        tester = new JdbcDatabaseTester(
                property.getProperty("db.driver"),
                property.getProperty("db.url"),
                property.getProperty("db.username"),
                property.getProperty("db.password"));
    }

    public DBUnitConfig(String name) {
        super(name);
        property = new Properties();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.config.properties")) {
            if (is != null) {
                property.load(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, property.getProperty("db.driver"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, property.getProperty("db.url"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, property.getProperty("db.username"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, property.getProperty("db.password"));
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return beforeData;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }

}
