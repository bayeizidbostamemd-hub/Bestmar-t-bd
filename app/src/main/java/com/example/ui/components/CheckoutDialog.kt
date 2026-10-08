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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PaymentMethod
import com.example.model.Product
import com.example.ui.theme.BestMartGreen
import com.example.ui.theme.BestMartGreenContainer
import com.example.ui.theme.BestMartGreenDark
import com.example.ui.theme.BestMartOrange
import com.example.ui.theme.BkashPink
import com.example.ui.theme.CodGreen
import com.example.ui.theme.NagadOrange
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted
import com.example.viewmodel.CartSummary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutDialog(
  directProduct: Product? = null,
  cartSummary: CartSummary,
  selectedPayment: PaymentMethod,
  isDhaka: Boolean,
  onPaymentSelected: (PaymentMethod) -> Unit,
  onDismiss: () -> Unit,
  onConfirmOrder: () -> Unit,
  onSendToWhatsApp: (name: String, phone: String, address: String) -> Unit = { _, _, _ -> },
  modifier: Modifier = Modifier
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  var fullName by remember { mutableStateOf("Md. Tanvir Ahmed") }
  var phoneNumber by remember { mutableStateOf("01712-345678") }
  var deliveryAddress by remember {
    mutableStateOf(if (isDhaka) "House 42, Road 9/A, Dhanmondi, Dhaka 1209" else "GEC Circle, Nasirabad, Chattogram")
  }

  val deliveryFee = if (isDhaka) 60 else 120
  val calculatedTotal = if (directProduct != null) {
    directProduct.priceBDT + deliveryFee
  } else {
    cartSummary.grandTotal
  }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = Color.White,
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    modifier = modifier.testTag("checkout_sheet")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
        .padding(bottom = 28.dp)
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
            text = "Checkout Order",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimaryDark
          )
          Text(
            text = "Bangladesh Express 24h/48h Delivery",
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
            .testTag("checkout_close_button")
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

      // If buying a direct product, show it at the top
      if (directProduct != null) {
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = Color(0xFFF8FAFC),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SurfaceBorder, RoundedCornerShape(16.dp))
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .border(0.5.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
            ) {
              Image(
                painter = painterResource(id = directProduct.imageRes),
                contentDescription = directProduct.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
              )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = directProduct.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimaryDark,
                maxLines = 1
              )
              Text(
                text = "${directProduct.category.displayName} • ${directProduct.brand}",
                fontSize = 11.5.sp,
                color = TextSecondaryMuted
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "৳ ${String.format("%,d", directProduct.priceBDT)}",
                fontSize = 14.5.sp,
                fontWeight = FontWeight.Black,
                color = BestMartOrange
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))
      }

      // Customer Details Section
      Text(
        text = "Customer Information",
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimaryDark
      )
      Spacer(modifier = Modifier.height(8.dp))

      // 1. Name input
      OutlinedTextField(
        value = fullName,
        onValueChange = { fullName = it },
        label = { Text("Customer Name") },
        leadingIcon = {
          Icon(Icons.Filled.Person, contentDescription = null, tint = BestMartGreen)
        },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("checkout_name_input"),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = BestMartGreen,
          unfocusedBorderColor = SurfaceBorder
        )
      )

      Spacer(modifier = Modifier.height(8.dp))

      // 2. Phone input
      OutlinedTextField(
        value = phoneNumber,
        onValueChange = { phoneNumber = it },
        label = { Text("Mobile Number (01728412057)") },
        leadingIcon = {
          Icon(Icons.Filled.Phone, contentDescription = null, tint = BestMartGreen)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("checkout_phone_input"),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = BestMartGreen,
          unfocusedBorderColor = SurfaceBorder
        )
      )

      Spacer(modifier = Modifier.height(8.dp))

      // 3. Address input
      OutlinedTextField(
        value = deliveryAddress,
        onValueChange = { deliveryAddress = it },
        label = { Text("Delivery Address (Dhaka / Nationwide)") },
        leadingIcon = {
          Icon(Icons.Filled.LocationOn, contentDescription = null, tint = BestMartGreen)
        },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("checkout_address_input"),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = BestMartGreen,
          unfocusedBorderColor = SurfaceBorder
        )
      )

      Spacer(modifier = Modifier.height(18.dp))

      // Payment Options: bKash, Nagad, Cash on Delivery
      Text(
        text = "Payment Method",
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimaryDark
      )
      Spacer(modifier = Modifier.height(8.dp))

      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // bKash
        PaymentOptionCard(
          method = PaymentMethod.BKASH,
          isSelected = selectedPayment == PaymentMethod.BKASH,
          badgeColor = BkashPink,
          logoLabel = "bKash বিকাশ",
          onClick = { onPaymentSelected(PaymentMethod.BKASH) },
          testTag = "checkout_payment_bkash"
        )

        // Nagad
        PaymentOptionCard(
          method = PaymentMethod.NAGAD,
          isSelected = selectedPayment == PaymentMethod.NAGAD,
          badgeColor = NagadOrange,
          logoLabel = "Nagad নগদ",
          onClick = { onPaymentSelected(PaymentMethod.NAGAD) },
          testTag = "checkout_payment_nagad"
        )

        // Cash on Delivery
        PaymentOptionCard(
          method = PaymentMethod.CASH_ON_DELIVERY,
          isSelected = selectedPayment == PaymentMethod.CASH_ON_DELIVERY,
          badgeColor = CodGreen,
          logoLabel = "COD ক্যাশ অন ডেলিভারি",
          onClick = { onPaymentSelected(PaymentMethod.CASH_ON_DELIVERY) },
          testTag = "checkout_payment_cod"
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Total Breakdown Summary
      Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFFF8FAFC),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, SurfaceBorder, RoundedCornerShape(14.dp))
      ) {
        Column(
          modifier = Modifier.padding(14.dp),
          verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(text = "Delivery Speed", fontSize = 12.5.sp, color = TextSecondaryMuted)
            Text(
              text = if (isDhaka) "Dhaka 24-Hour Express (৳60)" else "48-Hour Nationwide (৳120)",
              fontSize = 12.5.sp,
              fontWeight = FontWeight.Bold,
              color = BestMartGreen
            )
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(text = "Grand Total", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimaryDark)
            Text(
              text = "৳ ${String.format("%,d", calculatedTotal)}",
              fontSize = 18.sp,
              fontWeight = FontWeight.Black,
              color = BestMartGreenDark
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Primary Place Order Button
      Button(
        onClick = onConfirmOrder,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = BestMartOrange,
          contentColor = Color.White
        ),
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .testTag("confirm_order_button")
      ) {
        Icon(
          imageVector = Icons.Filled.Check,
          contentDescription = null,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Confirm Order (৳ ${String.format("%,d", calculatedTotal)})",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Send Order Details to WhatsApp 01728412057 Button
      Button(
        onClick = {
          onSendToWhatsApp(fullName, phoneNumber, deliveryAddress)
        },
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = Color(0xFF25D366),
          contentColor = Color.White
        ),
        modifier = Modifier
          .fillMaxWidth()
          .height(46.dp)
          .testTag("send_whatsapp_order_button")
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.Chat,
          contentDescription = null,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Send Details to WhatsApp (01728412057)",
          fontSize = 13.5.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}

@Composable
private fun PaymentOptionCard(
  method: PaymentMethod,
  isSelected: Boolean,
  badgeColor: Color,
  logoLabel: String,
  onClick: () -> Unit,
  testTag: String
) {
  Surface(
    shape = RoundedCornerShape(14.dp),
    color = if (isSelected) BestMartGreenContainer else Color.White,
    modifier = Modifier
      .fillMaxWidth()
      .border(
        width = if (isSelected) 2.dp else 1.dp,
        color = if (isSelected) BestMartGreen else SurfaceBorder,
        shape = RoundedCornerShape(14.dp)
      )
      .clickable { onClick() }
      .testTag(testTag)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      RadioButton(
        selected = isSelected,
        onClick = onClick,
        colors = RadioButtonDefaults.colors(
          selectedColor = BestMartGreen
        )
      )

      Spacer(modifier = Modifier.width(6.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            color = badgeColor,
            shape = RoundedCornerShape(6.dp)
          ) {
            Text(
              text = logoLabel,
              color = Color.White,
              fontSize = 10.5.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }

          Spacer(modifier = Modifier.width(8.dp))

          Surface(
            color = Color(0xFFF1F5F9),
            shape = RoundedCornerShape(4.dp)
          ) {
            Text(
              text = method.badgeText,
              color = TextPrimaryDark,
              fontSize = 9.5.sp,
              fontWeight = FontWeight.Medium,
              modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = method.description,
          fontSize = 11.5.sp,
          color = TextSecondaryMuted,
          lineHeight = 15.sp
        )
      }
    }
  }
}
