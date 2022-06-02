package com.ski.box.db.converter;

import androidx.annotation.NonNull;

import com.ski.box.db.table.CurrUserTable;
import com.ski.box.httpclient.model.response.UserCurrResp;


/**
 * <pre>
 *     time   : 2020/09/03
 *     desc   :
 * </pre>
 */
public class CurrUserTableConverter {

    @NonNull
    public static CurrUserTable getInstance(@NonNull UserCurrResp userCurrResp) {
        CurrUserTable currUserTable = new CurrUserTable();

        currUserTable.setAvatar(userCurrResp.avatar);
        currUserTable.setAvatarbig(userCurrResp.avatarbig);
        currUserTable.setCreatetime(userCurrResp.createtime);
        currUserTable.setDomain(userCurrResp.domain);
        currUserTable.setFdvalidtype(userCurrResp.fdvalidtype);
        currUserTable.setId((long) userCurrResp.id);
        currUserTable.setInvFlag(userCurrResp.invFlag);
        currUserTable.setInvitecode(userCurrResp.invitecode);
        //currUserTable.setIpInfo();
        currUserTable.setIpid(userCurrResp.ipid);
        currUserTable.setLevel(userCurrResp.level);
        currUserTable.setLoginname(userCurrResp.loginname);
        currUserTable.setMg(userCurrResp.mg);
        currUserTable.setMsgremindflag(userCurrResp.msgremindflag);
        currUserTable.setNick(userCurrResp.nick);
        currUserTable.setPhone(userCurrResp.phone);
        currUserTable.setReghref(userCurrResp.reghref);
        currUserTable.setRegistertype(userCurrResp.registertype);
        currUserTable.setRemark(userCurrResp.remark);
        currUserTable.setSearchflag(userCurrResp.searchflag);
        currUserTable.setSex(userCurrResp.sex);
        currUserTable.setSign(userCurrResp.sign);
        currUserTable.setStatus(userCurrResp.status);
        currUserTable.setThirdstatus(userCurrResp.thirdstatus);
        currUserTable.setUpdatetime(userCurrResp.updatetime);
        currUserTable.setXx(userCurrResp.xx);
        currUserTable.setUpdLoginNameExt(userCurrResp.updLoginNameExt);
        currUserTable.setRoles(userCurrResp.roles);
        currUserTable.setUserSetting(userCurrResp.userSetting);

        return currUserTable;
    }
}
