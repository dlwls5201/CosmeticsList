package com.tistory.blackjin.birdviewchallenge.presenter.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.tistory.blackjin.birdviewchallenge.R
import com.tistory.blackjin.birdviewchallenge.data.ProductRepository
import com.tistory.blackjin.birdviewchallenge.data.error.MyHttpException
import com.tistory.blackjin.birdviewchallenge.presenter.adapter.ProductAdapter
import com.tistory.blackjin.birdviewchallenge.presenter.adapter.itemdcoration.ProductItemDecoration
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private val productRepo by inject<ProductRepository>()

    private val productAdapter = ProductAdapter()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

                    val lm = recyclerView.layoutManager
                    if (lm is GridLayoutManager) {
                        if (lm.findLastCompletelyVisibleItemPosition() ==
                            recyclerView.adapter?.itemCount?.minus(1)
                        ) {
                            Toast.makeText(recyclerView.context, "Bottom", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }

        productRepo.get()
            .subscribe({
                productAdapter.replaceAll(it)
            }) {
                if (it is MyHttpException) {
                    Timber.d("code : ${it.code} , message : ${it.message}")
                } else {
                    Timber.e(it)
                }
            }.also {
                compositeDisposable.add(it)
            }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}
