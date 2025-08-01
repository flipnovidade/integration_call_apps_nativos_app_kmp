package br.com.kmp.demo.demo.location.uitl

import br.com.kmp.demo.demo.location.delegate.PermissionDelegate
import br.com.kmp.demo.demo.location.model.Permission
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

internal fun KoinComponent.getPermissionDelegate(permission: Permission): PermissionDelegate {
    val permissionDelegate by inject<PermissionDelegate>(named(permission.name))
    return permissionDelegate
}
