package controller;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import view.View;

public class Controller implements MqttCallback{
	private static View view;
	
	public static void main(String[] args) {  
		view = new View();  
	}

	@Override
	public void connectionLost(Throwable arg0) {
		System.out.println("Connection to MQTT broker lost!");		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageArrived(String userPlusTopic, MqttMessage mqttMessage) throws Exception {
		view.messageReceived(userPlusTopic.split("/")[0], userPlusTopic.split("/")[1], new String(mqttMessage.getPayload()));
	}
	
}
