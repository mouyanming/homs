package jp.co.hyron.ope.entity.type;

import java.io.Serializable;

import javax.persistence.Embeddable;

import jp.co.hyron.ope.entity.Authorities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@link Authorities}の主キー
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthoritiesId implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2772363301265555903L;

    private String user_id;

    private String authority;

}
