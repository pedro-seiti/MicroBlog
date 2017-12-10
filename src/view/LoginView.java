package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.eclipse.paho.client.mqttv3.MqttException;

public class LoginView {
	View parentView;
	JDialog d;
	JLabel usernameL, pwL;
	JTextField usernameF;
	JPasswordField pwF;
	JButton loginButton;
	
	public LoginView(JFrame parent, View view) {
		this.parentView = view;
		d = new JDialog(parent , "Login", true);
		d.setLayout(null);
		
		setLoginFields();
		setConfirmButton();
		

	    d.add(loginButton);//adding button in JFrame
		d.setSize(500,500);
		d.setVisible(true);  
	}
	
	private void setLoginFields() {
		usernameL = new JLabel("username");  
		usernameL.setBounds(100,100,150,30);
	    usernameF = new JTextField();
	    usernameF.setBounds(250,100,150,30);
	    pwL = new JLabel("password");
	    pwL.setBounds(100,200,150,30);
	    pwF = new JPasswordField();
	    pwF.setBounds(250,200,150,30);
	    d.add(usernameL);
		d.add(usernameF); 
		d.add(pwL);
		d.add(pwF);
	}
	private void setConfirmButton() {
		loginButton = new JButton("Login");//creating instance of JButton 
		loginButton.setBounds(200,300,100,30);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usernameInput = usernameF.getText();
				String pwdInput = String.valueOf(pwF.getPassword()); 
				
				try {
					Scanner scanner = new Scanner(new File("login.txt"));
					Boolean unFound = false;
					while(scanner.hasNext()) {
						String data = scanner.next();
						   if(data.equals(usernameInput)) {
							   unFound = true;
							   if(scanner.next().equals(pwdInput)) {
								   parentView.loginSucceded(usernameInput);
							   }
							   else {
								   JOptionPane.showMessageDialog(d, "Invalid password", "Error in login", JOptionPane.ERROR_MESSAGE);
							   }
						       break;
						   }
					}
					if(!unFound) JOptionPane.showMessageDialog(d, "User not found", "Error in login", JOptionPane.ERROR_MESSAGE);
					scanner.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(d, "Fail in the system1", "Error in login", JOptionPane.ERROR_MESSAGE);
				} catch (MqttException e1) {
					JOptionPane.showMessageDialog(d, "Fail in the system2", "Error in login", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				
				d.setVisible(false);
			}
		});
	}
}
