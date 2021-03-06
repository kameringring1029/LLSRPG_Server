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
        // 接続確立時、配列にWebSocketSessionの情報を追加.
        if(users.stream()
                .noneMatch(user -> user.getId().equals(session.getId()))){
            users.add(session);
            System.out.println("new user :"+users.size());
        }
    }
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // メッセージを受け取ったらペアにメッセージを送る.

        System.out.println("recv "+ message +" from user :"+session.getId());
        
        int pairSessionId;
        
        // セッションを確立した順番にペア設定
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
        
        
        /* 自分以外のすべてのユーザにブロードキャスト
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
        // 接続が切れたら配列から削除.

        System.out.println("logout user :"+session.getId());
        
        users.stream()
                .filter(user -> user.getId().equals(session.getId()))
                .findFirst()
                .ifPresent(user -> users.remove(user));
    }
}