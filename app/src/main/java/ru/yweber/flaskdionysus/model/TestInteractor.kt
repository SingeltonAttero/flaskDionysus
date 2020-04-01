package ru.yweber.flaskdionysus.model

import javax.inject.Inject

/**
 * Created on 30.03.2020
 * @author YWeber */


class TestInteractor @Inject constructor() {
    val test: String = "test inject"
}