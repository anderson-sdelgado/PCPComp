package br.com.usinasantafe.pcpcomp.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.usinasantafe.pcpcomp.utils.TypeButton


@Composable
fun ItemListDesign(
    text: String,
    setActionItem: () -> Unit
) {
    return Text(
        textAlign = TextAlign.Left,
        text = text,
        fontSize = 22.sp,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                setActionItem()
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
            .fillMaxWidth()
            .padding(12.dp)
    )
}

const val BUTTON_OK_ALERT_DIALOG_SIMPLE = "button_ok_alert_dialog_simple"

@Composable
fun AlertDialogSimpleDesign(
    text: String,
    setCloseDialog: () -> Unit
) {
    return AlertDialog(
        title = {
            Text(text = "ATENÇÃO")
        },
        text = {
            Text(
                text = text,
                modifier = Modifier.testTag("text_alert_dialog_simple")
            )
        },
        onDismissRequest = setCloseDialog,
        confirmButton = {
            Button(
                onClick = setCloseDialog,
                modifier = Modifier.testTag("button_ok_alert_dialog_simple")
            ) {
                Text("OK")
            }
        },
    )
}

@Composable
fun AlertDialogSimpleDesign(
    text: String,
    setCloseDialog: () -> Unit,
    setActionButtonOK: () -> Unit
) {
    return AlertDialog(
        title = {
            Text(text = "ATENÇÃO")
        },
        text = {
            Text(
                text = text,
                modifier = Modifier.testTag("text_alert_dialog_simple")
            )
        },
        onDismissRequest = setCloseDialog,
        confirmButton = {
            Button(
                onClick = setActionButtonOK,
                modifier = Modifier.testTag("button_ok_alert_dialog_simple")
            ) {
                Text("OK")
            }
        },
    )
}

@Composable
fun ButtonNumericDesign(
    text: @Composable () -> Unit,
    setActionButton: () -> Unit,
    modifier: Modifier
) {
    return ElevatedButton(
        onClick = {
            setActionButton()
        },
        modifier = modifier
            .fillMaxHeight(),
        shape = RoundedCornerShape(10.dp)
    ) {
        text()
    }
}

@Composable
fun TextButtonNumericDesign(
    text: String,
) {
    return Text(
        textAlign = TextAlign.Center,
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun TextButtonCleanDesign(
    text: String
) {
    return Text(
        textAlign = TextAlign.Center,
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp,
        modifier = Modifier
            .fillMaxWidth()
    )
}
