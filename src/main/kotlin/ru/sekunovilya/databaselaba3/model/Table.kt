package ru.sekunovilya.databaselaba3.model

import jakarta.persistence.*
import jakarta.persistence.Table
import java.util.Date

@Entity
@Table(name = "trips")
open class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VendorID")
    var vendorId: Long? = null

    @Column(name = "tpep_pickup_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    var trepPickupDatetime: Date? = null

    @Column(name = "tpep_dropoff_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    var tpepDropoffDatetime: Date? = null

    @Column(name = "passenger_count")
    var passengerCount: Int? = null
}