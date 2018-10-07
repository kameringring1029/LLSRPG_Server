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
        // ���b�Z�[�W���󂯎������y�A�Ƀ��b�Z�[�W�𑗂�.

        System.out.println("recv "+ message +" from user :"+session.getId());
        
        int pairSessionId;
        
        // �Z�b�V�������m���������ԂɃy�A�ݒ�
        if(Integer.parseInt(session.getId())%2 == 0) {
        	pairSessionId = Integer.parseInt(session.getId()) + 1;
        }else {
        	pairSessionId = Integer.parseInt(session.getId()) - 1;        	
        }

        try{
        	if(users.size() > 1) {
                users.get(pairSessionId).sendMessage(message);
                System.out.println("send "+message+" to "+pairSessionId);       		
        	}

        }
        catch (IOException ex){
            System.out.println(ex.getLocalizedMessage());
        }
        
        
        /* �����ȊO�̂��ׂẴ��[�U�Ƀu���[�h�L���X�g
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
                */
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // �ڑ����؂ꂽ��z�񂩂�폜.

        System.out.println("logout user :"+session.getId());
        
        users.stream()
                .filter(user -> user.getId().equals(session.getId()))
                .findFirst()
                .ifPresent(user -> users.remove(user));
    }
}