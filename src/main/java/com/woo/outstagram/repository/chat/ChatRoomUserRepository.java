package com.woo.outstagram.repository.chat;

import com.woo.outstagram.entity.chat.ChatRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {
}
