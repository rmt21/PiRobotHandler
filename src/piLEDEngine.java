

import com.pi4j.jni.I2C;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class piLEDEngine {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */

	piSender send;
	String ipAddress;
	JTextField console;
	public piLEDEngine(JTextField console, String ipAddress)
	{
		send = new piSender(console);
		this.ipAddress = ipAddress;
		this.console = console;
	}
	
	private int[] LEDColumn;
	private byte[] greenResult;
	private byte[] redResult;
	private int fd;
	private List<String> LEDLine;
	private String sCurrentLine;
	private int lineCount;
	private int LEDLineCount;
	
	public piLEDEngine()
	{
		//constructors
		LEDColumn = new int[17];
		LEDColumn[0] = 0x0f;
		LEDColumn[1] = 0x0e;
		LEDColumn[2] = 0x0d;
		LEDColumn[3] = 0x0c;
		LEDColumn[4] = 0x0b;
		LEDColumn[5] = 0x0a;
		LEDColumn[6] = 0x09;
		LEDColumn[7] = 0x08;
		LEDColumn[8] = 0x07;
		LEDColumn[9] = 0x06;
		LEDColumn[10] = 0x05;
		LEDColumn[11] = 0x04;
		LEDColumn[12] = 0x03;
		LEDColumn[13] = 0x02;
		LEDColumn[14] = 0x01;
		LEDColumn[15] = 0x00;
		LEDColumn[16] = 0;
	
		greenResult = null;
		redResult = null;
		
		LEDLine = new ArrayList<>();
		lineCount = 0;
		LEDLineCount = 0;
	}
	
	public void useLedMatrixSpecifyFileBi(String redFile, String greenFile) throws InterruptedException
	{
		populateDisplayBi("LED", "both", readFromFileSpecify(redFile, "red"), readFromFileSpecify(greenFile, "green"));		
	}
	
	public void resetDisplay()
	{
		clearMatrix(fd, LEDColumn);
	}
	
	public void useLedMatrixDisplaySingle (String colour) throws InterruptedException
	{
		clearMatrix(fd, LEDColumn); // clear led matrix for use
		readFromFile(colour);
		
		if (colour.equals("red"))
		{
		populateDisplaySingle(colour, redResult);
		}
		if (colour.equals("green"))
		{
		populateDisplaySingle(colour, greenResult);
		}	
	}
	
	public void useLedMatrixDisplayBi () throws InterruptedException
	{
		clearMatrix(fd, LEDColumn); // clear led matrix for use	
		//populateDisplayBi(readFromFile("red"), readFromFile("green"));		
	}
	
	
	private void initialiseDisplay()
	{
	    //turn on
	    I2C.i2cWriteByteDirect(fd, 0x70, (byte) 0x21);
	    I2C.i2cWriteByteDirect(fd, 0x70, (byte) 0x81);
	    I2C.i2cWriteByteDirect(fd, 0x70, (byte) 0xe0);
	    clearMatrix(fd, LEDColumn); // clear led matrix for use
	}
	
	private byte[] readFromFileSpecify(String fileName, String colour)
	{	
		//string list to hold values from text file
				try {
					BufferedReader br = new BufferedReader(new FileReader(fileName + ".txt"));
					
					while ((sCurrentLine = br.readLine()) != null) 
						{
							LEDLine.add(sCurrentLine);
							LEDLineCount++;
						}
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String lineA = null;
				String lineB = null;
				redResult = new byte[LEDLineCount+1];
				greenResult = new byte[LEDLineCount+1];

				for (lineCount=0; lineCount <LEDLineCount; lineCount++)
				{	
					//convert 8 letter string to 4
					lineA = Long.toHexString(Long.parseLong(LEDLine.get(lineCount).substring(0, 4),2)); 
					lineB = Long.toHexString(Long.parseLong(LEDLine.get(lineCount).substring(4, 8),2));
					
					String hold = lineA + lineB;
					//convert string to int to byte
					int value = Integer.parseInt(hold, 16); 
					
					if (colour.equals("red"))
					{
						redResult[lineCount] = (byte) value;
					}
					if (colour.equals("green"))
					{
						greenResult[lineCount] = (byte) value;			
					}					
				}
				if (colour.equals("red"))
				{
					return redResult;
				}
				else
				{
					return greenResult;	
				}				
	}
	
	private byte[] readFromFile(String colour)
	{	
		//string list to hold values from text file
				try {
					BufferedReader br = new BufferedReader(new FileReader("classes/LED" + colour + "Grid.txt"));
					
					while ((sCurrentLine = br.readLine()) != null) 
						{
							LEDLine.add(sCurrentLine);
							LEDLineCount++;
						}
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					console.setText(e.toString());
				}
				String lineA = null;
				String lineB = null;
				redResult = new byte[LEDLineCount+1];

				for (lineCount=0; lineCount <LEDLineCount; lineCount++)
				{	
					//convert 8 letter string to 4
					lineA = Long.toHexString(Long.parseLong(LEDLine.get(lineCount).substring(0, 4),2)); 
					lineB = Long.toHexString(Long.parseLong(LEDLine.get(lineCount).substring(4, 8),2));
					
					String hold = lineA + lineB;
					//convert string to int to byte
					int value = Integer.parseInt(hold, 16); 
					
					if (colour.equals("red"))
					{
						redResult[lineCount] = (byte) value;
						return redResult;
					}
					if (colour.equals("green"))
					{
						greenResult[lineCount] = (byte) value;	
						return greenResult;
					}					
				}
				return null;
	}
	
	private void populateDisplaySingle(String colour, byte[] result) throws InterruptedException
	{
		if (colour.equals("red"))
		{
		for (int scrollCount = 0; scrollCount <LEDLineCount; scrollCount++)
	    {
	    	int showResult = scrollCount;
	    	for (int columnCount = 0; columnCount <16; columnCount++)
	    	{
	    		if (showResult < LEDLineCount)
	    		{
	    			//write saved values to display RED LED
	    			showResult++;
	    			I2C.i2cWriteByte(fd ,0x70 ,LEDColumn[columnCount] , result[showResult]);
	    			columnCount++;
	    	}
	    }
    	//sleep so scrolling can be seen
	    Thread.sleep(200);
	    }
		}
		if (colour.equals("green"))
		{
	    	for (int scrollCount = 0; scrollCount <LEDLineCount; scrollCount++)
		    {
		    	int showResult = scrollCount;		 
		    	for (int columnCount = 1; columnCount <16; columnCount++)
		    	{
		    		if (showResult < LEDLineCount)
		    		{
		    			//write saved values to display GREEN LED
		    			showResult++;
		    			I2C.i2cWriteByte(fd ,0x70 ,LEDColumn[columnCount] , result[showResult]);
		    			columnCount++;
		    		}
		    }
	    	//sleep so scrolling can be seen
		    Thread.sleep(200);
		    }
		}
	}
	
	private void populateDisplayBi(String type, String colour, byte[] redResult, byte[] greenResult) throws InterruptedException
	{
		send.piSend(type, colour, redResult, greenResult, LEDLineCount, ipAddress);
	}
	
	private static void clearMatrix(int fd, int[] LEDColumn)
	{
		for (int i =0; i <=16; i++)
		{
		I2C.i2cWriteByte(fd ,0x70 ,LEDColumn[i] ,(byte) 0x00);
		}
	}

	}



