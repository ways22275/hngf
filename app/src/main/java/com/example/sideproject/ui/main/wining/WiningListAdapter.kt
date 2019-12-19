package com.example.sideproject.ui.main.wining

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sideproject.R
import com.example.sideproject.data.model.Winning
import com.example.sideproject.ui.base.BaseViewHolder
import com.example.sideproject.utils.widget.CircularTextView

class WiningListAdapter : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var _list :  MutableList<Winning> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*>
        = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_wining, parent, false))

    override fun getItemCount(): Int = _list.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(_list[position])
        }
    }

    fun setData (list : List<Winning>) {
        _list.clear()
        _list.addAll(list)
        notifyDataSetChanged()
    }

    fun addData (list : List<Winning>) {
        _list.addAll(_list.size, list)
        notifyItemRangeInserted(_list.size, list.size)
    }

    inner class ViewHolder(_itemView: View): BaseViewHolder<Winning>(_itemView) {

        private var _stage = _itemView.findViewById<TextView>(R.id.textView_wining_stage)
        private var _date = _itemView.findViewById<TextView>(R.id.textView_wining_stage_date)
        private var _winingList : MutableList<CircularTextView> = mutableListOf()

        init {
            _winingList.add(_itemView.findViewById(R.id.circular_1))
            _winingList.add(_itemView.findViewById(R.id.circular_2))
            _winingList.add(_itemView.findViewById(R.id.circular_3))
            _winingList.add(_itemView.findViewById(R.id.circular_4))
            _winingList.add(_itemView.findViewById(R.id.circular_5))
            _winingList.add(_itemView.findViewById(R.id.circular_6))
        }

        override fun bind (item : Winning) {
            _stage.text = item.stage
            _date.text = item.createTime
            for ((i, number) in item._winingList.withIndex()) {
                _winingList[i].text = number.toString()
            }
        }
    }
}