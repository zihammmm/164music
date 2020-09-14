package com.zihany.cloudmusic.login.bean

class LoginBean {
    var loginType: Int = 0
    var code: Int = 0
    var account: AccountBean? = null
    var profile: ProfileBean? = null
    var binding: List<BindingBean>? = null

    inner class AccountBean {
        var id: Long = 0
        var userName = ""
        var type: Int = 0
        var status: Int = 0
        var whiteListAuthority: Int = 0
        var createTime: Long = 0
        var salt: String? = null
        var tokenVersion: Int = 0
        var ban: Int = 0
        var baoyueVersion: Int = 0
        var donateVersion: Int = 0
        var vipType: Int = 0
        var vipTypeVersion: Int = 0
        var anonimousUser: Boolean = false

        override fun toString(): String {
            return "AccountBean{" +
                    "id=${id}" +
                    ", userName='${userName}" + '\'' +
                    ", type=${type}" +
                    ", status=${status}" +
                    ", whitelistAuthority=${whiteListAuthority}" +
                    ", createTime=${createTime}" +
                    ", salt='${salt}" + '\'' +
                    ", tokenVersion=${tokenVersion}" +
                    ", ban=${ban}" +
                    ", baoyueVersion=${baoyueVersion}" +
                    ", donateVersion=${donateVersion}" +
                    ", vipType=${vipType}" +
                    ", viptypeVersion=${vipTypeVersion}" +
                    ", anonimousUser=${anonimousUser}" +
                    '}'
        }


    }

    inner class ProfileBean {
        var detailDescription: String? = null
        var followed = false
        var userId = 0
        var defaultAvatar = false
        var avatarUrl: String? = null
        var nickname: String? = null
        var birthday: Long = 0
        var avatarImgId: Long = 0
        var province: Int = 0
        var accountStatus: Int = 0
        var vipType: Int = 0
        var gender: Int = 0
        var djStatus: Int = 0
        var avatarImgIdStr: String? = null
        var backgroundImgIdStr: String? = null
        var experts: ExpertsBean? = null
        var mutual = false
        var remarkName: Any? = null
        var expertTags: Any? = null
        var authStatus = 0
        var backgroundImgId = 0
        var userType = 0
        var city = 0
        var signature: String? = null
        var authority = 0
        var description: String? = null
        var backgroundUrl: String? = null
        var avatarImgId_str: String? = null
        var follows = 0
        var followeds = 0
        var eventCount = 0
        var playListCount = 0
        var playListBeSubscribedCount = 0

        inner class ExpertsBean {
        }

        override fun toString(): String {
            return "ProfileBean{" +
                    "detailDescription='${detailDescription}'" +
                    ", followed=${followed}" +
                    ", userId=${userId}" +
                    ", defaultAvatar=${defaultAvatar}" +
                    ", avatarUrl='${avatarUrl}'" +
                    ", nickname='${nickname}'" +
                    ", birthday=${birthday}" +
                    ", avatarImgId=${avatarImgId}" +
                    ", province=${province}" +
                    ", accountStatus=${accountStatus}" +
                    ", vipType=${vipType}" +
                    ", gender=${gender}" +
                    ", djStatus=${djStatus}" +
                    ", avatarImgIdStr='${avatarImgIdStr}'" +
                    ", backgroundImgIdStr='${backgroundImgIdStr}'" +
                    ", experts=${experts}" +
                    ", mutual=${mutual}" +
                    ", remarkName=${remarkName}" +
                    ", expertTags=${expertTags}" +
                    ", authStatus=${authStatus}" +
                    ", backgroundImgId=${backgroundImgId}" +
                    ", userType=${userType}" +
                    ", city=${city}" +
                    ", signature='${signature}'" +
                    ", authority=${authority}" +
                    ", description='${description}'" +
                    ", backgroundUrl='${backgroundUrl}'" +
                    ", avatarImgId_str='${avatarImgId_str}'" +
                    ", followeds=${followeds}" +
                    ", follows=${follows}" +
                    ", eventCount=${eventCount}" +
                    ", playlistCount=${playListCount}" +
                    ", playlistBeSubscribedCount=${playListBeSubscribedCount}" +
                    '}'
        }
    }



    inner class BindingBean {
        var refreshTime = 0
        var url: String? = null
        var userId = 0
        var tokenJsonStr: String? = null
        var id = 0L
        var type = 0
        var expiresIn = 0
        var bindingTime = 0L
        var expired = false

        override fun toString(): String {
            return "BindingsBean{" +
                    "refreshTime=${refreshTime}" +
                    ", url='${url}'" +
                    ", userId=${userId}" +
                    ", tokenJsonStr='${tokenJsonStr}'" +
                    ", id=${id}" +
                    ", type=${type}" +
                    ", expiresIn=${expiresIn}" +
                    ", bindingTime=${bindingTime}" +
                    ", expired=${expired}" +
                    '}'
        }
    }

    override fun toString(): String {
        return "LoginBean{" +
                "loginType=${loginType}" +
                ", code=${code}" +
                ", account=${account}" +
                ", profile=${profile}" +
                ", bindings=${binding}" +
                '}'
    }
}