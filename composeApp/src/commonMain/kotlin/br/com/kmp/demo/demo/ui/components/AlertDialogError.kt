import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

import androidx.compose.ui.window.DialogProperties

@Composable
fun AlertDialogError(
    icon: @Composable (() -> Unit),
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    showBtnTextConfirm: Boolean = true,
    btnTextConfirm: String = "Confirm",
    showBtnTextDismiss: Boolean = true,
    btnTextDismiss: String = "Dismiss",
    dismissOnBackPress: Boolean = false,
    dismissOnClickOutside: Boolean = false,
) {

    var isShowingDialog by rememberSaveable { mutableStateOf(true) }

    if (isShowingDialog){
        AlertDialog(
            properties = DialogProperties(
                dismissOnBackPress = dismissOnBackPress,
                dismissOnClickOutside = dismissOnClickOutside,
            ),
            icon = icon,
            title = {
                Text(text = dialogTitle)
            },
            text = {
                Text(text = dialogText)
            },
            onDismissRequest = { },
            confirmButton = {
                if (showBtnTextConfirm){
                    TextButton( onClick = {
                        isShowingDialog = false
                        onConfirmation()
                    } ) {
                        Text(btnTextConfirm)
                    }
                }else{
                    null
                }
            },
            dismissButton = {
                if (showBtnTextDismiss){
                    TextButton( onClick = {
                        isShowingDialog = false
                        onDismissRequest()
                    } ) {
                        Text(btnTextDismiss)
                    }
                }else{
                    null
                }
            }
        )
    }
}