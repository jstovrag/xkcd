package com.xk.cd.data.base.error

class AppException(val error: APIError): Exception(error.message)
