package com.hoostec.hfz.dto.back;

import lombok.Data;

import java.util.List;

@Data
public class BatchUpdateResultDto {
    private List<Integer> ids;
    private int status;
    private String msg;
}
