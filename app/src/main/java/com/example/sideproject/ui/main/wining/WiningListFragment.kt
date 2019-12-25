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
import com.example.sideproject.R
import kotlinx.android.synthetic.main.fragment_winning_list.*

class WiningListFragment : Fragment() {

    private lateinit var _winingViewModel: WiningViewModel
    private lateinit var _adapter : WiningListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_winning_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_line, null))
        recyclerView.addItemDecoration(itemDecoration)

        _adapter = WiningListAdapter()
        recyclerView.adapter = _adapter

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        _winingViewModel = ViewModelProviders.of(this, WiningViewModelFactory())
            .get(WiningViewModel::class.java)

        _winingViewModel.winingList.observe(this, Observer(_adapter :: submitList))

        scrollToTop.setOnClickListener {
            recyclerView.scrollToPosition(0)
        }

        swipeRefreshLayout.setOnRefreshListener {
            _winingViewModel.refresh()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun toastPage(page : Int) {
        Toast.makeText(context, resources.getString(R.string.current_page, page.toString()), Toast.LENGTH_SHORT).show()
    }
}