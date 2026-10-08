package com.example.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import java.net.URLEncoder

object WhatsAppHelper {
  const val DEFAULT_WHATSAPP_NUMBER = "01728412057"
  const val FACEBOOK_PAGE_URL = "https://www.facebook.com/share/1AQnG66E1a/"

  fun createBuyMessage(productName: String, priceBDT: Int? = null): String {
    val priceSuffix = if (priceBDT != null) " (Price: ৳ $priceBDT)" else ""
    return "I want to buy this product: $productName$priceSuffix"
  }

  fun openWhatsApp(
    context: Context,
    productName: String,
    priceBDT: Int? = null,
    whatsappNumber: String = DEFAULT_WHATSAPP_NUMBER
  ) {
    val message = createBuyMessage(productName, priceBDT)
    openWhatsAppWithMessage(context, message, whatsappNumber)
  }

  fun openWhatsAppWithMessage(
    context: Context,
    message: String,
    whatsappNumber: String = DEFAULT_WHATSAPP_NUMBER
  ) {
    try {
      // Format number for Bangladesh international code if starting with 01
      val cleanNumber = when {
        whatsappNumber.startsWith("01") -> "88$whatsappNumber"
        whatsappNumber.startsWith("+") -> whatsappNumber.removePrefix("+")
        else -> whatsappNumber
      }

      val encodedMessage = URLEncoder.encode(message, "UTF-8")
      val whatsappUri = Uri.parse("https://wa.me/$cleanNumber?text=$encodedMessage")

      val intent = Intent(Intent.ACTION_VIEW, whatsappUri).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      }

      context.startActivity(intent)
    } catch (e: Exception) {
      Toast.makeText(
        context,
        "Opening WhatsApp: \"$message\"",
        Toast.LENGTH_LONG
      ).show()
    }
  }

  fun openFacebookPage(context: Context) {
    try {
      val intent = Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_PAGE_URL)).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      }
      context.startActivity(intent)
    } catch (e: Exception) {
      Toast.makeText(context, "Opening Facebook Page: $FACEBOOK_PAGE_URL", Toast.LENGTH_SHORT).show()
    }
  }

  fun openMessengerChat(context: Context) {
    try {
      val intent = Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_PAGE_URL)).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      }
      context.startActivity(intent)
    } catch (e: Exception) {
      Toast.makeText(context, "Opening Messenger Chat...", Toast.LENGTH_SHORT).show()
    }
  }
}
