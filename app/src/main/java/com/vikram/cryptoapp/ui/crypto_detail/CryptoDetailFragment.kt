package com.vikram.cryptoapp.ui.crypto_detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.vikram.cryptoapp.CryptoApplication
import com.vikram.cryptoapp.databinding.FragmentCryptoDetailBinding
import com.vikram.cryptoapp.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_crypto_detail.*


class CryptoDetailFragment : Fragment() {

    private val viewModel by viewModels<CryptoDetailViewModel> {
        CryptoDetailViewModelFactory((requireContext().applicationContext as CryptoApplication).cryptoRepository)
    }
    val args: CryptoDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCryptoDetailBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).supportActionBar?.title = "Detail Screen"
        (requireActivity() as MainActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#101C28")))

        subscribeToObserver(binding)
        return binding.root
    }

    private fun subscribeToObserver(binding: FragmentCryptoDetailBinding) {
        viewModel.start(args.symbol)

        viewModel.cryptoItems.observe(viewLifecycleOwner) {
            tvPriceD.text = "₹${it.lastPrice}"
            tvSymbolD.text = it.symbol!!.uppercase()
            tvAskD.text = "Ask Price: ₹${it.askPrice}"
            tvOpenD.text = "Open: ₹${it.openPrice}"
            tvLowD.text = "Low: ₹${it.lowPrice}"
            tvHighD.text = "High: ₹${it.highPrice}"
            tvBidD.text = "Bid Price: ₹${it.bidPrice}"
            tvVolumeD.text = "Volume: ${it.volume}"
        }

        viewModel.dataLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.pbCryptoDetail.visibility = View.VISIBLE
                binding.cryptoRoot.visibility = View.GONE
            } else {
                binding.pbCryptoDetail.visibility = View.GONE
                binding.cryptoRoot.visibility = View.VISIBLE
            }
        }

    }

}