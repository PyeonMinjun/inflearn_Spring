package com.fairytale.FairyTale.domain.letterbox.domain.repository;

import com.fairytale.FairyTale.domain.letterbox.domain.LetterBox;
import com.fairytale.FairyTale.domain.letterbox.domain.LetterBoxId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterBoxRepository extends JpaRepository<LetterBox, LetterBoxId> {
}
