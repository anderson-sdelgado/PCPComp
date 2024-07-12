package br.com.usinasantafe.pcpcomp.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ItemListDesign(text: String, onNavigation: () -> Unit) {
    return Text(
        textAlign = TextAlign.Left,
        text = text,
        fontSize = 22.sp,
        modifier = Modifier.padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                onNavigation()
            }
    )
}

@Composable
fun TitleListDesign(text: String) {
    return Text(
        textAlign = TextAlign.Center,
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun TitleDesign(text: String) {
    return Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun TextButtonDesign(text: String) {
    return Text(
        textAlign = TextAlign.Center,
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier
            .fillMaxWidth().padding(12.dp)
    )
}

const val BUTTON_OK_ALERT_DIALOG_SIMPLE = "button_ok_alert_dialog_simple"

@Composable
fun AlertDialogSimpleDesign(text: String, setCloseDialog: () -> Unit, setOkDialog: () -> Unit) {
    return AlertDialog(
        title = {
            Text(text = "ATENÇÃO")
        },
        text = {
            Text(text = text)
        },
        onDismissRequest = setCloseDialog,
        confirmButton = {
            Button(
                onClick = setCloseDialog, modifier = Modifier.testTag("button_ok_alert_dialog_simple")
            ) {
                Text("OK")
            }
        },
    )
}