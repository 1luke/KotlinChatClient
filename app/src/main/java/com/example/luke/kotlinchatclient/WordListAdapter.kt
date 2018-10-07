package com.example.luke.kotlinchatclient

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class WordListAdapter internal constructor(val context: Context):
        RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    // Cache
    private var words = emptyList<Word>()

    inner class WordViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
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
    }

    /// numberOfItems
    override fun getItemCount(): Int = words.size

    // MARK: Internal

    internal fun setWords(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }
}