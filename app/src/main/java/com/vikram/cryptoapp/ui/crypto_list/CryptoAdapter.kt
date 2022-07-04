package com.vikram.cryptoapp.ui.crypto_list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vikram.cryptoapp.data.network.response.CryptoListResponse
import com.vikram.cryptoapp.databinding.LayoutCryptoItemsBinding


class CryptoAdapter : RecyclerView.Adapter<CryptoAdapter.IssuesItemViewHolder>() {

    class IssuesItemViewHolder(private val cryptoBinding: LayoutCryptoItemsBinding) :
        RecyclerView.ViewHolder(cryptoBinding.root) {


        fun bind(cryptoListResponse: CryptoListResponse) {

            val changePercentage =
                (cryptoListResponse.lastPrice?.toDouble()!! - (cryptoListResponse.openPrice?.toDouble()!!)) / (cryptoListResponse.openPrice?.toDouble()!!)

            val change:Double = String.format("%.3f", changePercentage).toDouble()


            cryptoBinding.apply {

                textViewBase.text = cryptoListResponse.baseAsset!!.uppercase()
                textViewQuote.text = "/${cryptoListResponse.quoteAsset!!.uppercase()}"
                textViewSymbol.text = cryptoListResponse.symbol
                textViewCurrPrice.text = "₹${cryptoListResponse.lastPrice}"
                textViewChange.text = "$change %"
                if (change > 0) {
                    textViewChange.setTextColor(Color.parseColor("#00FF00"))
                } else {
                    textViewChange.setTextColor(Color.parseColor("#FF0000"))

                }

            }


        }
    }


    private val diffCallback = object : DiffUtil.ItemCallback<CryptoListResponse>() {

        override fun areItemsTheSame(
            oldItem: CryptoListResponse,
            newItem: CryptoListResponse
        ): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(
            oldItem: CryptoListResponse,
            newItem: CryptoListResponse
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var issuesItems: List<CryptoListResponse>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssuesItemViewHolder {

        val binding =
            LayoutCryptoItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IssuesItemViewHolder(
            binding
        )
    }

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }


    override fun onBindViewHolder(holder: IssuesItemViewHolder, position: Int) {
        val issuesItem = issuesItems[position]

        holder.bind(issuesItem)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { click ->
                click(issuesItem.symbol!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return issuesItems.size
    }
}







