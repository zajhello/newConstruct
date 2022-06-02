package com.ski.box.db.converter;

import com.blankj.utilcode.util.GsonUtils;
import com.ski.box.httpclient.model.response.UserCurrResp;

import org.greenrobot.greendao.converter.PropertyConverter;

public class UserSettingConverter implements PropertyConverter<UserCurrResp.UserSettingBean, String> {
    @Override
    public UserCurrResp.UserSettingBean convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) return null;

        return GsonUtils.fromJson(databaseValue, UserCurrResp.UserSettingBean.class);
    }
    

    @Override
    public String convertToDatabaseValue(UserCurrResp.UserSettingBean entityProperty) {
        return GsonUtils.toJson(entityProperty);
    }
}
