package com.arkan.aresto.presentation.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.arkan.aresto.R
import com.arkan.aresto.data.model.Cart
import com.arkan.aresto.databinding.FragmentCartBinding
import com.arkan.aresto.presentation.cart.adapter.CartAdapter
import com.arkan.aresto.presentation.cart.adapter.CartListener
import com.arkan.aresto.presentation.checkout.CheckoutActivity
import com.arkan.aresto.presentation.login.LoginActivity
import com.arkan.aresto.utils.hideKeyboard
import com.arkan.aresto.utils.proceedWhen
import com.arkan.aresto.utils.toIndonesianFormat
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModel()
    private val adapter: CartAdapter by lazy {
        CartAdapter(
            object : CartListener {
                override fun onPlusTotalItemCartClicked(cart: Cart) {
                    viewModel.increaseCart(cart)
                }

                override fun onMinusTotalItemCartClicked(cart: Cart) {
                    viewModel.decreaseCart(cart)
                }

                override fun onRemoveCartClicked(cart: Cart) {
                    viewModel.removeCart(cart)
                }

                override fun onUserDoneEditingNotes(cart: Cart) {
                    viewModel.setCartNotes(cart)
                    hideKeyboard()
                }
            },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observeData()
        setClickListener()
    }

    private fun setClickListener() {
        binding.layoutCheckout.btnCheckout.setOnClickListener {
            checkIfUserLogin()
        }
    }

    private fun observeData() {
        viewModel.getAllCart().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvCart.isVisible = false
                    binding.layoutCheckout.btnCheckout.isActivated = false
                },
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.rvCart.isVisible = true
                    result.payload?.let { (carts, totalPrice) ->
                        adapter.submitData(carts)
                        binding.layoutCheckout.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                    }
                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = result.exception?.message.orEmpty()
                    binding.rvCart.isVisible = false
                    binding.layoutCheckout.btnCheckout.isEnabled = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                    binding.rvCart.isVisible = false
                    binding.layoutCheckout.btnCheckout.isEnabled = false
                    result.payload?.let { (_, totalPrice) ->
                        binding.layoutCheckout.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                    }
                },
            )
        }
    }

    private fun setupList() {
        binding.rvCart.adapter = this@CartFragment.adapter
    }

    private fun checkIfUserLogin() {
        if (viewModel.isUserLoggedIn()) {
            startActivity(Intent(requireContext(), CheckoutActivity::class.java))
        } else {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java))
    }
}
