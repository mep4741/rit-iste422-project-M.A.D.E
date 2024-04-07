import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateDDLMySQLTest {
    private CreateDDLMySQL testObj;
    private String testSQLString;

    private final PrintStream stOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    //These tests assume that EdgeField and EdgeTable all tested correctly.
    @Before
    public void setUp() throws Exception{
        EdgeTable testTableA = new EdgeTable("1|Table_A");
        EdgeTable tesTableB = new EdgeTable("2|Table_B");
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
            new EdgeField("1|Field_alpha"),
            new EdgeField("2|Field_bravo"),
            new EdgeField("3|Field_A"),
            new EdgeField("4|Field_B"),
            new EdgeField("5|Field_1"),
            new EdgeField("6|Field_beta")
        });
        testObj.createDDL();//Create String
        testSQLString = testObj.getSQLString();

    }//setUp

    /**
     * A helper function mostly just to help me write assertions.
     * @param msgpt1
     * @param statementbase
     * @param end
     */
    private void sqlStrCheckAssertion(String msgpt1, String statementbase, String end){
        assertTrue(msgpt1 + "\""+statementbase+"\"",testSQLString.contains(statementbase + end));
    }

    @Test
    public void testSQLStringExistence(){
        assertNotNull("SQLString should exist by default upon creation of CreateDDLMySQL object.", testSQLString);
    }

    @Test
    public void testDatabaseNameCreationandUse(){
        assertEquals("Database Name recorded does not match expected result entered of \""+testObj.getDatabaseName()+"\".",testObj.getDatabaseName(), testObj.getDatabaseName());
        assertTrue("SQL string should include statement to create database. \"CREATE DATABASE "+testObj.getDatabaseName()+";\"",testSQLString.contains("CREATE DATABASE "+testObj.getDatabaseName()+";"));
        assertTrue("SQL string should include statement to use database. \"USE MySQLDB;\"",testSQLString.contains("USE MySQLDB;"));
    }

    @Test
    public void testTableCreation(){
        assertTrue("SQL string should include statement to create Table_A. \"CREATE TABLE Table_A\"",testSQLString.contains("CREATE TABLE Table_A ("));
        assertTrue("SQL string should include statement to create Table_B. \"CREATE TABLE Table_B\"",testSQLString.contains("CREATE TABLE Table_B ("));
    }

    @Test
    public void testTableFieldsExistence(){
        sqlStrCheckAssertion("SQL string should include statement to create field alpha", "Field_alpha", "VARCHAR(1)");
        sqlStrCheckAssertion("field alpha should be in Table A", "TABLE Table_A (\r\n\tField_alpha", "");
        sqlStrCheckAssertion("Table A contains all necessary fields.", "Table_A (\r\n"+
                        "\tField_alpha VARCHAR(1)\r\n"+
                        "\tField_A VARCHAR(1)\r\n"+
                        "\tField_1 VARCHAR(1)\r\n", 
                        "\r\n);");
    }

    @After
    public void outputTestSQLString(){
       assertTrue(testSQLString,false);
    }
}
