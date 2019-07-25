package com.example.factory.presenter.collection

import com.example.factory.data.Job

interface ICollectionView {

    fun onRefreshList(list: List<Job>)

    fun onAddItem(job: Job)

    fun onRemove(job: Job)


}