import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextField;

public class piSpeechProcessorLocal {
	// process what speech is being passed in
	piInterfaceHandler piInterface;
	String ipAddress;
	public piSpeechProcessorLocal(JTextField console, String ipAddress)
	{
		piInterface = new piInterfaceHandler(console, ipAddress);
		this.ipAddress = ipAddress;
	}


	
	public void processSpeech(String speech) throws IOException, InterruptedException
	{			
			//movement sends to piInterfaceHandler for action
			if (speech.contains("move right"))
			{
				//piInterface.movement("right");
				piInterface.motorMovement("right", ipAddress);
			}
			if (speech.contains("move left"))
			{
				//piInterface.movement("left");
				piInterface.motorMovement("left", ipAddress);
			}
			if (speech.contains("move forward"))
			{
				//piInterface.movement("forward");
				piInterface.motorMovement("forward", ipAddress);
			}
			if (speech.contains("move back"))
			{
				//piInterface.movement("back");
				piInterface.motorMovement("back", ipAddress);
			}
		System.out.println("Speech set processed");
	}
}
	
	


