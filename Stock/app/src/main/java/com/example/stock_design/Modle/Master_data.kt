package com.example.stock_design.Modle

class Master_data {

    var br: String ?= null
    var quantity: Int=0
    var ic: String?=null


    constructor() {}

    constructor(
        br: String,
        quantity:Int,
        ic: String
    ) {
        this.br=br
        this.ic = ic
        this.quantity=quantity
    }

}