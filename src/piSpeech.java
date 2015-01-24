import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;


public class piSpeech {
	 private static final String VOICENAME_kevin = "kevin";
	 private String text; // string to speech
	 
	 public piSpeech(String text) {
	  this.text = text;
	 }
	 
	 public void speak() {
	  Voice voice;
	  VoiceManager voiceManager = VoiceManager.getInstance();
	  voice = voiceManager.getVoice(VOICENAME_kevin);
	  voice.allocate();
	  voice.speak(text);
	 }
	 
	 public void speaker(String text) {
	  piSpeech freeTTS = new piSpeech(text);
	  freeTTS.speak();
	 }
	 
}
