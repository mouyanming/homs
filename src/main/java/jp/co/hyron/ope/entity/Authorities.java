package jp.co.hyron.ope.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jp.co.hyron.ope.entity.type.AuthoritiesId;
import lombok.Data;

/**
 * authoritiesテーブルのエンティティ
 */
@Entity
@Table(name = "authorities")
@Data
public class Authorities {

    @Id
    private AuthoritiesId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}
