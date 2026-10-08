package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BestMartGreen
import com.example.ui.theme.BestMartGreenDark
import com.example.ui.theme.BestMartOrange
import com.example.ui.theme.BestMartOrangeDark
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted

@Composable
fun TopHeader(
  searchQuery: String,
  onSearchQueryChanged: (String) -> Unit,
  cartCount: Int,
  wishlistCount: Int,
  onCartClick: () -> Unit,
  onWishlistClick: () -> Unit,
  onProfileClick: () -> Unit,
  onFilterClick: () -> Unit,
  onFacebookClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  val focusManager = LocalFocusManager.current

  Surface(
    modifier = modifier.fillMaxWidth(),
    color = Color.White,
    shadowElevation = 3.dp
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
      // Top row: Brand & Action Icons
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Logo and Bangladeshi tagline
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.clickable { /* Brand Home */ }
        ) {
          Box(
            modifier = Modifier
              .size(42.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(
                Brush.linearGradient(
                  colors = listOf(BestMartGreen, BestMartGreenDark)
                )
              )
              .border(1.dp, BestMartOrange.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "BM",
              color = Color.White,
              fontWeight = FontWeight.Black,
              fontSize = 18.sp,
              letterSpacing = 0.5.sp
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "BestMart",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = BestMartGreenDark,
                letterSpacing = (-0.5).sp
              )
              Spacer(modifier = Modifier.width(4.dp))
              Surface(
                color = BestMartOrange,
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.offset(y = (-1).dp)
              ) {
                Text(
                  text = "BD",
                  color = Color.White,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Black,
                  modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                )
              }
            }
            Text(
              text = "সবকিছু এক ঠিকানায় • Dhaka & Nationwide",
              fontSize = 10.5.sp,
              color = TextSecondaryMuted,
              fontWeight = FontWeight.Medium
            )
          }
        }

        // Action Icons: Wishlist, Cart, Profile
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          // Wishlist
          BadgedBox(
            badge = {
              if (wishlistCount > 0) {
                Badge(
                  containerColor = BestMartOrange,
                  contentColor = Color.White
                ) {
                  Text(
                    text = wishlistCount.toString(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }
          ) {
            IconButton(
              onClick = onWishlistClick,
              modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color(0xFFF8FAFC))
                .testTag("wishlist_header_button")
            ) {
              Icon(
                imageVector = if (wishlistCount > 0) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Wishlist",
                tint = if (wishlistCount > 0) BestMartOrange else TextPrimaryDark,
                modifier = Modifier.size(20.dp)
              )
            }
          }

          // Cart
          BadgedBox(
            badge = {
              if (cartCount > 0) {
                Badge(
                  containerColor = BestMartGreen,
                  contentColor = Color.White
                ) {
                  Text(
                    text = cartCount.toString(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }
          ) {
            IconButton(
              onClick = onCartClick,
              modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color(0xFFF8FAFC))
                .testTag("cart_header_button")
            ) {
              Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = "Cart",
                tint = if (cartCount > 0) BestMartGreen else TextPrimaryDark,
                modifier = Modifier.size(20.dp)
              )
            }
          }

          // Facebook Page
          IconButton(
            onClick = onFacebookClick,
            modifier = Modifier
              .size(42.dp)
              .clip(CircleShape)
              .background(Color(0xFFEBF5FF))
              .testTag("facebook_header_button")
          ) {
            Box(
              modifier = Modifier
                .size(26.dp)
                .clip(CircleShape)
                .background(Color(0xFF1877F2)),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "f",
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Black
              )
            }
          }

          // User Profile
          IconButton(
            onClick = onProfileClick,
            modifier = Modifier
              .size(42.dp)
              .clip(CircleShape)
              .background(Color(0xFFF1F5F9))
              .testTag("profile_header_button")
          ) {
            Icon(
              imageVector = Icons.Filled.Person,
              contentDescription = "User Profile",
              tint = BestMartGreenDark,
              modifier = Modifier.size(22.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Search Bar Row with Filter Trigger Button
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        OutlinedTextField(
          value = searchQuery,
          onValueChange = onSearchQueryChanged,
          modifier = Modifier
            .weight(1f)
            .height(52.dp)
            .testTag("search_input_field"),
          placeholder = {
            Text(
              text = "Search groceries, electronics, fashion...",
              fontSize = 13.5.sp,
              color = TextSecondaryMuted
            )
          },
          leadingIcon = {
            Icon(
              imageVector = Icons.Filled.Search,
              contentDescription = "Search",
              tint = BestMartGreen,
              modifier = Modifier.size(20.dp)
            )
          },
          trailingIcon = {
            if (searchQuery.isNotEmpty()) {
              IconButton(onClick = { onSearchQueryChanged("") }) {
                Icon(
                  imageVector = Icons.Filled.Close,
                  contentDescription = "Clear search",
                  tint = TextSecondaryMuted,
                  modifier = Modifier.size(18.dp)
                )
              }
            }
          },
          singleLine = true,
          shape = RoundedCornerShape(14.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BestMartGreen,
            unfocusedBorderColor = SurfaceBorder,
            focusedContainerColor = Color(0xFFF8FAFC),
            unfocusedContainerColor = Color(0xFFF8FAFC)
          ),
          keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
          keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Filter Sidebar Trigger Button
        Surface(
          onClick = onFilterClick,
          shape = RoundedCornerShape(14.dp),
          color = Color(0xFFF1F5F9),
          modifier = Modifier
            .height(52.dp)
            .border(1.dp, SurfaceBorder, RoundedCornerShape(14.dp))
            .testTag("filter_trigger_button")
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Icon(
              imageVector = Icons.Filled.FilterList,
              contentDescription = "Filter",
              tint = BestMartGreenDark,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Filter",
              fontSize = 13.sp,
              fontWeight = FontWeight.SemiBold,
              color = BestMartGreenDark
            )
          }
        }
      }
    }
  }
}
