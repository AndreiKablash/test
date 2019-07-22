package by.pvt.service;

import by.pvt.basic.SystemUsers;
import config.DBUnitConfig;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemUsersServiceTest extends DBUnitConfig {
    private static Logger log = Logger.getLogger(SystemUsersService.class.getName());
    private SystemUsersService service;

    public SystemUsersServiceTest(String name) {
        super(name);
        service = new SystemUsersService();
        try {
            service.setSqlSessionFactory(
                    new SqlSessionFactoryBuilder().build(
                            Resources.getResourceAsStream("by/pvt/service/mybatis-config-junit.xml")
                    ));
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        beforeData = new FlatXmlDataSetBuilder().build(
                SystemUsersServiceTest.class.getResourceAsStream("system_users.xml"));
        tester.setDataSet(beforeData);
        tester.onSetup();
    }


    @Test
    public void testGetSystemUsers() throws Exception {
        List<SystemUsers> systemUsers = service.getSystemUsers();

        // Fetch database data after executing your code
        IDataSet actualData = new FlatXmlDataSetBuilder().build(
                SystemUsersServiceTest.class.getResourceAsStream("system_users.xml"));
        ITable actualTable = actualData.getTable("system_users");

        // Load expected data from an XML dataset
        IDataSet expectedData = beforeData;
        ITable expectedTable = expectedData.getTable("system_users");

        // Assert actual database table match expected table
        Assertion.assertEquals(expectedTable, actualTable);
        Assert.assertEquals(expectedTable.getRowCount(), systemUsers.size());

    }

    @Test
    public void testDeleteByPrimaryKey() throws Exception {
        Integer systemUserIdForDelete = 4;
        service.deleteByPrimaryKey(systemUserIdForDelete);

        // Load expected data from an XML dataset
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                SystemUsersServiceTest.class.getResourceAsStream("system_users_delete.xml"));
        ITable expectedTable = expectedData.getTable("system_users");

        // Fetch database data after executing your code
        IDataSet actualData = tester.getConnection().createDataSet();
        ITable actualTable = actualData.getTable("system_users");

        // Assert actual database table match expected table
        Assert.assertEquals(expectedTable.getRowCount(), actualTable.getRowCount());
    }

    @Test
    public void testAdd() throws Exception {
        SystemUsers systemUser = new SystemUsers();
        systemUser.setId(5);
        systemUser.setUsername("guest5");
        systemUser.setActive(true);
        systemUser.setDateofbirth(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2000-05-05 00:00:00"));
        service.add(systemUser);

        // Load expected data from an XML dataset
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                SystemUsersServiceTest.class.getResourceAsStream("system_users_add.xml"));
        ITable expectedTable = expectedData.getTable("system_users");

        // Fetch database data after executing your code
        IDataSet actualData = tester.getConnection().createDataSet();
        ITable actualTable = actualData.getTable("system_users");

        // Assert actual database table match expected table
        Assert.assertEquals(expectedTable.getRowCount(), actualTable.getRowCount());
    }

    @Test
    public void testUpdateByPrimaryKey() throws Exception {
        SystemUsers systemUser = new SystemUsers();
        systemUser.setId(4);
        systemUser.setUsername("user4");
        systemUser.setActive(true);
        systemUser.setDateofbirth(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2000-04-01 00:00:00"));
        service.updateByPrimaryKey(systemUser);

        // Load expected data from an XML dataset
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                SystemUsersServiceTest.class.getResourceAsStream("system_users_update.xml"));
        ITable expectedTable = expectedData.getTable("system_users");

        // Fetch database data after executing your code
        IDataSet actualData = tester.getConnection().createDataSet();
        ITable actualTable = actualData.getTable("system_users");

        // Assert actual database table match expected table
        Assertion.assertEquals(expectedTable, actualTable);
    }
}