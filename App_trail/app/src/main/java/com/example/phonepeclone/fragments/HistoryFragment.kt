package com.example.phonepeclone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phonepeclone.R
import com.example.phonepeclone.adapters.TransactionAdapter
import com.example.phonepeclone.models.Transaction
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment : Fragment() {
    
    private lateinit var recyclerViewTransactions: RecyclerView
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        
        recyclerViewTransactions = view.findViewById(R.id.recyclerViewAllTransactions)
        setupTransactionsList()
        
        return view
    }
    
    private fun setupTransactionsList() {
        val transactionsList = getAllDummyTransactions()
        
        recyclerViewTransactions.layoutManager = LinearLayoutManager(context)
        recyclerViewTransactions.adapter = TransactionAdapter(transactionsList)
    }
    
    private fun getAllDummyTransactions(): List<Transaction> {
        // Create more dummy transactions for the history tab
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
        
        // Add more past transactions
        for (i in 3..10) {
            calendar.add(Calendar.DAY_OF_MONTH, -2)
            transactions.add(
                Transaction(
                    id = "T1234567${80 - i}",
                    name = "Transaction #$i",
                    amount = if (i % 2 == 0) -i * 100.0 else i * 100.0,
                    date = dateFormat.format(calendar.time),
                    type = if (i % 2 == 0) Transaction.TYPE_PAID else Transaction.TYPE_RECEIVED
                )
            )
        }
        
        return transactions
    }
} 