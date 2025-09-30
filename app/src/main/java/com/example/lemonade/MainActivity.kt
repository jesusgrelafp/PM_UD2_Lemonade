package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center))
            }
        }
    }
}
@Preview
@Composable
fun LemonadeAppPreview() {
    LemonadeApp(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center))
}


@Composable
fun LemonadeApp(modifier : Modifier = Modifier) {
    var phase by remember { mutableStateOf(1) }
    var clicks by remember {mutableStateOf(0) }
    var times by remember {mutableStateOf(0) }

    // En función de la fase obtenemos una tripla con los identificadores de la imagen, texto del recurso y "content description"
    val resources = when (phase) {
        1 -> Triple(R.drawable.lemon_tree, R.string.tap_the_lemon, R.string.lemon_tree_content_description)
        2 -> Triple(R.drawable.lemon_squeeze, R.string.keep_tapping, R.string.lemon_content_description)
        3 -> Triple(R.drawable.lemon_drink, R.string.tap_lemonade, R.string.glass_of_lemonade_content_description)
        else -> Triple(R.drawable.lemon_restart, R.string.tap_empty_glass, R.string.empty_glass_content_description)
    }

    // A partir de la tripla de IDs obtener cada recurso en variables del tipo correspondiente
    val imageResource : Int = resources.first
    val textResource : String = stringResource(resources.second)
    val contentDescriptionResource : String = stringResource(resources.third)

    // Presentamos el texto con el nombre de la App en la parte superior.
    Text (
        text = stringResource(R.string.app_name),
        fontSize = 30.sp,
        lineHeight = 40.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Yellow)
            .padding(WindowInsets.statusBars.asPaddingValues()) // respeta la barra de estado
            .padding(8.dp)

    )

    //Añadimos una Column que contendrá el Button(y dentro la Image), el Spacer y el Text
    Column (modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Button(shape = RoundedCornerShape(16.dp),
               onClick = {
            phase = when (phase) {
                1 -> {
                        times = (2..4).random()  // Si pasamos a la fase 2, tenemos que obtener el número de repeticiones entre 2 y 4
                        2                               // Devolvemos la nueva fase (2)
                     }
                2 -> {
                        if (times == ++clicks) {    // Si la fase es 2, incrementamos los clicks y comparamos con el número de repeticiones
                            clicks = 0              // Si es igual inicializamos los clicks y pasamos a la fase 3
                            3
                        } else {                    // Sino ... seguimos en la fase 2
                            2
                        }
                     }
                3 -> 4                              // Si estamos en la fase 3 pasamos a la fase 4
                else -> 1                           // En cualquier otro caso pasamos a la fase 1
            }
        }) {
            // Mostramos la imagen en el botón
            Image(painter = painterResource(imageResource), contentDescription = contentDescriptionResource)
        }

        //Añadimos un espaciado vertical
        Spacer(modifier = Modifier.height(40.dp))

        //Mostramos el texto con la instrucción
        Text(textResource,
            fontSize = 10.sp)

    }

}