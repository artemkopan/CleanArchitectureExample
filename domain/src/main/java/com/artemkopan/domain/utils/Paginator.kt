package com.artemkopan.domain.utils

class Paginator<Data> {

    val data: MutableList<Data> = mutableListOf()

    var next: String = ""


    fun fetch() {
        data.clear()
        next = ""
    }

    fun addItems(items: Collection<Data>) {
        data.addAll(items)
    }


}