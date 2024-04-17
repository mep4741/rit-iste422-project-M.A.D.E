import java.io.*;
import java.util.*;
import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class EdgeConvertFileParser {
   private static final Logger logger = LogManager.getLogger(EdgeConvertFileParser.class);

   //private String filename = "test.edg";
   // private File parseFile; in edgefile
   protected String currentLine;
   protected BufferedReader br;
   protected FileReader fr;
   protected int numFigure;
   protected ArrayList alTables, alFields, alConnectors;
   protected File parseFile;
   //protected int numNativeRelatedFields; - no where? TODO
   protected int numLine;
   protected EdgeTable[] tables;
   protected EdgeField[] fields;
   protected EdgeConnector[] connectors;

   public static final String DELIM = "|";
   
   public EdgeConvertFileParser(File constructorFile) {
      numFigure = 0;
      alTables = new ArrayList();
      alFields = new ArrayList();
      alConnectors = new ArrayList();
      numLine = 0;
   }


   protected void makeArrays() { //convert ArrayList objects into arrays of the appropriate Class type
      if (alTables != null) {
         tables = (EdgeTable[])alTables.toArray(new EdgeTable[alTables.size()]);
      }
      if (alFields != null) {
         fields = (EdgeField[])alFields.toArray(new EdgeField[alFields.size()]);
      }
      if (alConnectors != null) {
         connectors = (EdgeConnector[])alConnectors.toArray(new EdgeConnector[alConnectors.size()]);
      }
   }
   
   public EdgeTable[] getEdgeTables() {
      return tables;
   }
   
   public EdgeField[] getEdgeFields() {
      return fields;
   }

} // EdgeConvertFileHandler
