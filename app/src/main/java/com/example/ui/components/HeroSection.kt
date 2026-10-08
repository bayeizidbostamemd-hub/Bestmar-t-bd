package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.BestMartGreen
import com.example.ui.theme.BestMartGreenDark
import com.example.ui.theme.BestMartOrange
import com.example.ui.theme.BestMartOrangeDark

@Composable
fun HeroSection(
  onShopNowClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
  ) {
    // Big 3D Hero Banner Card
    Surface(
      modifier = Modifier
        .fillMaxWidth()
        .height(230.dp)
        .shadow(8.dp, RoundedCornerShape(24.dp)),
      shape = RoundedCornerShape(24.dp),
      color = Color.White
    ) {
      Box(modifier = Modifier.fillMaxSize()) {
        // Hero Background Image with 3D product elements
        Image(
          painter = painterResource(id = R.drawable.hero_banner_bestmart_1791428124082),
          contentDescription = "Everything You Need, One Place - Hero Banner",
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay for contrast and sleek finish
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(
              Brush.horizontalGradient(
                colors = listOf(
                  BestMartGreenDark.copy(alpha = 0.90f),
                  BestMartGreenDark.copy(alpha = 0.75f),
                  Color.Black.copy(alpha = 0.20f)
                ),
                startX = 0f,
                endX = 750f
              )
            )
        )

        // Banner Content
        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 18.dp),
          verticalArrangement = Arrangement.SpaceBetween,
          horizontalAlignment = Alignment.Start
        ) {
          // Top promotional chips
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Surface(
              color = BestMartOrange,
              shape = RoundedCornerShape(20.dp)
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Filled.Percent,
                  contentDescription = null,
                  tint = Color.White,
                  modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "EID MEGA SALE",
                  color = Color.White,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  letterSpacing = 0.5.sp
                )
              }
            }

            Surface(
              color = Color.White.copy(alpha = 0.2f),
              shape = RoundedCornerShape(20.dp),
              modifier = Modifier.border(1.dp, Color.White.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
            ) {
              Text(
                text = "Dhaka Express 24h",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
              )
            }
          }

          // Main Headline & Subtitle
          Column {
            Text(
              text = "Everything You Need,\nOne Place",
              color = Color.White,
              fontSize = 24.sp,
              lineHeight = 28.sp,
              fontWeight = FontWeight.Black,
              letterSpacing = (-0.5).sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "Authentic Electronics, Fashion, Daily Groceries & Home Essentials at Bangladesh's best prices.",
              color = Color.White.copy(alpha = 0.9f),
              fontSize = 12.sp,
              lineHeight = 16.sp,
              maxLines = 2,
              modifier = Modifier.fillMaxWidth(0.78f)
            )
          }

          // Shop Now Button
          Button(
            onClick = onShopNowClick,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = BestMartOrange,
              contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
            modifier = Modifier
              .testTag("shop_now_button")
              .height(44.dp)
          ) {
            Text(
              text = "Shop Now",
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowForward,
              contentDescription = null,
              modifier = Modifier.size(16.dp)
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Bangladeshi Trust Badges Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(Color.White)
        .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(14.dp))
        .padding(horizontal = 12.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      TrustPill(
        icon = Icons.Filled.LocalShipping,
        label = "24h Dhaka Delivery",
        tint = BestMartGreen
      )
      TrustPill(
        icon = Icons.Filled.Security,
        label = "100% Genuine BD",
        tint = BestMartOrange
      )
      TrustPill(
        icon = Icons.Filled.CheckCircle,
        label = "bKash & COD Pay",
        tint = BestMartGreenDark
      )
    }
  }
}

@Composable
private fun TrustPill(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  label: String,
  tint: Color
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      tint = tint,
      modifier = Modifier.size(15.dp)
    )
    Spacer(modifier = Modifier.width(5.dp))
    Text(
      text = label,
      fontSize = 11.sp,
      fontWeight = FontWeight.SemiBold,
      color = Color(0xFF334155)
    )
  }
}
