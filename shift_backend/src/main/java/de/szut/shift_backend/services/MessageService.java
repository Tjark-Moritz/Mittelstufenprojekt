package de.szut.shift_backend.services;

import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.helper.ClassReflectionHelper;
import de.szut.shift_backend.model.Message;
import de.szut.shift_backend.model.MessageChannel;
import de.szut.shift_backend.repository.MessageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
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
        List<Message> messages = this.messageRepository.findAll(); //todo: refactor here with findAllById
        List<Message> messageList = new ArrayList<>();

        for (Message message : messages) {
            MessageChannel messageChannel = message.getMessageChannel();
            if (messageChannel.getId().equals(channelId))
                messageList.add(message);
        }

        if (messageList.isEmpty())
            throw new ResourceNotFoundException("No messages found for channelId "+ channelId + "' could not be found");

        return messageList;
    }

    public List<Message> getAllMessagesFromMessageChannel(Long channelId) {
        return this.messageRepository.findAllByMessageId(channelId);
    }

    public List<Message> findXMessagesByChannelId(int amount, Long channelId) {
        return this.messageRepository.findXMessagesByChannelId(PageRequest.of(0,amount), channelId);
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
