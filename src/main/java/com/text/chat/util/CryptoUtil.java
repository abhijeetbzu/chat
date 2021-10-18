package com.text.chat.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CryptoUtil {
    @Autowired
    PasswordEncoder passwordEncoder;

    public String encodeString(String str) {
        return passwordEncoder.encode(str);
    }

    public boolean matches(String str, String encodedString) {
        return passwordEncoder.matches(str, encodedString);
    }
}
