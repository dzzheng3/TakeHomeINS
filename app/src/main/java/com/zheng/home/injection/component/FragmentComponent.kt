package com.zheng.home.injection.component

import com.zheng.home.injection.PerFragment
import com.zheng.home.injection.module.FragmentModule
import dagger.Subcomponent

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent