package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Product
import com.example.ui.theme.BestMartGreen
import com.example.ui.theme.BestMartGreenDark
import com.example.ui.theme.BestMartOrange
import com.example.ui.theme.BestMartOrangeDark
import com.example.ui.theme.StarGold
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailDialog(
  product: Product,
  isWishlisted: Boolean,
  onDismiss: () -> Unit,
  onAddToCart: (quantity: Int) -> Unit,
  onBuyNow: (quantity: Int) -> Unit,
  onToggleWishlist: () -> Unit,
  modifier: Modifier = Modifier
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  var quantity by remember { mutableIntStateOf(1) }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = Color.White,
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    modifier = modifier.testTag("product_detail_sheet")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
        .padding(bottom = 24.dp)
        .verticalScroll(rememberScrollState())
    ) {
      // Header Actions
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          color = BestMartGreen.copy(alpha = 0.12f),
          shape = RoundedCornerShape(8.dp)
        ) {
          Text(
            text = product.category.displayName,
            color = BestMartGreenDark,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
          )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          IconButton(
            onClick = onToggleWishlist,
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(Color(0xFFF1F5F9))
          ) {
            Icon(
              imageVector = if (isWishlisted) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
              contentDescription = "Wishlist",
              tint = if (isWishlisted) BestMartOrange else TextPrimaryDark,
              modifier = Modifier.size(18.dp)
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
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 3D Product Image
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .aspectRatio(1.2f)
          .clip(RoundedCornerShape(20.dp))
          .background(Color(0xFFF8FAFC))
          .border(1.dp, SurfaceBorder, RoundedCornerShape(20.dp))
      ) {
        Image(
          painter = painterResource(id = product.imageRes),
          contentDescription = product.name,
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )

        if (product.badge != null) {
          Surface(
            color = BestMartOrange,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
              .align(Alignment.TopStart)
              .padding(12.dp)
          ) {
            Text(
              text = product.badge,
              color = Color.White,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Title & Brand
      Text(
        text = product.name,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimaryDark,
        lineHeight = 26.sp
      )

      Spacer(modifier = Modifier.height(6.dp))

      // Rating & Reviews Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = StarGold,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "${product.rating} / 5.0",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimaryDark
          )
          Text(
            text = " (${product.reviewCount} customer reviews)",
            fontSize = 12.sp,
            color = TextSecondaryMuted
          )
        }

        Spacer(modifier = Modifier.weight(1f))

        Surface(
          color = Color(0xFFE8F5E9),
          shape = RoundedCornerShape(6.dp)
        ) {
          Text(
            text = "In Stock (Dhaka Hub)",
            color = BestMartGreenDark,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Price Row
      Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Text(
          text = "৳ ${String.format("%,d", product.priceBDT)}",
          fontSize = 24.sp,
          fontWeight = FontWeight.Black,
          color = BestMartOrangeDark
        )

        if (product.originalPriceBDT != null) {
          Text(
            text = "৳ ${String.format("%,d", product.originalPriceBDT)}",
            fontSize = 15.sp,
            color = TextSecondaryMuted,
            textDecoration = TextDecoration.LineThrough,
            modifier = Modifier.padding(bottom = 2.dp)
          )
          Surface(
            color = Color(0xFFEF4444),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.padding(bottom = 3.dp)
          ) {
            Text(
              text = "SAVE ${product.discountPercent}%",
              color = Color.White,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))
      HorizontalDivider(color = SurfaceBorder)
      Spacer(modifier = Modifier.height(14.dp))

      // Description
      Text(
        text = "Product Details",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimaryDark
      )
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = product.description,
        fontSize = 13.sp,
        color = Color(0xFF475569),
        lineHeight = 19.sp
      )

      Spacer(modifier = Modifier.height(14.dp))

      // Delivery & Guarantee Card
      Surface(
        color = Color(0xFFF8FAFC),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, SurfaceBorder, RoundedCornerShape(14.dp))
      ) {
        Column(
          modifier = Modifier.padding(12.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.LocalShipping, contentDescription = null, tint = BestMartGreen, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Dhaka City Express: Delivered within 24 Hours", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextPrimaryDark)
          }
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Security, contentDescription = null, tint = BestMartOrange, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "bKash / Nagad Instant Cashback & Cash on Delivery Accepted", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextPrimaryDark)
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Quantity selector & Actions
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(text = "Quantity", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextPrimaryDark)

        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF1F5F9))
        ) {
          IconButton(
            onClick = { if (quantity > 1) quantity-- },
            modifier = Modifier.size(36.dp)
          ) {
            Icon(Icons.Filled.Remove, contentDescription = "Decrease", modifier = Modifier.size(16.dp))
          }
          Text(
            text = "$quantity",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimaryDark,
            modifier = Modifier.padding(horizontal = 12.dp)
          )
          IconButton(
            onClick = { quantity++ },
            modifier = Modifier.size(36.dp)
          ) {
            Icon(Icons.Filled.Add, contentDescription = "Increase", modifier = Modifier.size(16.dp))
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Buttons: Add to Cart and Buy Now
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        OutlinedButton(
          onClick = {
            onAddToCart(quantity)
            onDismiss()
          },
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .testTag("detail_add_to_cart_button")
        ) {
          Icon(Icons.Filled.AddShoppingCart, contentDescription = null, tint = BestMartGreenDark, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Add to Cart", color = BestMartGreenDark, fontWeight = FontWeight.Bold)
        }

        Button(
          onClick = {
            onBuyNow(quantity)
            onDismiss()
          },
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .testTag("detail_buy_now_button")
        ) {
          Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Buy on WhatsApp", color = Color.White, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
