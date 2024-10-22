package br.com.usinasantafe.pcpcomp.presenter.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcpcomp.BuildConfig
import br.com.usinasantafe.pcpcomp.R
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    onNavMenuInicial: () -> Unit,
    onNavMenuApont: () -> Unit
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            SplashContent(
                modifier = Modifier.padding(innerPadding),
                flagAccess = uiState.flagAccess,
                flagMovOpen = uiState.flagMovOpen,
                setCloseDialog = viewModel::setCloseDialog,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                onNavMenuInicial = onNavMenuInicial,
                onNavMenuApont = onNavMenuApont,
            )
            viewModel.processInitial(BuildConfig.VERSION_NAME)
        }
    }
}

@Composable
fun SplashContent(
    modifier: Modifier = Modifier,
    flagAccess: Boolean,
    flagMovOpen: Boolean,
    setCloseDialog: () -> Unit,
    flagDialog: Boolean,
    failure: String,
    onNavMenuInicial: () -> Unit,
    onNavMenuApont: () -> Unit,
) {
    var visibility by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (true) {
                // Toggle visibility
                visibility = !visibility
                // Delay to control the toggle frequency
                delay(2000) // Adjust delay as needed
            }
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = visibility,
            enter = fadeIn(animationSpec = tween(durationMillis = 1100)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1100))
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.app_name),
                contentScale = ContentScale.Fit,
                modifier = modifier.size(250.dp)
            )
        }
    }

    if(flagDialog) {
        AlertDialogSimpleDesign(
            text = stringResource(id = R.string.text_failure, failure),
            setCloseDialog = setCloseDialog,
            setActionButtonOK = { onNavMenuInicial() }
        )
    }

    if (flagAccess) {
        if (flagMovOpen) onNavMenuApont() else onNavMenuInicial()
    }

}

@Preview(showBackground = true)
@Composable
fun SplashPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SplashContent(
                modifier = Modifier.padding(innerPadding),
                flagAccess = false,
                flagMovOpen = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                onNavMenuInicial = {},
                onNavMenuApont = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPagePreviewFailure() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SplashContent(
                modifier = Modifier.padding(innerPadding),
                flagAccess = false,
                flagMovOpen = false,
                setCloseDialog = {},
                flagDialog = true,
                failure = "Failure",
                onNavMenuInicial = {},
                onNavMenuApont = {}
            )
        }
    }
}