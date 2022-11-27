package com.binar.tix.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespNotifCount {
    private int jumlahNotif;

    public RespNotifCount(int jumlahNotif) {
        this.jumlahNotif = jumlahNotif;
    }
}
