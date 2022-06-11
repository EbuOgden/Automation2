import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utility {
  public static List<String[]> readFromCSV(String filePath) throws IOException {

    List<String[]> fileContent = new ArrayList<>();
    BufferedReader br = null;

    try{
      br = new BufferedReader(new FileReader(new File(filePath)));

      String line = br.readLine();// skip the first line

      while((line = br.readLine()) != null){
        fileContent.add(line.split(","));
      }

      return fileContent;
    }catch(FileNotFoundException e){
      System.out.println("The given file does not exist.");
    }finally {
      br.close();
    }

    return fileContent;
  }
}
