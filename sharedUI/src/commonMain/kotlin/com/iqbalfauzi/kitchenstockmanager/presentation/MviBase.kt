package com.iqbalfauzi.kitchenstockmanager.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

interface ViewState
interface ViewIntent
interface ViewEffect

abstract class BaseViewModel<S : ViewState, I : ViewIntent, E : ViewEffect>(
    initialState: S
) : ViewModel() {

    private val _viewState = MutableStateFlow(initialState)
    val viewState: StateFlow<S> = _viewState.asStateFlow()

    private val _viewEffect = Channel<E>(Channel.BUFFERED)
    val viewEffect: Flow<E> = _viewEffect.receiveAsFlow()

    protected var currentState: S
        get() = _viewState.value
        set(value) {
            _viewState.value = value
        }

    abstract fun handleIntent(intent: I)

    protected fun setState(reducer: S.() -> S) {
        val newState = currentState.reducer()
        _viewState.value = newState
    }

    protected fun setEffect(effect: E) {
        viewModelScope.launch {
            _viewEffect.send(effect)
        }
    }
}
