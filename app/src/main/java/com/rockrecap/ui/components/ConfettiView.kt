import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp
import com.rockrecap.data.RouteViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

data class Confetti(var x: Float, var y: Float, val color: Color, var velocityY: Float = 0f, var velocityX: Float = 0f)

@Composable
fun ConfettiView(viewModel: RouteViewModel) {
    var confettiList by remember { mutableStateOf(List(100) { createConfetti() }) }

    LaunchedEffect(viewModel.displayConfetti.value) {
        while (viewModel.displayConfetti.value) {
            confettiList = confettiList.map { confetti ->
                confetti.y += confetti.velocityY
                confetti.x += confetti.velocityX
                confetti.velocityY += 0.5f // Gravity
                if ((Math.random() < 0.05)) { // Reset confetti when it goes off screen
                    createConfetti()
                } else {
                    confetti
                }
            }
            delay(23) // ~60 FPS
        }
    }

    if(viewModel.displayConfetti.value) {
        Canvas(modifier = Modifier.height(0.dp).fillMaxWidth()) {
            confettiList.forEach { confetti ->
                drawCircle(
                    color = confetti.color,
                    radius = 8f + Random.nextFloat() * (16f - 8f),
                    center = Offset(confetti.x, confetti.y),
                    style = Fill
                )
            }
        }
    }
}

fun createConfetti(): Confetti {
    return Confetti(
        x = Random.nextFloat() * 1000f,
        y = -50f,
        color = Color(Random.nextInt()),
        velocityY = Random.nextFloat() * 10 - 5,
        velocityX = Random.nextFloat() * 20 - 10
    )
}