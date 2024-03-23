import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RunEdgeConvert {

   // logger
   private static final Logger logger = LogManager.getLogger(RunEdgeConvert.class);


   public static void main(String[] args) {
      logger.info("App Started");
      EdgeConvertGUI edge = new EdgeConvertGUI();
   } // main
} // RunEdgeConvert