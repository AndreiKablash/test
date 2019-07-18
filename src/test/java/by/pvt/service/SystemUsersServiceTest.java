package by.pvt.service;

import by.pvt.basic.SystemUsers;
import config.DBUnitConfig;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class SystemUsersServiceTest extends DBUnitConfig {
    private SystemUsersService service = new SystemUsersService();

    public SystemUsersServiceTest(String name) {
        super(name);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        beforeData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader().
                        getResourceAsStream("by/pvt/system_users.xml"));
        tester.setDataSet(beforeData);
        tester.onSetup();
    }


    @Test
    public void testGetSystemUsers() throws Exception {
        List<SystemUsers> systemUsers = service.getSystemUsers();

        // Fetch database data after executing your code
        IDataSet actualData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("by/pvt/system_users.xml"));
        ITable actualTable = actualData.getTable("system_users");

        // Load expected data from an XML dataset
        IDataSet expectedData = beforeData;
        ITable expectedTable = expectedData.getTable("system_users");

        // Assert actual database table match expected table
        Assertion.assertEquals(expectedTable, actualTable);
        Assert.assertEquals(expectedTable.getRowCount(), systemUsers.size());

    }

    @Test
    public void deleteByPrimaryKey() throws Exception {
        Integer systemUserIdForDelete = 5;
        service.deleteByPrimaryKey(systemUserIdForDelete);

        // Load expected data from an XML dataset
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("by/pvt/system_users_delete.xml"));
        ITable expectedTable = expectedData.getTable("system_users");

        // Fetch database data after executing your code
        IDataSet actualData = tester.getConnection().createDataSet();
        ITable actualTable = actualData.getTable("system_users");

        // Assert actual database table match expected table
        Assertion.assertEquals(actualTable, expectedTable);
    }

    @Test
    public void testAdd() throws Exception {
        SystemUsers systemUser = new SystemUsers();
        systemUser.setId(5);
        systemUser.setUsername("User5");
        systemUser.setActive(true);
        systemUser.setDateofbirth(new Date(954547200));
        service.updateByPrimaryKey(systemUser);

        // Load expected data from an XML dataset
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("by/pvt/system_users_add.xml"));

        // Fetch database data after executing your code
        IDataSet actualData = tester.getConnection().createDataSet();

        // Assert actual database table match expected table
        String[] ignore = {"id"};
        Assertion.assertEqualsIgnoreCols(expectedData, actualData, "system_users", ignore);
    }

    @Test
    public void testUpdateByPrimaryKey() throws Exception {
        SystemUsers systemUser = new SystemUsers();
        systemUser.setId(5);
        systemUser.setUsername("guest5");
        systemUser.setActive(false);
        systemUser.setDateofbirth(new Date(954547200));

        service.updateByPrimaryKey(systemUser);

        // Load expected data from an XML dataset
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("by/pvt/system_users_update.xml"));
        ITable expectedTable = expectedData.getTable("system_users");

        // Fetch database data after executing your code
        IDataSet actualData = tester.getConnection().createDataSet();
        ITable actualTable = actualData.getTable("system_users");

        // Assert actual database table match expected table
        Assertion.assertEquals(expectedTable, actualTable);
    }
}