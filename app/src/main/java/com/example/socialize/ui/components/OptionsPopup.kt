package com.example.socialize.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A data class representing a single option in the popup.
 *
 * @param label      Text to display.
 * @param icon       Optional leading icon (ImageVector).
 * @param tint       Icon tint color (default dark gray).
 * @param labelColor Text color (default dark).
 * @param onClick    Called when this option is tapped.
 */
data class PopupOption(
    val label: String,
    val icon: ImageVector? = null,
    val tint: Color = Color(0xFF424242),
    val labelColor: Color = Color(0xFF1A1A1A),
    val onClick: () -> Unit
)

/**
 * A reusable dropdown menu anchored below its parent composable.
 *
 * ⚠️ Place both the trigger button AND this composable inside the same Box —
 *    DropdownMenu positions itself relative to its parent bounds.
 *
 * Usage:
 *   Box {
 *       IconButton(onClick = { expanded = true }) { ... }
 *       OptionsPopup(
 *           expanded = expanded,
 *           onDismiss = { expanded = false },
 *           options = listOf(
 *               PopupOption("Copy",   icon = Icons.Default.ContentCopy) { },
 *               PopupOption("Delete", icon = Icons.Default.Delete,
 *                   labelColor = Color.Red, tint = Color.Red) { }
 *           )
 *       )
 *   }
 */
@Composable
fun OptionsPopup(
    expanded: Boolean,
    options: List<PopupOption>,
    onDismiss: () -> Unit,
    title: String? = null
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        containerColor = Color.White
    ) {
        if (title != null) {
            Text(
                text = title.uppercase(),
                style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFAAAAAA),
                    letterSpacing = 0.8.sp
                ),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            HorizontalDivider(color = Color(0xFFF0F0F0))
        }

        options.forEachIndexed { index, option ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = option.label,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = option.labelColor
                        )
                    )
                },
                leadingIcon = option.icon?.let { icon ->
                    {
                        Icon(
                            imageVector = icon,
                            contentDescription = option.label,
                            tint = option.tint,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                onClick = {
                    option.onClick()
                    onDismiss()
                },
                colors = MenuDefaults.itemColors(
                    textColor = option.labelColor,
                    leadingIconColor = option.tint
                )
            )

            if (index < options.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    color = Color(0xFFF5F5F5)
                )
            }
        }
    }
}
