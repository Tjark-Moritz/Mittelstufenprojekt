package de.szut.shift_backend.services;

import de.szut.shift_backend.model.Message;
import de.szut.shift_backend.model.MessageChannel;
import de.szut.shift_backend.repository.MessageChannelRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageChannelService {

    private MessageChannelRepository messageChannelRepository;

    public MessageChannelService(MessageChannelRepository messageChannelRepository) {
        this.messageChannelRepository = messageChannelRepository;
    }

    public void create(MessageChannel newMessageChannel) {
        messageChannelRepository.save(newMessageChannel);
    }
}
