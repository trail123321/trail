package com.example.phonepeclone.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phonepeclone.QRCodeScannerActivity
import com.example.phonepeclone.R
import com.example.phonepeclone.adapters.TransactionAdapter
import com.example.phonepeclone.models.Transaction
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {
    
    private lateinit var recyclerViewTransactions: RecyclerView
    private lateinit var textViewNoTransactions: TextView
    private lateinit var linearLayoutScanPay: LinearLayout
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        
        recyclerViewTransactions = view.findViewById(R.id.recyclerViewTransactions)
        textViewNoTransactions = view.findViewById(R.id.textViewNoTransactions)
        linearLayoutScanPay = view.findViewById(R.id.linearLayoutScanPay)
        
        setupTransactionsList()
        setupClickListeners(view)
        
        return view
    }
    
    private fun setupTransactionsList() {
        val transactionsList = getDummyTransactions()
        
        if (transactionsList.isEmpty()) {
            recyclerViewTransactions.visibility = View.GONE
            textViewNoTransactions.visibility = View.VISIBLE
        } else {
            recyclerViewTransactions.visibility = View.VISIBLE
            textViewNoTransactions.visibility = View.GONE
            
            recyclerViewTransactions.layoutManager = LinearLayoutManager(context)
            recyclerViewTransactions.adapter = TransactionAdapter(transactionsList)
        }
    }
    
    private fun setupClickListeners(view: View) {
        // Scan & Pay
        linearLayoutScanPay.setOnClickListener {
            startActivity(Intent(activity, QRCodeScannerActivity::class.java))
        }
        
        // QR code icon in toolbar
        view.findViewById<ImageView>(R.id.imageViewQR).setOnClickListener {
            startActivity(Intent(activity, QRCodeScannerActivity::class.java))
        }
    }
    
    private fun getDummyTransactions(): List<Transaction> {
        // Create dummy transactions for demo purposes
        val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        
        val transactions = mutableListOf<Transaction>()
        
        // Today's transaction
        transactions.add(
            Transaction(
                id = "T123456789",
                name = "Vikram Sharma",
                amount = -500.0,
                date = dateFormat.format(calendar.time),
                type = Transaction.TYPE_PAID
            )
        )
        
        // Yesterday's transaction
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        transactions.add(
            Transaction(
                id = "T123456788",
                name = "Neha Patel",
                amount = 1200.0,
                date = dateFormat.format(calendar.time),
                type = Transaction.TYPE_RECEIVED
            )
        )
        
        // Day before yesterday
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        transactions.add(
            Transaction(
                id = "T123456787",
                name = "Coffee Shop",
                amount = -120.0,
                date = dateFormat.format(calendar.time),
                type = Transaction.TYPE_PAID
            )
        )
        
        return transactions
    }
} 