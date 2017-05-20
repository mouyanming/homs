package jp.co.hyron.ope.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.co.hyron.ope.entity.Bbs;

/**
 * @author li_x
 */
@Repository
public interface BbsRepository extends JpaRepository<Bbs, String> {

	//@Query("Select title From bbs WHERE title = ?")
    public Bbs findTitleByTitle(String title);
	
}
