package com.reminiscence.room.room;

import com.reminiscence.room.domain.Mode;
import com.reminiscence.room.domain.Participant;
import com.reminiscence.room.domain.Room;
import com.reminiscence.room.domain.Specification;
import com.reminiscence.room.participant.repository.ParticipantRepository;
import com.reminiscence.room.room.repository.RoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RoomJpaTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Test
    @DisplayName("다른 방 접속 확인 테스트(다른 방에 이미 접속한 경우)")
    public void checkRoomConnectionFailTest(){
        //given
        Long memberId = 3L;
        //when
        Participant participant = participantRepository.findByMemberIdAndConnectionY(memberId).orElse(null);

        //then
        assertNotNull(participant);
        assertEquals(1L, participant.getRoom().getId());
        assertEquals(memberId, participant.getMember().getId());
        assertEquals('Y', participant.getConnectionYn());
    }

    @Test
    @DisplayName("다른 방 접속 확인 테스트(다른 방에 접속하지 않은 경우)")
    public void checkRoomConnectionSuccessTest(){
        //given
        Long memberId = 5L;
        //when
        Participant participant = participantRepository.findByMemberIdAndConnectionY(memberId).orElse(null);

        //then
        assertNull(participant);
    }

    @Test
    @DisplayName("방 비밀번호 확인 테스트")
    public void checkPasswordTest(){
        // given
        String roomCode = "sessionA";
        String password = "1234";
        // when
        Room room = roomRepository.findByRoomCode(roomCode).orElse(null);
        // then
        assertNotNull(room);
        assertEquals(password, room.getPassword());
    }

    @Test
    @DisplayName("방 생성 테스트")
    public void createRoomTest(){
        // given
        Room room = Room.builder()
                .password("1234")
                .roomCode("tussle")
                .mode(Mode.GAME)
                .maxCount(4)
                .endDate(LocalDateTime.now())
                .specification(Specification.GRID)
                .build();
        // when
        roomRepository.save(room);
        Room findRoom = roomRepository.findById(room.getId()).orElse(null);
        // then
        assertNotNull(findRoom);
        assertEquals(room.getId(), findRoom.getId());
        assertEquals(room.getPassword(), findRoom.getPassword());
        assertEquals(room.getRoomCode(), findRoom.getRoomCode());
        assertEquals(room.getMode(), findRoom.getMode());
        assertEquals(room.getMaxCount(), findRoom.getMaxCount());
        assertEquals(room.getEndDate(), findRoom.getEndDate());
        assertEquals(room.getSpecification(), findRoom.getSpecification());
    }
    @Test
    @DisplayName("방 시작여부 변경 테스트")
    public void updateRoomStartYn(){
        //given
        String roomCode = "sessionA";

        //when
        Room room = roomRepository.findByRoomCode(roomCode).orElse(null);
        assertNotNull(room);
        room.updateRoomStart();

        //then
        roomRepository.findByRoomCode(roomCode).orElse(null);
        assertNotNull(room);
        assertEquals('Y', room.getStartYn());
    }

    @Test
    @DisplayName("방 삭제 테스트")
    public void deleteRoomTest(){
        // given
        String roomCode = "sessionA";
        Room room = roomRepository.findByRoomCode(roomCode).orElse(null);
        assertNotNull(room);

        // when
        participantRepository.deleteByRoomId(room.getId());
        roomRepository.deleteByRoomCode(roomCode);
        Room findRoom = roomRepository.findByRoomCode(roomCode).orElse(null);
        // then
        assertNull(findRoom);
    }
}
