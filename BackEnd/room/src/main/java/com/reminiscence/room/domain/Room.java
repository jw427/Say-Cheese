package com.reminiscence.room.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="password")
    private String password;
    @Column(name="max_count")
    private int maxCount;
    @Column(name="specification")
    private String specification;
    @Column(name="mode")
    @Enumerated(EnumType.STRING)
    private Mode mode;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @Column(name = "room_code")
    private String roomCode;
    @OneToMany(mappedBy = "room")
    private List<Participant> participants = new ArrayList<>();

    @Builder
    public Room(String password, int maxCount, String specification,
                Mode mode, LocalDateTime endDate, String roomCode) {
        this.password = password;
        this.maxCount = maxCount;
        this.specification = specification;
        this.mode = mode;
        this.endDate = endDate;
        this.roomCode = roomCode;
    }
}
