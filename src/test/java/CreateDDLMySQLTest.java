import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.StringTokenizer;

import org.junit.Before;
import org.junit.Test;

public class CreateDDLMySQLTest {
    private CreateDDLMySQL testObj;
    private String testSQLString;

    //These tests assume that EdgeField and EdgeTable all tested correctly.
    @Before
    public void setUp() throws Exception{
        EdgeTable testTableA = new EdgeTable("1|Table A");
        EdgeTable tesTableB = new EdgeTable("2|Table B");
        testTableA.addNativeField(1);
        testTableA.addNativeField(3);
        testTableA.addNativeField(5);
        testTableA.makeArrays();
        testTableA.setRelatedField(0, 2);

        tesTableB.addNativeField(2);
        tesTableB.addNativeField(4);
        tesTableB.addNativeField(6);
        tesTableB.makeArrays();
        tesTableB.setRelatedField(0, 1);
        tesTableB.addRelatedTable(1);

        testObj = new CreateDDLMySQL(new EdgeTable[]{testTableA,tesTableB}, 
        new EdgeField[]{
            new EdgeField("1|Field alpha"),
            new EdgeField("2|Field bravo"),
            new EdgeField("3|Field A"),
            new EdgeField("4|Field B"),
            new EdgeField("5|Field あ"),
            new EdgeField("6|Field beta")
        });
        testObj.createDDL();//Create String
        testSQLString = testObj.getSQLString();

    }//setUp

    @Test
    public void testSQLStringExistence(){
        assertNotNull("SQLString should exist by default upon creation of CreateDDLMySQL object.", testSQLString);
    }

    @Test
    public void testDatabaseNameCreationandUse(){
        assertEquals("Database Name recorded does not match expected default of \"MySQLDB\".","MySQLDB", testObj.getDatabaseName());
        assertTrue("SQL string should include statement to create database. \"CREATE DATABASE MySQLDB;\"",testSQLString.contains("CREATE DATABASE MySQLDB;"));
        assertTrue("SQL string should include statement to use database. \"USE MySQLDB;\"",testSQLString.contains("USE MySQLDB;"));
    }

    @Test
    public void testTableCreated(){
        boolean forcePrint = false;
        assertTrue(testSQLString, forcePrint);
    }

    @Test
    public void testTableFieldsExistence(){

    }

    @Test
    public void testNotNullFields(){

    }

    @Test
    public void testFieldDefaults(){

    }

    @Test
    public void testPrimaryKey(){

    }
    @Test
    public void testForeignKey(){

    }
}
