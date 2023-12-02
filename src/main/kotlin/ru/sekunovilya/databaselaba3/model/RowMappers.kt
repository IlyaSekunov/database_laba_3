package ru.sekunovilya.databaselaba3.model

import java.sql.ResultSet

fun firstQueryFromResultSet(rs: ResultSet) =
    FirstQuery(
        vendorId = rs.getBigDecimal(1),
        count = rs.getLong(2)
    )

fun listFirstQueryFromResultSet(rs: ResultSet): List<FirstQuery> =
    with(rs) {
        val result = ArrayList<FirstQuery>()
        while (next()) {
            result.add(
                FirstQuery(
                    vendorId = rs.getBigDecimal(1),
                    count = rs.getLong(2)
                )
            )
        }
        result
    }

fun secondQueryFromResultSet(rs: ResultSet) =
    SecondQuery(
        passengerCount = rs.getBigDecimal(1),
        avg = rs.getBigDecimal(2)
    )

fun listSecondQueryFromResultSet(rs: ResultSet): List<SecondQuery> =
    with(rs) {
        val result = ArrayList<SecondQuery>()
        while (next()) {
            result.add(
                SecondQuery(
                    passengerCount = rs.getBigDecimal(1),
                    avg = rs.getBigDecimal(2)
                )
            )
        }
        result
    }

fun thirdQueryFromResultSet(rs: ResultSet) =
    ThirdQuery(
        passengerCount = rs.getBigDecimal(1),
        extract = rs.getBigDecimal(2),
        count = rs.getLong(3)
    )

fun listThirdQueryFromResultSet(rs: ResultSet): List<ThirdQuery> =
    with(rs) {
        val result = ArrayList<ThirdQuery>()
        while (next()) {
            result.add(
                ThirdQuery(
                    passengerCount = rs.getBigDecimal(1),
                    extract = rs.getBigDecimal(2),
                    count = rs.getLong(3)
                )
            )
        }
        result
    }

fun fourthQueryFromResultSet(rs: ResultSet) =
    FourthQuery(
        passengerCount = rs.getBigDecimal(1),
        extract = rs.getBigDecimal(2),
        round = rs.getBigDecimal(3),
        count = rs.getLong(4)
    )

fun listFourthQueryFromResultSet(rs: ResultSet) =
    with(rs) {
        val result = ArrayList<FourthQuery>()
        while (next()) {
            result.add(
                FourthQuery(
                    passengerCount = rs.getBigDecimal(1),
                    extract = rs.getBigDecimal(2),
                    round = rs.getBigDecimal(3),
                    count = rs.getLong(4)
                )
            )
        }
        result
    }