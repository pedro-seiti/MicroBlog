package model;

import java.util.ArrayList;

import org.eclipse.paho.client.mqttv3.MqttException;

public class User {
	private String username;
	private Subscriber sub;
	private Publisher pub;
	private ArrayList<String> tags;
	private ArrayList<String> users;
	
	public User(String username) throws MqttException{
		this.username = username;
		sub = new Subscriber();
		pub = new Publisher();
		tags = new ArrayList<String>();
		users = new ArrayList<String>();
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
	
	public String getUsername() {
		return this.username;
	}
	public ArrayList<String> getTags(){
		return this.tags;
	}
	public void addTag(String tag) {
		this.tags.add(tag);
	}
	public ArrayList<String> getUsers(){
		return this.users;
	}
	public void addUser(String user) {
		this.users.add(user);
	}
}
