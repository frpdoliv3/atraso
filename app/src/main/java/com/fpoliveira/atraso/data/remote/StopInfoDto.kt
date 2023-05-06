package com.fpoliveira.atraso.data.remote

import com.squareup.moshi.Json

data class StopInfoDto(
    @field:Json(name = "ComboioPassou")
    val trainPassed: Boolean,

    @field:Json(name = "HoraProgramada")
    val scheduledTime: String,

    @field:Json(name = "NomeEstacao")
    val stationName: String,

    @field:Json(name = "Observacoes")
    val observations: String
)