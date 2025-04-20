package com.example.phonepeclone

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class UPIPinActivity : AppCompatActivity(), View.OnClickListener {
    
    private lateinit var pinDots: Array<View>
    private lateinit var buttonClose: ImageButton
    private lateinit var textViewAmount: TextView
    private var currentPin = StringBuilder()
    private lateinit var vibrator: Vibrator
    private var mediaPlayer: MediaPlayer? = null
    
    private var amount: Double = 0.0
    private var recipientName: String = ""
    private var upiId: String = ""
    private var note: String = ""
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upi_pin)
        
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        
        extractIntentExtras()
        initViews()
        setupUI()
        setupKeypadListeners()
    }
    
    private fun extractIntentExtras() {
        amount = intent.getDoubleExtra(EXTRA_AMOUNT, 0.0)
        recipientName = intent.getStringExtra(EXTRA_NAME) ?: "Unknown"
        upiId = intent.getStringExtra(EXTRA_UPI_ID) ?: ""
        note = intent.getStringExtra(EXTRA_NOTE) ?: ""
    }
    
    private fun initViews() {
        buttonClose = findViewById(R.id.buttonClose)
        textViewAmount = findViewById(R.id.textViewAmount)
        
        // Initialize PIN dots
        pinDots = arrayOf(
            findViewById(R.id.pinDot1),
            findViewById(R.id.pinDot2),
            findViewById(R.id.pinDot3),
            findViewById(R.id.pinDot4)
        )
    }
    
    private fun setupUI() {
        // Format amount with currency
        val formattedAmount = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
            .format(amount).replace("₹", "₹ ")
        
        // Set amount text
        textViewAmount.text = "Paying $formattedAmount to $recipientName"
        
        // Setup close button
        buttonClose.setOnClickListener {
            finish()
        }
    }
    
    private fun setupKeypadListeners() {
        // Number buttons (0-9)
        for (i in 0..9) {
            val buttonId = resources.getIdentifier("buttonKey$i", "id", packageName)
            findViewById<Button>(buttonId).setOnClickListener(this)
        }
        
        // Delete button
        findViewById<ImageButton>(R.id.buttonKeyDelete).setOnClickListener {
            deleteLastDigit()
        }
    }
    
    override fun onClick(view: View) {
        // Only handle number clicks and only if pin is not complete
        if (currentPin.length < 4) {
            // Get digit from button tag or resource name
            val digit = when (view.id) {
                R.id.buttonKey0 -> "0"
                R.id.buttonKey1 -> "1"
                R.id.buttonKey2 -> "2"
                R.id.buttonKey3 -> "3"
                R.id.buttonKey4 -> "4"
                R.id.buttonKey5 -> "5"
                R.id.buttonKey6 -> "6"
                R.id.buttonKey7 -> "7"
                R.id.buttonKey8 -> "8"
                R.id.buttonKey9 -> "9"
                else -> return
            }
            
            addDigit(digit)
        }
    }
    
    private fun addDigit(digit: String) {
        // Add digit to PIN
        currentPin.append(digit)
        
        // Update PIN dot display
        updatePinDots()
        
        // Vibrate for feedback
        vibrator.vibrate(50)
        
        // If PIN is complete, validate it
        if (currentPin.length == 4) {
            verifyPin()
        }
    }
    
    private fun deleteLastDigit() {
        if (currentPin.isNotEmpty()) {
            currentPin.deleteCharAt(currentPin.length - 1)
            updatePinDots()
            vibrator.vibrate(50)
        }
    }
    
    private fun updatePinDots() {
        // Update PIN dots based on current PIN length
        for (i in pinDots.indices) {
            pinDots[i].isActivated = i < currentPin.length
        }
    }
    
    private fun verifyPin() {
        // In a real app, we would verify the PIN with the server
        // For demo purposes, we'll consider any 4-digit PIN as valid
        
        // Play success sound
        playSuccessSound()
        
        // Simulate a short processing delay
        findViewById<View>(android.R.id.content).postDelayed({
            navigateToSuccess()
        }, 500)
    }
    
    private fun playSuccessSound() {
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.payment_success)
            mediaPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun navigateToSuccess() {
        val intent = Intent(this, TransactionSuccessActivity::class.java).apply {
            putExtra(TransactionSuccessActivity.EXTRA_AMOUNT, amount)
            putExtra(TransactionSuccessActivity.EXTRA_NAME, recipientName)
            putExtra(TransactionSuccessActivity.EXTRA_UPI_ID, upiId)
            putExtra(TransactionSuccessActivity.EXTRA_TRANSACTION_ID, generateTransactionId())
        }
        startActivity(intent)
        finish()
    }
    
    private fun generateTransactionId(): String {
        // In a real app, this would come from the server
        // For demo, we'll generate a random ID
        return "T" + System.currentTimeMillis().toString().takeLast(10)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
    
    companion object {
        const val EXTRA_AMOUNT = "extra_amount"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_UPI_ID = "extra_upi_id"
        const val EXTRA_NOTE = "extra_note"
    }
} 