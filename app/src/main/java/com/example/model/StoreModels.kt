package com.example.model

import androidx.annotation.DrawableRes

enum class StoreCategory(
  val id: String,
  val displayName: String,
  val subtitle: String
) {
  ALL("all", "All Items", "100+ Products"),
  ELECTRONICS("electronics", "Electronics", "Gadgets & Audio"),
  FASHION("fashion", "Fashion", "Clothing & Shoes"),
  HOME_LIVING("home_living", "Home & Living", "Decor & Lighting"),
  BEAUTY("beauty", "Beauty", "Skincare & Care"),
  GROCERIES("groceries", "Groceries", "Fresh & Organic")
}

data class CategoryItem(
  val category: StoreCategory,
  @DrawableRes val imageRes: Int,
  val tagText: String
)

data class Product(
  val id: String,
  val name: String,
  val category: StoreCategory,
  val priceBDT: Int,
  val originalPriceBDT: Int? = null,
  val rating: Float,
  val reviewCount: Int,
  @DrawableRes val imageRes: Int,
  val description: String,
  val badge: String? = null,
  val inStock: Boolean = true,
  val brand: String = "BestMart Select"
) {
  val discountPercent: Int
    get() = if (originalPriceBDT != null && originalPriceBDT > priceBDT) {
      (((originalPriceBDT - priceBDT).toFloat() / originalPriceBDT) * 100).toInt()
    } else 0
}

data class CartItem(
  val product: Product,
  val quantity: Int = 1
)

enum class SortOption(val title: String) {
  POPULAR("Popularity"),
  RATING("Top Rated"),
  PRICE_LOW_HIGH("Price: Low to High"),
  PRICE_HIGH_LOW("Price: High to Low")
}

data class FilterState(
  val selectedCategory: StoreCategory = StoreCategory.ALL,
  val priceRange: ClosedFloatingPointRange<Float> = 0f..5000f,
  val minRating: Float = 0f,
  val sortBy: SortOption = SortOption.POPULAR
)

enum class PaymentMethod(
  val title: String,
  val description: String,
  val badgeText: String
) {
  BKASH(
    title = "bKash Instant Pay",
    description = "Pay instantly with bKash app / USSD *247# with 1.5% cashback",
    badgeText = "Most Popular"
  ),
  NAGAD(
    title = "Nagad Smart Pay",
    description = "Zero cash-out fee payment via Nagad mobile wallet",
    badgeText = "Fast & Free"
  ),
  CASH_ON_DELIVERY(
    title = "Cash on Delivery (COD)",
    description = "Pay in cash (৳) when your parcel reaches your doorstep",
    badgeText = "Doorstep Check"
  ),
  WHATSAPP(
    title = "WhatsApp Direct Order",
    description = "Confirm order details and payment via WhatsApp (01728412057)",
    badgeText = "Direct Chat"
  )
}
