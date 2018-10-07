package com.example.luke.kotlinchatclient

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class MessageListAdapter internal constructor(val context: Context):
        RecyclerView.Adapter<MessageListAdapter.MessageViewHolder>() {

    // Latest cache
    private var messages: List<Message> = emptyList()

    inner class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        val initialsTextView: TextView = itemView.findViewById(R.id.name_initials)
        val avatarImageView: ImageView = itemView.findViewById(R.id.avatar)
        val previewTextView: TextView = itemView.findViewById(R.id.preview)
        val seenIndicator: ImageView = itemView.findViewById(R.id.seen)
        val lastSeenTextView: TextView = itemView.findViewById(R.id.last_seen)
    }

    // MARK: RecyclerView.Adapter

    // cellForRow
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false)
        )
    }

    /// willDisplayCell
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message: Message = messages[position]
        holder.nameTextView.text = message.sender
        holder.initialsTextView.text = message.senderInitials
        holder.previewTextView.text = message.previewText
        holder.lastSeenTextView.text = message.lastSeen

        // Placeholders
        holder.avatarImageView.setImageResource(R.drawable.placeholder_avatar)
        holder.seenIndicator.setImageResource(R.drawable.ic_check_black_24dp)
    }

    /// numberOfItems
    override fun getItemCount(): Int = messages.size

    // MARK: Internal

    internal fun setWords(words: List<Word>) {
        this.messages = messages
        notifyDataSetChanged()
    }
}