package com.neon.releasetracker.models;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Release {
    private Integer id;
    private String name;
    private String description;
    private String status;
    private Date releaseDate;
    private Timestamp createdAt;
    private Timestamp lastUpdatedAt;
}
