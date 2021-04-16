package com.gilbertparreno.cartrack.utils.providers

import com.gilbertparreno.cartrack.core.providers.CoroutineContextProvider
import com.gilbertparreno.cartrack.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class TestCoroutineContextProvider(testCoroutineRule: TestCoroutineRule) : CoroutineContextProvider() {
    override var Main: CoroutineContext = testCoroutineRule.testCoroutineDispatcher
    override var IO: CoroutineContext = testCoroutineRule.testCoroutineDispatcher
}