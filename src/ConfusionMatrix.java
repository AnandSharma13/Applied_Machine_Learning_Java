import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ConfusionMatrix {

	public static void printConfusionMatrix(HashMap<String, Integer> map) {
		ArrayList<String> labelNames = getClassNames(map);
		System.out.print("T\\P");
		for (String predictedValues : labelNames) {
			System.out.print("\t" + predictedValues);
		}
		System.out.println();
		
		for (String actualValues : labelNames) {
			System.out.print(actualValues);
			for (String predictedValues : labelNames) {
				Integer value = map.get(actualValues+","+predictedValues);
				System.out.print("\t");
				if (value != null) {
					System.out.print(value);
				}
				else
				{
					System.out.print(0);
				}
			}
			System.out.println();
		}

	}

	public static ArrayList<String> getClassNames(HashMap<String, Integer> map) {
		Set<String> labelNames = new HashSet<String>();
		for (String key : map.keySet()) {
			String[] labels = key.split(",");
			if (labels != null && labels.length > 0) {
				labelNames.addAll(Arrays.asList(labels));
			}
		}
		ArrayList<String> sortedLabels = new ArrayList<String>();
		sortedLabels.addAll(labelNames);
		Collections.sort(sortedLabels);
		return sortedLabels;
	}

}
