package com.casavieja.platform.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PasswordChangeRequestM {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
