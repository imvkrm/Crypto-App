package com.vikram.cryptoapp.ui.crypto_list

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.vikram.cryptoapp.CryptoApplication
import com.vikram.cryptoapp.databinding.FragmentCryptoListBinding
import com.vikram.cryptoapp.other.setupSnackbar
import com.vikram.cryptoapp.ui.MainActivity

class CryptoListFragment : Fragment() {

    private val viewModel by viewModels<CryptoListViewModel> {
        CryptoViewModelFactory((requireContext().applicationContext as CryptoApplication).cryptoRepository)
    }

    lateinit var cryptoAdapter: CryptoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCryptoListBinding.inflate(inflater, container, false)

        cryptoAdapter = CryptoAdapter()

        cryptoAdapter.setOnItemClickListener {
            findNavController().navigate(
                CryptoListFragmentDirections.actionCryptoListFragmentToCryptoDetailFragment(
                    it
                )
            )
        }

        (requireActivity() as MainActivity).supportActionBar?.title = "Crypto List Screen"
        (requireActivity() as MainActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                Color.parseColor("#101C28"))
        )


        val refreshListener = SwipeRefreshLayout.OnRefreshListener {
             binding.srCrypto.isRefreshing = true

             viewModel.fetchDataFromRepository()
            // call api to reload the screen
        }

        binding.srCrypto.setOnRefreshListener(refreshListener);

        setUpRecyclerView(binding)
        subscribeToObserve(binding)
        binding.srCrypto.isRefreshing = true
        binding.pbCryptoItems.visibility = View.GONE
        return binding.root
    }

    private fun subscribeToObserve(binding: FragmentCryptoListBinding) {

        binding.root.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)

        viewModel.dataLoading.observe(viewLifecycleOwner) { dataLoading ->

            binding.srCrypto.isRefreshing = dataLoading

        }

        viewModel.items.observe(viewLifecycleOwner) {

            cryptoAdapter.issuesItems = it
            Log.e("Vikram", cryptoAdapter.issuesItems.size.toString())

        }
    }

    private fun setUpRecyclerView(binding: FragmentCryptoListBinding) {
        binding.rvCryptoItems.apply {
            adapter = cryptoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}