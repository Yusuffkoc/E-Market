package com.example.e_market.ui.view.filterScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_market.R
import com.example.e_market.adapter.filter.FilterBrandAdapter
import com.example.e_market.adapter.filter.FilterModelAdapter
import com.example.e_market.databinding.FilterScreenBinding
import com.example.e_market.models.FilterEnum
import com.example.e_market.models.ProductResponseItem
import com.example.e_market.repository.FilterCommunication
import java.util.Locale

class FilterDialogFragment : DialogFragment() {

    private lateinit var binding: FilterScreenBinding
    private lateinit var filterBrandAdapter: FilterBrandAdapter
    private lateinit var filterModelAdapter: FilterModelAdapter
    var productList: ArrayList<ProductResponseItem> = ArrayList()
    var allBrandList: ArrayList<String> = ArrayList()
    var allModelList: ArrayList<String> = ArrayList()

    private var selectedFilterEnum: FilterEnum = FilterEnum.PRICELOWTOHIGH

    private var filterCommunication: FilterCommunication? = null

    companion object {
        private const val ALL_PRODUCT_LIST = "allProductList"

        fun newInstance(list1: ArrayList<ProductResponseItem>): FilterDialogFragment {
            val fragment = FilterDialogFragment()
            val args = Bundle()
            args.putParcelableArrayList(ALL_PRODUCT_LIST, list1)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FilterScreenBinding.inflate(inflater, container, false)
        initData()
        initListener()
        initControlRadioButton()
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
        return binding.root
    }


    fun onAttachToParentFragment(callBackInterface: FilterCommunication) {
        if (callBackInterface != null) {
            try {
                this.filterCommunication = callBackInterface;
            } catch (e: ClassCastException) {
                throw ClassCastException(callBackInterface.toString() + " must implement Callback");
            }
        }
    }

    private fun initControlRadioButton() {
        binding.oldToNew.setOnClickListener(radioButtonClickListener)
        binding.newToOld.setOnClickListener(radioButtonClickListener)
        binding.priceHightToLow.setOnClickListener(radioButtonClickListener)
        binding.PriceLowToHigh.setOnClickListener(radioButtonClickListener)

    }

    private val radioButtonClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.oldToNew -> {
                binding.oldToNew.isChecked = !binding.oldToNew.isSelected
                binding.newToOld.isChecked = false
                binding.priceHightToLow.isChecked = false
                binding.PriceLowToHigh.isChecked = false
                selectedFilterEnum = FilterEnum.OLDTONEW
            }

            R.id.newToOld -> {
                binding.newToOld.isChecked = !binding.newToOld.isSelected
                binding.oldToNew.isChecked = false
                binding.priceHightToLow.isChecked = false
                binding.PriceLowToHigh.isChecked = false
                selectedFilterEnum = FilterEnum.NEWTOOLD
            }

            R.id.priceHightToLow -> {
                binding.priceHightToLow.isChecked = !binding.priceHightToLow.isSelected
                binding.oldToNew.isChecked = false
                binding.newToOld.isChecked = false
                binding.PriceLowToHigh.isChecked = false
                selectedFilterEnum = FilterEnum.PRICEHIGHTTOLOW
            }

            R.id.PriceLowToHigh -> {
                binding.PriceLowToHigh.isChecked = !binding.PriceLowToHigh.isSelected
                binding.oldToNew.isChecked = false
                binding.priceHightToLow.isChecked = false
                binding.newToOld.isChecked = false
                selectedFilterEnum = FilterEnum.PRICELOWTOHIGH
            }
        }
    }

    private fun initData() {
        arguments?.getParcelableArrayList<ProductResponseItem>(ALL_PRODUCT_LIST)?.apply {
            productList = this
        }
        setupBrandRecyclerView()
        setupModelRecyclerView()
    }

    private fun initListener() {
        binding.close.setOnClickListener {
            Toast.makeText(
                this.requireContext(),
                getString(R.string.left_filter),
                Toast.LENGTH_SHORT
            ).show()
            this.dialog?.dismiss()
        }

        binding.filterCompleteButton.setOnClickListener {
            filterCommunication?.onFilterEnumSelected(selectedFilterEnum)
            this.dialog?.dismiss()
        }

        binding.searchBrandView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!allBrandList.isNullOrEmpty()) {
                    filterBrandList(newText, allBrandList)
                }
                return true
            }

        })

        binding.modelSearchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!allModelList.isNullOrEmpty()) {
                    filterModelList(newText, allModelList)
                }
                return true
            }

        })
    }

    var searchBrandQuery: String = ""
    private fun filterBrandList(query: String?, list: ArrayList<String>) {
        if (query != null && !list.isNullOrEmpty()) {
            searchBrandQuery = query
            val filteredList = ArrayList<String>()

            for (i in list) {
                if (i.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isNotEmpty()) {
                filterBrandAdapter.setItemList(filteredList)
                filterBrandAdapter.notifyDataSetChanged()
                binding.brandRv.smoothScrollToPosition(0)

            }
        }
    }

    var searchModelQuery: String = ""
    private fun filterModelList(query: String?, list: ArrayList<String>) {
        if (query != null && !list.isNullOrEmpty()) {
            searchModelQuery = query
            val filteredList = ArrayList<String>()

            for (i in list) {
                if (i.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isNotEmpty()) {
                filterModelAdapter.setItemList(filteredList)
                filterModelAdapter.notifyDataSetChanged()
                binding.modelRv.smoothScrollToPosition(0)

            }
        }
    }

    private fun setupModelRecyclerView() {
        filterModelAdapter = FilterModelAdapter()
        binding.modelRv.apply {
            adapter = filterModelAdapter
            layoutManager = LinearLayoutManager(this.context)
        }
        filterModelAdapter.setItemList(getModelList())
    }

    private fun setupBrandRecyclerView() {
        filterBrandAdapter = FilterBrandAdapter()
        binding.brandRv.apply {
            adapter = filterBrandAdapter
            layoutManager = LinearLayoutManager(this.context)
        }
        filterBrandAdapter.setItemList(getBrandList())
    }

    private fun getModelList(): ArrayList<String> {
        for (i in productList) {
            val brand = i.model
            if (!allModelList.contains(brand)) {
                brand?.let {
                    allModelList.add(it)
                }
            }
        }
        return allModelList
    }

    private fun getBrandList(): ArrayList<String> {
        for (i in productList) {
            val brand = i.brand
            if (!allBrandList.contains(brand)) {
                brand?.let {
                    allBrandList.add(it)
                }
            }
        }
        return allBrandList
    }


    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }
}