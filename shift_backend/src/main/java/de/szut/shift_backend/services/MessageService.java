package de.szut.shift_backend.services;

import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.helper.ClassReflectionHelper;
import de.szut.shift_backend.model.Message;
import de.szut.shift_backend.repository.MessageChannelRepository;
import de.szut.shift_backend.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageChannelRepository messageChannelRepository;

    public MessageService(MessageRepository messageRepository, MessageChannelRepository messageChannelRepository) {
        this.messageRepository = messageRepository;
        this.messageChannelRepository = messageChannelRepository;
    }

    public void create(Message newMessage) {
        messageRepository.save(newMessage);
    }

    public Message getMessageById(Long messageId) {
        Optional<Message> message = this.messageRepository.findById(messageId);

        if (message.isEmpty())
            throw new ResourceNotFoundException("Message with id '"+ messageId + "' could not be found");

        return message.get();
    }

    public List<Message> getMessagesByChannelId(Long channelId) {
        List<Message> messages = this.messageChannelRepository.findAllById(channelId); //todo: refactor here

        if (messages.isEmpty())
            throw new ResourceNotFoundException("No messages found for channelId '"+ channelId + "' could not be found");

        return messages;
    }

    public List<Message> getAllMessages() {
        return this.messageRepository.findAll();
    }

    public Message updateMessage(Long messageId, Map<String, Object> fieldsToPatch) {
        Message message = this.getMessageById(messageId);

        Message msgUpdated = ClassReflectionHelper.UpdateFields(message, fieldsToPatch);

        this.messageRepository.save(msgUpdated);

        return msgUpdated;
    }

    public void delete(long messageId) {
        this.messageRepository.deleteById(messageId);
    }
}
