package com.zheng.home.common.injection.component

import dagger.Component
import com.zheng.home.common.injection.module.ApplicationTestModule
import com.zheng.home.injection.component.AppComponent
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationTestModule::class))
interface TestComponent : AppComponent