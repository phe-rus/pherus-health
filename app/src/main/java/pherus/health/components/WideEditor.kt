package pherus.health.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.maxkeppeker.sheets.core.models.base.IconSource
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.input.InputDialog
import com.maxkeppeler.sheets.input.models.InputHeader
import com.maxkeppeler.sheets.input.models.InputSelection
import com.maxkeppeler.sheets.input.models.InputTextField
import com.maxkeppeler.sheets.input.models.InputTextFieldType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WideEditor(
    title: String,
    defaultValue: String? = "",
    closeSelection: () -> Unit,
    onValueListener: (String) -> Unit
) {
    var inputTextOpt by rememberSaveable { mutableStateOf(defaultValue) }

    val inputOptions = listOf(
        InputTextField(
            text = inputTextOpt,
            type = InputTextFieldType.OUTLINED,
            header = InputHeader(
                title = title,
                icon = IconSource(Icons.Filled.EmojiPeople)
            ),
            changeListener = {
                inputTextOpt = it.toString()
            },
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(10)
        )
    )

    InputDialog(
        state = rememberUseCaseState(visible = true, onCloseRequest = { closeSelection() }),
        selection = InputSelection(
            input = inputOptions,
            onPositiveClick = { result ->
                inputTextOpt?.let { onValueListener(it) }
            },
        )
    )
}