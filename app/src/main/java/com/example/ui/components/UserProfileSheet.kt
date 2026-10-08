package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Verified
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BestMartGreen
import com.example.ui.theme.BestMartGreenDark
import com.example.ui.theme.BestMartOrange
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileSheet(
  onDismiss: () -> Unit,
  modifier: Modifier = Modifier
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = Color.White,
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    modifier = modifier.testTag("user_profile_sheet")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
        .padding(bottom = 28.dp)
    ) {
      // Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Customer Account",
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimaryDark
        )

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

      Spacer(modifier = Modifier.height(16.dp))

      // Profile Card
      Surface(
        shape = RoundedCornerShape(18.dp),
        color = Color(0xFFF8FAFC),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, SurfaceBorder, RoundedCornerShape(18.dp))
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(54.dp)
              .clip(CircleShape)
              .background(BestMartGreen),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Filled.Person,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(30.dp)
            )
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "Md. Tanvir Ahmed",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimaryDark
              )
              Spacer(modifier = Modifier.width(6.dp))
              Icon(
                imageVector = Icons.Filled.Verified,
                contentDescription = "Verified",
                tint = BestMartGreen,
                modifier = Modifier.size(16.dp)
              )
            }
            Text(
              text = "+880 1712-345678 • Dhaka",
              fontSize = 12.sp,
              color = TextSecondaryMuted
            )
            Surface(
              color = BestMartOrange.copy(alpha = 0.15f),
              shape = RoundedCornerShape(4.dp),
              modifier = Modifier.padding(top = 4.dp)
            ) {
              Text(
                text = "Gold Member • 450 Points",
                color = BestMartOrange,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Delivery Address Info
      ProfileInfoRow(
        icon = Icons.Filled.LocationOn,
        title = "Saved Delivery Address",
        subtitle = "House 42, Road 9/A, Dhanmondi, Dhaka 1209",
        iconColor = BestMartGreen
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Loyalty Wallet
      ProfileInfoRow(
        icon = Icons.Filled.CardGiftcard,
        title = "BestMart Wallet Balance",
        subtitle = "৳ 450 Available (Usable on bKash/Nagad checkout)",
        iconColor = BestMartOrange
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Orders
      ProfileInfoRow(
        icon = Icons.Filled.ReceiptLong,
        title = "Recent Orders",
        subtitle = "Order #BM-BD-92185 • 2 items (Delivered to Dhanmondi)",
        iconColor = BestMartGreenDark
      )

      Spacer(modifier = Modifier.height(10.dp))

      // WhatsApp Official Ordering
      ProfileInfoRow(
        icon = Icons.AutoMirrored.Filled.Chat,
        title = "WhatsApp Quick Buy & Hotline",
        subtitle = "01728412057 • Instant WhatsApp orders supported",
        iconColor = Color(0xFF25D366)
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Support
      ProfileInfoRow(
        icon = Icons.Filled.SupportAgent,
        title = "Bangladesh Helpline & Live Chat",
        subtitle = "Call 16216 • Available 24/7 (Bangla & English)",
        iconColor = Color(0xFF2563EB)
      )
    }
  }
}

@Composable
private fun ProfileInfoRow(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  title: String,
  subtitle: String,
  iconColor: Color
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
        .padding(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(38.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(iconColor.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = iconColor,
          modifier = Modifier.size(20.dp)
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column {
        Text(
          text = title,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimaryDark
        )
        Text(
          text = subtitle,
          fontSize = 11.5.sp,
          color = TextSecondaryMuted
        )
      }
    }
  }
}
