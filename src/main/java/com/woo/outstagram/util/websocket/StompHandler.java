package com.woo.outstagram.util.websocket;

import com.woo.outstagram.util.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        String token = accessor.getFirstNativeHeader("Authorization");
        if (!accessor.getCommand().equals(StompCommand.DISCONNECT)) {
            if (token != null) {
                try {
                    jwtTokenProvider.getAuthentication(token);
                    return message;
                } catch (Exception e) {
                    throw new RuntimeException("인증이 잘못되었습니다.");
                }
            } else {
                throw new RuntimeException("Jwt 토큰값이 비었습니다.");
            }
        }

        return message;
    }

}
