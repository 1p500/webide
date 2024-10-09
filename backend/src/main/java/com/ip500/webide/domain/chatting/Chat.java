package com.ip500.webide.domain.chatting;

import com.ip500.webide.domain.user.Member;
import com.ip500.webide.dto.chatting.ChattingContentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name="Chatting")
public class Chat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long projectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @Column(length = 255, nullable = false)
    private String content;

    @CreationTimestamp
    private LocalDateTime sendAt;

    public Chat(ChattingContentRequestDto chattingDto) {
        this.content = chattingDto.getContent();
    }
}
