import SwiftUI

struct AppColors {
    static var primary: Color {
        Color(light: Color(hex: 0x2D6A4F), dark: Color(hex: 0x74D6A1))
    }
    
    static var background: Color {
        Color(light: Color(hex: 0xF8F9FA), dark: Color(hex: 0x121212))
    }
    
    static var surface: Color {
        Color(light: Color(hex: 0xFFFFFF), dark: Color(hex: 0x1E1E1E))
    }
    
    static var onSurface: Color {
        Color(light: .black, dark: .white)
    }

    static let primaryContainer = Color(hex: 0xD8F3DC)
    static let secondary = Color(hex: 0xF4D35E)
    static let tertiary = Color(hex: 0xE63946)
    static let lowStock = Color(hex: 0x9B8E1A)
    static let expiringSoon = Color(hex: 0xE63946)
    static let warningContainer = Color(light: Color(hex: 0xFFFFE5E5), dark: Color(hex: 0x3D0000))
    static let warningText = Color(light: Color(hex: 0x8B0000), dark: Color(hex: 0xFFFFB4B4))
}

extension Color {
    init(hex: UInt, alpha: Double = 1) {
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xff) / 255,
            green: Double((hex >> 08) & 0xff) / 255,
            blue: Double((hex >> 00) & 0xff) / 255,
            opacity: alpha
        )
    }
    
    init(light: Color, dark: Color) {
        self.init(uiColor: UIColor { traitCollection in
            traitCollection.userInterfaceStyle == .dark ? UIColor(dark) : UIColor(light)
        })
    }
}
