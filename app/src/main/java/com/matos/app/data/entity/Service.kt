package com.matos.app.data.entity

import java.util.*

class Service(
    val id: Int?,
    val clientId: Int?,
    val description: String?,
    val date: Date?,
    val price: Double?
) {
    constructor(clientId: Int?, description: String?, date: Date?, price: Double?) :
            this(null, clientId, description, date, price)
}