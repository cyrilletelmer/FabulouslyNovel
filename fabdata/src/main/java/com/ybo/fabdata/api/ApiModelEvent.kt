package com.ybo.fabdata.api

abstract class ApiModelEvent() {
    abstract val id: Int
    abstract val title: String
    abstract val url:String
    abstract val date:String
    abstract  val stats : ApiModelStats
    abstract  val performers : List<ApiModelPerformer>
    abstract val type: String
    abstract val venue: ApiModelVenue?
}