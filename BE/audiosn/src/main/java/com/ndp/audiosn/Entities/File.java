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
@Table(name = "tbl_file")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    @Column(name = "col_uuid")
    private String uuid;

    @Column(name = "col_filename_extension")
    private String filenameExtension;

    @Column(name = "col_filename")
    private String filename;
}
