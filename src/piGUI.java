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


public class piGUI extends JFrame {

	private JPanel contentPane;
	ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
	piInterfaceHandler pi = new piInterfaceHandler();
	

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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 261);
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
					executor.scheduleAtFixedRate(new piDriveHandler(), 10, 10, TimeUnit.SECONDS);
					executor.scheduleAtFixedRate(new piSpeechProcessor(), 10, 10, TimeUnit.SECONDS);
				}						
				else
				{
					executor.shutdown();
				}
			}});
		tglSpeechButton.setBounds(313, 11, 121, 23);
		panel.add(tglSpeechButton);
		
		JButton btnSendLedTest = new JButton("send led test");
		btnSendLedTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					pi.movement("right");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnSendLedTest.setBounds(313, 43, 127, 23);
		panel.add(btnSendLedTest);
		
		JButton motorForward = new JButton("^");
		motorForward.setBounds(47, 181, 41, 23);
		panel.add(motorForward);
		
		JButton motorBack = new JButton("v");
		motorBack.setBounds(47, 227, 41, 23);
		panel.add(motorBack);
		
		JButton motorLeft = new JButton("<");
		motorLeft.setBounds(23, 204, 41, 23);
		panel.add(motorLeft);
		
		JButton motorRight = new JButton(">");
		motorRight.setBounds(74, 204, 41, 23);
		panel.add(motorRight);
		
		JButton tiltUp = new JButton("^");
		tiltUp.setBounds(47, 101, 41, 23);
		panel.add(tiltUp);
		
		JButton panLeft = new JButton("<");
		panLeft.setBounds(23, 124, 41, 23);
		panel.add(panLeft);
		
		JButton panRight = new JButton(">");
		panRight.setBounds(74, 124, 41, 23);
		panel.add(panRight);
		
		JButton tiltDown = new JButton("v");
		tiltDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
		
		JLabel lblPiRobotPc = new JLabel("Pi Robot PC Client v1");
		lblPiRobotPc.setBounds(313, 247, 121, 14);
		panel.add(lblPiRobotPc);
	}
}
