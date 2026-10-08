package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Product
import com.example.model.StoreCategory
import com.example.ui.components.CartSheet
import com.example.ui.components.CategoriesSection
import com.example.ui.components.CheckoutDialog
import com.example.ui.components.FilterSidebarBottomSheet
import com.example.ui.components.FooterSection
import com.example.ui.components.HeroSection
import com.example.ui.components.MessengerChatButton
import com.example.ui.components.OrderSuccessDialog
import com.example.ui.components.ProductCard
import com.example.ui.components.ProductDetailDialog
import com.example.ui.components.TopHeader
import com.example.ui.components.UserProfileSheet
import com.example.ui.components.WishlistSheet
import com.example.ui.theme.BestMartGreen
import com.example.ui.theme.BestMartGreenDark
import com.example.ui.theme.BestMartOrange
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted
import com.example.util.WhatsAppHelper
import com.example.viewmodel.StoreViewModel
import kotlinx.coroutines.launch

@Composable
fun BestMartHomeScreen(
  viewModel: StoreViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val whatsappNumber by viewModel.whatsappNumber.collectAsState()
  val allProducts by viewModel.allProducts.collectAsState()
  val filteredProducts by viewModel.filteredProducts.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()
  val filterState by viewModel.filterState.collectAsState()
  val cartItems by viewModel.cartItems.collectAsState()
  val wishlistIds by viewModel.wishlistIds.collectAsState()
  val cartSummary by viewModel.cartSummary.collectAsState()
  val selectedProduct by viewModel.selectedProduct.collectAsState()

  val isFilterOpen by viewModel.isFilterOpen.collectAsState()
  val isCartOpen by viewModel.isCartOpen.collectAsState()
  val isWishlistOpen by viewModel.isWishlistOpen.collectAsState()
  val isProfileOpen by viewModel.isProfileOpen.collectAsState()
  val isCheckoutOpen by viewModel.isCheckoutOpen.collectAsState()
  val directCheckoutProduct by viewModel.directCheckoutProduct.collectAsState()
  val selectedPayment by viewModel.selectedPayment.collectAsState()
  val isDhaka by viewModel.deliveryZoneDhaka.collectAsState()
  val orderConfirmedId by viewModel.orderConfirmedId.collectAsState()
  val snackbarMessage by viewModel.snackbarMessage.collectAsState()

  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()
  val gridState = rememberLazyGridState()

  LaunchedEffect(snackbarMessage) {
    snackbarMessage?.let {
      snackbarHostState.showSnackbar(it)
      viewModel.clearSnackbar()
    }
  }

  val wishlistedProducts = remember(wishlistIds, allProducts) {
    allProducts.filter { wishlistIds.contains(it.id) }
  }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .statusBarsPadding()
      .navigationBarsPadding(),
    containerColor = Color(0xFFF8FAFC),
    contentWindowInsets = WindowInsets(0, 0, 0, 0),
    topBar = {
      TopHeader(
        searchQuery = searchQuery,
        onSearchQueryChanged = { viewModel.onSearchQueryChanged(it) },
        cartCount = cartSummary.itemCount,
        wishlistCount = wishlistIds.size,
        onCartClick = { viewModel.setCartOpen(true) },
        onWishlistClick = { viewModel.setWishlistOpen(true) },
        onProfileClick = { viewModel.setProfileOpen(true) },
        onFilterClick = { viewModel.setFilterOpen(true) },
        onFacebookClick = { WhatsAppHelper.openFacebookPage(context) }
      )
    },
    floatingActionButton = {
      MessengerChatButton(
        onClick = { WhatsAppHelper.openMessengerChat(context) }
      )
    },
    snackbarHost = {
      SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.padding(16.dp)
      )
    }
  ) { innerPadding ->
    LazyVerticalGrid(
      state = gridState,
      columns = GridCells.Adaptive(minSize = 160.dp),
      contentPadding = PaddingValues(bottom = 24.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(horizontal = 14.dp)
        .testTag("home_product_grid")
    ) {
      // 1. Hero Section
      item(span = { GridItemSpan(maxLineSpan) }) {
        HeroSection(
          onShopNowClick = {
            viewModel.setCategoryFilter(StoreCategory.ALL)
            viewModel.onSearchQueryChanged("")
            scope.launch {
              gridState.animateScrollToItem(index = 2)
            }
            viewModel.showSnackbar("Browsing BestMart BD Catalog • All 8 Products")
          }
        )
      }

      // 2. Categories Section (with 3D icons: Electronics, Fashion, Home & Living, Beauty, Groceries)
      item(span = { GridItemSpan(maxLineSpan) }) {
        CategoriesSection(
          categories = viewModel.categories,
          selectedCategory = filterState.selectedCategory,
          onCategorySelected = { category ->
            viewModel.setCategoryFilter(category)
          }
        )
      }

      // 3. Active Filters / Search Indicator & Grid Header
      item(span = { GridItemSpan(maxLineSpan) }) {
        Column(modifier = Modifier.fillMaxWidth()) {
          // If search or filter is applied
          val hasActiveFilters = filterState.selectedCategory != StoreCategory.ALL ||
              searchQuery.isNotBlank() ||
              filterState.minRating > 0f ||
              filterState.priceRange.start > 0f ||
              filterState.priceRange.endInclusive < 5000f

          if (hasActiveFilters) {
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = Color.White,
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = null,
                    tint = BestMartGreen,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "Filtering: ${if (filterState.selectedCategory != StoreCategory.ALL) filterState.selectedCategory.displayName else ""} ${if (searchQuery.isNotBlank()) "\"$searchQuery\"" else ""}".trim(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimaryDark
                  )
                }

                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier
                    .clickable { viewModel.resetFilters() }
                    .padding(4.dp)
                ) {
                  Text(
                    text = "Reset",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = BestMartOrange
                  )
                  Spacer(modifier = Modifier.width(2.dp))
                  Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = BestMartOrange,
                    modifier = Modifier.size(14.dp)
                  )
                }
              }
            }
          }

          // Section Title Row
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(4.dp, 18.dp)
                  .background(BestMartGreen, RoundedCornerShape(2.dp))
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Featured Products",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimaryDark
              )
              Spacer(modifier = Modifier.width(6.dp))
              Surface(
                color = BestMartGreen.copy(alpha = 0.12f),
                shape = RoundedCornerShape(6.dp)
              ) {
                Text(
                  text = "${filteredProducts.size} items",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = BestMartGreenDark,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }

            // Quick Sort indicator
            Text(
              text = filterState.sortBy.title,
              fontSize = 12.sp,
              color = TextSecondaryMuted,
              fontWeight = FontWeight.Medium,
              modifier = Modifier
                .clickable { viewModel.setFilterOpen(true) }
                .padding(4.dp)
            )
          }
        }
      }

      // Empty State if no product matched
      if (filteredProducts.isEmpty()) {
        item(span = { GridItemSpan(maxLineSpan) }) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(
              imageVector = Icons.Filled.SearchOff,
              contentDescription = null,
              tint = TextSecondaryMuted,
              modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "No products found",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimaryDark
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Try adjusting your search or filters",
              fontSize = 12.5.sp,
              color = TextSecondaryMuted
            )
            Spacer(modifier = Modifier.height(14.dp))
            Button(
              onClick = { viewModel.resetFilters() },
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = BestMartGreen)
            ) {
              Text("Reset All Filters", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      } else {
        // 4. Products Grid with 8 products
        items(filteredProducts, key = { it.id }) { product ->
          ProductCard(
            product = product,
            isWishlisted = wishlistIds.contains(product.id),
            onProductClick = { viewModel.selectProduct(product) },
            onAddToCart = { viewModel.addToCart(product) },
            onBuyClick = {
              // 1. Send product details to WhatsApp number 01728412057 using wa.me link
              WhatsAppHelper.openWhatsApp(
                context = context,
                productName = product.name,
                priceBDT = product.priceBDT,
                whatsappNumber = whatsappNumber
              )
              // 2. Open checkout page with Name, Phone, Address, and payment options bKash, Nagad, Cash on Delivery
              viewModel.initiateBuyProduct(product)
            },
            onToggleWishlist = { viewModel.toggleWishlist(product) }
          )
        }
      }

      // 5. Bangladeshi Footer Section (Payment methods bKash, Nagad, COD, etc.)
      item(span = { GridItemSpan(maxLineSpan) }) {
        Spacer(modifier = Modifier.height(16.dp))
        FooterSection(
          onFacebookClick = { WhatsAppHelper.openFacebookPage(context) }
        )
      }
    }
  }

  // Modals & Bottom Sheets

  // 1. Filter Sidebar Bottom Sheet
  if (isFilterOpen) {
    FilterSidebarBottomSheet(
      filterState = filterState,
      onDismiss = { viewModel.setFilterOpen(false) },
      onApplyFilters = { newFilters ->
        viewModel.setCategoryFilter(newFilters.selectedCategory)
        viewModel.setPriceRange(newFilters.priceRange)
        viewModel.setMinRating(newFilters.minRating)
        viewModel.setSortOption(newFilters.sortBy)
      },
      onResetFilters = {
        viewModel.resetFilters()
      }
    )
  }

  // 2. Shopping Cart Sheet
  if (isCartOpen) {
    CartSheet(
      cartItems = cartItems,
      cartSummary = cartSummary,
      isDhaka = isDhaka,
      onDismiss = { viewModel.setCartOpen(false) },
      onUpdateQuantity = { id, delta -> viewModel.updateCartQuantity(id, delta) },
      onRemoveItem = { id -> viewModel.removeFromCart(id) },
      onDeliveryZoneChanged = { isDhk -> viewModel.setDeliveryZoneDhaka(isDhk) },
      onProceedToCheckout = {
        viewModel.setCartOpen(false)
        viewModel.setCheckoutOpen(true)
      },
      onOrderViaWhatsApp = {
        val cartMessage = buildString {
          append("I want to buy these products from BestMart BD:\n")
          cartItems.forEach { item ->
            append("• ${item.product.name} x${item.quantity} (৳${item.product.priceBDT * item.quantity})\n")
          }
          append("Total: ৳${cartSummary.grandTotal}")
        }
        WhatsAppHelper.openWhatsAppWithMessage(
          context = context,
          message = cartMessage,
          whatsappNumber = whatsappNumber
        )
      }
    )
  }

  // 3. Checkout Modal (Name, Phone, Address, and payment options bKash, Nagad, COD)
  if (isCheckoutOpen) {
    CheckoutDialog(
      directProduct = directCheckoutProduct,
      cartSummary = cartSummary,
      selectedPayment = selectedPayment,
      isDhaka = isDhaka,
      onPaymentSelected = { viewModel.setPaymentMethod(it) },
      onDismiss = {
        viewModel.setCheckoutOpen(false)
        viewModel.clearDirectCheckout()
      },
      onConfirmOrder = {
        viewModel.completeOrder()
      },
      onSendToWhatsApp = { name, phone, address ->
        val itemDetails = if (directCheckoutProduct != null) {
          "Product: ${directCheckoutProduct!!.name} (Price: ৳${directCheckoutProduct!!.priceBDT})"
        } else {
          "Cart Items: ${cartSummary.itemCount} items (Grand Total: ৳${cartSummary.grandTotal})"
        }
        val orderMsg = "I want to buy this product:\n$itemDetails\nCustomer Name: $name\nMobile Number: $phone\nDelivery Address: $address\nPayment Method: ${selectedPayment.title}"
        WhatsAppHelper.openWhatsAppWithMessage(
          context = context,
          message = orderMsg,
          whatsappNumber = whatsappNumber
        )
      }
    )
  }

  // 4. Product Detail Modal
  selectedProduct?.let { prod ->
    ProductDetailDialog(
      product = prod,
      isWishlisted = wishlistIds.contains(prod.id),
      onDismiss = { viewModel.selectProduct(null) },
      onAddToCart = { qty -> viewModel.addToCart(prod, qty) },
      onBuyNow = { qty ->
        // 1. Send product details to WhatsApp number 01728412057 using wa.me link
        WhatsAppHelper.openWhatsApp(
          context = context,
          productName = prod.name,
          priceBDT = prod.priceBDT * qty,
          whatsappNumber = whatsappNumber
        )
        // 2. Open checkout page
        viewModel.initiateBuyProduct(prod)
      },
      onToggleWishlist = { viewModel.toggleWishlist(prod) }
    )
  }

  // 5. Wishlist Sheet
  if (isWishlistOpen) {
    WishlistSheet(
      wishlistedProducts = wishlistedProducts,
      onDismiss = { viewModel.setWishlistOpen(false) },
      onAddToCart = { prod -> viewModel.addToCart(prod) },
      onRemoveFromWishlist = { prod -> viewModel.toggleWishlist(prod) }
    )
  }

  // 6. User Profile Sheet
  if (isProfileOpen) {
    UserProfileSheet(
      onDismiss = { viewModel.setProfileOpen(false) }
    )
  }

  // 7. Order Placed Success Dialog
  orderConfirmedId?.let { id ->
    OrderSuccessDialog(
      orderId = id,
      paymentMethod = selectedPayment,
      isDhaka = isDhaka,
      onDismiss = { viewModel.dismissOrderConfirmation() }
    )
  }
}
