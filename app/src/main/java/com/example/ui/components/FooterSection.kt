package com.example.ui.components

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.ui.theme.BkashPink
import com.example.ui.theme.CodGreen
import com.example.ui.theme.NagadOrange
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted

@Composable
fun FooterSection(
  onFacebookClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier.fillMaxWidth(),
    color = Color.White,
    shadowElevation = 2.dp
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
      // Bangladeshi Payment Methods Title
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Box(
          modifier = Modifier
            .size(4.dp, 18.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(BestMartOrange)
        )
        Text(
          text = "Supported Payment Methods",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimaryDark
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // 3 Payment Cards: bKash, Nagad, COD
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // bKash Card
        PaymentMethodCard(
          name = "bKash",
          subtext = "1.5% Cashback",
          badgeColor = BkashPink,
          accentLabel = "বিকাশ",
          modifier = Modifier
            .weight(1f)
            .testTag("payment_badge_bkash")
        )

        // Nagad Card
        PaymentMethodCard(
          name = "Nagad",
          subtext = "0% Fee Instant",
          badgeColor = NagadOrange,
          accentLabel = "নগদ",
          modifier = Modifier
            .weight(1f)
            .testTag("payment_badge_nagad")
        )

        // COD Card
        PaymentMethodCard(
          name = "COD",
          subtext = "Cash on Delivery",
          badgeColor = CodGreen,
          accentLabel = "ক্যাশ",
          modifier = Modifier
            .weight(1f)
            .testTag("payment_badge_cod")
        )
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Trust guarantees
      Surface(
        color = Color(0xFFF8FAFC),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, SurfaceBorder, RoundedCornerShape(16.dp))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            TrustItem(
              title = "100% Authentic",
              desc = "Direct from brand partners"
            )
            TrustItem(
              title = "7-Day Easy Return",
              desc = "Hassle-free replacement"
            )
          }
          Spacer(modifier = Modifier.height(10.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            TrustItem(
              title = "Dhaka 24h Express",
              desc = "Nationwide 48-72h"
            )
            TrustItem(
              title = "Verified e-CAB",
              desc = "SSLCommerz 256-bit secure"
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Contact & Hub Information
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.Chat,
            contentDescription = null,
            tint = Color(0xFF25D366),
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "WhatsApp Order: 01728412057 (\"I want to buy this product\")",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F5132)
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Filled.Call,
            contentDescription = null,
            tint = BestMartGreen,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Hotline: 16216 / +880 9612-BESTMART (9 AM - 11 PM)",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = TextPrimaryDark
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = null,
            tint = BestMartOrange,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Fulfillment Hub: Road 27, Dhanmondi, Dhaka 1205, Bangladesh",
            fontSize = 11.5.sp,
            color = TextSecondaryMuted
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Facebook Page Official Link Banner
      Surface(
        onClick = onFacebookClick,
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFFF0F7FF),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, Color(0xFFD0E7FF), RoundedCornerShape(14.dp))
          .testTag("facebook_footer_banner")
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
          ) {
            Box(
              modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Color(0xFF1877F2)),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "f",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black
              )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
              Text(
                text = "Official Facebook Page",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimaryDark
              )
              Text(
                text = "facebook.com/share/1AQnG66E1a/",
                fontSize = 11.sp,
                color = Color(0xFF1877F2),
                fontWeight = FontWeight.Medium
              )
            }
          }

          Surface(
            color = Color(0xFF1877F2),
            shape = RoundedCornerShape(8.dp)
          ) {
            Text(
              text = "Follow",
              color = Color.White,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))
      HorizontalDivider(color = SurfaceBorder)
      Spacer(modifier = Modifier.height(12.dp))

      // Copyright & Footer Bottom
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(24.dp)
              .clip(CircleShape)
              .background(Color(0xFF1877F2))
              .clickable { onFacebookClick() }
              .testTag("facebook_footer_icon"),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "f",
              color = Color.White,
              fontSize = 14.sp,
              fontWeight = FontWeight.Black
            )
          }
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "© 2026 BestMart BD. All rights reserved.",
            fontSize = 11.sp,
            color = TextSecondaryMuted
          )
        }
        Text(
          text = "Made for Bangladesh 🇧🇩",
          fontSize = 11.sp,
          fontWeight = FontWeight.SemiBold,
          color = BestMartGreenDark
        )
      }
    }
  }
}

@Composable
private fun PaymentMethodCard(
  name: String,
  subtext: String,
  badgeColor: Color,
  accentLabel: String,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
      .clip(RoundedCornerShape(14.dp))
      .border(1.dp, SurfaceBorder, RoundedCornerShape(14.dp)),
    color = Color(0xFFFAFAFA),
    shadowElevation = 1.dp
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(CircleShape)
          .background(badgeColor),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = accentLabel,
          color = Color.White,
          fontWeight = FontWeight.Black,
          fontSize = 11.sp
        )
      }
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = name,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = TextPrimaryDark
      )
      Text(
        text = subtext,
        fontSize = 9.sp,
        color = TextSecondaryMuted,
        fontWeight = FontWeight.Medium
      )
    }
  }
}

@Composable
private fun TrustItem(
  title: String,
  desc: String
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.width(160.dp)
  ) {
    Icon(
      imageVector = Icons.Filled.CheckCircle,
      contentDescription = null,
      tint = BestMartGreen,
      modifier = Modifier.size(15.dp)
    )
    Spacer(modifier = Modifier.width(6.dp))
    Column {
      Text(
        text = title,
        fontSize = 11.5.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimaryDark
      )
      Text(
        text = desc,
        fontSize = 9.5.sp,
        color = TextSecondaryMuted
      )
    }
  }
}
