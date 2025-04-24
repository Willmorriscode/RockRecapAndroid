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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rockrecap.data.RouteViewModel
import com.rockrecap.ui.theme.RockBlack
import com.rockrecap.ui.theme.RockGray
import com.rockrecap.ui.theme.RockGray50
import com.rockrecap.ui.theme.RockWhite
import kotlinx.coroutines.delay
import kotlin.random.Random

data class Confetti(var x: Float, var y: Float, val color: Color, var velocityY: Float = 0f, var velocityX: Float = 0f, var id: Float)

@Composable
fun ConfettiView(viewModel: RouteViewModel, color: Color) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    var confettiList by remember { mutableStateOf(List(300) { createConfetti(color) }) }

    LaunchedEffect(viewModel.displayConfetti.value) {
        while (viewModel.displayConfetti.value) {
            confettiList = confettiList.map { confetti ->
                confetti.y += confetti.velocityY
                confetti.x += confetti.velocityX
                confetti.velocityY += 0.5f // Gravity
                if ((Math.random() < 0.02)) {
                    animateConfetti(confetti)
                } else {
                    confetti
                }
            }
            delay(21) // ~60 FPS
        }
    }

    Canvas(modifier = Modifier.height(0.dp).fillMaxWidth()) {
        if(viewModel.displayConfetti.value) {
            confettiList.forEach { confetti ->
                drawCircle(
                    color = confetti.color,
                    radius = 8f + Random.nextFloat() * (8f),
                    center = Offset(confetti.x, confetti.y),
                    style = Fill
                )
            }
        }
    }
}

fun animateConfetti(confetti: Confetti):Confetti{
    return confetti.copy(
        id = Math.random().toFloat()
    )
}

fun createConfetti(color: Color): Confetti {
    return Confetti(

        x = 600 + (Random.nextInt(-1,1) * 300f),
        y = 1400f,
        color = ( offsetColor(color) ),
        velocityY = Random.nextFloat() * -40 - 20,
        velocityX = Random.nextFloat() * 40 - 20,
        id = 0.0f
    )
}

fun offsetColor(color: Color): Color {
    return if(color == RockWhite){
        RockGray50.copy(
            alpha = (RockGray50.alpha * (Math.random().toFloat())).coerceIn(0f, 1f)
        )
    }
    else{
        color.copy(
            alpha = (color.alpha * (Math.random().toFloat())).coerceIn(0f, 1f)
        )
    }
}
