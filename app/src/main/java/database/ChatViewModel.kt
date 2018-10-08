package com.example.luke.kotlinchatclient

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.util.Log
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import okhttp3.*
import okio.ByteString
import org.json.JSONObject
import org.xml.sax.Parser
import java.util.*
import kotlin.coroutines.experimental.CoroutineContext

class ChatViewModel(application: Application): AndroidViewModel(application) {

    private val repository: ChatRepository

    private val client = OkHttpClient()

    val messages: LiveData<List<Message>>

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    init {
        val db = ChatDatabase.database(application, scope)
        repository = ChatRepository(db.messageDao())
        messages = repository.messages
    }

    val tag: String = "ViewModelTag"

    inner class Websocket: WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            //webSocket.send("Got this?")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            Log.d(tag, "Received-- " + text)

            val json: JSONObject = JSONObject(text)

            val message = Message(Date().time,
                    json["timestamp"].toString(),
                    json["type"].toString(),
                    json["message"].toString(),
                    json["sender"].toString())

            scope.launch(Dispatchers.IO) {
                repository.insert(message)
            }
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            Log.d(tag, "Closing")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.d(tag, "Failure ${response.toString()} $t")
        }
    }

    // Implementation of insert() is completely hidden from the UI. We want the insert()
    // method to be called away from the main thread, so we're launching a new coroutine,
    // based on the coroutine scope defined previously. Because we're doing a database
    // operation, we're using the IO Dispatcher.
    fun insert(message: Message) = scope.launch(Dispatchers.IO) {
        Log.d(tag, "Insert")

        val url =  "ws://${Private.IP}:7070/chat"
        val request = Request.Builder().url(url).build()
        val listener = Websocket()
        val ws = client.newWebSocket(request, listener)
        ws.send(message.content)
        //client.dispatcher().executorService().shutdown()
    }

    // deinit
    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}