package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material3.OutlinedButton

@Composable
fun ProductCard(
  product: Product,
  isWishlisted: Boolean,
  onProductClick: () -> Unit,
  onAddToCart: () -> Unit,
  onBuyClick: () -> Unit,
  onToggleWishlist: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .border(1.dp, SurfaceBorder, RoundedCornerShape(20.dp))
      .clickable { onProductClick() }
      .testTag("product_card_${product.id}"),
    color = Color.White,
    shadowElevation = 3.dp
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
    ) {
      // 3D Product Image Container with floating Badges
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .aspectRatio(1f)
          .clip(RoundedCornerShape(16.dp))
          .background(Color(0xFFF8FAFC))
          .border(0.5.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
      ) {
        Image(
          painter = painterResource(id = product.imageRes),
          contentDescription = product.name,
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )

        // Top Badges
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.Top
        ) {
          // Promo / Badge Tag
          if (product.badge != null) {
            Surface(
              color = if (product.badge.contains("Hot") || product.badge.contains("Sale")) BestMartOrange else BestMartGreen,
              shape = RoundedCornerShape(8.dp),
              shadowElevation = 2.dp
            ) {
              Text(
                text = product.badge,
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
              )
            }
          } else {
            Spacer(modifier = Modifier.width(1.dp))
          }

          // Wishlist Heart Button
          val heartColor by animateColorAsState(
            targetValue = if (isWishlisted) BestMartOrange else Color(0xFF64748B),
            label = "heartColor"
          )
          Surface(
            shape = CircleShape,
            color = Color.White.copy(alpha = 0.92f),
            shadowElevation = 2.dp,
            modifier = Modifier.size(32.dp)
          ) {
            IconButton(
              onClick = onToggleWishlist,
              modifier = Modifier
                .fillMaxSize()
                .testTag("wishlist_button_${product.id}")
            ) {
              Icon(
                imageVector = if (isWishlisted) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Toggle Wishlist",
                tint = heartColor,
                modifier = Modifier.size(17.dp)
              )
            }
          }
        }

        // Discount pill bottom-left if discounted
        if (product.discountPercent > 0) {
          Surface(
            color = Color(0xFF0F172A).copy(alpha = 0.85f),
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
              .align(Alignment.BottomStart)
              .padding(8.dp)
          ) {
            Text(
              text = "-${product.discountPercent}%",
              color = Color.White,
              fontSize = 9.5.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Category / Brand row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = product.category.displayName,
          fontSize = 11.sp,
          color = BestMartGreenDark,
          fontWeight = FontWeight.SemiBold
        )

        // Rating pill: e.g. ⭐ 4.8 (186)
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = StarGold,
            modifier = Modifier.size(13.dp)
          )
          Spacer(modifier = Modifier.width(3.dp))
          Text(
            text = "${product.rating}",
            fontSize = 11.5.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimaryDark
          )
          Text(
            text = " (${product.reviewCount})",
            fontSize = 10.sp,
            color = TextSecondaryMuted
          )
        }
      }

      Spacer(modifier = Modifier.height(4.dp))

      // Product Title
      Text(
        text = product.name,
        fontSize = 13.5.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextPrimaryDark,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        lineHeight = 17.sp,
        modifier = Modifier.height(34.dp)
      )

      Spacer(modifier = Modifier.height(8.dp))

      // Price Row with Bangladeshi Taka (৳)
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column {
          if (product.originalPriceBDT != null) {
            Text(
              text = "৳ ${String.format("%,d", product.originalPriceBDT)}",
              fontSize = 11.sp,
              color = TextSecondaryMuted,
              textDecoration = TextDecoration.LineThrough
            )
          }
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "৳",
              fontSize = 14.sp,
              fontWeight = FontWeight.ExtraBold,
              color = BestMartOrangeDark
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
              text = String.format("%,d", product.priceBDT),
              fontSize = 17.sp,
              fontWeight = FontWeight.Black,
              color = TextPrimaryDark
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Actions: Cart & Buy via WhatsApp
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        OutlinedButton(
          onClick = onAddToCart,
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .size(38.dp)
            .testTag("add_to_cart_${product.id}"),
          contentPadding = PaddingValues(0.dp),
          border = BorderStroke(1.dp, SurfaceBorder)
        ) {
          Icon(
            imageVector = Icons.Filled.AddShoppingCart,
            contentDescription = "Add to Cart",
            tint = BestMartGreenDark,
            modifier = Modifier.size(16.dp)
          )
        }

        Button(
          onClick = onBuyClick,
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF25D366),
            contentColor = Color.White
          ),
          modifier = Modifier
            .weight(1f)
            .height(38.dp)
            .testTag("buy_button_${product.id}"),
          contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.Chat,
            contentDescription = null,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Buy Now",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}
