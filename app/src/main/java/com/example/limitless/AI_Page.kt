package com.example.limitless

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.limitless.data.AI.AIDecoder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.LinkedList
import java.util.Queue

class AI_Page : AppCompatActivity() {
    private val decoder = AIDecoder()
    private val lstMessages: Queue<Pair<Boolean, TextView>> = LinkedList()
    private var currentResponse: TextView? = null
    private var subscriberCount = 0
    private val messageHeight = 30.0
    private var isDone = true
    private var isBold = false

    private lateinit var llMessages_AI: LinearLayout
    private lateinit var txtChat: TextView
    var saveMessages: ((SaveMessagesEventArgs) -> Unit)? = null

    init {
        val qMessages = LinkedList(lstMessages)

        while (qMessages.isNotEmpty()) {
            val tuple = qMessages.poll()
            val message = tuple?.second

            val border = createBorder(tuple!!.first, message!!)
            // Add the border to your chat layout
            llMessages_AI.addView(border)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ai_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        llMessages_AI = findViewById(com.example.limitless.R.id.llMessages_AI)
        txtChat = findViewById(R.id.txtChat_AI)

        //Nicks Animation things
//        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
        val btt = AnimationUtils.loadAnimation(this, R.anim.btt)
        val btt2 = AnimationUtils.loadAnimation(this, R.anim.btt2)
//        val btt3 = AnimationUtils.loadAnimation(this, R.anim.btt3)
//        val btt4 = AnimationUtils.loadAnimation(this, R.anim.btt4)

        val imageView26 = findViewById<ImageView>(R.id.imageView26)
        val linearLayout2 = findViewById<ConstraintLayout>(R.id.linearLayoutai)

        imageView26.startAnimation(stb)
        llMessages_AI.startAnimation(btt)
        linearLayout2.startAnimation(btt2)
        //till here


        val btnSend: ImageButton = findViewById(R.id.imgSearch_AI)

        btnSend.setOnClickListener {
            onChatButtonClick()
        }
    }

        fun createBorder(isUserMessage: Boolean, message: TextView): View {
            val border = FrameLayout(this).apply {
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
                setBackgroundColor(if (isUserMessage) Color.parseColor("#EDC0DB") else Color.parseColor("#E9EBED"))
                val params = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(20, 0, 0, 0)
                message.layoutParams = params
                addView(message)
            }
            return border
        }

        fun onChatButtonClick() {
            // Calls the SendMessage method
            if (isDone) {
                sendMessage()
                isDone = false
            }
        }

        fun sendMessage() {
            val request = txtChat.text.toString()

            if (request.isNotEmpty()) { // Null check
                createMessage(request) // Create a message to display the user's input in the chat UI
                //subscribeToEvent() // Subscribe to the event that processes the response if not already subscribed

                // Make a POST request to the AI API to process the user's message
                GlobalScope.launch(Dispatchers.IO) {
                    decoder.makePostRequest(request) { response ->
                        handleResponse(response.response.toString(), response.done)
                    }
                }

                // Create a temporary "..." message to show that the system is processing the response
                val message = TextView(this).apply {
                    text = " ... "
                    isFocusable = false
                    maxWidth = 500
                    setBackgroundColor(Color.parseColor("#EDC0DB"))
                    setTextColor(Color.BLACK)
                }

                currentResponse = message

                // Add the message to the queue of chat messages
                lstMessages.add(Pair(false, message))
                val border = createBorder(false, message)
                llMessages_AI.addView(border)

                // Clear the textbox after the message is sent
                txtChat.text = "";
            }
        }

        fun createMessage(request: String) {
            val message = TextView(this).apply {
                text = request
                isFocusable = false
                maxWidth = 500
                setBackgroundColor(Color.parseColor("#E9EBED"))
                setTextColor(Color.BLACK)
            }

            val border = createBorder(true, message)
            lstMessages.add(Pair(true, message)) // Add the message to the queue of chat messages
            llMessages_AI.addView(border)
        }

        fun handleResponse(word: String, done: Boolean) {
            if (done) {
                currentResponse = null // Clear the current response
                isDone = true
            } else {
                isDone = false
            }

            // If there is an active response, append the words to the current message
            currentResponse?.let { response ->
                if (response.text == " ... ") {
                    response.text = ""
                }

                val temp = response.text.toString() + word
                response.text = temp
            }
        }

        /*fun subscribeToEvent() {
            // Check if no subscribers exist, then subscribe to the response handling event
            if (subscriberCount == 0) {
                decoder.onStringProcessed = ::handleResponse
                subscriberCount++ // Increment the subscriber count
            }
        }*/

        /*fun onTxtChatKeyDown(view: View, event: KeyEvent) {
            // If the Enter key is pressed, simulate the chat button click
            if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                onChatButtonClick(view) // Call the button click event
                event.isCanceled = true // Mark the event as handled to prevent further processing
            }
        }*/

        fun onHelpButtonClick(view: View) {
            AlertDialog.Builder(this)
                .setTitle("ChatBot Help")
                .setMessage("Welcome to the municipal app! Our chatbot is here to assist you in reporting local issues and staying informed about community events. If you encounter problems like illegal dumping or broken streetlights, simply describe the issue and its location, and the chatbot will guide you through the reporting process. It can also provide insights into how these issues impact public safety, utilities, and sanitation.\n\nUsing the chatbot is easy. Just type your question or report, and follow the prompts to explore various options related to municipal services. You can also ask about upcoming events, allowing you to engage more with your community. The chatbot offers quick responses, making it convenient for you to access essential information and report issues. By utilizing this feature, you're playing an active role in helping create a cleaner, safer, and more engaged community. Thank you for using the municipal app; we hope you find the chatbot helpful in your interactions with local services!")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .setIcon(android.R.drawable.ic_dialog_info)
                .show()
        }

        fun getMessages(qMessages: Queue<Pair<Boolean, TextView>>) {
            lstMessages.addAll(qMessages)
        }
    }

    data class SaveMessagesEventArgs(val lstMessages: Queue<Pair<Boolean, TextView>>)