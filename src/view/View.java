package view;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.eclipse.paho.client.mqttv3.MqttException;

import model.User;

public class View extends JFrame {
	private static final long serialVersionUID = 1L;
	private boolean logged;
	
	private JFrame f;
	
	private JPanel loginPanel;
	private JButton loginButton;
	private JButton closeButton;
	private JButton usernameLabel;
	
	private JPanel messagePanel;
	private JScrollPane messageSP;
	private JTable messageTable;
	private JTextField subTagField;
	private JButton subTagButton;
	private JLabel tagFollowedList;
	private JTextField subUserField;
	private JButton subUserButton;
	private JLabel userFollowedList;

	private JPanel writePanel;
	private JTextField tagText;
	private JTextArea messageText;
	private JLabel tagLabel, messageLabel;
	private JButton submitButton;
	
	private User user;
	
	public View() {
		setLoginPanel();
		setMessagePanel();
		setWritePanel();
		setLogged(false, "");
		
		setSize(1000,1000);  
		setLayout(null);  
		setVisible(true);  
		initiateLoginView();
	}
	
	public void loginSucceded(String username) throws MqttException {
		setLogged(true, username);
		user = new User(username);
	}
	
	public void messageReceived(String username, String tag, String message) {
		DefaultTableModel table = (DefaultTableModel) messageTable.getModel();
		table.addRow(new Object[] {username, tag, message});
	}
	
	private void setLoginPanel() {
		loginPanel = new JPanel();
		loginPanel.setBounds(0,0,1000,30);
		loginPanel.setLayout(new BorderLayout());
		loginButton = new JButton();
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				initiateLoginView();
			}
		});
		
		closeButton = new JButton("Exit");
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		usernameLabel = new JButton();
		
		loginPanel.add(usernameLabel, BorderLayout.CENTER);
		loginPanel.add(closeButton, BorderLayout.WEST);
		loginPanel.add(loginButton, BorderLayout.EAST);
		this.add(loginPanel);
	}
	
	private void setMessagePanel() {
		messagePanel = new JPanel();
		messagePanel.setBounds(0, 100, 500, 700);
		messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
		messageSP = new JScrollPane();
		messageSP.setMaximumSize(new Dimension(400,450));
		messageSP.setMinimumSize(new Dimension(400,450));
		messageTable = new JTable();
		messageSP.setViewportView(messageTable);
		messagePanel.add(messageSP);

		resetTable();
		
		messageTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		messageTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		messageTable.getColumnModel().getColumn(2).setPreferredWidth(300);
      
		subTagField = new JTextField();
		subTagField.setMaximumSize(new Dimension(400,subTagField.getPreferredSize().height));
		subTagField.setMinimumSize(new Dimension(400,subTagField.getPreferredSize().height));
		subTagButton = new JButton("Subscribe to Tag");
		subTagButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addTag(subTagField.getText());
			}
		});
		tagFollowedList = new JLabel("Tags followed:");
		tagFollowedList.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		subUserField = new JTextField();
		subUserField.setMaximumSize(new Dimension(400,subUserField.getPreferredSize().height));
		subUserField.setMinimumSize(new Dimension(400,subUserField.getPreferredSize().height));
		subUserButton = new JButton("Subscribe to User");
		subUserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addUser(subUserField.getText());
			}
		});
		userFollowedList = new JLabel("User followed:");
		userFollowedList.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		messagePanel.add(subTagField);
		messagePanel.add(subTagButton);
		messagePanel.add(tagFollowedList);
		messagePanel.add(subUserField);
		messagePanel.add(subUserButton);
		messagePanel.add(userFollowedList);
		
		this.add(messagePanel);
	}
	
	private void setWritePanel() {
		writePanel = new JPanel();
		writePanel.setBounds(500,100,500,900);
		writePanel.setLayout(new BoxLayout(writePanel, BoxLayout.Y_AXIS));
		
		tagText = new JTextField();
		tagText.setMaximumSize(new Dimension(400,tagText.getPreferredSize().height));
		tagText.setMinimumSize(new Dimension(400,tagText.getPreferredSize().height));
		
		messageText = new JTextArea();
		messageText.setMaximumSize(new Dimension(400,400));
		messageText.setMinimumSize(new Dimension(400,400));
		messageText.setLineWrap(true);
		
		tagLabel = new JLabel("Tags");
		tagLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		messageLabel = new JLabel("Message");
		messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (user != null) {
					try {
						user.publishMessage(usernameLabel.getText(), tagText.getText(), messageText.getText());
						tagText.setText("");
						messageText.setText("");
					} catch (MqttException e1) {
						e1.printStackTrace();
					}
				}
			}			
		});
		
		writePanel.add(tagLabel);
		writePanel.add(tagText);
		writePanel.add(messageLabel);
		writePanel.add(messageText);
		writePanel.add(submitButton);;
		
		this.add(writePanel);
	}
	
	private void setLogged(Boolean logged, String username){
		this.logged = logged;
		if(logged) {
			loginButton.setText("logout");
			usernameLabel.setText(username);
		}
		else {
			loginButton.setText("login");
			usernameLabel.setText(username);
			cleanScreens();
		}
	}
	
	private void initiateLoginView() {
		if(logged) {
			setLogged(false, "");
		}
		else {
			try{  
				new LoginView(f, this);
			} catch(Exception ex){
				System.out.println(ex);
			}
		}  
	}
	
	private void addTag(String tag) {
		if(user != null) {
			try {
				user.subscribeTag(tag);
				user.addTag(tag);
				
				String previousList = tagFollowedList.getText();
				tagFollowedList.setText(previousList + " #" + tag);
			} catch (MqttException e1) {
				e1.printStackTrace();
			}
			subTagField.setText("");
		}
	}
	private void addUser(String user) {
		if(this.user != null) {
			try {
				this.user.subscribeUser(user);
				this.user.addUser(user);
				
				String previousList = userFollowedList.getText();
				userFollowedList.setText(previousList + " @" + user);
			} catch (MqttException e1) {
				e1.printStackTrace();
			}
			subUserField.setText("");
		}
	}
	
	private void cleanScreens() {
		resetTable();
		tagFollowedList.setText("Tags followed:");
		userFollowedList.setText("Users followed:");
	}
	private void resetTable() {
		DefaultTableModel tableModel = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }			
		};
		tableModel.setColumnIdentifiers(new String[] {"User", "Tags", "Message"});
		messageTable.setModel(tableModel);
	}
}
