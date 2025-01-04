package pt.isec.amov.quizectpamov.ui.screens.reports

import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isec.amov.quizectpamov.R
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun GraficsScreen(questionId: String, indexQuestion: Int) {
    val correctAnswers = 7
    val wrongAnswers = 3
    val totalAnswers = correctAnswers + wrongAnswers

    val questionResultsText = stringResource(id = R.string.question_results, indexQuestion)
    val totalAnswersText = stringResource(id = R.string.total_answers, totalAnswers)
    val correctLabel = stringResource(id = R.string.correct)
    val wrongLabel = stringResource(id = R.string.wrong)
    val context = LocalContext.current
    
    BackHandler {
        Toast.makeText(context, R.string.error_go_back, Toast.LENGTH_SHORT).show()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = questionResultsText,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = totalAnswersText,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        BarChart(
            data = listOf(correctAnswers, wrongAnswers),
            labels = listOf(correctLabel, wrongLabel),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        PieChart(
            data = listOf(correctAnswers, wrongAnswers),
            labels = listOf(correctLabel, wrongLabel),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(top = 16.dp)
        )

    }
}

@Composable
fun BarChart(data: List<Int>, labels: List<String>, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val barWidth = size.width / (data.size * 2)
        val maxDataValue = data.maxOrNull() ?: 1
        val barSpacing = barWidth
        val textPaint = android.graphics.Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 35f
            textAlign = android.graphics.Paint.Align.CENTER
            isFakeBoldText = true
        }

        data.forEachIndexed { index, value ->
            val barHeight = (value / maxDataValue.toFloat()) * size.height
            val xOffset = index * (barWidth + barSpacing) + barSpacing / 2

            val barColor = if (index == 0) Color(0xFF4CAF50) else Color(0xFFB71C1C) // Verde suave e Azul suave

            drawRect(
                color = barColor,
                topLeft = Offset(xOffset, size.height - barHeight),
                size = Size(barWidth, barHeight)
            )

            drawContext.canvas.nativeCanvas.drawText(
                labels[index],
                xOffset + barWidth / 2,
                size.height + 40f,
                textPaint
            )
        }
    }
}


@Composable
fun PieChart(data: List<Int>, labels: List<String>, modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    Canvas(modifier = modifier) {
        val total = data.sum()
        var startAngle = 0f
        val radius = size.minDimension / 2
        val textPaint = android.graphics.Paint().apply {
            color = android.graphics.Color.WHITE
            textSize = 40f
            textAlign = android.graphics.Paint.Align.CENTER
            isFakeBoldText = true
        }

        data.forEachIndexed { index, value ->
            val sweepAngle = (value / total.toFloat()) * 360
            val pieSliceColor = if (index == 0) Color(0xFF4CAF50) else Color(0xFFB71C1C)

            drawArc(
                color = pieSliceColor,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(size.width / 2 - radius, size.height / 2 - radius),
                size = Size(radius * 2, radius * 2)
            )

            if (isPortrait) {
                val angle = Math.toRadians((startAngle + sweepAngle / 2).toDouble())
                val labelX = (size.width / 2 + (radius / 1.4) * cos(angle)).toFloat()
                val labelY = (size.height / 2 + (radius / 1.4) * sin(angle)).toFloat()

                drawContext.canvas.nativeCanvas.drawText(
                    labels[index],
                    labelX,
                    labelY,
                    textPaint
                )
            }

            startAngle += sweepAngle
        }
    }
}
