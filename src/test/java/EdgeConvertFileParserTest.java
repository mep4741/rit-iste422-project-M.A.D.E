import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;


public class EdgeConvertFileParserTest {
    EdgeConvertFileParser edgeTestObj;
    EdgeTable edgeTableTestObj;
    EdgeConvertFileParser saveTestObj;

    @Before
    public void setUp() throws Exception{
        //Begin by testing a save file.
        saveTestObj = new SaveFile(null);
        edgeTestObj = new EdgeFile(null);
    } //setUp

    @Test
    public void testParseSaveFile(){
        
    }

    @Test
    public void testIsTableDup_yes(){
        //Tests if a table passed is a duplicate of the one stored in the edge table.
    }
}
