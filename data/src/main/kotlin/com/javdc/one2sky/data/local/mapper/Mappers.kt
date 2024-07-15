package com.javdc.one2sky.data.local.mapper

import com.javdc.one2sky.data.local.model.LocationDbo
import com.javdc.one2sky.domain.model.LocationBo

fun LocationBo.toLocationDbo() = LocationDbo(
    query = query,
    areaName = areaName,
    region = region,
)

fun LocationDbo.toLocationBo(): LocationBo {
    return LocationBo(
        query = query,
        areaName = areaName,
        region = region,
    )
}
