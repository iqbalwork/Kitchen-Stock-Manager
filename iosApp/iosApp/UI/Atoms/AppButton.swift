import SwiftUI

struct AppButton: View {
    let title: String
    let icon: String?
    let action: () -> Void
    
    init(title: String, icon: String? = nil, action: @escaping () -> Void) {
        self.title = title
        self.icon = icon
        self.action = action
    }
    
    var body: some View {
        Button(action: action) {
            HStack(spacing: 8) {
                if let icon = icon {
                    Image(systemName: icon)
                }
                Text(title)
                    .fontWeight(.bold)
            }
            .frame(maxWidth: .infinity)
            .padding()
            .background(AppColors.primary)
            .foregroundColor(.white)
            .cornerRadius(12)
        }
    }
}
