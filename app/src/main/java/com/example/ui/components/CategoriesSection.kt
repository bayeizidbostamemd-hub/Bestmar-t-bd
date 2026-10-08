package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CategoryItem
import com.example.model.StoreCategory
import com.example.ui.theme.BestMartGreen
import com.example.ui.theme.BestMartGreenContainer
import com.example.ui.theme.BestMartGreenDark
import com.example.ui.theme.BestMartOrange
import com.example.ui.theme.BestMartOrangeDark
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted

@Composable
fun CategoriesSection(
  categories: List<CategoryItem>,
  selectedCategory: StoreCategory,
  onCategorySelected: (StoreCategory) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 12.dp)
  ) {
    // Header row
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(4.dp, 18.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(BestMartOrange)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Featured Categories",
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimaryDark
        )
      }

      Text(
        text = if (selectedCategory != StoreCategory.ALL) "Clear Filter" else "See All",
        fontSize = 12.5.sp,
        fontWeight = FontWeight.SemiBold,
        color = if (selectedCategory != StoreCategory.ALL) BestMartOrange else BestMartGreen,
        modifier = Modifier
          .clickable {
            onCategorySelected(StoreCategory.ALL)
          }
          .padding(4.dp)
          .testTag("clear_category_filter_button")
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Horizontal 3D Category Row
    LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      // "All Products" chip
      item {
        val isAllSelected = selectedCategory == StoreCategory.ALL
        CategoryPillAll(
          isSelected = isAllSelected,
          onClick = { onCategorySelected(StoreCategory.ALL) }
        )
      }

      // 5 Categories: Electronics, Fashion, Home & Living, Beauty, Groceries
      items(categories) { item ->
        val isSelected = selectedCategory == item.category
        Category3DCard(
          item = item,
          isSelected = isSelected,
          onClick = {
            if (isSelected) {
              onCategorySelected(StoreCategory.ALL)
            } else {
              onCategorySelected(item.category)
            }
          }
        )
      }
    }
  }
}

@Composable
private fun CategoryPillAll(
  isSelected: Boolean,
  onClick: () -> Unit
) {
  val borderColor by animateColorAsState(
    targetValue = if (isSelected) BestMartGreen else SurfaceBorder,
    animationSpec = tween(200)
  )
  val backgroundColor by animateColorAsState(
    targetValue = if (isSelected) BestMartGreenContainer else Color.White,
    animationSpec = tween(200)
  )

  Surface(
    modifier = Modifier
      .width(80.dp)
      .height(115.dp)
      .clip(RoundedCornerShape(18.dp))
      .border(
        width = if (isSelected) 2.dp else 1.dp,
        color = borderColor,
        shape = RoundedCornerShape(18.dp)
      )
      .clickable { onClick() }
      .testTag("category_chip_all"),
    color = backgroundColor,
    shadowElevation = if (isSelected) 4.dp else 1.dp
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Box(
        modifier = Modifier
          .size(54.dp)
          .clip(CircleShape)
          .background(
            if (isSelected) BestMartGreen else Color(0xFFF1F5F9)
          ),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Filled.Category,
          contentDescription = "All Categories",
          tint = if (isSelected) Color.White else BestMartGreenDark,
          modifier = Modifier.size(24.dp)
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "All Items",
        fontSize = 12.sp,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
        color = if (isSelected) BestMartGreenDark else TextPrimaryDark,
        textAlign = TextAlign.Center
      )
    }
  }
}

@Composable
private fun Category3DCard(
  item: CategoryItem,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  val borderColor by animateColorAsState(
    targetValue = if (isSelected) BestMartGreen else SurfaceBorder,
    animationSpec = tween(200)
  )

  Surface(
    modifier = Modifier
      .width(96.dp)
      .height(115.dp)
      .clip(RoundedCornerShape(18.dp))
      .border(
        width = if (isSelected) 2.dp else 1.dp,
        color = borderColor,
        shape = RoundedCornerShape(18.dp)
      )
      .clickable { onClick() }
      .testTag("category_card_${item.category.id}"),
    color = Color.White,
    shadowElevation = if (isSelected) 6.dp else 2.dp
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(6.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      // 3D Category Image with soft background & slight 3D elevation
      Box(
        modifier = Modifier
          .size(64.dp)
          .clip(RoundedCornerShape(14.dp))
          .background(Color(0xFFF8FAFC))
          .border(0.5.dp, Color(0xFFE2E8F0), RoundedCornerShape(14.dp))
      ) {
        Image(
          painter = painterResource(id = item.imageRes),
          contentDescription = item.category.displayName,
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )

        // Selected active glow marker
        if (isSelected) {
          Box(
            modifier = Modifier
              .align(Alignment.TopEnd)
              .padding(4.dp)
              .size(8.dp)
              .clip(CircleShape)
              .background(BestMartOrange)
          )
        }
      }

      // Title & Tag
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = item.category.displayName,
          fontSize = 11.5.sp,
          fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
          color = if (isSelected) BestMartGreenDark else TextPrimaryDark,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          textAlign = TextAlign.Center
        )
        Text(
          text = item.tagText,
          fontSize = 9.sp,
          color = if (isSelected) BestMartOrangeDark else TextSecondaryMuted,
          fontWeight = FontWeight.Medium,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          textAlign = TextAlign.Center
        )
      }
    }
  }
}
