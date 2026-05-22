import SwiftUI
import SharedLogic

@main
struct iOSApp: App {
    init() {
        KoinHelperKt.doInitKoinIos()
    }

    var body: some Scene {
        WindowGroup {
            MainTabView()
        }
    }
}
