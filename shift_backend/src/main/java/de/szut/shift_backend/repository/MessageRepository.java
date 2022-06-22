package de.szut.shift_backend.repository;

import de.szut.shift_backend.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM  Message m WHERE m.messageChannel.id = :messageChannelId")
    List<Message> findAllByMessageId(@Param("messageChannelId") Long messageChannelId);

    @Query("SELECT m FROM Message m WHERE m.messageChannel.id = :messageChannelId ORDER BY m.dateTime DESC ")
    List<Message> findXMessagesByChannelId(Pageable pageable, @Param("messageChannelId") Long messageChannelId);
}
