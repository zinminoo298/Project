package com.example.stock_design.Modle

class Item {

    var br: String ?= null
    var quantity: Int=0
    var ic: String?=null
    var branch: String?=null
    var date: String?=null
    var location: String?=null
    var master_quantity: Int=0
//    var quantity:Int = 0

    constructor() {}

    constructor(
        br: String,
        branch:String,
        ic: String,
        quantity: Int,
        date: String?,
        location: String?

    ) {
        this.br=br
        this.ic = ic
        this.branch = branch
        this.quantity=quantity
        this.date=date
        this.location=location
    }

}