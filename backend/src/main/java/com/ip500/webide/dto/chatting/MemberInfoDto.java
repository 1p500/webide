package com.ip500.webide.dto.chatting;

import com.ip500.webide.domain.user.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberInfoDto {
    private Long id;
    private String email; // 이메일
    private String name; // 이름

    public MemberInfoDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getUserName();
    }
}
