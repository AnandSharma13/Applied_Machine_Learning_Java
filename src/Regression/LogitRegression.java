package Regression;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class LogitRegression {

	public static double[] findWeightVector(ArrayList<ArrayList<String>> data) {
		int length = data.get(0).size();
		double[] weightVector = new double[length - 1];
		double[] prevWeightVector = new double[length - 1];
		while (true) {
			double[] gradientVector = new double[length - 1];
			
			if (IsConverge(prevWeightVector, weightVector))
				break;
			else
				prevWeightVector = Arrays.copyOf(weightVector, weightVector.length);

			for (int i = 1; i < data.size(); i++) {
				double p = findSigmoidOutput(data, i, length, weightVector);
				double error = Double.parseDouble(data.get(i).get(length - 1)) - p;
				for (int k = 0; k < length - 1; k++) {
					gradientVector[k] = gradientVector[k] + error * Double.parseDouble(data.get(i).get(k));
					gradientVector[k] = gradientVector[k] * 0.001;
				}
				for (int m = 0; m < weightVector.length; m++) {
					weightVector[m] = weightVector[m] + gradientVector[m];
				}
			}
		}
		return weightVector;
	}

	public static void runTest(ArrayList<ArrayList<String>> data, double[] weightVector) {
		int length = data.get(0).size();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 1; i < data.size(); i++) {
			double predictedLabel = findSigmoidOutput(data, i, length, weightVector);
			double trueLabel = Double.parseDouble(data.get(i).get(length - 1));
			
			int adjustedLabel = (predictedLabel > 0.5) ? 1 : 0;
			String labels = (int) trueLabel + "," + adjustedLabel;
			if (map.containsKey(labels))
				// if key already exists increase the count
				map.put(labels, map.get(labels) + 1);
			else
				map.put(labels, 1);
		}
		ConfusionMatrix.printConfusionMatrix(map);
	}

	public static double findSigmoidOutput(ArrayList<ArrayList<String>> data, int i, int length,
			double[] weightVector) {
		int sum = 0;
		for (int j = 0; j < length - 1; j++) {
			sum += Double.parseDouble(data.get(i).get(j)) * weightVector[j];
		}
		double probability = 1 / (1 + Math.exp(-sum));
		return probability;
	}

	public static boolean IsConverge(double[] Vector1, double[] Vector2) {
		double sum = 0;
		for (int i = 0; i < Vector1.length; i++) {
			double difference = Vector2[i] - Vector1[i];
			sum += difference;
		}
		sum = Math.abs(sum);
		if (sum != 0.0 && sum < 0.00001) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		CsvReader reader = new CsvReader();
		String trainFile = "src/zoo-train.csv";
		String testFile = "src/zoo-test.csv";
		try {
			ArrayList<ArrayList<String>> testData = reader.readFile(trainFile);
			double[] weightVector = findWeightVector(testData);
			ArrayList<ArrayList<String>> trainData = reader.readFile(testFile);
			runTest(trainData, weightVector);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
