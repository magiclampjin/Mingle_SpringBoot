package com.mingle.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mingle.domain.entites.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String>{

}
