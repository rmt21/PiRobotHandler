import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class piSpeechProcessor implements Runnable {
	// process what speech is being passed in
	piInterfaceHandler piInterface = new piInterfaceHandler();

	@Override
	public void run() {
		try {
			processSpeech();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void processSpeech() throws IOException, InterruptedException
	{
		ArrayList<String> speechToBeProcessed = readFile();
		speechToBeProcessed.trimToSize();
		for (int i =0; i < speechToBeProcessed.size();i++)
		{			
			//movement sends to piInterfaceHandler for action
			if (speechToBeProcessed.get(i).toString().contains("Paul move right"))
			{
				piInterface.movement("right");
			}
			if (speechToBeProcessed.get(i).toString().contains("Paul move left"))
			{
				piInterface.movement("left");
			}
			if (speechToBeProcessed.get(i).toString().contains("Paul move forward"))
			{
				piInterface.movement("forward");
			}
			if (speechToBeProcessed.get(i).toString().contains("Paul move back"))
			{
				piInterface.movement("back");
			}
		}
		System.out.println("Speech set processed");
	}
	
	
	private ArrayList<String> readFile() throws IOException {
		// read in files
		ArrayList<String> speech = new ArrayList<String>();
		ArrayList<String> results = new ArrayList<String>();
		File[] files = new File("../").listFiles();
		// If this pathname does not denote a directory, then listFiles()
		// returns null.

		for (File file : files) {
			if (file.isFile()) {
				if (file.getName().contains("textToSpeech")) {
					results.add(file.getName());
				}
			}
		}
		results.trimToSize();
		for (int i = 0; i < results.size(); i++) {
			BufferedReader br = new BufferedReader(new FileReader(results
					.get(i).toString()));
			try {
				String line = br.readLine();
				while (line != null) {
					line = br.readLine();
					speech.add(line);
				}

			} finally {
				br.close();
			}
		}
		//delete loaded files
		for (File file : files) {
			if (file.isFile()) {
				if (file.getName().contains("textToSpeech")) {
					file.delete();
				}
			}
	}
		return speech;
	}



}
	
	


