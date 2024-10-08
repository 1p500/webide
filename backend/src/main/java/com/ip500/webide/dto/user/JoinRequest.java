package com.ip500.webide.dto.user;

import com.ip500.webide.domain.user.Member;
import com.ip500.webide.domain.user.MemberRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
                .userId(this.loginId)
                .password(this.password)
                .userName(this.loginId.toString().split("@")[0])
                .email(this.loginId)
                .role(MemberRole.USER)
                .createdUserDate(new Date())
                .provider("form")
                .build();
    }
}
