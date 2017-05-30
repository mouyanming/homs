package jp.co.hyron.ope.common;

import java.util.Date;

import jp.co.hyron.ope.dto.UserDto;
import jp.co.hyron.ope.entity.UserMst;

public class ConvertDtoToEntity {

    public static void convertUserDtoToUserMst(UserDto dto, UserMst entity) {
        String usrNm = dto.getUsrNm();
        if (usrNm != null && !usrNm.equals(entity.getUsrNm())) {
            entity.setUsrNm(usrNm);
        }
        String usrMb = dto.getUsrMb();
        if (usrMb != null && !usrMb.equals(entity.getUsrMb())) {
            entity.setUsrMb(usrMb);
        }
        Position usrTtl = dto.getUsrTtl();
        if (usrTtl != null && !usrTtl.equals(entity.getUsrTtl())) {
            entity.setUsrTtl(usrTtl);
        }
        Affiliation jsgKb = dto.getJsgKb();
        if (jsgKb != null && !jsgKb.equals(entity.getJsgKb())) {
            entity.setJsgKb(jsgKb);
        }
        Date epDt = dto.getEpDt();
        if (epDt != null && !epDt.equals(entity.getEpDt())) {
            entity.setEpDt(epDt);
        }
        Date lfDt = dto.getLfDt();
        if (lfDt != null && !lfDt.equals(entity.getLfDt())) {
            entity.setLfDt(lfDt);
        }
        String spUsrId = dto.getSpUsrId();
        if (spUsrId != entity.getSpUsrId()) {
            entity.setSpUsrId(spUsrId);
        }
        Date usrBth = dto.getUsrBth();
        if (usrBth != null && !usrBth.equals(entity.getUsrBth())) {
            entity.setUsrBth(usrBth);
        }
        Department usrDept = dto.getUsrDept();
        if (usrDept != entity.getUsrDept()) {
            entity.setUsrDept(usrDept);
        }
        short usrSex = dto.getUsrSex();
        if (usrSex != entity.getUsrSex()) {
            entity.setUsrSex(usrSex);
        }
        Status acSts = dto.getStatus();
        if (acSts != entity.getAcSts()) {
            entity.setAcSts(acSts);
        }
        entity.setPwdErrCnt(dto.getPwdErrCnt());
    }

}
