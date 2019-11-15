package com.baotrung.springkotlincrud.model

import java.io.Serializable

class LoginUser : Serializable {

    var username: String? = null

    var password: String? = null

    constructor() {}

    constructor(username: String?, password: String?) {
        this.username = username
        this.password = password
    }


    companion object {
        private const val serialVersionUID = -1764970284520387975L
    }
}