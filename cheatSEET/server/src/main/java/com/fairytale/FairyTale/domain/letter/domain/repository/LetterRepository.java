package com.fairytale.FairyTale.domain.letter.domain.repository;

import com.fairytale.FairyTale.domain.letter.domain.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<Letter, Long> {
}
