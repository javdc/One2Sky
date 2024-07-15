package com.javdc.one2sky.data

import com.javdc.one2sky.data.remote.mapper.toWeatherBo
import com.javdc.one2sky.data.remote.wttrResponseDto
import org.junit.Test

class MappersTest {

    @Test
    fun `when WttrResponseDto mapper is called, then a WeatherBo is returned successfully`() {
        // Given
        val dto = wttrResponseDto

        // When
        val bo = dto.toWeatherBo()

        // Then
        assert(bo.tempCelsius == dto.currentCondition?.first()?.tempC?.toIntOrNull())
    }

}
