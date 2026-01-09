package kr.modernworld.modernworldv2.member.presentation.user.dto.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateUserNicknameRequestDTO(
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{2,10}$")
    @NotNull
    String nickname) {

}
