package com.reminiscence.room.room.service;

import com.reminiscence.room.domain.Participant;
import com.reminiscence.room.domain.Room;
import com.reminiscence.room.exception.customexception.RoomException;
import com.reminiscence.room.exception.message.RoomExceptionMessage;
import com.reminiscence.room.participant.repository.ParticipantRepository;
import com.reminiscence.room.room.dto.RoomInfoResponseDto;
import com.reminiscence.room.room.dto.WriteRoomRequestDto;
import com.reminiscence.room.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomServiceImpl implements RoomService{
    private final RoomRepository roomRepository;
    private final ParticipantRepository participantRepository;
    @Value("${spring.data.openvidu-url}")
    private String BASE_URL;
    @Value("${spring.data.openvidu-secret}")
    private String SECRET;
    @Override
    public void writeRoom(WriteRoomRequestDto requestDto) {
        roomRepository.save(requestDto.toEntity());
    }


    @Override
    public RoomInfoResponseDto readRoomInfo(String roomCode) {
        Optional<RoomInfoResponseDto> roomInfo = roomRepository.findRoomInfoByRoomCode(roomCode);
        roomInfo.orElseThrow(()->
                new RoomException(RoomExceptionMessage.NOT_FOUND_ROOM));
        return roomInfo.get();
    }

    @Override
    public void checkRoomConnection(Long memberId) {
        Participant participant =
                participantRepository.findByMemberIdAndConnectionY(memberId).orElse(null);

        if(participant != null){
            throw new RoomException(RoomExceptionMessage.ALREADY_IN_ROOM);
        }
    }

    @Override
    public void checkRoomPassword(String roomCode, String password) {
        WebClient build = WebClientBuild();
        checkSession(roomCode, build);
        Optional<Room> room = roomRepository.findByRoomCode(roomCode);
        room.orElseThrow(() -> new RoomException(RoomExceptionMessage.NOT_FOUND_ROOM));
        if(!room.get().getPassword().equals(password)){
            throw new RoomException(RoomExceptionMessage.NOT_MATCH_PASSWORD);
        }
        Long participantCount =
                participantRepository.countByRoomId(room.get().getId());

        if(participantCount > room.get().getMaxCount()){
            throw new RoomException(RoomExceptionMessage.ROOM_IS_FULL);
        }
    }

    @Override
    public void updateRoomStart(String roomCode) {
        Optional<Room> room = roomRepository.findByRoomCode(roomCode);
        room.orElseThrow(()->
                new RoomException(RoomExceptionMessage.NOT_FOUND_ROOM));

        room.get().updateRoomStart();
    }

    @Override
    public void deleteRoom(String roomCode) {
        Optional<Room> room = roomRepository.findByRoomCode(roomCode);
        room.orElseThrow(() -> new RoomException(RoomExceptionMessage.NOT_FOUND_ROOM));
        participantRepository.deleteByRoomId(room.get().getId());
        roomRepository.deleteByRoomCode(roomCode);
    }

    private void checkSession(String roomCode, WebClient build) {
        try{
            build.get()
                    .uri("openvidu/api/sessions/" + roomCode)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        }catch (Exception e){
            throw new RoomException(RoomExceptionMessage.NOT_FOUND_SESSION);
        }
    }

    public WebClient WebClientBuild(){
        return WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + SECRET)
                .build();
    }
}
