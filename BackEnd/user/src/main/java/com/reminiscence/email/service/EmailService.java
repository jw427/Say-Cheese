package com.reminiscence.email.service;

import com.reminiscence.email.dto.EmailCheckRequestDto;
import com.reminiscence.email.dto.EmailRequestDto;

public interface EmailService {
    public void storeAuthToken(EmailRequestDto emailRequestDto);
    public void checkAuthToken(EmailCheckRequestDto emailCheckRequestDto);
}
