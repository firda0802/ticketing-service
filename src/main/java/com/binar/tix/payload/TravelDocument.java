package com.binar.tix.payload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @RequiredArgsConstructor
public class TravelDocument {

    private String passportNumber;

    private String issuingCountry;

    private LocalDate expirationDate;

    public TravelDocument(String passportNumber, String issuingCountry, LocalDate expirationDate) {
        this.passportNumber = passportNumber;
        this.issuingCountry = issuingCountry;
        this.expirationDate = expirationDate;
    }
}
