package com.ndp.audiosn.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_user_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    @Id
    @Column(name = "col_username")
    private String username;

    @Column(name = "col_display_name")
    private String displayName;

    @Column(name = "col_avatar")
    private String avatar;

    @Column(name = "col_email")
    private String email;
}
