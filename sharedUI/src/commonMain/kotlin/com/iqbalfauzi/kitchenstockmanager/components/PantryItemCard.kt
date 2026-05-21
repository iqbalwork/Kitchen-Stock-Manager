package com.iqbalfauzi.kitchenstockmanager.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantryItemCard(
    name: String,
    modifier: Modifier = Modifier,
    quantity: String? = null,
    category: String? = null,
    status: String? = null,
    statusColor: Color = Color.Gray,
    progress: Float? = null,
    icon: ImageVector? = null,
    iconBg: Color? = null,
    imagePlaceholder: String? = null,
    showProgress: Boolean = false,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    price: String? = null,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Leading Content
            if (leadingContent != null) {
                leadingContent()
                Spacer(modifier = Modifier.width(8.dp))
            } else if (icon != null && iconBg != null) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(iconBg.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = iconBg, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
            } else if (imagePlaceholder != null) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFF0F0F0)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(imagePlaceholder, fontWeight = FontWeight.Bold, color = Color.Gray)
                }
                Spacer(modifier = Modifier.width(12.dp))
            }
            
            // Main Content
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    if (price != null) {
                        Text(text = price, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    } else if (category != null) {
                        Badge(containerColor = Color(0xFFF0F0F0), contentColor = Color.Gray) {
                            Text(category, fontSize = 10.sp)
                        }
                    } else if (status != null && !showProgress) {
                        Text(text = status, fontSize = 12.sp, color = statusColor)
                    }
                }
                
                if (showProgress && progress != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Remaining", fontSize = 12.sp, color = Color.Gray)
                        Text(text = status ?: "", fontSize = 12.sp, color = statusColor)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                        color = statusColor,
                        trackColor = Color(0xFFEEEEEE)
                    )
                } else if (quantity != null) {
                    Text(text = "Quantity", fontSize = 12.sp, color = Color.Gray)
                    Text(text = quantity, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                    
                    if (status != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(statusColor))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = status, fontSize = 12.sp, color = statusColor)
                        }
                    }
                }
                
                // For shopping list specific status badge at the bottom right of column
                if (price != null && status != null) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                        Badge(containerColor = statusColor.copy(alpha = 0.1f), contentColor = statusColor) {
                            Text(status, fontSize = 10.sp)
                        }
                    }
                }
            }
            
            // Trailing Content
            if (trailingContent != null) {
                Spacer(modifier = Modifier.width(8.dp))
                trailingContent()
            }
        }
    }
}
