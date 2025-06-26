package br.com.kmp.demo.demo

import platform.UIKit.UIDevice

class IOSPlatform : Platform {
//    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val name: String = "ios"
}

actual fun getPlatform(): Platform = IOSPlatform()