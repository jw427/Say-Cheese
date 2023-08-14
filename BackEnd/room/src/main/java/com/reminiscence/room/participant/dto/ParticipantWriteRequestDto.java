package com.reminiscence.room.participant.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ParticipantWriteRequestDto {
    @NotNull(message = "방장 여부를 입력해주세요.")
    private Character ownerYn;
    @NotBlank(message = "방 코드를 입력해주세요.")
    private String roomCode;
}
