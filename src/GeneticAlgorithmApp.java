import java.io.BufferedReader;
import java.io.InputStreamReader;


public class GeneticAlgorithmApp {

	public static void main(String args[]) {
		
		SystemParameters params = XmlConfigReader.ReadConfig("Config.xml");
		TextFileLogListener.LogPath = params.getLogPath();
		
		ConsoleLogListener consoleListener = new ConsoleLogListener(); 
		Logger.attachListener(consoleListener);

		TextFileLogListener fileListener = new TextFileLogListener();
		Logger.attachListener(fileListener);
		
		Logger.logMessage("--------------------------------");
		Logger.logMessage("Genetic Algorithm Implementation");
		Logger.logMessage("--------------------------------");
		Logger.logMessage(params.toString());
		
		GeneticAlgorithm genetic = new GeneticAlgorithm(params);
		genetic.run();
		
		Logger.logMessage("Genetic Algorithm completed");
	}
	
}
