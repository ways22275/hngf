package com.example.sideproject.ui.chat

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sideproject.R
import com.example.sideproject.data.model.Chat
import com.example.sideproject.ui.base.BaseViewHolder
import java.util.*

class ChatListAdapter(private val _userName : String) : RecyclerView.Adapter<BaseViewHolder<*>>() {
    companion object {
        const val VIEW_TYPE_CHAT = 0
        const val VIEW_TYPE_CONNECT = 1
        const val VIEW_TYPE_DISCONNECT = 2
    }

    private val mList = ArrayList<Chat>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            VIEW_TYPE_CONNECT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_msg_connect, parent, false)
                ConnectViewHolder(view)
            }
            VIEW_TYPE_DISCONNECT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_msg_disconnect, parent, false)
                DisconnectViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_msg, parent, false)
                ViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is ConnectViewHolder -> holder.bind(mList[holder.adapterPosition])
            is DisconnectViewHolder -> holder.bind(mList[holder.adapterPosition])
            is ViewHolder -> holder.bind(mList[holder.adapterPosition])
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mList[position].type!!
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    internal fun appendMsg(chat: Chat) {
        mList.add(chat)
        notifyDataSetChanged()
    }

    private fun isSelf(userName : String) : Boolean = userName.trim() == _userName.trim()

    inner class ViewHolder(v: View) : BaseViewHolder<Chat>(v) {
        private var mTextViewContent: TextView = v.findViewById(R.id.textView_content)
        private var mTextViewUserName: TextView = v.findViewById(R.id.textView_chat_user_name)
        private val mLayout : LinearLayout = v.findViewById(R.id.layout_chat_msg)

        override fun bind (item : Chat) {
            mLayout.gravity = if (isSelf(item.from)) Gravity.END else Gravity.START
            mTextViewContent.text = item.content
            mTextViewUserName.text = item.from
        }
    }

    inner class ConnectViewHolder(v: View) : BaseViewHolder<Chat>(v) {
        private var mTextViewContent: TextView = v.findViewById(R.id.textView_chat_connect)

        override fun bind (item : Chat) {
            val content = StringBuilder().append(item.from).append("已加入聊天")
            mTextViewContent.text = content.toString()
        }
    }

    inner class DisconnectViewHolder(v: View) : BaseViewHolder<Chat>(v) {
        private var mTextViewContent: TextView = v.findViewById(R.id.textView_chat_disconnect)

        override fun bind (item : Chat) {
            val content = StringBuilder().append(item.from).append("已離開聊天")
            mTextViewContent.text = content.toString()
        }
    }
}
