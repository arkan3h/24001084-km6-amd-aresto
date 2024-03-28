package com.arkan.aresto.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.arkan.aresto.R
import com.arkan.aresto.presentation.home.adapter.CategoryAdapter
import com.arkan.aresto.presentation.home.adapter.OnItemCLickedListener
import com.arkan.aresto.presentation.home.adapter.ProductAdapter
import com.arkan.aresto.data.datasource.DummyCategoryDataSource
import com.arkan.aresto.data.datasource.DummyProductDataSource
import com.arkan.aresto.data.model.Category
import com.arkan.aresto.data.model.Product
import com.arkan.aresto.data.repository.CategoryRepository
import com.arkan.aresto.data.repository.CategoryRepositoryImpl
import com.arkan.aresto.data.repository.ProductRepository
import com.arkan.aresto.data.repository.ProductRepositoryImpl
import com.arkan.aresto.databinding.FragmentHomeBinding
import com.arkan.aresto.presentation.detailproduct.DetailProductActivity
import com.arkan.aresto.utils.GenericViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val categoryAdapter = CategoryAdapter()
    private var adapter : ProductAdapter? = null
    private var isUsingGridMode : Boolean = false
    private val viewModel: HomeViewModel by viewModels {
        val productDataSource = DummyProductDataSource()
        val productRepository: ProductRepository = ProductRepositoryImpl(productDataSource)
        val categoryDataSource = DummyCategoryDataSource()
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        GenericViewModelFactory.create(HomeViewModel(categoryRepository, productRepository))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindProductList(isUsingGridMode, viewModel.getProduct())
        setClickAction()
        bindCategoryList(viewModel.getCategory())
    }

    private fun setClickAction() {
        binding.layoutListMenu.ivListMenu.setOnClickListener {
            isUsingGridMode = !isUsingGridMode
            setButtonImage(isUsingGridMode)
            bindProductList(isUsingGridMode, viewModel.getProduct())
        }
    }

    private fun setButtonImage(usingGridMode: Boolean) {
        binding.layoutListMenu.ivListMenu.setImageResource(if (usingGridMode)
            R.drawable.ic_grid_list
        else
            R.drawable.ic_list_grid)
    }

    private fun bindProductList(isUsingGrid: Boolean, data: List<Product>) {
        val listMode = if (isUsingGrid)
            ProductAdapter.MODE_GRID
        else
            ProductAdapter.MODE_LIST
        adapter = ProductAdapter(
            listMode = listMode,
            listener = object : OnItemCLickedListener<Product> {
                override fun onItemClicked(item: Product) {
                    navigateToDetail(item)
                }
            }
        )
        binding.rvProduct.apply {
            adapter = this@HomeFragment.adapter
            layoutManager = GridLayoutManager(requireContext(), if (isUsingGrid)
                2
            else
                1)
        }
        adapter?.submitData(data)
    }

    private fun bindCategoryList(data: List<Category>) {
        binding.rvCategory.apply {
            adapter = this@HomeFragment.categoryAdapter
        }
        categoryAdapter.submitData(data)
    }

    private fun startActivity(item: Product) {
        val intent = Intent(activity, DetailProductActivity::class.java)
        intent.putExtra(DetailProductActivity.EXTRAS_ITEM_ACT, item)
        startActivity(intent)
    }

    private fun navigateToDetail(item: Product) {
        startActivity(item)
    }
}