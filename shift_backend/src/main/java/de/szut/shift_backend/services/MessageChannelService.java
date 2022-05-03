package de.szut.shift_backend.services;

import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.model.Message;
import de.szut.shift_backend.model.MessageChannel;
import de.szut.shift_backend.repository.MessageChannelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageChannelService {

    private MessageChannelRepository messageChannelRepository;

    public MessageChannelService(MessageChannelRepository messageChannelRepository) {
        this.messageChannelRepository = messageChannelRepository;
    }

    public void create(MessageChannel newMessageChannel) {
        messageChannelRepository.save(newMessageChannel);
    }

    public void delete(long messageChannelId) {
        this.messageChannelRepository.deleteById(messageChannelId);
    }

    public MessageChannel getMessageChannelById(Long messageChannelId) {
        Optional<MessageChannel> messageChannel = this.messageChannelRepository.findById(messageChannelId);

        if (messageChannel.isEmpty())
            throw new ResourceNotFoundException("Message-channel with id '"+ messageChannelId + "' could not be found");

        return messageChannel.get();
    }

    public List<MessageChannel> getAll() {
        return this.messageChannelRepository.findAll();
    }
}
