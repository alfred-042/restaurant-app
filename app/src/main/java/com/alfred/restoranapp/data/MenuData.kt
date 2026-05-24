package com.alfred.restoranapp.data

import com.alfred.restoranapp.R

val menuList = listOf(

    MenuItem(
        id = 1,
        name = "Burger Special",
        price = "Rp 25.000",
        description = "Burger daging sapi dengan keju lumer",
        imageRes = R.drawable.burger
    ),

    MenuItem(
        id = 2,
        name = "Pizza Mozarella",
        price = "Rp 40.000",
        description = "Pizza dengan topping melimpah",
        imageRes = R.drawable.pizza
    ),

    MenuItem(
        id = 3,
        name = "Ramen Jepang",
        price = "Rp 30.000",
        description = "Ramen kuah gurih ala Jepang",
        imageRes = R.drawable.ramen
    ),

    MenuItem(
        id = 4,
        name = "Es Teh Manis",
        price = "Rp 8.000",
        description = "Minuman segar pelepas dahaga",
        imageRes = R.drawable.teh
    ),

    MenuItem(
        id = 5,
        name = "Kopi Susu",
        price = "Rp 15.000",
        description = "Kopi creamy dengan susu segar",
        imageRes = R.drawable.kopi
    )

)