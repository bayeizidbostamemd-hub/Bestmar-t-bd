package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.FilterState
import com.example.model.SortOption
import com.example.model.StoreCategory
import com.example.ui.theme.BestMartGreen
import com.example.ui.theme.BestMartGreenContainer
import com.example.ui.theme.BestMartGreenDark
import com.example.ui.theme.BestMartOrange
import com.example.ui.theme.StarGold
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterSidebarBottomSheet(
  filterState: FilterState,
  onDismiss: () -> Unit,
  onApplyFilters: (FilterState) -> Unit,
  onResetFilters: () -> Unit,
  modifier: Modifier = Modifier
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  var tempCategory by remember(filterState) { mutableStateOf(filterState.selectedCategory) }
  var tempPriceRange by remember(filterState) { mutableStateOf(filterState.priceRange) }
  var tempRating by remember(filterState) { mutableFloatStateOf(filterState.minRating) }
  var tempSort by remember(filterState) { mutableStateOf(filterState.sortBy) }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = Color.White,
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    modifier = modifier.testTag("filter_sidebar_sheet")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
        .padding(bottom = 24.dp)
        .verticalScroll(rememberScrollState())
    ) {
      // Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Filter Products",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimaryDark
          )
          Text(
            text = "Refine BestMart BD catalog",
            fontSize = 12.sp,
            color = TextSecondaryMuted
          )
        }

        IconButton(
          onClick = onDismiss,
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(Color(0xFFF1F5F9))
            .testTag("filter_sheet_close_button")
        ) {
          Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close",
            tint = TextPrimaryDark,
            modifier = Modifier.size(18.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Section 1: Categories
      Text(
        text = "Category",
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimaryDark
      )
      Spacer(modifier = Modifier.height(8.dp))

      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        StoreCategory.entries.forEach { cat ->
          val isSelected = tempCategory == cat
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (isSelected) BestMartGreen else Color(0xFFF8FAFC),
            modifier = Modifier
              .border(
                1.dp,
                if (isSelected) BestMartGreen else SurfaceBorder,
                RoundedCornerShape(12.dp)
              )
              .clickable { tempCategory = cat }
              .testTag("filter_category_${cat.id}")
          ) {
            Text(
              text = cat.displayName,
              fontSize = 12.5.sp,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              color = if (isSelected) Color.White else TextPrimaryDark,
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Section 2: Price Range in BDT (৳)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Price Range (BDT)",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimaryDark
        )
        Text(
          text = "৳ ${tempPriceRange.start.toInt()} - ৳ ${tempPriceRange.endInclusive.toInt()}",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = BestMartOrange
        )
      }

      Spacer(modifier = Modifier.height(6.dp))

      RangeSlider(
        value = tempPriceRange.start..tempPriceRange.endInclusive,
        onValueChange = { range ->
          tempPriceRange = range
        },
        valueRange = 0f..5000f,
        steps = 19, // increments of 250 BDT
        colors = SliderDefaults.colors(
          thumbColor = BestMartGreen,
          activeTrackColor = BestMartGreen,
          inactiveTrackColor = Color(0xFFE2E8F0)
        ),
        modifier = Modifier.testTag("price_range_slider")
      )

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(text = "৳ 0", fontSize = 11.sp, color = TextSecondaryMuted)
        Text(text = "৳ 2,500", fontSize = 11.sp, color = TextSecondaryMuted)
        Text(text = "৳ 5,000+", fontSize = 11.sp, color = TextSecondaryMuted)
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Section 3: Rating Filter
      Text(
        text = "Minimum Rating",
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimaryDark
      )
      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        val ratings = listOf(
          0f to "Any Rating",
          4.0f to "4.0+ ⭐",
          4.5f to "4.5+ ⭐",
          4.8f to "4.8+ ⭐"
        )
        ratings.forEach { (rVal, label) ->
          val isSelected = tempRating == rVal
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (isSelected) BestMartOrange else Color(0xFFF8FAFC),
            modifier = Modifier
              .weight(1f)
              .border(
                1.dp,
                if (isSelected) BestMartOrange else SurfaceBorder,
                RoundedCornerShape(12.dp)
              )
              .clickable { tempRating = rVal }
              .testTag("filter_rating_${rVal}")
          ) {
            Box(
              modifier = Modifier.padding(vertical = 8.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = label,
                fontSize = 11.5.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.White else TextPrimaryDark
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Section 4: Sort Option
      Text(
        text = "Sort By",
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimaryDark
      )
      Spacer(modifier = Modifier.height(8.dp))

      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        SortOption.entries.forEach { sort ->
          val isSelected = tempSort == sort
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (isSelected) BestMartGreenContainer else Color(0xFFF8FAFC),
            modifier = Modifier
              .border(
                1.dp,
                if (isSelected) BestMartGreen else SurfaceBorder,
                RoundedCornerShape(12.dp)
              )
              .clickable { tempSort = sort }
              .testTag("filter_sort_${sort.name}")
          ) {
            Text(
              text = sort.title,
              fontSize = 12.sp,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              color = if (isSelected) BestMartGreenDark else TextPrimaryDark,
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(26.dp))

      // Action Buttons: Reset & Apply
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        OutlinedButton(
          onClick = {
            tempCategory = StoreCategory.ALL
            tempPriceRange = 0f..5000f
            tempRating = 0f
            tempSort = SortOption.POPULAR
            onResetFilters()
            onDismiss()
          },
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .testTag("reset_filters_button")
        ) {
          Icon(
            imageVector = Icons.Filled.RestartAlt,
            contentDescription = null,
            tint = TextSecondaryMuted,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Reset", color = TextPrimaryDark, fontWeight = FontWeight.SemiBold)
        }

        Button(
          onClick = {
            onApplyFilters(
              FilterState(
                selectedCategory = tempCategory,
                priceRange = tempPriceRange,
                minRating = tempRating,
                sortBy = tempSort
              )
            )
            onDismiss()
          },
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = BestMartGreen,
            contentColor = Color.White
          ),
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .testTag("apply_filters_button")
        ) {
          Text(
            text = "Apply Filters",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}
