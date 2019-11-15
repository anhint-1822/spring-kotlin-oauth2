package com.baotrung.springkotlincrud.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

class NewUser : Serializable {

    @JsonProperty("username")
    var username: String? = null

    @JsonProperty("firstName")
    var firstname: String? = null

    @JsonProperty("lastName")
    var lastName: String? = null

    @JsonProperty("email")
    var email: String? = null

    @JsonProperty("password")
    var password: String? = null

    constructor() {}

    constructor(username: String?, firstname: String?, lastName: String?, email: String?, password: String?) {
        this.username = username
        this.firstname = firstname
        this.lastName = lastName
        this.email = email
        this.password = password
    }

    companion object {
        private const val erialVersionUID = -1764970284520387975L
    }
}