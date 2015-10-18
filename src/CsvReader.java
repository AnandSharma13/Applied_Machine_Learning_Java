
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CsvReader {


  @SuppressWarnings({ "resource", "unchecked" })
public ArrayList <ArrayList<String>> readFile(String path) throws IOException {

	
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
	@SuppressWarnings("rawtypes")
	ArrayList result = new ArrayList<>();
	
	
		br = new BufferedReader(new FileReader(path));
		while ((line = br.readLine()) != null) {
 
			String[] data = line.split(cvsSplitBy);
			ArrayList<String> list =   new ArrayList<String>();
			  for (String e : data)  
		      {  
				  list.add(e);
		      }  
			result.add(list);
		}
	return result;
	
  }

}