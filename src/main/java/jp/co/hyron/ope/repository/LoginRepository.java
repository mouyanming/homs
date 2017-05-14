package jp.co.hyron.ope.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.hyron.ope.entity.Login;

/**
 * @author li_x
 */
@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {

    public Login findByUserId(String userId);

}
