package com.zynetic.bookstore.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String password;
}
