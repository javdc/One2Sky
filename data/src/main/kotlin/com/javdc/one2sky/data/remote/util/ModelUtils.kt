package com.javdc.one2sky.data.remote.util

import com.javdc.one2sky.data.remote.model.StringValueDto

val List<StringValueDto>?.value: String?
    get() = this?.firstOrNull()?.value