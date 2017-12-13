package model;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Publisher {
	Publisher(){
		
	}
	public void publishMessage(String user, String tag, String messageString) throws MqttException {
		System.out.println("====== PUBLISHER ACTIVE =======");
		MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
		client.connect();
		MqttMessage message = new MqttMessage();
		message.setPayload(messageString.getBytes());
		client.publish(user + "/" + tag, message);
		client.disconnect();
	}
}
