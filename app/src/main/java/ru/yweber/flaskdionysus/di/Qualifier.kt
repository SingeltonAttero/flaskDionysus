package ru.yweber.flaskdionysus.di

import javax.inject.Qualifier

/**
 * Created on 31.03.2020
 * @author YWeber */

@Qualifier
annotation class MainFlowRouter

@Qualifier
annotation class MainFlowHolder

// Drink of the day feature
@Qualifier
annotation class DrinkDayRouter

@Qualifier
annotation class DrinkDayHolder

@Qualifier
annotation class DrinkDayNestedRouter

@Qualifier
annotation class DrinkDayNestedHolder