package com.lssoft2022.letsapp

class ItemAPI constructor(
    var imgId:Int, var title:String, var loca:String, var state:String
    )

class ParsingData constructor(
    var IMGURL:String,
    var SVCNM:String,
    var USETGTINFO:String,
    var AREANM:String,
    var PLACENM:String,
    var V_MIN:String,
    var V_MAX:String,
    var PAYATNM:String,
    var X:Double,
    var Y:Double,
    var SVCURL:String)