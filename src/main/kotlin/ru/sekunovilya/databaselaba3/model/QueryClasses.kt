package ru.sekunovilya.databaselaba3.model

import java.math.BigDecimal

open class FirstQuery(
    open val vendorId: BigDecimal,
    open val count: Long
)


open class SecondQuery(
    open val passengerCount: BigDecimal,
    open val avg: BigDecimal
)


open class ThirdQuery(
    open val passengerCount: BigDecimal,
    open val extract: BigDecimal,
    open val count: Long
)


open class FourthQuery(
    open val passengerCount: BigDecimal,
    open val extract: BigDecimal,
    open val round: BigDecimal,
    open val count: Long
)