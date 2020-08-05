package com.opay.im.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OpayUser {
    private String id;
    private String phoneNumber;
    private String firstName;
    private String middleName;
    private String surname;
}
