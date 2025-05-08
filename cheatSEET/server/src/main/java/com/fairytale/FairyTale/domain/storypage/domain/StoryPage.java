package com.fairytale.FairyTale.domain.storypage.domain;

import com.fairytale.FairyTale.domain.book.domain.Book;
import com.fairytale.FairyTale.domain.pageaudio.domain.PageAudio;
import com.fairytale.FairyTale.domain.storyinteraction.domain.StoryInteraction;
import com.fairytale.FairyTale.global.database.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "StoryPages")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoryPage extends BaseEntity {

    @EmbeddedId
    private StoryPageId id;

    @MapsId("bookId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "text_content")
    private String textContent;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "storyPage", cascade = CascadeType.ALL)
    private final List<PageAudio> pageAudioList = new ArrayList<>();

    @OneToOne(mappedBy = "storyPage")
    private StoryInteraction storyInteraction;

    @Builder
    public StoryPage(Book book, Integer pageNumber, String textContent, String imageUrl) {
        this.book = book;
        this.textContent = textContent;
        this.imageUrl = imageUrl;
        this.id = new StoryPageId(pageNumber, book.getId());
    }
}
