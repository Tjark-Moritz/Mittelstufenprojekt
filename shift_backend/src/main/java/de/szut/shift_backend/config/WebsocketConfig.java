package de.szut.shift_backend.config;

import de.szut.shift_backend.controller.MessageHandlingController;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
@CrossOrigin
class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/message/create", "/message/delete"); //TODO: Alle Endpunkte rein
    }

    //Info: Für den Verbindungsaufbau
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/connect")
                .setAllowedOrigins("http://localhost:8090", "chrome-extension://ggnhohnkfcpcanfekomdkjffnfcjnjam")
                .setHandshakeHandler(new DefaultHandshakeHandler());


        registry.addEndpoint("/connect")
                .withSockJS();
    }
}