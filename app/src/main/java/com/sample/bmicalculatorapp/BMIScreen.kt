package com.sample.bmicalculatorapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.sample.bmicalculatorapp.ui.theme.BMICalculatorAppTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.text.DecimalFormat

@Composable
fun BMIScreen() {

    val isMetric = remember { mutableStateOf(false) }
    val weightState = remember { mutableStateOf(0) }
    val heightFeetState = remember { mutableStateOf(0) }
    val heightInchesState = remember { mutableStateOf(0) }
    val heightCentimetersState = remember { mutableStateOf(0) }
    val bmiState = remember { mutableStateOf(0.0) }


   Column(
       modifier = Modifier.fillMaxSize(),
       verticalArrangement = Arrangement.Center,
       horizontalAlignment = Alignment.CenterHorizontally
   ) {
       Text(
           text = "BMI Calculator",
           fontSize = 32.sp,
           fontWeight = FontWeight.Bold
       )
       Spacer(modifier = Modifier.height(16.dp))

       Row(
           modifier = Modifier.fillMaxWidth(),
           horizontalArrangement = Arrangement.Center
       ) {
           Text(
               text = "Weight ",
               fontSize = 18.sp,
               fontWeight = FontWeight.Bold
           )
           Text(
               text = if (isMetric.value) "kg" else "lbs",
               fontSize = 18.sp,
               fontWeight = FontWeight.Bold
           )

       }
       val pattern = remember { Regex("^\\d+\$") }
       TextField(
           value = weightState.value.toString(),
           onValueChange = {
               if (it.equals(""))
                   weightState.value = 0
               else if (it.matches(pattern) )
                   weightState.value = it.toInt()

           },

           keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
           label = { Text("Weight") }
       )

       Spacer(modifier = Modifier.height(16.dp))

       Row(
           modifier = Modifier.fillMaxWidth(),
           horizontalArrangement = Arrangement.Center
       ) {
           Text(
               text = "Height ",
               fontSize = 18.sp,
               fontWeight = FontWeight.Bold
           )
           Text(
               text = if (isMetric.value) "cm" else "ft/in",
               fontSize = 18.sp,
               fontWeight = FontWeight.Bold
           )
       }

       if (!isMetric.value) {

           val pattern = remember { Regex("^\\d+\$") }
           TextField(
               value = heightFeetState.value.toString(),

               onValueChange = {
                   if (it.equals(""))
                       heightFeetState.value = 0
                   else if (it.matches(pattern) )
                       heightFeetState.value = it.toInt()

               },
               keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
               keyboardActions = KeyboardActions(
                   onDone = {

                   }
               ),
               label = { Text("Feet")
               }
           )
           Spacer(modifier = Modifier.width(8.dp))
           TextField(
               value = heightInchesState.value.toString(),
               onValueChange = {
                   if (it.equals(""))
                       heightInchesState.value = 0
                   else if (it.matches(pattern) )
                       heightInchesState.value = it.toInt()

               },
               keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
               keyboardActions = KeyboardActions(
                   onDone = {

                   }
               ),
               label = { Text("Inch")
               }
           )
       } else {
           val pattern = remember { Regex("^\\d+\$") }
           TextField(
               value = heightCentimetersState.value.toString(),

               onValueChange = {
                   if (it.equals(""))
                       heightCentimetersState.value = 0
                   else if (it.matches(pattern) )
                       heightCentimetersState.value = it.toInt()
               },
               keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
               label = { Text("Centimeters") }
           )
       }
       Spacer(modifier = Modifier.height(16.dp))

       Button(
           onClick = {
               val bmi =  if (isMetric.value) {
                   weightState.value / (heightCentimetersState.value.toDouble() / 100) /
                           (heightCentimetersState.value.toDouble() / 100)
               } else {
                   (weightState.value * 703) / ((heightFeetState.value.toDouble() * 12 + heightInchesState.value.toDouble()) *
                           (heightFeetState.value.toDouble() * 12 + heightInchesState.value.toDouble()))
               }
               bmiState.value = bmi
           },
       ) {
           Text("Calculate")
       }

       Spacer(modifier = Modifier.height(16.dp))

       val df = DecimalFormat("#.##")
       if(bmiState.value.isNaN())
           bmiState.value = 0.0

       val roundedBmi = df.format(bmiState.value)

       Text(
           text = "Your BMI is ${roundedBmi} ${determineFitness(bmiState.value)}",
           fontSize = 24.sp,
           fontWeight = FontWeight.Bold
       )
       Spacer(modifier = Modifier.height(16.dp))

       Text(
           text = "Metric",
           fontSize = 18.sp,
           fontWeight = FontWeight.Bold
       )

       Switch(
           checked = isMetric.value,
           onCheckedChange = { isMetric.value = it },
       )

   }


}

//Underweight = <18.5
//Normal weight = 18.5–24.9
//Overweight = 25–29.9
//Obesity = BMI of 30 or greater
fun determineFitness(bmi : Double) : String {

    if(bmi <= 0 )
        return ""
    else if(bmi < 18.5)
        return  "Under Weight"
    else if(bmi >= 18.5 && bmi <= 24.9)
        return "Healthy Weight"
    else if(bmi >= 25 && bmi <= 29.9)
        return "Over Weight"
    else
        return "Obese Weight"
}