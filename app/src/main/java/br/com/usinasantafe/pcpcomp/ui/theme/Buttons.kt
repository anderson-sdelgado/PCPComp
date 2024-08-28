package br.com.usinasantafe.pcpcomp.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.usinasantafe.pcpcomp.utils.TypeButton
import br.com.usinasantafe.pcpcomp.utils.TypeButtonWithoutUpdate

fun addTextField(text: String, char: String): String {
    return text + char
}

fun clearTextField(text: String): String {
    return if (text.length > 1) text.substring(0, text.length - 1) else ""
}

@Composable
fun ButtonsGenericNumeric(
    setActionButton: (
        text: String,
        typeButton: TypeButton
    ) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "1") },
                { setActionButton("1", TypeButton.NUMERIC) },
                Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "2") },
                { setActionButton("2", TypeButton.NUMERIC) },
                Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "3") },
                { setActionButton("3", TypeButton.NUMERIC) },
                Modifier
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "4") },
                { setActionButton("4", TypeButton.NUMERIC) },
                Modifier
                    .weight(1f)
            )

            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "5") },
                { setActionButton("5", TypeButton.NUMERIC) },
                Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "6") },
                { setActionButton("6", TypeButton.NUMERIC) },
                Modifier
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "7") },
                { setActionButton("7", TypeButton.NUMERIC) },
                Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "8") },
                { setActionButton("8", TypeButton.NUMERIC) },
                Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "9") },
                { setActionButton("9", TypeButton.NUMERIC) },
                Modifier
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ButtonNumericDesign(
                text = { TextButtonCleanDesign("APAGAR") },
                { setActionButton("APAGAR", TypeButton.CLEAN) },
                modifier = Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "0") },
                { setActionButton("0", TypeButton.NUMERIC) },
                Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "OK") },
                { setActionButton("OK", TypeButton.OK) },
                Modifier
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "ATUALIZAR DADOS") },
                { setActionButton("ATUALIZAR DADOS", TypeButton.UPDATE) },
                Modifier
                    .weight(1f)
            )
        }
    }
}

@Composable
fun ButtonsGenericNumericWithoutUpdate(
    setActionButton: (
        text: String,
        typeButtonWithoutUpdate: TypeButtonWithoutUpdate
    ) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "1") },
                { setActionButton("1", TypeButtonWithoutUpdate.NUMERIC) },
                Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "2") },
                { setActionButton("2", TypeButtonWithoutUpdate.NUMERIC) },
                Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "3") },
                { setActionButton("3", TypeButtonWithoutUpdate.NUMERIC) },
                Modifier
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "4") },
                { setActionButton("4", TypeButtonWithoutUpdate.NUMERIC) },
                Modifier
                    .weight(1f)
            )

            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "5") },
                { setActionButton("5", TypeButtonWithoutUpdate.NUMERIC) },
                Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "6") },
                { setActionButton("6", TypeButtonWithoutUpdate.NUMERIC) },
                Modifier
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "7") },
                { setActionButton("7", TypeButtonWithoutUpdate.NUMERIC) },
                Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "8") },
                { setActionButton("8", TypeButtonWithoutUpdate.NUMERIC) },
                Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "9") },
                { setActionButton("9", TypeButtonWithoutUpdate.NUMERIC) },
                Modifier
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ButtonNumericDesign(
                text = { TextButtonCleanDesign("APAGAR") },
                { setActionButton("APAGAR", TypeButtonWithoutUpdate.CLEAN) },
                modifier = Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "0") },
                { setActionButton("0", TypeButtonWithoutUpdate.NUMERIC) },
                Modifier
                    .weight(1f)
            )
            ButtonNumericDesign(
                { TextButtonNumericDesign(text = "OK") },
                { setActionButton("OK", TypeButtonWithoutUpdate.OK) },
                Modifier
                    .weight(1f)
            )
        }
    }
}
