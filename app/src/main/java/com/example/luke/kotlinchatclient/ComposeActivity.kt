package com.example.luke.kotlinchatclient

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import java.util.*

class ComposeActivity : AppCompatActivity() {

    private lateinit var contentEditText: EditText
    private lateinit var nameEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        title = "Compose message"

        contentEditText = findViewById(R.id.message_field)
        nameEditText = findViewById(R.id.name_field)

        val saveButton = findViewById<Button>(R.id.button_save)
        saveButton.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(contentEditText.text) || TextUtils.isEmpty(nameEditText.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val content = contentEditText.text.toString()
                val name = nameEditText.text.toString()
                val message = Message("$name", Date().time, "type", "$content", "$name")

                replyIntent.putExtra(EXTRA_REPLY, message)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        val discardButton = findViewById<Button>(R.id.button_discard)
        discardButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED, Intent())
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}
