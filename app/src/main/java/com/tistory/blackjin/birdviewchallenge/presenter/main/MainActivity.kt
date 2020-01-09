package com.tistory.blackjin.birdviewchallenge.presenter.main

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.tistory.blackjin.birdviewchallenge.R
import com.tistory.blackjin.birdviewchallenge.data.ProductRepository
import com.tistory.blackjin.birdviewchallenge.presenter.adapter.ProductAdapter
import com.tistory.blackjin.birdviewchallenge.presenter.adapter.itemdcoration.ProductItemDecoration
import com.tistory.blackjin.birdviewchallenge.presenter.model.ProductItem
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity(), MainContract.View {

    override lateinit var presenter: MainContract.Presenter

    private val productRepo by inject<ProductRepository>()

    private val productAdapter = ProductAdapter()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(
            this, productRepo, compositeDisposable
        )

        initRecyclerView()
        initEditView()

        presenter.loadProduct()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    override fun showProduct(items: List<ProductItem>) {
        productAdapter.replaceAll(items)
    }

    override fun showAddProduct(items: List<ProductItem>) {
        productAdapter.addItems(items)
    }

    override fun showBottomProductLoading() {
        productAdapter.addBottomLoading()
        rvMainProduct.scrollToPosition(productAdapter.itemCount - 1)
    }

    override fun hideBottomProductLoading() {
        productAdapter.removeBottomLoading()
    }

    override fun showLoading() {
        pbMainLoading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pbMainLoading.visibility = View.GONE
    }

    override fun showToast(message: String?) {
        if (!message.isNullOrEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showEmptyText() {
        tvMainEmptyProduct.visibility = View.VISIBLE
    }

    override fun hideEmptyText() {
        tvMainEmptyProduct.visibility = View.GONE
    }

    private fun initRecyclerView() {

        with(rvMainProduct) {
            layoutManager = GridLayoutManager(this.context, ProductAdapter.spanCount).apply {
                spanSizeLookup = object : SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (ProductAdapter.ViewType.getViewType(
                            productAdapter.getItemViewType(position)
                        )) {
                            ProductAdapter.ViewType.PRODUCT -> 1
                            ProductAdapter.ViewType.LOADING -> 2
                        }

                    }
                }
            }

            adapter = productAdapter

            addItemDecoration(ProductItemDecoration())

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0) {
                        val lm = recyclerView.layoutManager
                        if (lm is GridLayoutManager) {
                            if (lm.findLastCompletelyVisibleItemPosition() ==
                                recyclerView.adapter?.itemCount?.minus(1)
                            ) {
                                presenter.addProduct()
                            }
                        }
                    }

                }
            })
        }
    }

    private fun initEditView() {
        etMainSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = v.text.toString()

                if (query.isNotEmpty()) {
                    presenter.initData()
                    presenter.searchProduct(query)
                    removeSearchFocus()
                    hideKeyboard()
                }

                true
            } else {
                false
            }
        }
    }

    private fun removeSearchFocus() {
        etMainSearch.clearFocus()
    }

    private val imm by lazy { getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager }

    private fun hideKeyboard() {
        imm.hideSoftInputFromWindow(etMainSearch.windowToken, 0)
    }
}
