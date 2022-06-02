package com.ski.box.httpclient.model.response;

import com.blankj.utilcode.util.StringUtils;
import com.ski.box.httpclient.R;

import java.io.Serializable;
import java.util.List;

/**
 * date : 2020-01-07
 * desc :
 */
public class UserCurrResp implements Serializable {

    /*
    {"data":{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/27/110231/1254969215140634624_sm.jpg","avatarbig":"/user/avatar/22/9010/1119563/88097616/74541310984/27/110231/1254969215140634624.jpg","createtime":"2018-11-08 09:08:29","domain":"","email":"gmail.com","emailbindflag":1,"emailpwd":"bc645234eee78a17bb01152901395210","fdvalidtype":1,"id":23436,"invFlag":false,"invitecode":"854135","ipInfo":{"area":"","city":"杭州市","country":"中国","operator":"联通","province":"浙江省"},"ipid":164340,"level":1,"loginname":"gmail.com","mg":false,"msgforbiddenflag":2,"msgremindflag":1,"nick":"wataw","openflag":2,"phone":"18768177675","phonebindflag":1,"phonepwd":"a18c012c4b5540bfe536db3cde448c37","reghref":"https://www.t-io.org","registertype":1,"remark":"王涛","roles":[2,6,7],"searchflag":1,"sex":3,"sign":"wata主账号","status":1,"thirdbindflag":2,"thirdstatus":1,"thirdtype":5,"updatetime":"2020-12-24 16:42:47","xx":2},"ok":true}

    {
        "data":{
            "avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/27/110231/1254969215140634624_sm.jpg",
            "avatarbig":"/user/avatar/22/9010/1119563/88097616/74541310984/27/110231/1254969215140634624.jpg",
            "createtime":"2018-11-08 09:08:29",
            "domain":"",
            "email":"gmail.com",
            "emailbindflag":1,
            "emailpwd":"bc645234eee78a17bb01152901395210",
            "fdvalidtype":1,
            "id":23436,
            "invFlag":false,
            "invitecode":"854135",
            "ipInfo":{
                "area":"",
                "city":"杭州市",
                "country":"中国",
                "operator":"联通",
                "province":"浙江省"
            },
            "ipid":164340,
            "level":1,
            "loginname":"gmail.com",
            "mg":false,
            "msgforbiddenflag":2,
            "msgremindflag":1,
            "nick":"wataw",
            "openflag":2,
            "phone":"18768177675",
            "phonebindflag":1,
            "phonepwd":"a18c012c4b5540bfe536db3cde448c37",
            "reghref":"https://www.t-io.org",
            "registertype":1,
            "remark":"王涛",
            "roles":[
                2,
                6,
                7
            ],
            "searchflag":1,
            "sex":3,
            "sign":"wata主账号",
            "status":1,
            "thirdbindflag":2,
            "thirdstatus":1,
            "thirdtype":5,
            "updatetime":"2020-12-24 16:42:47",
            "xx":2,
            "userSetting":{
                "searchbytel":0,
                "searchbyname":0,
                "searchbyloginname":0,
                "showlastonlinetime":0,
                "invitejoingroup":0,
                "invitejoinchannel":0,
                "showtel":0,
                "voip":0,
                "sendmsg":0,
                "uid":0,
                "needchangepwd":0,
                "friendapplysearch":0,
                "friendapplygroup":0,
                "friendapplyvisitingcard":0,
                "friendapplyqrcode":0,
                "friendapplyfriend":0,
            }
        },
        "ok":true
    }
     */

    public String avatar;
    public String avatarbig;
    public String createtime;
    public String domain;
    public int id;
    public boolean invFlag;
    public String invitecode;
    public IpInfoBean ipInfo;
    public int ipid;
    public int level;
    public String loginname;
    public boolean mg;
    public String nick;
    public String reghref;
    public int registertype;
    public String remark;
    public int status;
    public int thirdstatus;
    public String updatetime;
    public int xx;
    public List<String> roles;
    public UserSettingBean userSetting;
    /**
     * 消息提醒开关：1：开启；2：不开启
     */
    public int msgremindflag;
    /**
     * 手机号
     */
    public String phone;
    /**
     * 手机绑定状态：1 绑定，2 未绑定
     */
    public int phonebindflag;
    /**
     * 三方绑定状态：1 绑定，2 未绑定
     */
    public int thirdbindflag;
    /**
     * 邮箱
     */
    public String email;
    /**
     * 性别：1：男；2：女；3:保密
     */
    public int sex;
    /**
     * 签名
     */
    public String sign;
    /**
     * 好友验证-开关：1：开启验证；2：不开启
     */
    public int fdvalidtype;
    /**
     * 允许他人搜索-开关：1：开启允许搜索；2：不开启
     */
    public int searchflag;

    public String areaCode;

    public Byte bindingVerifyType;  //用户绑定验证类型  1-U盾验证 2-Google验证

    public Byte updLoginNameExt; // 是否可以修改用户名 1-是 2-否

    public Byte allowModifyLoginName; // 本月用户是否能修改用户名

    public String getSex() {
        switch (sex) {
            case 1:
                return StringUtils.getString(R.string.lib_httpclient_male);
            case 2:
                return StringUtils.getString(R.string.lib_httpclient_female);
            case 3:
                return StringUtils.getString(R.string.lib_httpclient_secrecy);
            default:
                return "";
        }
    }

    public boolean isBindPhone() {
        return phonebindflag == 1;
    }

    public boolean isThirdbindflag() {
        return thirdbindflag == 1;
    }

    public String getRegion() {
        if (ipInfo == null) return "";
        String country = ipInfo.country;
        String province = ipInfo.province;
        String city = ipInfo.city;
        return country + " " + province + " " + city;
    }

    public static class IpInfoBean implements Serializable {
        /**
         * area :
         * city : 杭州市
         * country : 中国
         * operator : 联通
         * province : 浙江省
         */

        public String area;
        public String city;
        public String country;
        public String operator;
        public String province;
    }

    @Override
    public String toString() {
        return "UserCurrResp{" +
                "loginname='" + loginname + '\'' +
                ", nick='" + nick + '\'' +
                ", remark='" + remark + '\'' +
                ", id=" + id +
                ", roles=" + roles +
                '}';
    }

    public static class UserSettingBean implements Serializable {
        /**
         * searchbytel : 电话搜索 0 否 1 是
         * searchbyname : 昵称搜索 0 否 1 是
         * searchbyloginname : 用户名搜索 0 否 1 是
         * showlastonlinetime : 显示最后登录时间 0 所有人 1 我的联系人 2 不允许任何人
         * invitejoingroup : 显示最后登录时间 0 所有人 1 我的联系人 2 不允许任何人
         * invitejoinchannel : 允许被邀请入频道 0 所有人 1 我的联系人 2 不允许任何人
         * showtel : 显示电话号码 0 所有人 1 我的联系人 2 不允许任何人
         * voip : 语音通话 0 所有人 1 我的联系人 2 不允许任何人
         * sendmsg : 谁可以给你发送消息 0 所有人 1 我的联系人 2 不允许任何人
         * uid : 用户id 0 否 1 是
         * needchangepwd : 是否需要修改密码  0-否  1-是
         * lang : 语言类型   0-中文  1-英文
         * friendapplysearch : 搜索申请开关
         * friendapplygroup : 群聊申请开关
         * friendapplyvisitingcard : 名片申请开关
         * friendapplyqrcode : 二维码申请开关
         * friendapplyfriend : 聊天申请开关
         */

        public int searchbytel;
        public int searchbyname;
        public int searchbyloginname;
        public int showlastonlinetime;
        public int invitejoingroup;
        public int invitejoinchannel;
        public int showtel;
        public int voip;
        public int sendmsg;
        public int uid;
        /**
         * 0:不需要  1:需要  2:需要,切需要用户填名字和昵称
         */
        public int needchangepwd;
        public int lang;
        public int friendapplysearch;
        public int friendapplygroup;
        public int friendapplyvisitingcard;
        public int friendapplyqrcode;
        public int friendapplyfriend;
    }

}
