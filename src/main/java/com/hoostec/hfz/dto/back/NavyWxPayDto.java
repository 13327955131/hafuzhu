package com.hoostec.hfz.dto.back;

import lombok.Data;

@Data
public class NavyWxPayDto {
    private int todaySum;
    private int totalSum;

    public NavyWxPayDto(int todaySum, int totalSum) {
        this.todaySum = todaySum;
        this.totalSum = totalSum;
    }
}
