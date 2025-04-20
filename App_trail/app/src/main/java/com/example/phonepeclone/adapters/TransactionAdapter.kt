package com.example.phonepeclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.phonepeclone.R
import com.example.phonepeclone.models.Transaction
import kotlin.math.abs

class TransactionAdapter(
    private val transactions: List<Transaction>
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }
    
    override fun getItemCount(): Int = transactions.size
    
    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        private val textViewAmount: TextView = itemView.findViewById(R.id.textViewAmount)
        
        fun bind(transaction: Transaction) {
            textViewName.text = transaction.name
            textViewDate.text = transaction.date
            
            // Format amount based on transaction type
            val formattedAmount = when (transaction.type) {
                Transaction.TYPE_PAID -> "- ₹${abs(transaction.amount)}"
                Transaction.TYPE_RECEIVED -> "+ ₹${transaction.amount}"
                else -> "₹${transaction.amount}"
            }
            
            textViewAmount.text = formattedAmount
            
            // Set color based on transaction type
            val colorRes = when (transaction.type) {
                Transaction.TYPE_PAID -> R.color.transaction_paid
                Transaction.TYPE_RECEIVED -> R.color.transaction_received
                else -> R.color.phonepe_black
            }
            
            textViewAmount.setTextColor(ContextCompat.getColor(itemView.context, colorRes))
        }
    }
} 