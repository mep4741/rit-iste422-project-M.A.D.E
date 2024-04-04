import static org.junit.Assert.*;

import java.lang.reflect.Array;

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
        EdgeTable[] tables = new EdgeTable[]{
            new EdgeTable("1|Table a|Field alpha|Field A"),
            new EdgeTable("2|Table b|Field bravo|Field B")
        };
        EdgeField[] fields = new EdgeField[]{
            new EdgeField("1|Field alpha"),
            new EdgeField("2|Field bravo"),
            new EdgeField("3|Field A"),
            new EdgeField("4|Field B")
        };
        testObj = new CreateDDLMySQL(tables, fields);
        testObj.createDDL();//Create String
        testSQLString = testObj.getSQLString();
    }//setUp

    @Test
    public void testSQLStringExistence(){
        assertNotNull("SQLString should exist by default upon creation of CreateDDLMySQL object.", testSQLString);
    }

    @Test
    public void testDefaultDatabaseNameCreationandUse(){
        assertEquals("Database Name recorded does not match expected default of \"MySQLDB\".","MySQLDB", testObj.getDatabaseName());
        assertTrue("SQL string should include statement to create database. \"CREATE DATABASE MySQLDB;\"",testSQLString.contains("CREATE DATABASE MySQLDB;"));
        assertTrue("SQL string should include statement to use database. \"USE MySQLDB;\"",testSQLString.contains("USE MySQLDB;"));
    }

    @Test
    public void testTableCreated(){
        
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
