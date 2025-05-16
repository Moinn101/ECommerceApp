package com.example.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.ecommerceapp.ui.ColorAdapter
import com.example.ecommerceapp.ui.ImagePagerAdapter
import com.example.ecommerceapp.viewmodel.ProductViewModel
import com.example.ecommerceapp.viewmodel.ProductViewModelFactory
import com.moin.ecommerceapp.R
import com.moin.ecommerceapp.repository.ProductRepository
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val topNavigationBar = findViewById<LinearLayout>(R.id.topNavigationBar)
        val bottomBar = findViewById<LinearLayout>(R.id.bottomBar)

        ViewCompat.setOnApplyWindowInsetsListener(topNavigationBar) { view, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                view.paddingLeft,
                systemInsets.top,
                view.paddingRight,
                view.paddingBottom
            )
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(bottomBar) { view, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                view.paddingLeft,
                view.paddingTop,
                view.paddingRight,
                systemInsets.bottom
            )
            insets
        }

        val repository = ProductRepository()
        val factory = ProductViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(ProductViewModel::class.java)

        viewModel.fetchProductDetails()

        viewModel.productData.observe(this) { product ->
            findViewById<TextView>(R.id.brandName).text = product.brand_name?.uppercase(Locale.getDefault())
            findViewById<TextView>(R.id.productName).text = product.name
            findViewById<TextView>(R.id.productSku).text = "SKU: ${product.sku}"
            findViewById<TextView>(R.id.productPrice).text = "${product.final_price} KWD"

            findViewById<TextView>(R.id.productDescription).text = Html.fromHtml(
                product.description,
                Html.FROM_HTML_MODE_COMPACT
            )

            val viewPager = findViewById<ViewPager2>(R.id.imageViewPager)
            val dotIndicator = findViewById<LinearLayout>(R.id.dotIndicator)

            if (product.images.isNotEmpty()) {
                viewPager.adapter = ImagePagerAdapter(product.images)
                setupDotIndicators(product.images.size)
                viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        updateDotIndicators(position)
                    }
                })
            } else {
                viewPager.visibility = View.GONE
                dotIndicator.visibility = View.GONE
            }

            val colors = product.configurable_option.firstOrNull { it.attribute_code == "color" }?.attributes
                ?: emptyList()
            val recyclerView = findViewById<RecyclerView>(R.id.colorRecyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = ColorAdapter(colors)

            findViewById<Button>(R.id.shareButton).setOnClickListener {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Check out this product: ${product.web_url}")
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(shareIntent, "Share product"))
            }

            findViewById<ImageView>(R.id.shareIconButton).setOnClickListener {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Check out this product: ${product.web_url}")
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(shareIntent, "Share product"))
            }
        }

        viewModel.error.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }

        var quantity = 1
        findViewById<TextView>(R.id.quantityText).text = quantity.toString()
        findViewById<Button>(R.id.decrementButton).setOnClickListener {
            if (quantity > 1) {
                quantity--
                findViewById<TextView>(R.id.quantityText).text = quantity.toString()
            }
        }
        findViewById<Button>(R.id.incrementButton).setOnClickListener {
            quantity++
            findViewById<TextView>(R.id.quantityText).text = quantity.toString()
        }

        findViewById<Button>(R.id.addToBagButton).setOnClickListener {
            Toast.makeText(this, "Added to bag", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            finish()
        }

        findViewById<ImageView>(R.id.likeButton).setOnClickListener {
            Toast.makeText(this, "Liked!", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageView>(R.id.cartButton).setOnClickListener {
            Toast.makeText(this, "Go to cart", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupDotIndicators(count: Int) {
        val dotIndicator = findViewById<LinearLayout>(R.id.dotIndicator)
        dotIndicator.removeAllViews()

        for (i in 0 until count) {
            val dot = ImageView(this).apply {
                setImageResource(R.drawable.dot_unselected)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(8, 0, 8, 0)
                }
            }
            dotIndicator.addView(dot)
        }

        if (count > 0) {
            (dotIndicator.getChildAt(0) as ImageView).setImageResource(R.drawable.dot_selected)
        }
    }

    private fun updateDotIndicators(position: Int) {
        val dotIndicator = findViewById<LinearLayout>(R.id.dotIndicator)
        for (i in 0 until dotIndicator.childCount) {
            val dot = dotIndicator.getChildAt(i) as ImageView
            dot.setImageResource(if (i == position) R.drawable.dot_selected else R.drawable.dot_unselected)
        }
    }
}