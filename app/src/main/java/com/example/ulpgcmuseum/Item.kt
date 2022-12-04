package com.example.ulpgcmuseum

data class Item(
    var Name : String ?= "",
    var Description : String ?= "",
    var Year : Int ?= 0,
    var Image : String ?= "",
    var mostVisited : Int ?= 0
)
