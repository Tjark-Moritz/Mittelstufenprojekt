package de.szut.shift_backend.repository;

import de.szut.shift_backend.model.MessageChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageChannelRepository extends JpaRepository<MessageChannel, Long> {
}
