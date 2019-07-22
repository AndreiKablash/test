package by.pvt.service;

import by.pvt.basic.SystemUsers;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import java.util.List;
import java.util.logging.Logger;

public class SystemUsersServiceLessonTest extends DBTestCase {
    private static final Logger log = Logger.getLogger(SystemUsersServiceLessonTest.class.getName());
    SystemUsersService objectUnderTest;

    public SystemUsersServiceLessonTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:3306/hello_mysql_junit");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "root");

    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(
                SystemUsersServiceLessonTest.class.getResourceAsStream("system_users.xml"));
    }

    @Test
    public void testGetSystemUsers() throws Exception {
        // given
        objectUnderTest = new SystemUsersService();
        objectUnderTest.setSqlSessionFactory(
                new SqlSessionFactoryBuilder().build(
                        Resources.getResourceAsStream("by/pvt/service/mybatis-config-junit.xml")
                ));
        List<SystemUsers> list = objectUnderTest.getSystemUsers();
        assertEquals(4, list.size());
    }
//    @Test
//    public void add() {
//    }
//
//    @Test
//    public void addAll() {
//    }
}