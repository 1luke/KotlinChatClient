package com.example.luke.kotlinchatclient

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class WordListAdapter internal constructor(context: Context):
        RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    inner class WordViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }

    // MARK: RecyclerView.Adapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        throw Exception("TODO:")
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        //
    }

    override fun getItemCount(): Int {
        throw Exception("TODO:")
    }
}