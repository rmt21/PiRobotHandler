
public class piInterfaceHandler {
	// main implementer of outcome
	piLEDEngine piLED = new piLEDEngine();
	piSender send = new piSender();
	
	//LED Display for Movement
	private String LEDMoveRight = "LEDMoveRight";
	private String LEDMoveLeft = "left";
	private String LEDMoveForward = "forward";
	private String LEDMoveBack = "back";
	
	public void movement(String direction) throws InterruptedException
	{
		//movement (LEDs (implemented), speech response and physical(to be implemented..))
		if (direction.equals("right"))
		{
			//send.piSendSpeechMotorServo("motor", "forward");
			send.piSendServo("servo", "pan","right");

			//send.piSendSpeechMotor("speech", "hello hello hello hello hello hello hello hello hello");
			piLED.useLedMatrixSpecifyFileBi(LEDMoveRight, LEDMoveRight);
		}
		if (direction.equals("left"))
		{
			piLED.useLedMatrixSpecifyFileBi(LEDMoveLeft, LEDMoveLeft);
		}
		if (direction.equals("forward"))
		{
			send.piSendSpeechMotor("motor", "forward");
			piLED.useLedMatrixSpecifyFileBi(LEDMoveForward, LEDMoveForward);
		}
		if (direction.equals("back"))
		{
			send.piSendSpeechMotor("motor", "backward");
			piLED.useLedMatrixSpecifyFileBi(LEDMoveBack, LEDMoveBack);
		}
	}

}
