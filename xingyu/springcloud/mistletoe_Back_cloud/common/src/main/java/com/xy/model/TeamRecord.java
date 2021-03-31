package com.xy.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 调队记录实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TeamRecord implements Serializable {

    private int id;

    private String username;

    private String pastTeam;

    private String currentTeam;

    private LocalDateTime changeDate;
}
