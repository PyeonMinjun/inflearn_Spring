package com.fairytale.FairyTale.domain.character.domain;

import com.fairytale.FairyTale.domain.book.domain.Book;
import com.fairytale.FairyTale.domain.letter.domain.Letter;
import com.fairytale.FairyTale.domain.storyinteraction.domain.StoryInteraction;
import com.fairytale.FairyTale.global.database.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "Characters")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Character extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "ai_prompt")
    private String aiPrompt;

    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL)
    private final List<Letter> letterList = new ArrayList<>();

    @Builder
    public Character(Book book, String name, String imageUrl, String aiPrompt) {
        this.book = book;
        this.name = name;
        this.imageUrl = imageUrl;
        this.aiPrompt = aiPrompt;
    }
}
