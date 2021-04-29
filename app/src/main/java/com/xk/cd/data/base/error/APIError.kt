package com.xk.cd.data.base.error

import java.io.Serializable

class APIError : Serializable {

    enum class ReasonOfError {
        SERVER_ERROR, NETWORK_ERROR, UNKNOWN_ERROR}

    var status = 0
    var message: String? = null
    var reason: ReasonOfError? = null
    var error: String? = null

    constructor(status: Int, errorMessage: String) {
        this.status = status
        reason = getReasonOfError(errorMessage)
    }

    constructor(status: Int) {
        this.status = status
    }

    constructor(reason: ReasonOfError) {
        this.reason = reason
    }

    fun setErrorCode(statusCode: Int) {
        status = statusCode
    }

    private fun getReasonOfError(message: String): ReasonOfError {
        for (error in ReasonOfError.values()) {
            if (message.equals(error.toString(), ignoreCase = true)) {
                return error
            }
        }
        return ReasonOfError.UNKNOWN_ERROR
    }
}
