package com.attyran.compass.ui

import androidx.compose.animation.core.animateFloatAsState
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

@Composable
fun CompassScreen(
    modifier: Modifier = Modifier,
    viewModel: CompassViewModel = hiltViewModel()
) {
    val compassData by viewModel.compassData.collectAsStateWithLifecycle()
    val locationData by viewModel.locationData.collectAsStateWithLifecycle()
    val rotation by animateFloatAsState(
        targetValue = -compassData.degrees.toFloat(),
        label = "compass_rotation",
        animationSpec = spring(
            dampingRatio = 0.8f,
            stiffness = Spring.StiffnessLow
        )
    )

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
                text = "MAGNETIC HEADING",
                style = typography.titleMedium,
                color = colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Box(
                modifier = Modifier
                    .size(300.dp)
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

                    // Draw outer circle
                    drawCircle(
                        color = colorScheme.outline,
                        radius = radius,
                        style = Stroke(width = 2.dp.toPx())
                    )

                    // Draw cardinal points and labels
                    val cardinalPoints = listOf("N", "E", "S", "W")
                    val textPaint = Paint().asFrameworkPaint().apply {
                        textSize = 14.dp.toPx()
                        textAlign = android.graphics.Paint.Align.CENTER
                        color = colorScheme.onBackground.toArgb()
                    }

                    cardinalPoints.forEachIndexed { index, point ->
                        val angle = Math.toRadians((-90.0) + (90.0 * index))
                        val dotRadius = if (point == "N") 6.dp.toPx() else 4.dp.toPx()
                        
                        // Position for the dot
                        val dotX = center.x + (radius - 30.dp.toPx()) * kotlin.math.cos(angle).toFloat()
                        val dotY = center.y + (radius - 30.dp.toPx()) * kotlin.math.sin(angle).toFloat()
                        
                        // Position for the label
                        val labelX = center.x + (radius - 50.dp.toPx()) * kotlin.math.cos(angle).toFloat()
                        val labelY = center.y + (radius - 50.dp.toPx()) * kotlin.math.sin(angle).toFloat() + 5.dp.toPx()

                        // Draw the dot
                        drawCircle(
                            color = if (point == "N") Color.Red else colorScheme.onBackground,
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

                    // Draw degree markers
                    for (i in 0..359 step 15) {
                        val angle = Math.toRadians(i.toDouble())
                        val startRadius = if (i % 45 == 0) radius - 20.dp.toPx() else radius - 10.dp.toPx()
                        val endRadius = radius
                        val startX = center.x + startRadius * kotlin.math.cos(angle).toFloat()
                        val startY = center.y + startRadius * kotlin.math.sin(angle).toFloat()
                        val endX = center.x + endRadius * kotlin.math.cos(angle).toFloat()
                        val endY = center.y + endRadius * kotlin.math.sin(angle).toFloat()
                        drawLine(
                            color = colorScheme.onBackground,
                            start = Offset(startX, startY),
                            end = Offset(endX, endY),
                            strokeWidth = if (i % 45 == 0) 2.dp.toPx() else 1.dp.toPx()
                        )
                    }
                }

                // Current heading
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${compassData.degrees}° ${compassData.direction}",
                        style = typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Strength: ${String.format("%.1f", compassData.strength)} μT",
                        style = typography.bodyMedium,
                        color = colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            // Location information
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Elevation: ${String.format("%.1f", locationData.elevation)}ft",
                    style = typography.bodyLarge,
                    color = colorScheme.onSurfaceVariant
                )
                Text(
                    text = locationData.address,
                    style = typography.bodyMedium,
                    color = colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
