package com.example.luke.kotlinchatclient

import android.content.Context
import android.support.v4.widget.ImageViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class WordListAdapter internal constructor(val context: Context):
        RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    // Cache
    private var words = mutableListOf<Word>(Word("Hello"), Word("World!"))

    inner class WordViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.name)
        val initialsTextView: TextView = itemView.findViewById(R.id.name_initials)
        val avatarImageView: ImageView = itemView.findViewById(R.id.avatar)
        val previewTextView: TextView = itemView.findViewById(R.id.preview)
        val seenIndicator: ImageView = itemView.findViewById(R.id.seen)
        val lastSeenTextView: TextView = itemView.findViewById(R.id.last_seen)
    }

    // MARK: RecyclerView.Adapter

    // cellForRow
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
       return WordViewHolder(
               LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false)
       )
    }

    /// willDisplayCell
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.wordItemView.text = words[position].word
        holder.initialsTextView.text = ""
        holder.previewTextView.text = "some last message..."
        holder.lastSeenTextView.text = "Today at: 08"
        holder.avatarImageView.setImageResource(R.drawable.placeholder_avatar)
        holder.seenIndicator.setImageResource(R.drawable.ic_check_black_24dp)
    }

    /// numberOfItems
    override fun getItemCount(): Int = words.size

    // MARK: Internal

    internal fun setWords(words: List<Word>) {
        this.words = words.toMutableList()
        notifyDataSetChanged()
    }
}