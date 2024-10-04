package com.ip500.webide.dto.user;

import com.ip500.webide.domain.user.Member;
import com.ip500.webide.domain.user.MemberRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {
    @NotBlank(message = "ID를 입력하세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
    private String passwordCheck;

    public Member toEntity(){
        return Member.builder()
                .loginId(this.loginId)
                .password(this.password)
                .role(MemberRole.USER)
                .build();
    }
}
