package com.fairytale.FairyTale.domain.uservoice.domain.respository;

import com.fairytale.FairyTale.domain.user.domain.User;
import com.fairytale.FairyTale.domain.uservoice.domain.UserVoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserVoiceRepository extends JpaRepository<UserVoice, Long> {

    @Query("SELECT v FROM UserVoice v WHERE v.user = :user OR v.user IS NULL")
    List<UserVoice> findByUserWithDefaultVoices(@Param("user") User user);
}
