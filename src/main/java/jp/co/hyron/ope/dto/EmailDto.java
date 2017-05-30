package jp.co.hyron.ope.dto;

import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {

    @NotEmpty
    @Pattern(regexp = ".*@hyron(-js)?.com", message = "{email.format}")
    protected String email;

}
