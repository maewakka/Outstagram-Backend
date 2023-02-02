package com.woo.outstagram.repository.chat;

import com.woo.outstagram.entity.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
