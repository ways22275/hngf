package com.example.sideproject.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sideproject.R
import com.example.sideproject.data.model.Chat
import com.example.sideproject.ui.base.BaseViewHolder
import java.util.*

class ChatListAdapter : RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {

    private val mList = ArrayList<Chat>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context).inflate(R.layout.item_msg, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[holder.adapterPosition])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    internal fun appendMsg(chat: Chat) {
        mList.add(chat)
        notifyDataSetChanged()
    }

    inner class ViewHolder(v: View) : BaseViewHolder<Chat>(v) {
        var mTextViewContent: TextView = v.findViewById(R.id.textView_content)
        var mTextViewUserName: TextView = v.findViewById(R.id.textView_chat_user_name)

        override fun bind (item : Chat) {
            mTextViewContent.text = item.content
            mTextViewUserName.text = item.from
        }
    }
}
