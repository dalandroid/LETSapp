package com.lssoft2022.letsapp

import com.google.gson.annotations.SerializedName

data class ApiResponse (
    @SerializedName("ListPublicReservationSport")
    var apiResult:ApiResult?
)
data class ApiResult (
    @SerializedName("row")
    var ApiList:MutableList<ApiDto> = arrayListOf()
)

data class ApiDto (
    @SerializedName("IMGURL")
    var imgurl:String?,
    @SerializedName("SVCNM")
    var title:String?,
    @SerializedName("USETGTINFO")
    var target:String?,
    @SerializedName("AREANM")
    var area:String?,
    @SerializedName("PLACENM")
    var place:String?,
    @SerializedName("V_MIN")
    var v_min:String?,
    @SerializedName("V_MAX")
    var v_max:String?,
    @SerializedName("PAYATNM")
    var pay:String?,
    @SerializedName("SVCSTATNM")
    var state:String?,
    @SerializedName("X")
    var x:Double?,
    @SerializedName("Y")
    var y:Double?,
    @SerializedName("SVCURL")
    var site:String?
)
