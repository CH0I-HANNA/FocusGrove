package com.focusglove.api.log.entity;

import com.focusglove.api.common.entity.BaseEntity;
import com.focusglove.api.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DailyLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 200)
    private String content;

    private String emoji; // 기분 선택 (선택 사항)

    @Column(nullable = false)
    private LocalDate logDate; // 기록 날짜

    // 내용 수정을 위한 메서드
    public void updateContent(String content, String emoji) {
        this.content = content;
        this.emoji = emoji;
    }
}