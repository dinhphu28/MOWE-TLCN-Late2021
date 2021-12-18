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
public class UserInfoReturnModel {
    private String username;

    private String displayName;

    private String avatar;

    private String email;

    public UserInfoReturnModel (UserInfo userInfo) {
        username = userInfo.getUsername();

        displayName = userInfo.getDisplayName();

        avatar = userInfo.getAvatar();

        email = userInfo.getEmail();
    }
}
