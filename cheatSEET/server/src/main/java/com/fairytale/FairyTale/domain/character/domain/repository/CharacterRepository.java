package com.fairytale.FairyTale.domain.character.domain.repository;

import com.fairytale.FairyTale.domain.character.domain.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
}
