package com.orange.mqspring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Controller {
    @Autowired
    private JmsTemplate jmsTemplate;

    @GetMapping("send")
    String send(){
        try{
            jmsTemplate.convertAndSend("DEV.QUEUE.1", new User("Mohamed","Ayman").toByteArray());
            return "OK";
        }catch(JmsException ex){
            ex.printStackTrace();
            return "FAIL";
        }
    }

    @GetMapping("recv")
    String recv(){
        try{
            return jmsTemplate.receiveAndConvert("DEV.QUEUE.1").toString();
        }catch(JmsException ex){
            ex.printStackTrace();
            return "FAIL";
        }
    }

    @JmsListener(destination = "DEV.QUEUE.1")
    public void handleMessage(byte[] message) {//implicit message type conversion
        ObjectMapper mapper = new ObjectMapper();
        try {
            User user = mapper.readValue(message, User.class);
            System.out.println("received: "+user.getLastname());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
