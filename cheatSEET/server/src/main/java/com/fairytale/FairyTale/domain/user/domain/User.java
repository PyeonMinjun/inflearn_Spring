package com.fairytale.FairyTale.domain.user.domain;

import com.fairytale.FairyTale.domain.child.domain.Child;
import com.fairytale.FairyTale.domain.uservoice.domain.UserVoice;
import com.fairytale.FairyTale.global.database.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "Users")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "oauth_provider", nullable = false)
    private String oauthProvider;

    @Column(name = "oauth_id", nullable = false)
    private String oauthId;

    @Column(name = "is_new", nullable = false)
    private Boolean isNew;

    @Enumerated(EnumType.STRING)
    private final AccountRole accountRole = AccountRole.USER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Child> childList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<UserVoice> userVoiceList = new ArrayList<>();

    @Builder
    public User(
            String email,
            String oauthProvider,
            String oauthId,
            Boolean isNew
    ) {
        this.email = email;
        this.oauthProvider = oauthProvider;
        this.oauthId = oauthId;
        this.isNew = isNew;
    }
}
