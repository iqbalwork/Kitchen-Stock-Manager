import SwiftUI

struct ExpiringSoonCard: View {
    var body: some View {
        HStack(alignment: .top, spacing: 16) {
            ZStack {
                Circle()
                    .fill(AppColors.expiringSoon)
                    .frame(width: 40, height: 40)
                
                Image(systemName: "exclamationmark.triangle.fill")
                    .foregroundColor(.white)
            }
            
            VStack(alignment: .leading, spacing: 4) {
                Text("Items Expiring Soon")
                    .font(.system(size: 16, weight: .bold))
                    .foregroundColor(AppColors.warningText)
                
                Text("3 items will expire within the next 48 hours. Consider using them today.")
                    .font(.system(size: 14))
                    .foregroundColor(AppColors.warningText)
                    .lineLimit(2)
                
                Button(action: {}) {
                    Text("Review Items →")
                        .font(.system(size: 14, weight: .bold))
                        .foregroundColor(AppColors.warningText)
                }
                .padding(.top, 4)
            }
        }
        .padding(16)
        .background(AppColors.warningContainer)
        .cornerRadius(16)
    }
}
