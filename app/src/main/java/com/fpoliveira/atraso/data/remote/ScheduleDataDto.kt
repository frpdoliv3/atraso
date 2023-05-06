package com.fpoliveira.atraso.data.remote

import com.squareup.moshi.Json

data class ScheduleDataDto (
    @field:Json(name = "DataHoraDestino")
    val arrivalDate: String,

    @field:Json(name = "DataHoraOrigem")
    val departureDate: String,

    @field:Json(name = "DuracaoViagem")
    val duration: String,

    @field:Json(name = "Origem")
    val departure: String,

    @field:Json(name = "Destino")
    val destination: String,

    @field:Json(name = "Operador")
    val operator: String,

    @field:Json(name = "TipoServico")
    val serviceType: String,

    @field:Json(name = "SituacaoComboio")
    val trainStatus: String,

    @field:Json(name = "NodesPassagemComboio")
    val trainPassageNodes: List<StopInfoDto>
)
