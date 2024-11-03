package com.goruna.spring.users.entity;

import com.goruna.spring.common.aggregate.UserRole;
import com.goruna.spring.common.aggregate.entity.BaseTimeEntity;
import com.goruna.spring.review.entity.Good;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_seq", nullable = false)
    private long userSeq;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "user_nickname", nullable = false)
    private String userNickname;

    @Column(name = "user_deleted_status", nullable = false)
    private boolean userDeletedStatus = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole = UserRole.USER;

    @Column(name = "user_deleted_time", nullable = true)
    private LocalDateTime userDeletedTime;

    @Column(name = "user_saved_money", nullable = false)
    private int userSavedMoney;

    @Column(name = "user_usage_count", nullable = false)
    private int userUsageCount = 0;

    @OneToMany(mappedBy = "user")
    private List<Good> goods;
}
