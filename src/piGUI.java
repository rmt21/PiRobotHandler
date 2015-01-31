import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ButtonModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class piGUI extends JFrame {

	private JPanel contentPane;
	ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
	private JTextField ipAddress;
	private JTextField userConsole;
	piInterfaceHandler pi;
	piSpeechServer speechServer;

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					piGUI frame = new piGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public piGUI() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 709, 468);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 693, 429);
		contentPane.add(panel);
		panel.setLayout(null);
		final JToggleButton tglSpeechButton = new JToggleButton("Enable speech");
		tglSpeechButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get state of button, execute scheduled task
				//shutdown scheduled task
				ButtonModel buttonModel = tglSpeechButton.getModel();
				if (buttonModel.isSelected())
				{
					//executor.scheduleAtFixedRate(new piDriveHandler(), 10, 10, TimeUnit.SECONDS);
					//executor.scheduleAtFixedRate(new piSpeechProcessor(userConsole, ipAddress.getText()), 10, 10, TimeUnit.SECONDS);
				}					
				else
				{
					executor.shutdown();
				}
			}});
		tglSpeechButton.setBounds(572, 11, 121, 23);
		panel.add(tglSpeechButton);
		
		JButton btnSendLedTest = new JButton("send led test");
		btnSendLedTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnSendLedTest.setBounds(566, 33, 127, 23);
		panel.add(btnSendLedTest);
		
		JButton motorForward = new JButton("^");
		motorForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					pi.motorMovement("forward", ipAddress.getText());
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		motorForward.setBounds(47, 181, 41, 23);
		panel.add(motorForward);
		
		JButton motorBack = new JButton("v");
		motorBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					pi.motorMovement("back", ipAddress.getText());
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		motorBack.setBounds(47, 227, 41, 23);
		panel.add(motorBack);
		
		JButton motorLeft = new JButton("<");
		motorLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					pi.motorMovement("left", ipAddress.getText());
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		motorLeft.setBounds(23, 204, 41, 23);
		panel.add(motorLeft);
		
		JButton motorRight = new JButton(">");
		motorRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					pi.motorMovement("right", ipAddress.getText());
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		motorRight.setBounds(74, 204, 41, 23);
		panel.add(motorRight);
		
		JButton tiltUp = new JButton("^");
		tiltUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					pi.panTiltMovement("up", "tilt", ipAddress.getText());
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		tiltUp.setBounds(47, 101, 41, 23);
		panel.add(tiltUp);
		
		JButton panLeft = new JButton("<");
		panLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					pi.panTiltMovement("left", "pan", ipAddress.getText());
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panLeft.setBounds(23, 124, 41, 23);
		panel.add(panLeft);
		
		JButton panRight = new JButton(">");
		panRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					pi.panTiltMovement("right", "pan", ipAddress.getText());
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panRight.setBounds(74, 124, 41, 23);
		panel.add(panRight);
		
		JButton tiltDown = new JButton("v");
		tiltDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					pi.panTiltMovement("down", "tilt", ipAddress.getText());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		tiltDown.setBounds(47, 147, 41, 23);
		panel.add(tiltDown);
		
		JLabel lblMotors = new JLabel("Motors");
		lblMotors.setBounds(98, 231, 46, 14);
		panel.add(lblMotors);
		
		JLabel lblPantilt = new JLabel("PanTilt");
		lblPantilt.setBounds(98, 151, 46, 14);
		panel.add(lblPantilt);
		
		JLabel lblPiRobotPc = new JLabel("Pi Robot PC Client v0.002");
		lblPiRobotPc.setBounds(539, 415, 154, 14);
		panel.add(lblPiRobotPc);
		
		ipAddress = new JTextField();
		ipAddress.setBounds(10, 0, 121, 20);
		panel.add(ipAddress);
		ipAddress.setColumns(10);
		
		userConsole = new JTextField();
		userConsole.setBounds(0, 395, 693, 23);
		panel.add(userConsole);
		userConsole.setColumns(10);
		
		JButton btnStartProcess = new JButton("Connect");
		btnStartProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 pi = new piInterfaceHandler(userConsole, ipAddress.getText());
			      try
			      {
			         Thread t = new piSpeechServer(userConsole, ipAddress.getText());
			         t.start();
			      }catch(IOException e2)
			      {
			         e2.printStackTrace();
			      }
				 userConsole.setText("setting up..");
			}
		});
		btnStartProcess.setBounds(141, -1, 102, 23);
		panel.add(btnStartProcess);
	}
}
