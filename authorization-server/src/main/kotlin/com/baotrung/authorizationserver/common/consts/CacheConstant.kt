package com.baotrung.authorizationserver.common.consts

class CacheConstant {


    var SPLIT = ":"


    var PREFIX = "$SPLIT"


    var REDIS_SESSION_PREFIX = PREFIX + "session"


    var REDIS_TOKEN_PREFIX = PREFIX + "token" + SPLIT


    var REDIS_CLIENTS_PREFIX = PREFIX + "clients" + SPLIT


    var DEFAULT_CODE_KEY = PREFIX + "code" + SPLIT


    var DEFAULT_EXPIRE_SECONDS = 60
}