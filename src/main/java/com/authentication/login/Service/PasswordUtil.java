package com.authentication.login.Service;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    public static String encodePassword(String password)
    {
        return BCrypt.hashpw(password,BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String password,String hashedPassword)
    {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
