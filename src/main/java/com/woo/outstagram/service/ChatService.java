package com.woo.outstagram.service;

import com.woo.outstagram.dto.chat.*;
import com.woo.outstagram.entity.chat.Chat;
import com.woo.outstagram.entity.chat.ChatRoom;
import com.woo.outstagram.entity.chat.ChatRoomUser;
import com.woo.outstagram.entity.user.User;
import com.woo.outstagram.repository.chat.ChatRepository;
import com.woo.outstagram.repository.chat.ChatRoomRepository;
import com.woo.outstagram.repository.chat.ChatRoomUserRepository;
import com.woo.outstagram.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final SimpMessagingTemplate simpMessageTemplate;

    @Transactional
    public ChatUserListResponseDto getChatUserList(User user) {
        List<User> userList = userRepository.findAll();
        List<ChatUserDto> chatUserDtoList = new ArrayList<>();

        userList.forEach((member) -> {
            if(!member.getEmail().equals(user.getEmail())) {
                ChatRoomUser chatRoomUser = chatRoomUserRepository.findByUserAndTargetUser(user, member).orElse(null);
                Long chatRoomId = null;
                ChatRoom chatRoom = new ChatRoom();
                String lastMessage = null;

                if(chatRoomUser != null) {
                    chatRoomId = chatRoomUser.getChatRoom().getId();
                    chatRoom = chatRoomRepository.findById(chatRoomId).orElse(null);
                    if(chatRoom != null) {
                        lastMessage = chatRoom.getLastMessage();
                    }
                }

                chatUserDtoList.add(
                        ChatUserDto.builder()
                                .email(member.getEmail())
                                .nickname(member.getNickname())
                                .profileUrl(member.getProfileImgUrl())
                                .chatRoomId(chatRoomId)
                                .lastMessage(lastMessage)
                                .modifiedDate(chatRoom.getModifiedDate())
                                .build()
                );
            }
        });

        return ChatUserListResponseDto.builder().chatRoomList(chatUserDtoList).build();
    }

    @Transactional
    public ChatUserListResponseDto createChatRoom(User user, String target) throws Exception {
        User targetUser = userRepository.findByEmail(target).orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));

        ChatRoomUser userChatRoom = chatRoomUserRepository.findByUserAndTargetUser(user, targetUser).orElse(null);
        ChatRoomUser targetChatRoom = chatRoomUserRepository.findByUserAndTargetUser(targetUser, user).orElse(null);

        if(userChatRoom == null && targetChatRoom == null) {
            ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.builder().user(user).build());
            chatRoomUserRepository.save(ChatRoomUser.builder().chatRoom(chatRoom).user(user).targetUser(targetUser).build());
            chatRoomUserRepository.save(ChatRoomUser.builder().chatRoom(chatRoom).user(targetUser).targetUser(user).build());
        }

        return this.getChatUserList(user);
    }

    @Transactional
    public ChatResponseDto getChatList(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new EntityNotFoundException());

        List<Chat> chatList = chatRepository.findAllByChatRoom(chatRoom);
        List<ChatDto> chatDtoList = new ArrayList<>();

        chatList.forEach((chat) -> {
            chatDtoList.add(ChatDto.toDto(chat));
        });

        return ChatResponseDto.builder().chatList(chatDtoList).build();
    }

    @Transactional
    public ChatResponseDto saveChat(User user, ChatRequestDto requestDto) {
        ChatRoom chatRoom = chatRoomRepository.findById(requestDto.getChatRoomId()).orElseThrow(() -> new EntityNotFoundException());

        chatRepository.save(Chat.builder()
                .chatRoom(chatRoom)
                .user(user)
                .content(requestDto.getMessage()).build());

        chatRoom.setLastMessage(requestDto.getMessage());

        return this.getChatList(requestDto.getChatRoomId());
    }

    @Transactional
    public void sendMessage(ChatRequestDto requestDto) {
        log.info(requestDto.toString());
        User sendUser = userRepository.findByEmail(requestDto.getSenderEmail()).orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));

        simpMessageTemplate.convertAndSend("/subscribe/rooms/" + requestDto.getChatRoomId(),
                ChatMessage.builder()
                        .email(sendUser.getEmail())
                        .content(requestDto.getMessage())
                        .sendDate(LocalDateTime.now())
                        .nickname(sendUser.getNickname())
                        .profileUrl(sendUser.getProfileImgUrl())
                        .build());
    }
}
