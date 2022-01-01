package com.ndp.audiosn.Models.UserInfo;

import com.ndp.audiosn.Entities.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoUpdateModel {
    private String displayName;

    private String avatar;

    private String email;

    public UserInfo toUserInfoEntity(String username) {
        // return new UserInfo(username, avatar, email);
        return new UserInfo(username, displayName, avatar, email, false);
    }
}
