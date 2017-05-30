package jp.co.hyron.ope.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper = false)
public class PasswordDto extends EmailDto {
    @NotEmpty
    @Length(min = 8, max = 20)
    private String password;

    @NotEmpty
    @Length(min = 8, max = 20)
    private String passWordTwice;

    @NotEmpty
    @Length(min = 8, max = 20)
    private String oldPassword;

}
