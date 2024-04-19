package com.arkan.aresto.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.arkan.aresto.R
import com.arkan.aresto.base.OnItemCLickedListener
import com.arkan.aresto.data.datasource.category.CategoryApiDataSource
import com.arkan.aresto.data.datasource.category.CategoryDataSource
import com.arkan.aresto.presentation.home.adapter.CategoryAdapter
import com.arkan.aresto.presentation.home.adapter.ProductAdapter
import com.arkan.aresto.data.datasource.product.ProductApiDataSource
import com.arkan.aresto.data.datasource.product.ProductDataSource
import com.arkan.aresto.data.model.Category
import com.arkan.aresto.data.model.Product
import com.arkan.aresto.data.repository.CategoryRepository
import com.arkan.aresto.data.repository.CategoryRepositoryImpl
import com.arkan.aresto.data.repository.ProductRepository
import com.arkan.aresto.data.repository.ProductRepositoryImpl
import com.arkan.aresto.data.source.network.services.ArestoApiService
import com.arkan.aresto.databinding.FragmentHomeBinding
import com.arkan.aresto.presentation.detailproduct.DetailProductActivity
import com.arkan.aresto.utils.GenericViewModelFactory
import com.arkan.aresto.utils.UserPreferenceImpl
import com.arkan.aresto.utils.proceedWhen

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var categoryAdapter : CategoryAdapter? = null
    private var productAdapter : ProductAdapter? = null
    private val viewModel: HomeViewModel by viewModels {
        val service = ArestoApiService.invoke()
        val productDataSource: ProductDataSource = ProductApiDataSource(service)
        val productRepository: ProductRepository = ProductRepositoryImpl(productDataSource)
        val categoryDataSource: CategoryDataSource = CategoryApiDataSource(service)
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        GenericViewModelFactory.create(
            HomeViewModel(
                categoryRepository,
                productRepository
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val prefGrid = UserPreferenceImpl(requireContext()).isUsingGrid()
        super.onViewCreated(view, savedInstanceState)
        getCategoryData()
        getProductData(null, prefGrid)
        setButtonImage(prefGrid)
        setClickAction()
    }

    private fun setClickAction() {
        var prefGrid = UserPreferenceImpl(requireContext()).isUsingGrid()
        binding.layoutListMenu.ivListMenu.setOnClickListener {
            prefGrid = !prefGrid
            setButtonImage(prefGrid)
            getProductData(null, prefGrid)
            UserPreferenceImpl(requireContext()).setUsingGridMode(prefGrid)
        }
        categoryAdapter = CategoryAdapter(
            listener = object : OnItemCLickedListener<Category> {
                override fun onItemClicked(item: Category) {
                    getProductData(item.name, prefGrid)
                }
            }
        )
    }

    private fun setButtonImage(usingGridMode: Boolean) {
        binding.layoutListMenu.ivListMenu.setImageResource(if (usingGridMode)
            R.drawable.ic_grid_list
        else
            R.drawable.ic_list_grid)
    }

    private fun getCategoryData() {
        viewModel.getCategory().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data ->
                        bindCategoryList(data)
                    }
                    binding.pbLoadingCategory.isVisible = false
                    binding.tvErrorCategory.isVisible = false
                },
                doOnLoading = {
                    binding.pbLoadingCategory.isVisible = true
                    binding.tvErrorCategory.isVisible = false
                },
                doOnError = {
                    binding.tvErrorCategory.isVisible = true
                    binding.pbLoadingCategory.isVisible = false
                    binding.tvErrorCategory.text = it.exception?.message.orEmpty()
                }
            )
        }
    }

    private fun getProductData(categorySlug: String?, isUsingGrid: Boolean) {
        viewModel.getProduct(categorySlug).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data ->
                        bindProductList(isUsingGrid, data)
                    }
                    binding.pbLoadingProduct.isVisible = false
                    binding.tvErrorProduct.isVisible = false
                },
                doOnLoading = {
                    binding.pbLoadingProduct.isVisible = true
                    binding.tvErrorProduct.isVisible = false
                },
                doOnError = {
                    binding.tvErrorProduct.isVisible = true
                    binding.pbLoadingProduct.isVisible = false
                    binding.tvErrorProduct.text = it.exception?.message.orEmpty()
                }
            )
        }
    }

    private fun bindProductList(isUsingGrid: Boolean, data: List<Product>) {
        val listMode = if (isUsingGrid)
            ProductAdapter.MODE_GRID
        else
            ProductAdapter.MODE_LIST
        productAdapter = ProductAdapter(
            listMode = listMode,
            listener = object : OnItemCLickedListener<Product> {
                override fun onItemClicked(item: Product) {
                    navigateToDetail(item)
                }
            }
        )
        binding.rvProduct.apply {
            adapter = this@HomeFragment.productAdapter
            layoutManager = GridLayoutManager(requireContext(), if (isUsingGrid)
                2
            else
                1)
        }
        productAdapter?.submitData(data)
    }

    private fun bindCategoryList(data: List<Category>) {
        binding.rvCategory.apply {
            adapter = this@HomeFragment.categoryAdapter
        }
        categoryAdapter?.submitData(data)
    }

    private fun startActivity(item: Product) {
        startActivity(Intent(activity, DetailProductActivity::class.java).putExtra(
            DetailProductActivity.EXTRAS_ITEM_ACT, item
        ))
    }

    private fun navigateToDetail(item: Product) {
        startActivity(item)
    }
}