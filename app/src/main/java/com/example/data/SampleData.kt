package com.example.data

import com.example.R
import com.example.model.CategoryItem
import com.example.model.Product
import com.example.model.StoreCategory

object SampleData {

  val categories = listOf(
    CategoryItem(
      category = StoreCategory.ELECTRONICS,
      imageRes = R.drawable.cat_electronics_3d_1791428151836,
      tagText = "Up to 40% OFF"
    ),
    CategoryItem(
      category = StoreCategory.FASHION,
      imageRes = R.drawable.cat_fashion_3d_1791428165885,
      tagText = "Trending BD"
    ),
    CategoryItem(
      category = StoreCategory.HOME_LIVING,
      imageRes = R.drawable.cat_home_3d_1791428183867,
      tagText = "Modern Living"
    ),
    CategoryItem(
      category = StoreCategory.BEAUTY,
      imageRes = R.drawable.cat_beauty_3d_1791428196707,
      tagText = "100% Authentic"
    ),
    CategoryItem(
      category = StoreCategory.GROCERIES,
      imageRes = R.drawable.cat_groceries_3d_1791428215049,
      tagText = "Farm Fresh"
    )
  )

  val products = listOf(
    Product(
      id = "bm_prod_1",
      name = "Smart ANC Wireless Earbuds Pro",
      category = StoreCategory.ELECTRONICS,
      priceBDT = 3450,
      originalPriceBDT = 4200,
      rating = 4.8f,
      reviewCount = 186,
      imageRes = R.drawable.prod_earbuds_3d_1791428234706,
      description = "Next-gen active noise cancellation, immersive deep bass, dual mic with AI environmental noise reduction. Up to 36 hours total battery backup with USB-C quick charge. Water-resistant IPX5.",
      badge = "Hot Deal",
      brand = "AcoustiQ Pro"
    ),
    Product(
      id = "bm_prod_2",
      name = "Urban Knit Breathable Running Sneakers",
      category = StoreCategory.FASHION,
      priceBDT = 2150,
      originalPriceBDT = 2650,
      rating = 4.7f,
      reviewCount = 94,
      imageRes = R.drawable.cat_fashion_3d_1791428165885,
      description = "Ultra-flexible honeycomb knit upper with shock-absorbent cloud foam sole. Designed for tropical weather airflow, daily casual commute, and workouts. Ergonomic arch support.",
      badge = "Trending",
      brand = "Stride Dhaka"
    ),
    Product(
      id = "bm_prod_3",
      name = "Aura Nordic Ceramic Touch Lamp",
      category = StoreCategory.HOME_LIVING,
      priceBDT = 2890,
      originalPriceBDT = 3400,
      rating = 4.9f,
      reviewCount = 62,
      imageRes = R.drawable.cat_home_3d_1791428183867,
      description = "Minimalist matte ceramic body with 3-level warm ambient touch dimming. Features an integrated fast Qi wireless phone charging pad base. Perfect for bedside or modern study desks.",
      badge = "Best Seller",
      brand = "Nordic Living"
    ),
    Product(
      id = "bm_prod_4",
      name = "GlowBoost Niacinamide Radiance Serum (30ml)",
      category = StoreCategory.BEAUTY,
      priceBDT = 1250,
      originalPriceBDT = 1500,
      rating = 4.6f,
      reviewCount = 215,
      imageRes = R.drawable.cat_beauty_3d_1791428196707,
      description = "Formulated with 10% Pure Niacinamide, Zinc PCA, and soothing Hyaluronic Acid. Reduces hyperpigmentation, tightens pores, and gives radiant glass skin. Dermatologically certified safe.",
      badge = "Organic",
      brand = "Lumina Skin BD"
    ),
    Product(
      id = "bm_prod_5",
      name = "Pure Sundarbans Khalisa Honey (500g)",
      category = StoreCategory.GROCERIES,
      priceBDT = 850,
      originalPriceBDT = 990,
      rating = 4.9f,
      reviewCount = 310,
      imageRes = R.drawable.prod_honey_3d_1791428267641,
      description = "100% natural, raw, unprocessed forest honey ethically harvested from Sundarbans mangrove Khalisa blossoms. Lab-tested purity, rich golden texture, no added sugar or syrup.",
      badge = "100% Pure",
      brand = "Shundorban Agro"
    ),
    Product(
      id = "bm_prod_6",
      name = "Titanium Smart Fitness Watch AMOLED",
      category = StoreCategory.ELECTRONICS,
      priceBDT = 4200,
      originalPriceBDT = 4950,
      rating = 4.8f,
      reviewCount = 142,
      imageRes = R.drawable.prod_smartwatch_3d_1791428249407,
      description = "1.43-inch vivid AMOLED Always-On display, aircraft-grade titanium frame, SpO2 & 24/7 heart rate monitor, Bluetooth calling, and 100+ fitness tracking modes. 10-day battery life.",
      badge = "New Arrival",
      brand = "Chronos Tech"
    ),
    Product(
      id = "bm_prod_7",
      name = "Handcrafted Cotton Embroidered Panjabi",
      category = StoreCategory.FASHION,
      priceBDT = 1850,
      originalPriceBDT = 2250,
      rating = 4.7f,
      reviewCount = 88,
      imageRes = R.drawable.prod_panjabi_3d_1791428280418,
      description = "Traditional 100% combed cotton handloom fabric with delicate collar and placket thread embroidery. Breathable, elegant tailored regular fit. Ideal for Eid, Jumuah, and festive celebrations.",
      badge = "Festive BD",
      brand = "Aarong Style Heritage"
    ),
    Product(
      id = "bm_prod_8",
      name = "Cold-Pressed Raw Mustard Oil (1L Bottle)",
      category = StoreCategory.GROCERIES,
      priceBDT = 420,
      originalPriceBDT = 480,
      rating = 4.9f,
      reviewCount = 420,
      imageRes = R.drawable.cat_groceries_3d_1791428215049,
      description = "Pure traditional ghani-broken mustard oil extracted at cold temperature. Authentic pungent aroma and rich flavor for Bangladeshi vorta, pickle making, and daily healthy cooking.",
      badge = "Traditional",
      brand = "Pabna Pure Food"
    )
  )
}
