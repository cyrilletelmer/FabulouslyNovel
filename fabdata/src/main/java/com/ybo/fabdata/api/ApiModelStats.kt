package com.ybo.fabdata.api

abstract class ApiModelStats {
    abstract val listingCount: Int?
    abstract val averagePrice : Int?
    abstract val lowerPrice : Int?
    abstract val highestPrice : Int?
}