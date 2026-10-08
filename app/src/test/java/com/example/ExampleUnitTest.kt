package com.example

import com.example.model.PaymentMethod
import com.example.model.SortOption
import com.example.model.StoreCategory
import com.example.util.WhatsAppHelper
import com.example.viewmodel.StoreViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun testStoreInitialProductsCount() {
    val viewModel = StoreViewModel()
    assertEquals(8, viewModel.allProducts.value.size)
  }

  @Test
  fun testWhatsAppBuyMessageFormat() {
    val message = WhatsAppHelper.createBuyMessage("Smart ANC Wireless Earbuds Pro")
    assertTrue(message.startsWith("I want to buy this product: Smart ANC Wireless Earbuds Pro"))
    assertEquals("01728412057", WhatsAppHelper.DEFAULT_WHATSAPP_NUMBER)
    assertEquals("https://www.facebook.com/share/1AQnG66E1a/", WhatsAppHelper.FACEBOOK_PAGE_URL)

    val viewModel = StoreViewModel()
    assertEquals("01728412057", viewModel.whatsappNumber.value)
  }

  @Test
  fun testDirectBuyInitiation() {
    val viewModel = StoreViewModel()
    val product = viewModel.allProducts.value.first()
    assertNull(viewModel.directCheckoutProduct.value)
    assertFalse(viewModel.isCheckoutOpen.value)

    viewModel.initiateBuyProduct(product)
    assertNotNull(viewModel.directCheckoutProduct.value)
    assertEquals(product.id, viewModel.directCheckoutProduct.value?.id)
    assertTrue(viewModel.isCheckoutOpen.value)

    viewModel.clearDirectCheckout()
    assertNull(viewModel.directCheckoutProduct.value)
  }

  @Test
  fun testCategoryAndFilterState() {
    val viewModel = StoreViewModel()
    viewModel.setCategoryFilter(StoreCategory.ELECTRONICS)
    assertEquals(StoreCategory.ELECTRONICS, viewModel.filterState.value.selectedCategory)

    viewModel.setPriceRange(500f..3000f)
    assertEquals(500f..3000f, viewModel.filterState.value.priceRange)

    viewModel.setSortOption(SortOption.PRICE_LOW_HIGH)
    assertEquals(SortOption.PRICE_LOW_HIGH, viewModel.filterState.value.sortBy)

    viewModel.resetFilters()
    assertEquals(StoreCategory.ALL, viewModel.filterState.value.selectedCategory)
    assertEquals(0f..5000f, viewModel.filterState.value.priceRange)
  }

  @Test
  fun testCartAddAndUpdate() {
    val viewModel = StoreViewModel()
    val product = viewModel.allProducts.value.first()
    viewModel.addToCart(product, 2)
    assertEquals(1, viewModel.cartItems.value.size)
    assertEquals(2, viewModel.cartItems.value.first().quantity)

    viewModel.updateCartQuantity(product.id, -1)
    assertEquals(1, viewModel.cartItems.value.first().quantity)

    viewModel.removeFromCart(product.id)
    assertTrue(viewModel.cartItems.value.isEmpty())
  }

  @Test
  fun testWishlistToggle() {
    val viewModel = StoreViewModel()
    val product = viewModel.allProducts.value.first()
    assertFalse(viewModel.isWishlisted(product.id))

    viewModel.toggleWishlist(product)
    assertTrue(viewModel.isWishlisted(product.id))

    viewModel.toggleWishlist(product)
    assertFalse(viewModel.isWishlisted(product.id))
  }

  @Test
  fun testPaymentAndOrderPlacement() {
    val viewModel = StoreViewModel()
    val product = viewModel.allProducts.value.first()
    viewModel.addToCart(product, 1)
    viewModel.setPaymentMethod(PaymentMethod.NAGAD)
    assertEquals(PaymentMethod.NAGAD, viewModel.selectedPayment.value)

    viewModel.completeOrder()
    assertTrue(viewModel.orderConfirmedId.value?.startsWith("BM-BD-") == true)
    assertTrue(viewModel.cartItems.value.isEmpty())
  }
}
