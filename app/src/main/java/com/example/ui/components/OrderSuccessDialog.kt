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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PaymentMethod
import com.example.ui.theme.BestMartGreen
import com.example.ui.theme.BestMartGreenDark
import com.example.ui.theme.BestMartOrange
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted

@Composable
fun OrderSuccessDialog(
  orderId: String,
  paymentMethod: PaymentMethod,
  isDhaka: Boolean,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(24.dp),
    containerColor = Color.White,
    title = null,
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(Color(0xFFE8F5E9)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = "Success",
            tint = BestMartGreen,
            modifier = Modifier.size(46.dp)
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "Dhonnobad! ধন্যবাদ!",
          fontSize = 20.sp,
          fontWeight = FontWeight.Black,
          color = BestMartGreenDark
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = "Your order has been placed successfully.",
          fontSize = 13.sp,
          color = TextSecondaryMuted,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
          shape = RoundedCornerShape(14.dp),
          color = Color(0xFFF8FAFC),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SurfaceBorder, RoundedCornerShape(14.dp))
        ) {
          Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Order ID", fontSize = 12.sp, color = TextSecondaryMuted)
              Text(text = "#$orderId", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimaryDark)
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Payment Mode", fontSize = 12.sp, color = TextSecondaryMuted)
              Text(text = paymentMethod.title, fontSize = 12.5.sp, fontWeight = FontWeight.SemiBold, color = BestMartOrange)
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Estimated Delivery", fontSize = 12.sp, color = TextSecondaryMuted)
              Text(
                text = if (isDhaka) "Within 24 Hours (Dhaka)" else "2-3 Days Nationwide",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = BestMartGreen
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = Icons.Filled.LocalShipping,
            contentDescription = null,
            tint = BestMartGreen,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "SMS confirmation sent with tracking link",
            fontSize = 11.sp,
            color = TextSecondaryMuted
          )
        }
      }
    },
    confirmButton = {
      Button(
        onClick = onDismiss,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = BestMartGreen),
        modifier = Modifier
          .fillMaxWidth()
          .height(46.dp)
          .testTag("dismiss_order_success_button")
      ) {
        Text(text = "Continue Shopping", fontWeight = FontWeight.Bold, fontSize = 14.sp)
      }
    }
  )
}
