package model;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import controller.Controller;

public class Subscriber {
	MqttClient client;
	
	Subscriber() throws MqttException {

		System.out.println("== START SUBSCRIBER ==");

		client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
		client.setCallback( new Controller() );
		client.connect();

	}
	public void subscribeTag(String tag) throws MqttException {
		client.subscribe("+/" + tag);
	}
	public void subscribeUser(String user) throws MqttException {
		client.subscribe(user + "/#");
	}
}
