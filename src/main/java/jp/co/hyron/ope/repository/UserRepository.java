package jp.co.hyron.ope.repository;

import jp.co.hyron.ope.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author li_x
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * @param userId
     * @return
     */
    public User findByUserId(String userId);

}
