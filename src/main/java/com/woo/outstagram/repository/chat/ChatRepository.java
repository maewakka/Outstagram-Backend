package com.woo.outstagram.repository.chat;

import com.woo.outstagram.entity.chat.Chat;
import com.woo.outstagram.entity.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByChatRoom(ChatRoom chatRoom);
}
