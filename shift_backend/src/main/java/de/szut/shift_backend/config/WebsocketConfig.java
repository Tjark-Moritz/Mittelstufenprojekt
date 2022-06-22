package de.szut.shift_backend.config;

import de.szut.shift_backend.socket.UserDetermineHandshakeHandlerService;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@CrossOrigin
class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    private final UserDetermineHandshakeHandlerService userDetermineHandshakeHandlerService;

    WebsocketConfig(UserDetermineHandshakeHandlerService userDetermineHandshakeHandlerService) {
        this.userDetermineHandshakeHandlerService = userDetermineHandshakeHandlerService;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(
                "/message",
                "message/create",
                "message/update/{id}",
                "message/delete/{id}");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/connect/**")
                .setAllowedOrigins("http://localhost:8090", "chrome-extension://ggnhohnkfcpcanfekomdkjffnfcjnjam")
                .setHandshakeHandler(userDetermineHandshakeHandlerService);
    }
}