package de.szut.shift_backend.socket;

import de.szut.shift_backend.helper.UUIDHelper;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Service
public class UserDetermineHandshakeHandlerService extends DefaultHandshakeHandler {
    private final UUIDHelper uuidHelper;

    public UserDetermineHandshakeHandlerService(UUIDHelper uuidHelper) {
        this.uuidHelper = uuidHelper;
    }

    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler webSocketHandler,
                                      Map<String, Object> attributes) {
        if (!(request instanceof ServletServerHttpRequest)) {
            return super.determineUser(request, webSocketHandler, attributes);
        }

        ServletServerHttpRequest req = (ServletServerHttpRequest)request;
        String requestURI = req.getServletRequest().getRequestURI();

        return uuidHelper.extractUUIDOf(requestURI)
                .map(uuid -> (Principal) () -> uuid)
                .orElse(super.determineUser(request, webSocketHandler, attributes));
    }
}