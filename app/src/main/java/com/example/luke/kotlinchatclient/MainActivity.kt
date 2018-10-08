package com.example.luke.kotlinchatclient

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var chatViewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        title = "Chat"

        addButton.setOnClickListener { startNewWordActivity() }

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupViewModel() {
        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel::class.java)
        chatViewModel.messages.observe(this, Observer { messages ->
            (recyclerview.adapter as? MessageListAdapter)
                    ?.updateMessages(messages ?: emptyList<Message>())
        })
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set data source
        recyclerView.adapter = MessageListAdapter(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode.isActivityResultOk && requestCode.isNewActivityStatusCode) {
            data?.let {
                val message: Message = data.getSerializableExtra(ComposeActivity.EXTRA_REPLY) as Message
                chatViewModel.insert(message)
            }
        } else {
            Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

fun AppCompatActivity.startNewWordActivity() {
    startActivityForResult(
            Intent(this, ComposeActivity::class.java),
            ActivityRequestCode.NewWordActivity.ordinal
    )
}

// MARK: Helpers (TODO: Move to separate file)

/// Returns true if status code is `Activity.RESULT_OK`
val Int.isActivityResultOk: Boolean get() = this == Activity.RESULT_OK

/// List of App's activity request codes
enum class ActivityRequestCode {
    NewWordActivity
}

val Int.isNewActivityStatusCode: Boolean get() = this == ActivityRequestCode.NewWordActivity.ordinal