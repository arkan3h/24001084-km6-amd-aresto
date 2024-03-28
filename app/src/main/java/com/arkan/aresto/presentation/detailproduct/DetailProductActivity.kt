package com.arkan.aresto.presentation.detailproduct

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.arkan.aresto.R
import com.arkan.aresto.data.model.Product
import com.arkan.aresto.databinding.ActivityDetailProductBinding
import com.arkan.aresto.utils.GenericViewModelFactory
import com.arkan.aresto.utils.toIndonesianFormat

class DetailProductActivity : AppCompatActivity() {
    companion object {
        const val EXTRAS_ITEM_ACT = "EXTRAS_ITEM_ACT"
    }

    private val binding: ActivityDetailProductBinding by lazy {
        ActivityDetailProductBinding.inflate(layoutInflater)
    }
    private val viewModel: DetailProductViewModel by viewModels {
        GenericViewModelFactory.create(
            DetailProductViewModel(intent?.extras)
        )
    }
    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        bindProduct(viewModel.product)
        setClickListener()
        setAddToCartData()
    }

    private fun bindProduct(product: Product?) {
        product?.let {
            binding.ivProductImage.load(it.imgUrl)
            binding.tvProductName.text = it.name
            binding.tvProductPrice.text = it.price.toIndonesianFormat()
            binding.tvProductDesc.text = it.desc
            binding.layoutLocation.tvProductAddress.text = it.address
            url = it.addressUrl
        }
    }

    private fun setClickListener() {
        binding.btnProfileBack.setOnClickListener {
            backNavigation()
        }
        binding.layoutLocation.layoutLocation.setOnClickListener {
            navigateToMaps()
        }
        binding.layoutAddToCart.btnPlusCart.setOnClickListener {
            viewModel.addQtyProduct()
        }
        binding.layoutAddToCart.btnMinusCart.setOnClickListener {
            viewModel.removeQtyProduct()
        }
    }

    private fun backNavigation() = onBackPressedDispatcher.onBackPressed()

    private fun navigateToMaps() {
        val i = Intent(Intent.ACTION_VIEW)
        i.setData(Uri.parse(url))
        startActivity(i)
    }

    private fun setAddToCartData() {
        viewModel.totalPrice.observe(this) {
            binding.layoutAddToCart.btnAddToCart.text = buildString {
                append("Tambahkan ke Keranjang - ")
                append(it.toIndonesianFormat())
            }
        }
        viewModel.productQty.observe(this) {
            binding.layoutAddToCart.tvQtyProduct.text = it.toString()
        }
    }
}