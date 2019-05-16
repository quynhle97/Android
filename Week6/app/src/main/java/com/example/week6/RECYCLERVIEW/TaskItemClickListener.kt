package com.example.week6.RECYCLERVIEW

interface TaskItemClickListener {
    fun onItemCLicked(position: Int)
    fun onItemLongCLicked(position: Int)
    fun onEditIconClicked(position: Int)
}