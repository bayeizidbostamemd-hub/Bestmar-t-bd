package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CartItem
import com.example.ui.theme.BestMartGreen
import com.example.ui.theme.BestMartGreenContainer
import com.example.ui.theme.BestMartGreenDark
import com.example.ui.theme.BestMartOrange
import com.example.ui.theme.BestMartOrangeDark
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted
import com.example.viewmodel.CartSummary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartSheet(
  cartItems: List<CartItem>,
  cartSummary: CartSummary,
  isDhaka: Boolean,
  onDismiss: () -> Unit,
  onUpdateQuantity: (productId: String, delta: Int) -> Unit,
  onRemoveItem: (productId: String) -> Unit,
  onDeliveryZoneChanged: (Boolean) -> Unit,
  onProceedToCheckout: () -> Unit,
  onOrderViaWhatsApp: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = Color.White,
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    modifier = modifier.testTag("cart_sheet")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
        .padding(bottom = 24.dp)
    ) {
      // Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Filled.ShoppingBag,
            contentDescription = null,
            tint = BestMartGreen,
            modifier = Modifier.size(24.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Shopping Cart (${cartSummary.itemCount})",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimaryDark
          )
        }

        IconButton(
          onClick = onDismiss,
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(Color(0xFFF1F5F9))
        ) {
          Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close",
            tint = TextPrimaryDark,
            modifier = Modifier.size(18.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      if (cartItems.isEmpty()) {
        // Empty State
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Box(
            modifier = Modifier
              .size(80.dp)
              .clip(CircleShape)
              .background(Color(0xFFF8FAFC)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Filled.ShoppingBag,
              contentDescription = null,
              tint = TextSecondaryMuted,
              modifier = Modifier.size(40.dp)
            )
          }
          Spacer(modifier = Modifier.height(16.dp))
          Text(
            text = "Your Cart is Empty",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimaryDark
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "Discover top deals and add items to your cart",
            fontSize = 13.sp,
            color = TextSecondaryMuted
          )
          Spacer(modifier = Modifier.height(20.dp))
          Button(
            onClick = onDismiss,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BestMartGreen)
          ) {
            Text(text = "Start Shopping", fontWeight = FontWeight.Bold)
          }
        }
      } else {
        // Cart items list
        LazyColumn(
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f, fill = false),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          items(cartItems, key = { it.product.id }) { item ->
            CartItemRow(
              item = item,
              onIncrease = { onUpdateQuantity(item.product.id, 1) },
              onDecrease = { onUpdateQuantity(item.product.id, -1) },
              onRemove = { onRemoveItem(item.product.id) }
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Delivery Location Switcher (Dhaka vs Outside Dhaka)
        Surface(
          shape = RoundedCornerShape(14.dp),
          color = Color(0xFFF8FAFC),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SurfaceBorder, RoundedCornerShape(14.dp))
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Filled.LocalShipping,
                contentDescription = null,
                tint = BestMartGreen,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = "Delivery Location",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = TextPrimaryDark
                )
                Text(
                  text = if (isDhaka) "Inside Dhaka (৳60 / Free over ৳2,500)" else "Outside Dhaka (৳120 nationwide)",
                  fontSize = 10.5.sp,
                  color = TextSecondaryMuted
                )
              }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              LocationPill(
                label = "Dhaka",
                isSelected = isDhaka,
                onClick = { onDeliveryZoneChanged(true) }
              )
              LocationPill(
                label = "Outside",
                isSelected = !isDhaka,
                onClick = { onDeliveryZoneChanged(false) }
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Bill Summary Breakdown
        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          BillRow(label = "Subtotal", value = "৳ ${String.format("%,d", cartSummary.subtotal)}")
          BillRow(
            label = "Delivery Fee",
            value = if (cartSummary.deliveryFee == 0) "FREE" else "৳ ${cartSummary.deliveryFee}"
          )
          if (cartSummary.discount > 0) {
            BillRow(
              label = "Special Online Discount (5%)",
              value = "- ৳ ${cartSummary.discount}",
              valueColor = BestMartOrange
            )
          }

          HorizontalDivider(
            color = SurfaceBorder,
            modifier = Modifier.padding(vertical = 4.dp)
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Total Payable",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimaryDark
            )
            Text(
              text = "৳ ${String.format("%,d", cartSummary.grandTotal)}",
              fontSize = 18.sp,
              fontWeight = FontWeight.Black,
              color = BestMartGreenDark
            )
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Proceed to Checkout Button
        Button(
          onClick = onProceedToCheckout,
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = BestMartOrange,
            contentColor = Color.White
          ),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("proceed_to_checkout_button")
        ) {
          Text(
            text = "Proceed to Checkout • ৳ ${String.format("%,d", cartSummary.grandTotal)}",
            fontSize = 14.5.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
          onClick = onOrderViaWhatsApp,
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF25D366),
            contentColor = Color.White
          ),
          modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .testTag("whatsapp_cart_order_button")
        ) {
          Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Buy on WhatsApp",
            fontSize = 13.5.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}

@Composable
private fun CartItemRow(
  item: CartItem,
  onIncrease: () -> Unit,
  onDecrease: () -> Unit,
  onRemove: () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(14.dp),
    color = Color.White,
    modifier = Modifier
      .fillMaxWidth()
      .border(1.dp, SurfaceBorder, RoundedCornerShape(14.dp))
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(60.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(Color(0xFFF8FAFC))
      ) {
        Image(
          painter = painterResource(id = item.product.imageRes),
          contentDescription = item.product.name,
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = item.product.name,
          fontSize = 13.sp,
          fontWeight = FontWeight.SemiBold,
          color = TextPrimaryDark,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = "৳ ${String.format("%,d", item.product.priceBDT)}",
          fontSize = 13.5.sp,
          fontWeight = FontWeight.Bold,
          color = BestMartOrangeDark
        )
      }

      // Quantity Controller
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .clip(RoundedCornerShape(8.dp))
          .background(Color(0xFFF1F5F9))
      ) {
        IconButton(
          onClick = onDecrease,
          modifier = Modifier.size(30.dp)
        ) {
          Icon(
            imageVector = if (item.quantity == 1) Icons.Filled.Delete else Icons.Filled.Remove,
            contentDescription = "Decrease",
            modifier = Modifier.size(14.dp),
            tint = if (item.quantity == 1) Color.Red else TextPrimaryDark
          )
        }

        Text(
          text = "${item.quantity}",
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimaryDark,
          modifier = Modifier.padding(horizontal = 6.dp)
        )

        IconButton(
          onClick = onIncrease,
          modifier = Modifier.size(30.dp)
        ) {
          Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Increase",
            modifier = Modifier.size(14.dp),
            tint = TextPrimaryDark
          )
        }
      }
    }
  }
}

@Composable
private fun LocationPill(
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(8.dp),
    color = if (isSelected) BestMartGreen else Color.White,
    modifier = Modifier
      .border(
        1.dp,
        if (isSelected) BestMartGreen else SurfaceBorder,
        RoundedCornerShape(8.dp)
      )
      .clickable { onClick() }
  ) {
    Text(
      text = label,
      fontSize = 11.sp,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
      color = if (isSelected) Color.White else TextPrimaryDark,
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
    )
  }
}

@Composable
private fun BillRow(
  label: String,
  value: String,
  valueColor: Color = TextPrimaryDark
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(text = label, fontSize = 12.5.sp, color = TextSecondaryMuted)
    Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = valueColor)
  }
}
