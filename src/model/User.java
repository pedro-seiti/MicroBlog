package model;

import org.eclipse.paho.client.mqttv3.MqttException;

public class User {
	private String username;
	private Subscriber sub;
	private Publisher pub;
	
	public User(String username) throws MqttException{
		this.username = username;
		sub = new Subscriber();
		pub = new Publisher();
	}
	
	public void publishMessage(String user, String tag, String message) throws MqttException {
		pub.publishMessage(user, tag, message);
	}
	public void subscribeTag(String tag) throws MqttException {
		sub.subscribeTag(tag);
	}
	public void subscribeUser(String user) throws MqttException {
		sub.subscribeUser(user);
	}
}
