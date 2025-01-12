package com.example.todoapp.composables

import TodoItem
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.todoapp.ui.constants.MediumDp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun TodoItemsContainer(
    modifier: Modifier = Modifier,
    todoItemsFlow: Flow<List<TodoItem>> = flowOf(listOf()),
    onItemClick: (TodoItem) -> Unit = {},
    onItemDelete: (TodoItem) -> Unit = {},
    overlappingElementsHeight: Dp = 0.dp
) {
    val todos = todoItemsFlow.collectAsState(initial = listOf()).value
    val (isAscending, setIsAscending) = remember { mutableStateOf(true) }

    val sortedTodos = if (isAscending) {
        todos.sortedBy { it.title }
    } else {
        todos.sortedByDescending { it.title }
    }

    Column(modifier = modifier.fillMaxSize()) {
        Row {
            Button(onClick = { setIsAscending(!isAscending) }) {
                Text(text = if (isAscending) "Sort Descending" else "Sort Ascending")
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(MediumDp),
            verticalArrangement = Arrangement.spacedBy(MediumDp)
        ) {
            items(sortedTodos, key = { it.id }) { item ->
                TodoItemUi(
                    todoItem = item,
                    onItemClick = onItemClick,
                    onItemDelete = onItemDelete
                )
            }
            item { Spacer(modifier = Modifier.height(overlappingElementsHeight)) }
        }
    }
}