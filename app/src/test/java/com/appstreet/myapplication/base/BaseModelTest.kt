package com.appstreet.myapplication.base

import okhttp3.mockwebserver.MockWebServer

abstract class BaseModelTest {
    protected val server by lazy { MockWebServer() }
}