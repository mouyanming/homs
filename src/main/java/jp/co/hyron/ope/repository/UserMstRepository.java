package jp.co.hyron.ope.repository;

import jp.co.hyron.ope.entity.UserMst;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author li_x
 */
@Repository
public interface UserMstRepository extends JpaRepository<UserMst, String> {

    public UserMst findByVdCd(String vdCd);

}