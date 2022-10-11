package com.lion.spring_soundtrack.app.song.repository;

import com.lion.spring_soundtrack.app.song.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}
