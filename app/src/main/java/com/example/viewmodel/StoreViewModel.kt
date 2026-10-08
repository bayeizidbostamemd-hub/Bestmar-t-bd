package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.SampleData
import com.example.model.CartItem
import com.example.model.FilterState
import com.example.model.PaymentMethod
import com.example.model.Product
import com.example.model.SortOption
import com.example.model.StoreCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class CartSummary(
  val subtotal: Int,
  val deliveryFee: Int,
  val discount: Int,
  val grandTotal: Int,
  val itemCount: Int
)

class StoreViewModel : ViewModel() {

  private val _products = MutableStateFlow(SampleData.products)
  val allProducts: StateFlow<List<Product>> = _products.asStateFlow()

  val categories = SampleData.categories

  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  private val _filterState = MutableStateFlow(FilterState())
  val filterState: StateFlow<FilterState> = _filterState.asStateFlow()

  private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
  val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

  private val _wishlistIds = MutableStateFlow<Set<String>>(emptySet())
  val wishlistIds: StateFlow<Set<String>> = _wishlistIds.asStateFlow()

  private val _selectedProduct = MutableStateFlow<Product?>(null)
  val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

  private val _isFilterOpen = MutableStateFlow(false)
  val isFilterOpen: StateFlow<Boolean> = _isFilterOpen.asStateFlow()

  private val _isCartOpen = MutableStateFlow(false)
  val isCartOpen: StateFlow<Boolean> = _isCartOpen.asStateFlow()

  private val _isWishlistOpen = MutableStateFlow(false)
  val isWishlistOpen: StateFlow<Boolean> = _isWishlistOpen.asStateFlow()

  private val _isProfileOpen = MutableStateFlow(false)
  val isProfileOpen: StateFlow<Boolean> = _isProfileOpen.asStateFlow()

  private val _isCheckoutOpen = MutableStateFlow(false)
  val isCheckoutOpen: StateFlow<Boolean> = _isCheckoutOpen.asStateFlow()

  private val _selectedPayment = MutableStateFlow(PaymentMethod.BKASH)
  val selectedPayment: StateFlow<PaymentMethod> = _selectedPayment.asStateFlow()

  private val _directCheckoutProduct = MutableStateFlow<Product?>(null)
  val directCheckoutProduct: StateFlow<Product?> = _directCheckoutProduct.asStateFlow()

  private val _whatsappNumber = MutableStateFlow("01728412057")
  val whatsappNumber: StateFlow<String> = _whatsappNumber.asStateFlow()

  private val _deliveryZoneDhaka = MutableStateFlow(true)
  val deliveryZoneDhaka: StateFlow<Boolean> = _deliveryZoneDhaka.asStateFlow()

  private val _snackbarMessage = MutableStateFlow<String?>(null)
  val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

  private val _orderConfirmedId = MutableStateFlow<String?>(null)
  val orderConfirmedId: StateFlow<String?> = _orderConfirmedId.asStateFlow()

  // Dynamic filtered products
  val filteredProducts: StateFlow<List<Product>> = combine(
    _products,
    _searchQuery,
    _filterState
  ) { prods, query, filter ->
    prods.filter { product ->
      val matchesCategory = filter.selectedCategory == StoreCategory.ALL ||
          product.category == filter.selectedCategory

      val matchesQuery = query.isBlank() ||
          product.name.contains(query, ignoreCase = true) ||
          product.brand.contains(query, ignoreCase = true) ||
          product.category.displayName.contains(query, ignoreCase = true) ||
          product.description.contains(query, ignoreCase = true)

      val matchesPrice = product.priceBDT >= filter.priceRange.start &&
          product.priceBDT <= filter.priceRange.endInclusive

      val matchesRating = product.rating >= filter.minRating

      matchesCategory && matchesQuery && matchesPrice && matchesRating
    }.let { list ->
      when (filter.sortBy) {
        SortOption.POPULAR -> list.sortedByDescending { it.reviewCount }
        SortOption.RATING -> list.sortedByDescending { it.rating }
        SortOption.PRICE_LOW_HIGH -> list.sortedBy { it.priceBDT }
        SortOption.PRICE_HIGH_LOW -> list.sortedByDescending { it.priceBDT }
      }
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SampleData.products)

  // Dynamic cart calculations
  val cartSummary: StateFlow<CartSummary> = combine(
    _cartItems,
    _deliveryZoneDhaka
  ) { items, isDhaka ->
    val subtotal = items.sumOf { it.product.priceBDT * it.quantity }
    val count = items.sumOf { it.quantity }
    // Free delivery if subtotal > 2500 BDT
    val deliveryFee = when {
      items.isEmpty() -> 0
      subtotal >= 2500 -> 0
      isDhaka -> 60
      else -> 120
    }
    // 5% digital promotion discount for bKash/Nagad
    val discount = if (subtotal > 2000) (subtotal * 0.05).toInt() else 0
    val grandTotal = if (items.isEmpty()) 0 else (subtotal + deliveryFee - discount).coerceAtLeast(0)
    CartSummary(
      subtotal = subtotal,
      deliveryFee = deliveryFee,
      discount = discount,
      grandTotal = grandTotal,
      itemCount = count
    )
  }.stateIn(
    viewModelScope,
    SharingStarted.WhileSubscribed(5000),
    CartSummary(0, 0, 0, 0, 0)
  )

  fun onSearchQueryChanged(query: String) {
    _searchQuery.value = query
  }

  fun setCategoryFilter(category: StoreCategory) {
    _filterState.value = _filterState.value.copy(selectedCategory = category)
  }

  fun setPriceRange(range: ClosedFloatingPointRange<Float>) {
    _filterState.value = _filterState.value.copy(priceRange = range)
  }

  fun setMinRating(rating: Float) {
    _filterState.value = _filterState.value.copy(minRating = rating)
  }

  fun setSortOption(sort: SortOption) {
    _filterState.value = _filterState.value.copy(sortBy = sort)
  }

  fun resetFilters() {
    _filterState.value = FilterState()
    _searchQuery.value = ""
    showSnackbar("Filters have been reset")
  }

  fun addToCart(product: Product, quantity: Int = 1) {
    val current = _cartItems.value.toMutableList()
    val existingIndex = current.indexOfFirst { it.product.id == product.id }
    if (existingIndex >= 0) {
      val existing = current[existingIndex]
      current[existingIndex] = existing.copy(quantity = existing.quantity + quantity)
    } else {
      current.add(CartItem(product = product, quantity = quantity))
    }
    _cartItems.value = current
    showSnackbar("Added ${product.name} to Cart")
  }

  fun updateCartQuantity(productId: String, delta: Int) {
    val current = _cartItems.value.toMutableList()
    val index = current.indexOfFirst { it.product.id == productId }
    if (index >= 0) {
      val item = current[index]
      val newQty = item.quantity + delta
      if (newQty <= 0) {
        current.removeAt(index)
        showSnackbar("Removed from cart")
      } else {
        current[index] = item.copy(quantity = newQty)
      }
      _cartItems.value = current
    }
  }

  fun removeFromCart(productId: String) {
    _cartItems.value = _cartItems.value.filterNot { it.product.id == productId }
    showSnackbar("Item removed from cart")
  }

  fun toggleWishlist(product: Product) {
    val current = _wishlistIds.value.toMutableSet()
    if (current.contains(product.id)) {
      current.remove(product.id)
      showSnackbar("Removed from wishlist")
    } else {
      current.add(product.id)
      showSnackbar("Saved to wishlist ♥")
    }
    _wishlistIds.value = current
  }

  fun isWishlisted(productId: String): Boolean = _wishlistIds.value.contains(productId)

  fun selectProduct(product: Product?) {
    _selectedProduct.value = product
  }

  fun setFilterOpen(open: Boolean) {
    _isFilterOpen.value = open
  }

  fun setCartOpen(open: Boolean) {
    _isCartOpen.value = open
  }

  fun setWishlistOpen(open: Boolean) {
    _isWishlistOpen.value = open
  }

  fun setProfileOpen(open: Boolean) {
    _isProfileOpen.value = open
  }

  fun setCheckoutOpen(open: Boolean) {
    _isCheckoutOpen.value = open
  }

  fun setPaymentMethod(method: PaymentMethod) {
    _selectedPayment.value = method
  }

  fun setDeliveryZoneDhaka(isDhaka: Boolean) {
    _deliveryZoneDhaka.value = isDhaka
  }

  fun initiateBuyProduct(product: Product) {
    _directCheckoutProduct.value = product
    _isCheckoutOpen.value = true
    showSnackbar("Opening checkout for ${product.name}")
  }

  fun clearDirectCheckout() {
    _directCheckoutProduct.value = null
  }

  fun completeOrder() {
    val orderId = "BM-BD-" + (100000..999999).random()
    _orderConfirmedId.value = orderId
    if (_directCheckoutProduct.value != null) {
      _directCheckoutProduct.value = null
    } else {
      _cartItems.value = emptyList()
    }
    _isCheckoutOpen.value = false
    showSnackbar("Order #$orderId placed successfully!")
  }

  fun dismissOrderConfirmation() {
    _orderConfirmedId.value = null
  }

  fun showSnackbar(message: String) {
    _snackbarMessage.value = message
  }

  fun clearSnackbar() {
    _snackbarMessage.value = null
  }
}
