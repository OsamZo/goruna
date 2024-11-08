package com.goruna.spring.users.service;

import com.goruna.spring.common.util.CustomUserUtils;
import com.goruna.spring.users.dto.NickNameRequestDto;
import com.goruna.spring.users.dto.UserInfoRequestDto;
import com.goruna.spring.users.dto.UserInfoResponse;
import com.goruna.spring.users.entity.User;
import com.goruna.spring.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Transactional
    public void createNickname(Long userSeq, NickNameRequestDto nickNameRequestDto) {
        User user = userRepository.findById(userSeq)
                .orElseThrow(EntityNotFoundException::new);
        modelMapper.map(nickNameRequestDto, user);
        user.addUserSeq(userSeq);

        userRepository.save(user);
    }

    @Transactional
    public UserInfoResponse getUserInfo(Long userSeq) {
        User user = userRepository.getUserInfo(CustomUserUtils.getCurrentUserSeq());
        return modelMapper.map(user, UserInfoResponse.class);
    }

    @Transactional
    public UserInfoRequestDto updateUserInfo(UserInfoRequestDto userInfoRequestDto) {
        User user = userRepository.getUserNickName(CustomUserUtils.getCurrentUserSeq(), userInfoRequestDto);
        return modelMapper.map(user, UserInfoRequestDto.class);
    }
}
