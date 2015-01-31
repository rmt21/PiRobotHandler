import javax.swing.JTextField;


public class piInterfaceHandler {
	
	piSender send;
	piLEDEngine piLED;
	public piInterfaceHandler(JTextField console, String ipAddress)
	{
		send = new piSender(console);
		piLED = new piLEDEngine(console, ipAddress);
	}
	
	// main implementer of outcome
	
	
	
	//LED Display for Movement
	private String LEDMoveRight = "LEDMoveRight";
	private String LEDMoveLeft = "left";
	private String LEDMoveForward = "forward";
	private String LEDMoveBack = "back";
	
	public void motorMovement(String direction, String ipAddress) throws InterruptedException
	{
		//movement (LEDs (implemented), speech response and physical(to be implemented..))
		if (direction.equals("right"))
		{
			send.piSendSpeechMotor("motor", "right", ipAddress);
			piLED.useLedMatrixSpecifyFileBi(LEDMoveRight, LEDMoveRight);
		}
		if (direction.equals("left"))
		{
			send.piSendSpeechMotor("motor", "left", ipAddress);
			piLED.useLedMatrixSpecifyFileBi(LEDMoveLeft, LEDMoveLeft);
		}
		if (direction.equals("forward"))
		{
			send.piSendSpeechMotor("motor", "forward", ipAddress);
			piLED.useLedMatrixSpecifyFileBi(LEDMoveForward, LEDMoveForward);
		}
		if (direction.equals("back"))
		{
			send.piSendSpeechMotor("motor", "backward", ipAddress);
			piLED.useLedMatrixSpecifyFileBi(LEDMoveBack, LEDMoveBack);
		}
	}
		
		public void panTiltMovement(String direction, String type, String ipAddress) throws InterruptedException
		{
			if (direction.equals("right"))
			{
				send.piSendServo("servo", type, "right", ipAddress);
				piLED.useLedMatrixSpecifyFileBi(LEDMoveRight, LEDMoveRight);
			}
			if (direction.equals("left"))
			{
				send.piSendServo("servo", type, "left", ipAddress);
			}
			if (direction.equals("forward"))
			{
				send.piSendServo("servo", type, "forward", ipAddress);
			}
			if (direction.equals("back"))
			{
				send.piSendServo("servo", type, "back", ipAddress);
			}
		
	}

}
