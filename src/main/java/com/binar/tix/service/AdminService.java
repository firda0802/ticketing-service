package com.binar.tix.service;

import com.binar.tix.payload.ReqUpdatePayment;

public interface AdminService {

    void createPayment(ReqUpdatePayment req);
    void disablePayment(int idPayment);
    Boolean updatePayment(int idPayment, ReqUpdatePayment req);
}
