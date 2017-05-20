package jp.co.hyron.ope.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "bbs")
public class Bbs {
	
	@Id
	private String title;
	
	private Date updDt;

}
