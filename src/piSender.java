
import java.net.*;
import java.io.*;

import javax.swing.JTextField;

public class piSender
{	
	JTextField console;
	public piSender(JTextField ipAddress)
	{
		this.console = ipAddress;
	}
	
	
   public void piSend (String type, String output1, byte[] output2, byte[] output3, int count, String ipAddress)
   {
      String serverName = ipAddress;
      int port = Integer.parseInt("6066");
      try
      {
         console.setText("Connecting to " + serverName + " on port " + port);//System.out.println("Connecting to " + serverName + " on port " + port);
         Socket client = new Socket(serverName, port);
         console.setText("connected..sending...led" + client.getRemoteSocketAddress());//System.out.println("connected..sending...led" + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);
         
         //send data
         out.writeUTF(type);
         out.writeUTF(output1);
         out.writeInt(count);
         out.writeInt(output2.length);
         out.write(output2);
         out.writeInt(output3.length);
         out.write(output3);
         
         InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
         System.out.println(in.readUTF());
         client.close();
      }catch(IOException e)
      {
         e.printStackTrace();
			console.setText(e.toString());

      }
   }
   
   public void piSendSpeechMotor (String type, String output1, String ipAddress)
   {
      String serverName = ipAddress;
      int port = Integer.parseInt("6066");
      try
      {
    	  console.setText("Connecting to " + serverName + " on port " + port);//System.out.println("Connecting to " + serverName + " on port " + port);
          Socket client = new Socket(serverName, port);
          console.setText("connected..sending...led" + client.getRemoteSocketAddress());//System.out.println("connected..sending...led" + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);
         
         //send data
         out.writeUTF(type);
         out.writeUTF(output1);
         
         InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
         System.out.println(in.readUTF());
         client.close();
      }catch(IOException e)
      {
         e.printStackTrace();
			console.setText(e.toString());

      }
   }
   
   public void piSendServo (String type, String output1, String output2, String ipAddress)
   {
	   //type=servo, output1=pan,tilt output2=direction
      String serverName = ipAddress;
      int port = Integer.parseInt("6066");
      try
      {
    	  console.setText("Connecting to " + serverName + " on port " + port);//System.out.println("Connecting to " + serverName + " on port " + port);
          Socket client = new Socket(serverName, port);
          console.setText("connected..sending...led" + client.getRemoteSocketAddress());//System.out.println("connected..sending...led" + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);
         
         //send data
         out.writeUTF(type);
         out.writeUTF(output1);
         out.writeUTF(output2);
         
         InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
         System.out.println(in.readUTF());
         client.close();
      }catch(IOException e)
      {
         e.printStackTrace();
			console.setText(e.toString());

      }
   }
}