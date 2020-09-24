package com.zihany.cloudmusic.main.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    var type = 0
    private var currentPage = 1
    private var previousTotal = 0
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var loading = true

    constructor(gridLayoutManager: GridLayoutManager) {
        this.gridLayoutManager = gridLayoutManager
        type = 2
    }

    constructor(linearLayoutManager: LinearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager
        type = 1
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (type == 1) {
            visibleItemCount = recyclerView.childCount
            totalItemCount = linearLayoutManager.itemCount
            firstVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
        } else if (type == 2) {
            visibleItemCount = recyclerView.childCount
            totalItemCount = gridLayoutManager.itemCount
            firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition()
        }

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem) {
            currentPage++
            onLoadMore(currentPage)
            loading = true
        }
    }

    abstract fun onLoadMore(currentPage: Int)

    fun reset(previousTotal: Int, loading: Boolean) {
        this.previousTotal = previousTotal
        this.loading = loading
    }
}