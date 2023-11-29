package ru.sekunovilya.databaselaba3.model

import jakarta.persistence.*
import jakarta.persistence.Table
import java.util.Date

@Entity
@Table(name = "taxi")
open class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VendorID")
    open var vendorId: Long = 1

    @Column(name = "passenger_count")
    open var passengerCount: Double? = null

    @Column(name = "tpep_pickup_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    open var tpepPickupDatetime: Date? = null

    @Column(name = "tpep_dropoff_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    open var tpepDropoffDatetime: Date? = null

    @Column(name = "trip_distance")
    open var tripDistance: Double? = null

    @Column(name = "RatecodeID")
    open var ratecodeId: Double? = null

    @Column(name = "fare_amount")
    open var fareAmount: Double? = null

    @Column(name = "store_and_fwd_flag")
    open var storeAndFwdFlag: Double? = null

    @Column(name = "PULocationID")
    open var pulLocationId: Double? = null

    @Column(name = "DOLocationID")
    open var dolLocationId: Double? = null

    @Column(name = "payment_type")
    open var paymentType: Double? = null

    @Column(name = "extra")
    open var extra: Double? = null

    @Column(name = "mta_tax")
    open var mtaTax: Double? = null

    @Column(name = "tip_amount")
    open var tipAmount: Double? = null

    @Column(name = "tolls_amount")
    open var tollsAmount: Double? = null

    @Column(name = "improvement_surcharge")
    open var improvementSurcharge: Double? = null

    @Column(name = "total_amount")
    open var totalAmount: Double? = null

    @Column(name = "congestion_surcharge")
    open var congestionSurcharge: Double? = null

    @Column(name = "airport_fee")
    open var airportFee: Double? = null

    @Column(name = "'Airport_fee'")
    open var airportFeeAnother: Double? = null
}