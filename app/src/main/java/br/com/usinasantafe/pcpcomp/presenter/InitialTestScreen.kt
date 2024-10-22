package br.com.usinasantafe.pcpcomp.presenter

import androidx.compose.runtime.Composable
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme

@Composable
fun InititalTestScreen(
    onNavSplash: () -> Unit,
) {
    PCPCompTheme {
        onNavSplash()
    }
}
