package org.kokelink.ws;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


import java.io.IOException;
import java.util.ArrayList;

public class MessageHandler extends TextWebSocketHandler {
    private ArrayList< WebSocketSession > users;
    public MessageHandler(){
        users = new ArrayList<>();
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // �ڑ��m�����A�z���WebSocketSession�̏���ǉ�.
        if(users.stream()
                .noneMatch(user -> user.getId().equals(session.getId()))){
            users.add(session);
            System.out.println("new user :"+users.size());
        }
    }
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // ���b�Z�[�W���󂯎�����瑗�M���ȊO�Ƀ��b�Z�[�W�𑗂�.
        users.stream()
                .filter(user -> !user.getId().equals(session.getId()))
                .forEach(user -> {
                    try{
                        user.sendMessage(message);
                        System.out.println("send "+message+" to "+user.getId());
                    }
                    catch (IOException ex){
                        System.out.println(ex.getLocalizedMessage());
                    }
                });
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // �ڑ����؂ꂽ��z�񂩂�폜.
        users.stream()
                .filter(user -> user.getId().equals(session.getId()))
                .findFirst()
                .ifPresent(user -> users.remove(user));
    }
}