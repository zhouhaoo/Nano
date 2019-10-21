/*
 * Copyright 2018 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhouhaoh.nano.ui

import android.app.Activity
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.zhouhaoh.nano.core.BaseActivity
import kotlin.reflect.KProperty

/**
 * A delegate who lazily inflates a data binding layout, calls [Activity.setContentView] and returns
 * the binding.
 */
class ContentViewBindingDelegate<in R : AppCompatActivity, out T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) {

    private var binding: T? = null

    operator fun getValue(activity: R, property: KProperty<*>): T {
        if (binding == null) {
            binding = DataBindingUtil.setContentView(activity, layoutRes)
        }
        binding?.let {
            it.lifecycleOwner = activity
        }
        return binding!!
    }
}

fun <R : AppCompatActivity, T : ViewDataBinding> contentView(
    @LayoutRes layoutRes: Int
): ContentViewBindingDelegate<R, T> = ContentViewBindingDelegate(layoutRes)

fun <R : BaseActivity, T : ViewDataBinding> BaseActivity.bindingView(): ContentViewBindingDelegate<R, T> =
    ContentViewBindingDelegate(this.layoutResID)
