package jp.co.hyron.ope.dto;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import jp.co.hyron.ope.entity.Applytr;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyDto {
	
	/** 連番 */
	@Id
	@NotNull
    private int apsNo;
    
    /** 申請者 */
    @NotEmpty
    private String usrId;
    
    /** 申請時間 */
    private Timestamp apTm;

    /** 申請種別区部 "Z":在職証明 "S": 収入証明 */
    @NotEmpty
    private String apKb;

    /** 申請内容 */
    @Length(min = 0, max = 150)
    private String apCnt;
    
    /** 承認者（usr_id） */
    private String 	apLet;

    /** 処理者（usr_id） */
    private String dlUsrId;
    
    /** 処理状態 0：初期状態　1:処理中　8:差戻　9:処理済 */
    private int dlSts;

    /** 発行処理日付 */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date ddDt;

    /** 処理者から当該申請に対しての意見とコメント */
    private String dlCmt;

    /** 更新時間 */
    private Timestamp updTm;
    
    public ApplyDto(Applytr applytr) {
    	this.apsNo = applytr.getApsNo();
    	this.usrId = applytr.getUsrId();
    	this.apTm = applytr.getApTm();
    	this.apKb = applytr.getApKb();
    	this.apCnt = applytr.getApCnt();
    	this.apLet = applytr.getApLet();
    	this.dlUsrId = applytr.getDlUsrId();
    	this.dlSts = applytr.getDlSts();
    	this.ddDt = applytr.getDdDt();
    	this.dlCmt = applytr.getDlCmt();
    	this.updTm = applytr.getUpdTm();
    }

}
