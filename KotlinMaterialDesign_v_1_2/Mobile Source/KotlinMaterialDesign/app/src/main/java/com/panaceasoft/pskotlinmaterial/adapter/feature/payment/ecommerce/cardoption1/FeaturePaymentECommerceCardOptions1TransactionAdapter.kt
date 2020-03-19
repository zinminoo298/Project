package com.panaceasoft.pskotlinmaterial.adapter.feature.payment.ecommerce.cardoption1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.panaceasoft.pskotlinmaterial.R
import com.panaceasoft.pskotlinmaterial.`object`.FinanceTransferTransaction

/**
 * Created by Panacea-Soft on 7/18/18.
 * Contact Email : teamps.is.cool@gmail.com
 */


class FeaturePaymentECommerceCardOptions1TransactionAdapter(private val financeTransferTransactionList: List<FinanceTransferTransaction>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    private val WHITE_CELL = 0
    private val GREY_CELL = 1

    interface OnItemClickListener {
        fun onItemClick(view: View, obj: FinanceTransferTransaction, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val itemView: View

        when (viewType) {
            WHITE_CELL -> {

                itemView = LayoutInflater.from(parent.context).inflate(R.layout.feature_payment_ecommerce_card_options_1_transaction_item, parent, false)
                return FinanceTransactionViewHolder(itemView)
            }

            GREY_CELL -> {

                itemView = LayoutInflater.from(parent.context).inflate(R.layout.feature_payment_ecommerce_card_options_1_transaction_grey_item, parent, false)
                return FinanceTransactionViewHolder(itemView)
            }

            else -> {

                itemView = LayoutInflater.from(parent.context).inflate(R.layout.feature_payment_ecommerce_card_options_1_transaction_item, parent, false)
                return FinanceTransactionViewHolder(itemView)
            }
        }

    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is FinanceTransactionViewHolder) {
            val financePersonalLog = financeTransferTransactionList[position]

            viewHolder.titleTextView.text = financePersonalLog.userTo
            viewHolder.amountTextView.text = financePersonalLog.amount
            viewHolder.dateTextView.text = financePersonalLog.date
            viewHolder.typeTextView.text = financePersonalLog.type

            viewHolder.constraintLayout.setOnClickListener { v: View -> itemClickListener.onItemClick(v, financeTransferTransactionList[position], position) }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) {
            GREY_CELL
        } else {
            WHITE_CELL
        }
    }

    override fun getItemCount(): Int {

        return financeTransferTransactionList.size

    }

    inner class FinanceTransactionViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        internal var titleTextView: TextView
        internal var amountTextView: TextView
        internal var dateTextView: TextView
        internal var typeTextView: TextView
        internal var constraintLayout: ConstraintLayout


        init {

            titleTextView = view.findViewById(R.id.titleTextView)
            amountTextView = view.findViewById(R.id.amountTextView)
            dateTextView = view.findViewById(R.id.dateTextView)
            typeTextView = view.findViewById(R.id.typeTextView)
            constraintLayout = view.findViewById(R.id.constraintLayout)

        }
    }
}

