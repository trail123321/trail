package com.example.phonepeclone

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionSuccessActivity : AppCompatActivity() {

    private lateinit var textAmount: TextView
    private lateinit var textTransactionId: TextView
    private lateinit var textDateTime: TextView
    private lateinit var textPayeeName: TextView
    private lateinit var textUpiId: TextView
    private lateinit var buttonDone: Button
    private lateinit var buttonShare: Button

    private var amount: Double = 0.0
    private var recipientName: String = ""
    private var upiId: String = ""
    private var transactionId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_success)

        extractIntentExtras()
        initViews()
        setupUI()
        setupListeners()
    }

    private fun extractIntentExtras() {
        amount = intent.getDoubleExtra(EXTRA_AMOUNT, 0.0)
        recipientName = intent.getStringExtra(EXTRA_NAME) ?: "Unknown"
        upiId = intent.getStringExtra(EXTRA_UPI_ID) ?: ""
        transactionId = intent.getStringExtra(EXTRA_TRANSACTION_ID) ?: "Unknown"
    }

    private fun initViews() {
        textAmount = findViewById(R.id.textAmount)
        textTransactionId = findViewById(R.id.textTransactionId)
        textDateTime = findViewById(R.id.textDateTime)
        textPayeeName = findViewById(R.id.textPayeeName)
        textUpiId = findViewById(R.id.textUpiId)
        buttonDone = findViewById(R.id.buttonDone)
        buttonShare = findViewById(R.id.buttonShare)
    }

    private fun setupUI() {
        // Format amount with currency
        val formattedAmount = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
            .format(amount).replace("₹", "₹ ")
        
        // Set current date and time
        val currentDateTime = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
            .format(Date())
        
        // Set text views
        textAmount.text = formattedAmount
        textTransactionId.text = "Transaction ID: $transactionId"
        textDateTime.text = "Date & Time: $currentDateTime"
        textPayeeName.text = "Paid to: $recipientName"
        textUpiId.text = "UPI ID: $upiId"
    }

    private fun setupListeners() {
        buttonDone.setOnClickListener {
            // Return to home
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        buttonShare.setOnClickListener {
            shareTransactionDetails()
        }
    }

    private fun shareTransactionDetails() {
        val shareText = """
            Payment Successful!
            Amount: ${NumberFormat.getCurrencyInstance(Locale("en", "IN")).format(amount)}
            Paid to: $recipientName
            UPI ID: $upiId
            Transaction ID: $transactionId
            Date & Time: ${SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date())}
            
            -Sent via Clone It App
        """.trimIndent()

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    companion object {
        const val EXTRA_AMOUNT = "extra_amount"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_UPI_ID = "extra_upi_id"
        const val EXTRA_TRANSACTION_ID = "extra_transaction_id"
    }
} 