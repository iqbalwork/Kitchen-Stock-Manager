import SwiftUI

struct CategoryIcon: View {
    let systemName: String
    let backgroundColor: Color
    let iconColor: Color
    
    var body: some View {
        ZStack {
            Circle()
                .fill(backgroundColor)
                .frame(width: 48, height: 48)
            
            Image(systemName: systemName)
                .resizable()
                .scaledToFit()
                .frame(width: 24, height: 24)
                .foregroundColor(iconColor)
        }
    }
}
