/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.payload;

import com.binar.tix.utility.Constant;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Riko
 */
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Messages {
    private Integer responseCode;
    private String responseMessage;
    private Object data;
    private PagingResponse paging;

    public void success() {
        this.responseCode = Constant.OK;
        this.responseMessage = Constant.SUKSES;
    }
    public void notFound() {
        this.responseCode = Constant.NO_CONTENT;
        this.responseMessage = "Data tidak ditemukan";
    }
    public Messages() {
    }

    public Messages(Integer responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }
}
