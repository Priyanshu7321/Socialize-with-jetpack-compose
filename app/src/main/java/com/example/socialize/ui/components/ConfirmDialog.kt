package com.example.socialize.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * A beautiful, reusable confirmation dialog.
 *
 * Usage:
 *   ConfirmDialog(
 *       title = "Delete message?",
 *       subtitle = "This action cannot be undone.",
 *       onConfirm = { /* yes clicked */ },
 *       onDismiss = { /* no / outside clicked */ }
 *   )
 *
 * Optional:
 *   yesLabel  — label for the confirm button (default "Yes")
 *   noLabel   — label for the dismiss button  (default "No")
 *   yesColor  — background color of the confirm button
 */
@Composable
fun ConfirmDialog(
    title: String,
    subtitle: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    yesLabel: String = "Yes",
    noLabel: String = "No",
    yesColor: Color = Color(0xFF4CAF50)
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .padding(horizontal = 24.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
            )

            Spacer(Modifier.height(10.dp))

            // Subtitle
            Text(
                text = subtitle,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color(0xFF757575),
                    lineHeight = 20.sp
                )
            )

            Spacer(Modifier.height(28.dp))

            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // No (dismiss)
                OutlinedButton(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF757575))
                ) {
                    Text(
                        text = noLabel,
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    )
                }

                Spacer(Modifier.width(12.dp))

                // Yes (confirm)
                Button(
                    onClick = onConfirm,
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = yesColor)
                ) {
                    Text(
                        text = yesLabel,
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    )
                }
            }
        }
    }
}
