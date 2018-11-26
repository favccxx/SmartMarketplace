package com.favccxx.mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.favccxx.mp.entity.SmartRoom;

public interface RoomRepository extends JpaRepository<SmartRoom, Long> {

}
