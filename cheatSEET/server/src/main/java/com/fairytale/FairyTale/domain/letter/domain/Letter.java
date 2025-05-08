package com.fairytale.FairyTale.domain.letter.domain;

import com.fairytale.FairyTale.domain.book.domain.Book;
import com.fairytale.FairyTale.domain.character.domain.Character;
import com.fairytale.FairyTale.domain.child.domain.Child;
import com.fairytale.FairyTale.domain.letterbox.domain.LetterBox;
import com.fairytale.FairyTale.global.database.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "Letters")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Letter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "letter_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "child_id", referencedColumnName = "child_id"),
            @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    })
    private LetterBox letterBox;

    @Column(name = "letter_content")
    private String letterContent;

    @Column(name = "is_read")
    private Boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private Character character;

    @Builder
    public Letter(LetterBox letterBox, String letterContent, Boolean isRead, Character character) {
        this.letterBox = letterBox;
        this.letterContent = letterContent;
        this.isRead = isRead;
        this.character = character;
    }
}
