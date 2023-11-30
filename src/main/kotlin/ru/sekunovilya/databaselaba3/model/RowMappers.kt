package ru.sekunovilya.databaselaba3.model

import java.sql.ResultSet

fun firstQueryFromResultSet(rs: ResultSet) =
    FirstQuery(
        vendorId = rs.getBigDecimal(1),
        count = rs.getLong(2)
    )

fun listFirstQueryFromResultSet(rs: ResultSet): List<FirstQuery> =
    rs.let {
        val result = ArrayList<FirstQuery>()
        while (it.next()) {
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
    rs.let {
        val result = ArrayList<SecondQuery>()
        while (it.next()) {
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
    rs.let {
        val result = ArrayList<ThirdQuery>()
        while (it.next()) {
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
    rs.let {
        val result = ArrayList<FourthQuery>()
        while (it.next()) {
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