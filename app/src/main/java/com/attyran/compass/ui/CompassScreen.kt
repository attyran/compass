package com.attyran.compass.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.abs

@Composable
fun CompassScreen(
    modifier: Modifier = Modifier,
    viewModel: CompassViewModel = hiltViewModel()
) {
    val compassData by viewModel.compassData.collectAsStateWithLifecycle()
    val locationData by viewModel.locationData.collectAsStateWithLifecycle()
    val currentRotation = remember { Animatable(0f) }

    LaunchedEffect(compassData.degrees) {
        val newRotation = -compassData.degrees.toFloat()
        
        // Simple approach: calculate the shortest rotation path
        val currentValue = currentRotation.value
        var targetRotation = newRotation
        
        // Handle the wrap-around case by finding the shortest path
        val diff = newRotation - currentValue
        
        // If the difference is greater than 180 degrees, take the shorter path
        if (diff > 180) {
            targetRotation = currentValue + (diff - 360)
        } else if (diff < -180) {
            targetRotation = currentValue + (diff + 360)
        }
        
        currentRotation.animateTo(
            targetValue = targetRotation,
            animationSpec = spring(
                dampingRatio = 0.8f,
                stiffness = Spring.StiffnessLow
            )
        )
    }

    val rotation by currentRotation.asState()

    // Extract colorScheme and typography before Canvas
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Surface(
        modifier = modifier.fillMaxSize(),
        color = colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "COMPASS",
                style = typography.titleMedium,
                color = colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Box(
                modifier = Modifier
                    .size(320.dp)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                // Compass circle
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(rotation)
                ) {
                    val center = Offset(size.width / 2, size.height / 2)
                    val radius = size.width / 2

                    // Draw outer circle with gradient effect
                    drawCircle(
                        color = colorScheme.outline,
                        radius = radius,
                        style = Stroke(width = 3.dp.toPx())
                    )
                    
                    // Draw inner circle for better visual depth
                    drawCircle(
                        color = colorScheme.outline.copy(alpha = 0.3f),
                        radius = radius - 2.dp.toPx(),
                        style = Stroke(width = 1.dp.toPx())
                    )

                    // Draw cardinal points and labels with improved positioning
                    val cardinalPoints = listOf("N", "E", "S", "W")
                    val textPaint = Paint().asFrameworkPaint().apply {
                        textSize = 16.dp.toPx()
                        textAlign = android.graphics.Paint.Align.CENTER
                        color = colorScheme.onBackground.toArgb()
                        isFakeBoldText = true
                    }

                    cardinalPoints.forEachIndexed { index, point ->
                        val angle = Math.toRadians((-90.0) + (90.0 * index))
                        val dotRadius = if (point == "N") 8.dp.toPx() else 5.dp.toPx()
                        
                        // Position for the dot (closer to center)
                        val dotX = center.x + (radius - 25.dp.toPx()) * cos(angle).toFloat()
                        val dotY = center.y + (radius - 25.dp.toPx()) * sin(angle).toFloat()
                        
                        // Position for the label (further out)
                        val labelX = center.x + (radius - 45.dp.toPx()) * cos(angle).toFloat()
                        val labelY = center.y + (radius - 45.dp.toPx()) * sin(angle).toFloat() + 6.dp.toPx()

                        // Draw the dot with different colors for cardinal points
                        val dotColor = when (point) {
                            "N" -> Color.Red
                            "E" -> Color(0xFF4CAF50) // Green
                            "S" -> Color(0xFF2196F3) // Blue
                            "W" -> Color(0xFFFF9800) // Orange
                            else -> colorScheme.onBackground
                        }
                        
                        drawCircle(
                            color = dotColor,
                            radius = dotRadius,
                            center = Offset(dotX, dotY)
                        )

                        // Draw the label
                        drawContext.canvas.nativeCanvas.drawText(
                            point,
                            labelX,
                            labelY,
                            textPaint
                        )
                    }

                    // Draw degree markers with improved spacing
                    for (i in 0..359 step 15) {
                        val angle = Math.toRadians(i.toDouble())
                        val startRadius = if (i % 45 == 0) radius - 25.dp.toPx() else radius - 15.dp.toPx()
                        val endRadius = radius
                        val startX = center.x + startRadius * cos(angle).toFloat()
                        val startY = center.y + startRadius * sin(angle).toFloat()
                        val endX = center.x + endRadius * cos(angle).toFloat()
                        val endY = center.y + endRadius * sin(angle).toFloat()
                        
                        val strokeWidth = if (i % 45 == 0) 3.dp.toPx() else 1.5.dp.toPx()
                        val markerColor = if (i % 45 == 0) colorScheme.primary else colorScheme.onBackground.copy(alpha = 0.7f)
                        
                        drawLine(
                            color = markerColor,
                            start = Offset(startX, startY),
                            end = Offset(endX, endY),
                            strokeWidth = strokeWidth
                        )
                    }
                    
                    // Draw intermediate degree markers (every 5 degrees)
                    for (i in 0..359 step 5) {
                        if (i % 15 != 0) { // Skip the main markers
                            val angle = Math.toRadians(i.toDouble())
                            val startRadius = radius - 8.dp.toPx()
                            val endRadius = radius
                            val startX = center.x + startRadius * cos(angle).toFloat()
                            val startY = center.y + startRadius * sin(angle).toFloat()
                            val endX = center.x + endRadius * cos(angle).toFloat()
                            val endY = center.y + endRadius * sin(angle).toFloat()
                            
                            drawLine(
                                color = colorScheme.onBackground.copy(alpha = 0.4f),
                                start = Offset(startX, startY),
                                end = Offset(endX, endY),
                                strokeWidth = 0.5.dp.toPx()
                            )
                        }
                    }
                }

                // Current heading display
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Up arrow pointer
                    Canvas(
                        modifier = Modifier.size(40.dp)
                    ) {
                        val center = Offset(size.width / 2, size.height / 2)
                        val arrowLength = 16.dp.toPx()
                        val arrowWidth = 8.dp.toPx()
                        
                        // Draw the arrow head (triangle pointing up)
                        val path = androidx.compose.ui.graphics.Path().apply {
                            moveTo(center.x, center.y - arrowLength / 2)
                            lineTo(center.x - arrowWidth / 2, center.y + arrowLength / 2)
                            lineTo(center.x + arrowWidth / 2, center.y + arrowLength / 2)
                            close()
                        }
                        drawPath(
                            path = path,
                            color = colorScheme.primary
                        )
                    }
                    
                    Text(
                        text = "${compassData.degrees}°",
                        style = typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.primary
                    )
                    Text(
                        text = compassData.direction,
                        style = typography.titleLarge,
                        fontWeight = FontWeight.Medium,
                        color = colorScheme.onSurface
                    )
                }
            }

            // Location information with improved layout
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Elevation: ${String.format("%.1f", locationData.elevation)}ft",
                    style = typography.bodyLarge,
                    color = colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = locationData.address,
                    style = typography.bodyMedium,
                    color = colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "Lat: ${String.format("%.4f", locationData.latitude)}, Lng: ${String.format("%.4f", locationData.longitude)}",
                    style = typography.bodyMedium,
                    color = colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}