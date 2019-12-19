package com.example.sideproject.ui.main.wining

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.sideproject.R
import kotlinx.android.synthetic.main.fragment_winning_list.*

class WiningListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var _winingViewModel: WiningViewModel
    private lateinit var _adapter : WiningListAdapter

    private var _isLoadMore = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_winning_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout.setOnRefreshListener(this)

        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_line, null))
        recyclerView.addItemDecoration(itemDecoration)

        _adapter = WiningListAdapter()
        recyclerView.adapter = _adapter

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var mShouldReload = false
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lm = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItem = lm.findFirstVisibleItemPosition()
                val visibleItemCount = lm.childCount
                val totalItemCount: Int = lm.itemCount
                mShouldReload = firstVisibleItem + visibleItemCount == totalItemCount && dy > 0
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mShouldReload)
                    fetchList()
            }
        })

        _winingViewModel = ViewModelProviders.of(this, WiningViewModelFactory())
            .get(WiningViewModel::class.java)

        _winingViewModel.winingList.observe(this, Observer {
            swipeRefreshLayout.isRefreshing = false
            val list = it ?: return@Observer
            if (list.size > 0) {
                val nowPage = _winingViewModel.page
                toastPage(nowPage)
                if (nowPage == 1) {
                    _adapter.setData(list)
                } else if (nowPage > 1) {
                    _adapter.addData(list)
                }
            } else {
                _isLoadMore = false
            }
        })

        scrollToTop.setOnClickListener {
            recyclerView.scrollToPosition(0)
        }
    }

    override fun onStart() {
        super.onStart()
        fetchList()
    }

    override fun onRefresh() {
        _winingViewModel.resetPageCount()
        _isLoadMore = true
        fetchList()
    }

    private fun fetchList() {
        if (_isLoadMore)
            _winingViewModel.getList()
        else
            Toast.makeText(context, resources.getString(R.string.fetch_list_end), Toast.LENGTH_SHORT).show()
    }

    private fun toastPage(page : Int) {
        Toast.makeText(context, resources.getString(R.string.current_page, page.toString()), Toast.LENGTH_SHORT).show()
    }
}