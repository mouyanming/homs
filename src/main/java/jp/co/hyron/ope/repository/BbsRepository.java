package jp.co.hyron.ope.repository;

import jp.co.hyron.ope.entity.Bbs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author li_x
 */
@Repository
public interface BbsRepository extends JpaRepository<Bbs, String> {

    // @Query("Select title From bbs WHERE title = ?")
    public Bbs findTitleByTitle(String title);

}
