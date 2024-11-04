package com.main.limitless

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.main.limitless.data.AI.AIDecoder
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private val lstMessages: MutableList<Message> = mutableListOf()

class AI_Page : AppCompatActivity() {
    private val decoder = AIDecoder()
    private var currentResponse: TextView? = null
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var ai_Chat: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ai_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtChat: EditText = findViewById(R.id.txtChat_AI)
        ai_Chat = findViewById(R.id.chatRecyclerView)
        chatAdapter = ChatAdapter(lstMessages)

        ai_Chat.layoutManager = LinearLayoutManager(this)
        ai_Chat.adapter = chatAdapter

        //Nicks Animation things
//        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
        val btt = AnimationUtils.loadAnimation(this, R.anim.btt)
        val btt2 = AnimationUtils.loadAnimation(this, R.anim.btt2)
//        val btt3 = AnimationUtils.loadAnimation(this, R.anim.btt3)
//        val btt4 = AnimationUtils.loadAnimation(this, R.anim.btt4)

        val back = findViewById<ImageView>(R.id.backAi)
        val imageView26 = findViewById<ImageView>(R.id.imageView26)
        val linearLayout2 = findViewById<ConstraintLayout>(R.id.linearLayoutai)

        back.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        imageView26.startAnimation(stb)
        ai_Chat.startAnimation(btt)
        linearLayout2.startAnimation(btt2)
        //till here


        val btnSend: Button = findViewById(R.id.imgSearch_AI)

        btnSend.setOnClickListener {
            val request = txtChat.text.toString()

            if(request.isNotEmpty()){
                sendMessage(request)
            }

            txtChat.text.clear()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun sendMessage(request: String) {
        if (request.isNotEmpty()) { // Null check
            lstMessages.add(Message(request, true))

            chatAdapter.notifyItemInserted(lstMessages.size - 1)
            ai_Chat.scrollToPosition(lstMessages.size - 1)

            // Add placeholder message for bot's response
            var responseMessage = Message("...", false)
            lstMessages.add(responseMessage)
            val placeholderPosition = lstMessages.size - 1
            chatAdapter.notifyItemInserted(placeholderPosition)
            ai_Chat.scrollToPosition(placeholderPosition)

            // Make a POST request to the AI API to process the user's message
            GlobalScope.launch(Dispatchers.IO) {
                decoder.makePostRequest(request) { response ->
                    // Switch to the main thread to update the UI
                    GlobalScope.launch(Dispatchers.Main) {
                        if(responseMessage.text == "..."){
                            // Update the placeholder message with each part of the response
                            responseMessage.text = response.response.toString()
                            chatAdapter.notifyItemChanged(placeholderPosition)
                            ai_Chat.scrollToPosition(placeholderPosition)
                        }else{
                            // Update the placeholder message with each part of the response
                            responseMessage.text += response.response.toString()
                            chatAdapter.notifyItemChanged(placeholderPosition)
                            ai_Chat.scrollToPosition(placeholderPosition)
                        }
                    }
                }
            }
        }
    }


    class ChatAdapter(private val messages: List<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_SENT = 1
        const val VIEW_TYPE_RECEIVED = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isSentByUser) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENT) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_sent, parent, false)
            SentMessageViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_received, parent, false)
            ReceivedMessageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (holder is SentMessageViewHolder) {
            holder.bind(message)
        } else if (holder is ReceivedMessageViewHolder) {
            holder.bind(message)
        }
    }

    override fun getItemCount() = messages.size

    inner class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.sentMessageTextView)

        fun bind(message: Message) {
            messageTextView.text = message.text
        }
    }

    inner class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.receivedMessageTextView)

        fun bind(message: Message) {
            messageTextView.text = message.text
        }
    }
}

}

data class Message(var text: String, val isSentByUser: Boolean)

