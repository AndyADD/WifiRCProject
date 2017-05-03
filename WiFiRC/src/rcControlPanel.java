import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

import javax.swing.SpringLayout;
import javax.swing.JTextField;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Set;
import java.util.TreeSet;


public class rcControlPanel extends JFrame{

	private JFrame frame;
	JButton btnForward, btnReverse, btnLeft, btnRight;
	Set<Integer> pressedKeys = new TreeSet<Integer>();
	
	public static DataOutputStream rc_DOS;
	
	public static final byte forward_GO   = 10;
	public static final byte reverse_GO   = 11;
	public static final byte right_GO     = 12;
	public static final byte left_GO      = 13;
	
	public static final byte forward_STOP = 20;
	public static final byte reverse_STOP = 21;
	public static final byte right_STOP   = 22;
	public static final byte left_STOP    = 23;

	/**
	 * Launch the application.
	
	 */
	
	private class rcControl_Dispatcher implements KeyEventDispatcher{

		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if(e.getID() == KeyEvent.KEY_PRESSED){
				int key_Code = e.getKeyCode();
				Integer int_Key_Value = Integer.valueOf(key_Code);
				
				if(pressedKeys.contains(int_Key_Value)){
					//This is here to prevent sending multiple packets, and causing RC car to stutter
				}
				else{
					switch(e.getKeyCode()){
					case KeyEvent.VK_W:
						pressedKeys.add(e.getKeyCode());
						try {
							rc_DOS.writeByte(forward_GO);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						//System.out.println("W has been pressed");
						break;
						
					case KeyEvent.VK_S:
						pressedKeys.add(e.getKeyCode());
						try {
							rc_DOS.writeByte(reverse_GO);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//System.out.println("S has been pressed");
						break;
						
					case KeyEvent.VK_A:
						pressedKeys.add(e.getKeyCode());
						try {
							rc_DOS.writeByte(left_GO);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//System.out.println("A has been pressed");
						break;
						
					case KeyEvent.VK_D:
						pressedKeys.add(e.getKeyCode());
						try {
							rc_DOS.writeByte(right_GO);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//System.out.println("D has been pressed");
						break;
					
					}
					
				}
				
			}
			else if (e.getID() == KeyEvent.KEY_RELEASED){
				switch(e.getKeyCode()){
				case KeyEvent.VK_W:
					pressedKeys.remove(e.getKeyCode());
					try {
						rc_DOS.writeByte(forward_STOP);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//System.out.println("W has been released");
					break;
					
				case KeyEvent.VK_S:
					pressedKeys.remove(e.getKeyCode());
					try {
						rc_DOS.writeByte(reverse_STOP);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//System.out.println("S has been released");
					break;
					
				case KeyEvent.VK_A:
					pressedKeys.remove(e.getKeyCode());
					try {
						rc_DOS.writeByte(left_STOP);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//System.out.println("A has been released");
					break;
					
				case KeyEvent.VK_D:
					pressedKeys.remove(e.getKeyCode());
					try {
						rc_DOS.writeByte(right_STOP);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//System.out.println("D has been released");
				}
			}
			return false;
		}
		
	}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					rcControlPanel window = new rcControlPanel();
					window.frame.setVisible(true);
					
					//Replace the 192.168.42.1 with YOUR Raspberry pi IP Address.
					//You can chage port (15000) to different once if you like, just remember
					//to change it on rpi_Server file, too.
					Socket rc_Socket = new Socket("192.168.42.1", 15000);
					rc_DOS = new DataOutputStream(rc_Socket.getOutputStream());
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public rcControlPanel() {
		initialize();
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new rcControl_Dispatcher());
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1366, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		
		btnForward = new JButton("Forward[W]");
		springLayout.putConstraint(SpringLayout.SOUTH, btnForward, -119, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnForward, -594, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnForward);
		
		btnReverse = new JButton("Reverse[S]");
		springLayout.putConstraint(SpringLayout.NORTH, btnReverse, 34, SpringLayout.SOUTH, btnForward);
		springLayout.putConstraint(SpringLayout.WEST, btnReverse, 0, SpringLayout.WEST, btnForward);
		springLayout.putConstraint(SpringLayout.EAST, btnReverse, -594, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnReverse);
		
		btnLeft = new JButton("Left[A]");
		springLayout.putConstraint(SpringLayout.WEST, btnLeft, 601, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnLeft, -90, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnLeft, 680, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(btnLeft);
		
		btnRight = new JButton("Right[D]");
		springLayout.putConstraint(SpringLayout.WEST, btnRight, 79, SpringLayout.EAST, btnLeft);
		springLayout.putConstraint(SpringLayout.SOUTH, btnRight, -90, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnRight, 158, SpringLayout.EAST, btnLeft);
		frame.getContentPane().add(btnRight);
	}
	

}
