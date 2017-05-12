package jp.co.hyron.ope.repository;

import java.util.List;

import jp.co.hyron.ope.entity.Applytr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author li_x
 */
@Repository
public interface ApplytrRepository extends JpaRepository<Applytr, Integer> {

    public List<Applytr> findListByUsrId(String usrId);
}
