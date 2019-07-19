package com.example.factory.data

data class Job(val isNew: Boolean,
               val imgList_workstation: MutableList<String>,
               val list_photography: List<Triple<String, String, String>>) {

    data class Photography(val imgUr:String,val name:String, val brief:String)
}