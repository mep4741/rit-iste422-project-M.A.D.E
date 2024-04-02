import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CreateDDLMySQLTest {
    private CreateDDLMySQL testObj;
    private String testSQLString;


    @Before
    public void setUp() throws Exception{
        testObj = new CreateDDLMySQL(null, null);
        testSQLString = testObj.getSQLString();
    }//setUp

    @Test
    public void testDefaultDatabaseName(){
        assertEquals("Database Name recorded does not match expected default of \"MySQLDB\".","MySQLDB", testObj.getDatabaseName());
    }

    @Test
    public void testSQLStringExistence(){
        
    }

    @Test
    public void testDatabaseCreation(){

    }

    @Test
    public void testDatabaseUsed(){

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
