package com.ybo.fabdata.api

abstract class ApiModelPerformer
    {
    abstract val id:Int
    abstract val name:String
    abstract val shortName:String
    abstract val url:String
    abstract val image:String
    abstract val images:ApiModelMultiImage
    abstract val score:Float
    }