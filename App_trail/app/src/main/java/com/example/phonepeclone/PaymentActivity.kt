package com.example.phonepeclone

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.hdodenhof.circleimageview.CircleImageView

class PaymentActivity : AppCompatActivity() {
    
    private lateinit var textViewRecipientName: TextView
    private lateinit var textViewUpiId: TextView
    private lateinit var imageViewRecipient: CircleImageView
    private lateinit var editTextAmount: EditText
    private lateinit var editTextNote: EditText
    private lateinit var buttonPay: Button
    private lateinit var buttonBack: ImageButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        
        initViews()
        setupRecipientInfo()
        setupListeners()
    }
    
    private fun initViews() {
        textViewRecipientName = findViewById(R.id.textViewRecipientName)
        textViewUpiId = findViewById(R.id.textViewUpiId)
        imageViewRecipient = findViewById(R.id.imageViewRecipient)
        editTextAmount = findViewById(R.id.editTextAmount)
        editTextNote = findViewById(R.id.editTextNote)
        buttonPay = findViewById(R.id.buttonPay)
        buttonBack = findViewById(R.id.buttonBack)
    }
    
    private fun setupRecipientInfo() {
        val upiId = intent.getStringExtra(EXTRA_UPI_ID) ?: "unknown@upi"
        val name = intent.getStringExtra(EXTRA_NAME) ?: "Unknown"
        
        textViewRecipientName.text = name
        textViewUpiId.text = upiId
        
        // In a real app, we would load the recipient's profile image if available
        // For demo, we'll use the placeholder
    }
    
    private fun setupListeners() {
        buttonBack.setOnClickListener {
            finish()
        }
        
        buttonPay.setOnClickListener {
            proceedToPayment()
        }
        
        editTextAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            
            override fun afterTextChanged(s: Editable?) {
                validateAmount(s.toString())
            }
        })
    }
    
    private fun validateAmount(amount: String) {
        try {
            if (amount.isEmpty()) {
                buttonPay.isEnabled = false
                return
            }
            
            val value = amount.toDouble()
            buttonPay.isEnabled = value > 0
            
        } catch (e: NumberFormatException) {
            buttonPay.isEnabled = false
            Toast.makeText(this, R.string.invalid_amount, Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun proceedToPayment() {
        val amount = editTextAmount.text.toString()
        
        if (amount.isEmpty()) {
            Toast.makeText(this, R.string.invalid_amount, Toast.LENGTH_SHORT).show()
            return
        }
        
        try {
            val amountValue = amount.toDouble()
            
            if (amountValue <= 0) {
                Toast.makeText(this, R.string.invalid_amount, Toast.LENGTH_SHORT).show()
                return
            }
            
            // Navigate to UPI PIN entry activity
            val intent = Intent(this, UPIPinActivity::class.java).apply {
                putExtra(UPIPinActivity.EXTRA_AMOUNT, amountValue)
                putExtra(UPIPinActivity.EXTRA_NAME, textViewRecipientName.text.toString())
                putExtra(UPIPinActivity.EXTRA_UPI_ID, textViewUpiId.text.toString())
                putExtra(UPIPinActivity.EXTRA_NOTE, editTextNote.text.toString())
            }
            startActivity(intent)
            
        } catch (e: NumberFormatException) {
            Toast.makeText(this, R.string.invalid_amount, Toast.LENGTH_SHORT).show()
        }
    }
    
    companion object {
        const val EXTRA_UPI_ID = "extra_upi_id"
        const val EXTRA_NAME = "extra_name"
    }
} 