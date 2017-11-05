package org.kameringring1028.ws;

import org.kameringring1028.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class BroadCastController {

    @MessageMapping("/broadcast")
    @SendTo("/topic/broadcast")
    public Message broadcast(Message message) {
        return message;
    }
}
