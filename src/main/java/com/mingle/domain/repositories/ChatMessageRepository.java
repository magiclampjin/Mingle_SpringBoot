package com.mingle.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mingle.domain.entites.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{

}
