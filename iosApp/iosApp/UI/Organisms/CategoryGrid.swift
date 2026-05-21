import SwiftUI

struct CategoryItemData: Identifiable {
    let id = UUID()
    let name: String
    let icon: String
    let color: Color
}

struct CategoryGrid: View {
    let categories = [
        CategoryItemData(name: "Spices", icon: "leaf.fill", color: Color(hex: 0xFFF9E6)),
        CategoryItemData(name: "Grains", icon: "Drop.fill", color: Color(hex: 0xFFF9E6)),
        CategoryItemData(name: "Fruits", icon: "applelogo", color: Color(hex: 0xFFFFE5E5)),
        CategoryItemData(name: "Vegetables", icon: "carrot.fill", color: Color(hex: 0xFFE5F9F0))
    ]
    
    let columns = [
        GridItem(.flexible(), spacing: 12),
        GridItem(.flexible(), spacing: 12)
    ]
    
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("Categories")
                .font(.system(size: 20, weight: .bold))
            
            LazyVGrid(columns: columns, spacing: 12) {
                ForEach(categories) { category in
                    VStack(spacing: 8) {
                        CategoryIcon(systemName: category.icon, backgroundColor: category.color, iconColor: .darkGray)
                        Text(category.name)
                            .font(.system(size: 14, weight: .medium))
                    }
                    .frame(maxWidth: .infinity)
                    .frame(height: 100)
                    .background(AppColors.surface)
                    .cornerRadius(16)
                    .shadow(color: Color.black.opacity(0.05), radius: 2, x: 0, y: 2)
                }
            }
        }
    }
}

extension Color {
    static let darkGray = Color(light: Color(white: 0.3), dark: Color(white: 0.7))
}
